package com.ChasingtheLight.controller;

import com.ChasingtheLight.VO.UserVO;
import com.ChasingtheLight.entity.User;
import com.ChasingtheLight.service.UserService;
import com.ChasingtheLight.util.JwtUtils;
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

    // UserController.java 中修正登录接口
    @PostMapping("/login")  // 添加斜杠，保持路径规范
    public Result login(@RequestBody User user) {
        // 打印接收的参数，用于调试
        System.out.println("接收的用户名：" + user.getUserName() + "，密码：" + user.getPassword());

        UserVO foundUser = userService.checkUser(user);
        if (foundUser != null) {
            String token = JwtUtils.generateToken(foundUser.getUserId(), foundUser.getUserName());
            return Result.success(token);
        } else {
            return Result.fail(401, "用户名或密码错误");
        }
    }
}

