package com.ChasingtheLight.service;

import com.ChasingtheLight.VO.UserVO;
import com.ChasingtheLight.entity.User;
import com.ChasingtheLight.mapper.UserMapper;
import com.ChasingtheLight.util.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder; // 注入Bcrypt加密器
    private final EmailUtils emailUtils;

    @Autowired
    public UserService(UserMapper userMapper, EmailUtils emailUtils) {
        this.userMapper = userMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.emailUtils = emailUtils;
    }
    @Transactional
    public void insertUser(User user) {
        // 检查用户名是否已存在
        Integer userNameCount = userMapper.countByUserName(user.getUserName());
        if (userNameCount != null && userNameCount > 0) {
            throw new RuntimeException("用户名已存在");
        }

        // 检查邮箱是否已存在
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            Integer emailCount = userMapper.countByEmail(user.getEmail());
            if (emailCount != null && emailCount > 0) {
                throw new RuntimeException("邮箱已存在");
            }
        }

        // 检查手机号是否已存在
        if (user.getPhoneNumber() != null && !user.getPhoneNumber().isEmpty()) {
            Integer phoneCount = userMapper.countByPhoneNumber(user.getPhoneNumber());
            if (phoneCount != null && phoneCount > 0) {
                throw new RuntimeException("手机号已存在");
            }
        }

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        // 设置初始状态为未激活
        user.setStatus(0);

        // 生成激活码
        String activationCode = UUID.randomUUID().toString().replaceAll("-", "");
        user.setActivationCode(activationCode);

        // 设置创建时间
        user.setCreateTime(new Date());

        // 所有校验通过，插入用户数据
        userMapper.insertUser(user);

        // 发送激活邮件
        emailUtils.sendActivationEmail(user.getEmail(), user.getUserName(), activationCode);
    }

    // 激活用户账号
    public boolean activateUser(String activationCode) {
        // 查找未激活且激活码匹配的用户
        User user = userMapper.findByActivationCode(activationCode);
        if (user == null) {
            return false; // 激活码无效或已使用
        }

        // 检查激活链接是否过期（24小时内有效）
        long now = System.currentTimeMillis();
        long createTime = user.getCreateTime().getTime();
        if (now - createTime > 24 * 60 * 60 * 1000) {
            return false; // 链接已过期
        }

        // 更新用户状态为已激活
        user.setStatus(1);
        user.setActivationTime(new Date());
        user.setActivationCode(null); // 激活后清空激活码
        userMapper.updateUserStatus(user);
        return true;
    }
    public UserVO checkUser(User user) {
        // 1. 先根据用户名查询数据库中的用户信息（需要修改UserMapper的checkUser方法SQL）
        UserVO dbUser = userMapper.checkUserByUsername(user.getUserName());
        if (dbUser == null) {
            return null; // 用户名不存在
        }

        // 检查用户是否已激活
        if (dbUser.getStatus() != null && dbUser.getStatus() == 0) {
            throw new RuntimeException("账号未激活，请先激活邮箱");
        }

        // 2. 使用jbcrypt验证密码（核心修改）
        String rawPassword = user.getPassword();
        String encryptedPassword = dbUser.getPassword();
        if (BCrypt.checkpw(rawPassword, encryptedPassword)) {
            return dbUser; // 密码匹配，返回用户信息
        }
        return null; // 密码不匹配
    }
}
