package com.ChasingtheLight.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

public interface UserCameraLikeMapper {
    @Insert("insert into user_camera_like(user_id, camera_id) values(#{userId}, #{cameraId})")
    void insertByUserAndCamera(int  userId,int cameraId);

    // 删除点赞记录
    @Delete("delete from user_camera_like where user_id = #{userId} and camera_id = #{cameraId}")
    int deleteByUserAndCamera(int  userId,int cameraId);
}
