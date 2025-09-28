package com.ChasingtheLight.VO;

import lombok.Data;

@Data
public class UserVO {
    private String userName;
    private Integer userId;
    private Integer roleId;
    private  String roleName;
    private String password;
}