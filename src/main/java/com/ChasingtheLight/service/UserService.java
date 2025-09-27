package com.ChasingtheLight.service;

import com.ChasingtheLight.entity.User;
import com.ChasingtheLight.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class UserService {

    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
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

        // 所有校验通过，插入用户数据
        userMapper.insertUser(user);
    }

}
