package com.ChasingtheLight.controller;

import com.ChasingtheLight.entity.User;
import com.ChasingtheLight.service.UserService;
import com.ChasingtheLight.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result registerUser(@RequestBody User user) {
        try {
            user.setUserName(user.getUserName().trim());
            userService.insertUser(user);
            return Result.success(); // 成功时返回明确的成功标识
        } catch (RuntimeException e) {
            // 捕获用户名已存在的异常（Service层抛出的）
            return Result.fail(500, e.getMessage());
        } catch (Exception e) {
            return Result.fail(500, "注册失败：" + e.getMessage());
        }
    }
}

