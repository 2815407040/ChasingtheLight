package com.ChasingtheLight.entity;

import lombok.Data;

import java.io.Serial;
import java.util.Date;

@Data
public class Camera {
    @Serial
    private static final long serialVersionUID = 894911391307000682L;

    private Integer cameraId;
    private String brand;
    private String model;
    private Integer price;
    private String imgUrl;
    private String pixel;
    private String videoRes;
    private String stabilization;
    private String design_style;
    private Integer category_id;
    private Integer stock;
    private Character status;
    private Date createTime;
    private Date updateTime;
    private Integer love;


}

