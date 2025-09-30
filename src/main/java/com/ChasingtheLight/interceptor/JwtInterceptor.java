package com.ChasingtheLight.interceptor;

import com.ChasingtheLight.util.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 获取请求头中的token
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // 截取"Bearer "之后的部分
        }
        // 2. 验证token（排除登录/注册接口）
        String path = request.getRequestURI();
        if ("/user/login".equals(path) || "/user/register".equals(path)|| "/camera/popular".equals(path)) {
            return true;
        }
        // 3. 验证token有效性
        if (token == null || token.isEmpty()) {
            response.setStatus(401);
            response.getWriter().write("未登录，请先登录");
            return false;
        }

        try {
            Claims claims = JwtUtils.verifyToken(token);
            // 将用户信息存入请求域，方便后续接口使用
            request.setAttribute("userId", claims.get("userId"));
            request.setAttribute("userName", claims.getSubject());
            return true; // 验证通过，放行
        } catch (Exception e) {
            response.setStatus(401);
            response.getWriter().write(e.getMessage());
            return false;
        }
    }
}