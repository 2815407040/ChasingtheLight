package com.ChasingtheLight.controller;

import com.ChasingtheLight.VO.UserVO;
import com.ChasingtheLight.entity.User;
import com.ChasingtheLight.mapper.UserMapper;
import com.ChasingtheLight.service.UserService;
import com.ChasingtheLight.util.JwtUtils;
import com.ChasingtheLight.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/register")
    public Result registerUser(@RequestBody User user) {
        try {
            user.setUserName(user.getUserName().trim());
            userService.insertUser(user);
            return Result.success(null, "注册成功，请查收邮件并激活账号");
        } catch (RuntimeException e) {
            return Result.fail(500, e.getMessage());
        } catch (Exception e) {
            return Result.fail(500, "注册失败：" + e.getMessage());
        }
    }

    // 激活账号接口
    @GetMapping("/activate")
    public Result activateAccount(@RequestParam String code) {
        try {
            boolean success = userService.activateUser(code);
            if (success) {
                return Result.success(null, "账号激活成功，请登录");
            } else {
                // 更具体的错误提示
                User user = userMapper.findByActivationCode(code);
                if (user == null) {
                    return Result.fail(400, "激活码无效或已使用");
                } else {
                    long now = System.currentTimeMillis();
                    long createTime = user.getCreateTime().getTime();
                    if (now - createTime > 24 * 60 * 60 * 1000) {
                        return Result.fail(400, "激活链接已过期，请重新注册");
                    }
                    return Result.fail(400, "激活失败，请稍后重试");
                }
            }
        } catch (Exception e) {
            return Result.fail(500, "激活失败：" + e.getMessage());
        }
    }

    // UserController.java 中修正登录接口
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        UserVO userVO = userService.checkUser(user);
        if (userVO != null) {
            // 生成token并返回
            String token = JwtUtils.generateToken(userVO.getUserId(), userVO.getUserName());
            return Result.success(token);
        }
        return Result.error("用户名或密码错误");
    }
}

