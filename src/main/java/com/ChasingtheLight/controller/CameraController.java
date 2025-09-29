package com.ChasingtheLight.controller;

import com.ChasingtheLight.VO.CameraVO;
import com.ChasingtheLight.entity.Camera;
import com.ChasingtheLight.service.CameraService;
import com.ChasingtheLight.service.UserService;
import com.ChasingtheLight.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/camera")
public class CameraController {
    @Autowired
    private CameraService cameraService;


    @GetMapping("/popular")
    public Result selectCameraByLove(){
        List<CameraVO> cameras = cameraService.selectCameraByLove();
        return Result.success(cameras);
    }
}
