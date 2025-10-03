package com.ChasingtheLight.service;

import com.ChasingtheLight.VO.CameraVO;
import com.ChasingtheLight.entity.Camera;
import com.ChasingtheLight.mapper.CameraMapper;
import com.ChasingtheLight.mapper.UserCameraLikeMapper;
import com.ChasingtheLight.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CameraService {
    private final CameraMapper cameraMapper;
    @Autowired
            public CameraService(CameraMapper cameraMapper) {
        this.cameraMapper = cameraMapper;
    }

    @Autowired
    private UserCameraLikeMapper likeMapper;

    public List<CameraVO> selectCameraByLove(){
        return cameraMapper.selectCameraByLove();
    }

    public List<CameraVO> selectCameraBySearch(String searchTerm){return cameraMapper.selectCameraBySearch(searchTerm);}

    @Transactional
    public void addCameraLove(int userId,int cameraId){
        cameraMapper.addCameraLove(cameraId);
        likeMapper.insertByUserAndCamera(userId, cameraId);
    }
    @Transactional
    public void reduceCameraLove(int userId,int cameraId){
        likeMapper.deleteByUserAndCamera(userId, cameraId);
        cameraMapper.reduceCameraLove(cameraId);
        }
}
