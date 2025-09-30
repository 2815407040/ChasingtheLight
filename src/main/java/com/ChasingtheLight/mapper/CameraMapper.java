package com.ChasingtheLight.mapper;

import com.ChasingtheLight.VO.CameraVO;
import com.ChasingtheLight.entity.Camera;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CameraMapper {
    @Select("select camera_id,brand,model,price,image_url,pixel,video_res,stabilization,design_style from camera "+
    "order by love desc limit 20")
    List<CameraVO> selectCameraByLove();

    @Select("SELECT camera_id,brand,model,price,image_url,pixel,video_res,stabilization,design_style " +
            "FROM camera " +
            "WHERE LOWER(brand) LIKE CONCAT('%', LOWER(#{searchTerm}), '%') " +
            "OR LOWER(model) LIKE CONCAT('%', LOWER(#{searchTerm}), '%')")
    List<CameraVO> selectCameraBySearch(@Param("searchTerm") String searchTerm);

    @Update("update camera set love = love + 1 where camera_id = #{cameraId}")
    void addCameraLove(int cameraId);

    @Update("update camera set love = love - 1 where camera_id = #{cameraId}")
    void reduceCameraLove(int cameraId);
}
