package com.ChasingtheLight.service;

import com.ChasingtheLight.VO.UserVO;
import com.ChasingtheLight.entity.User;
import com.ChasingtheLight.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class UserService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder; // 注入Bcrypt加密器

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
        this.passwordEncoder = new BCryptPasswordEncoder(); // 初始化加密器
    }

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
        // 所有校验通过，插入用户数据
        userMapper.insertUser(user);
    }

    public UserVO checkUser(User user) {
        // 1. 先根据用户名查询数据库中的用户信息（需要修改UserMapper的checkUser方法SQL）
        UserVO dbUser = userMapper.checkUserByUsername(user.getUserName());
        if (dbUser == null) {
            return null; // 用户名不存在
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
