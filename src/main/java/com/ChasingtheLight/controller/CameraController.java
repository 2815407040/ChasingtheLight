package com.ChasingtheLight.controller;

import com.ChasingtheLight.VO.CameraVO;
import com.ChasingtheLight.entity.Camera;
import com.ChasingtheLight.service.CameraService;
import com.ChasingtheLight.service.UserService;
import com.ChasingtheLight.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/camera")
public class CameraController {
    @Autowired
    private CameraService cameraService;


    @GetMapping("/popular")
    public Result selectCameraByLove() {
        List<CameraVO> cameras = cameraService.selectCameraByLove();
        return Result.success(cameras);
    }

    @PostMapping("/search")
    public Result selectCameraBySearch(@RequestBody Map<String, String> requestBody) {
        String searchTerm = requestBody.get("keyword");
        List<CameraVO> cameras = cameraService.selectCameraBySearch(searchTerm);
        return Result.success(cameras);
    }

    @PostMapping("/addCameralike")
    public Result addCameraLike(@RequestBody Map<String, Integer> params, HttpServletRequest request) {
        // 检查用户是否已登录
        Integer userId = (Integer) request.getAttribute("userId");
        if (userId == null) {
            return Result.error("请先登录"); // 返回未登录错误
        }

        Integer cameraId = params.get("cameraId");
        if (cameraId == null) {
            return Result.error("相机ID不能为空");
        }

        try {
            cameraService.addCameraLove(userId, cameraId);
            return Result.success("点赞成功");
        } catch (Exception e) {
            return Result.error("点赞失败：" + e.getMessage());
        }
    }

    @PostMapping("/reduceCameralike")
    public Result reduceCameraLike(@RequestBody Map<String, Integer> params, HttpServletRequest request) {
        // 同样添加用户验证
        Integer userId = (Integer) request.getAttribute("userId");
        if (userId == null) {
            return Result.error("请先登录");
        }

        Integer cameraId = params.get("cameraId");
        if (cameraId == null) {
            return Result.error("相机ID不能为空");
        }

        try {
            cameraService.reduceCameraLove(userId, cameraId);
            return Result.success("取消点赞成功");
        } catch (Exception e) {
            return Result.error("取消点赞失败：" + e.getMessage());
        }
    }
}
