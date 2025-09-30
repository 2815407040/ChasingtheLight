package com.ChasingtheLight.service;

import com.ChasingtheLight.VO.CameraVO;
import com.ChasingtheLight.entity.Camera;
import com.ChasingtheLight.mapper.CameraMapper;
import com.ChasingtheLight.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CameraService {
    private final CameraMapper cameraMapper;
    @Autowired
            public CameraService(CameraMapper cameraMapper) {
        this.cameraMapper = cameraMapper;
    }


    public List<CameraVO> selectCameraByLove(){
        return cameraMapper.selectCameraByLove();
    }

    public List<CameraVO> selectCameraBySearch(String searchTerm){return cameraMapper.selectCameraBySearch(searchTerm);}

    public void addCameraLove(int cameraId){
        cameraMapper.addCameraLove(cameraId);
    }

    public void reduceCameraLove(int cameraId){
        cameraMapper.reduceCameraLove(cameraId);
    }
}
