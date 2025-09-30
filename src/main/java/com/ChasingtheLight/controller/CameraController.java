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
        int cameraId = params.get("cameraId");
        Integer userId = (Integer) request.getAttribute("userId");
        cameraService.addCameraLove(cameraId);
        return Result.success(); // 添加返回值
    }

    @PostMapping("/reduceCameralike")
    public Result redeceCameraLike(@RequestBody Map<String, Integer> params, HttpServletRequest request) {
        int cameraId = params.get("cameraId");
        Integer userId = (Integer) request.getAttribute("userId");
        cameraService.reduceCameraLove(cameraId);
        return Result.success(); // 添加返回值
    }
}
