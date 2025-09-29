package com.ChasingtheLight.mapper;

import com.ChasingtheLight.VO.CameraVO;
import com.ChasingtheLight.entity.Camera;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CameraMapper {
    @Select("select camera_id,brand,model,price,image_url,pixel,video_res,stabilization,design_style from camera "+
    "order by love desc limit 20")
    List<CameraVO> selectCameraByLove();
}
