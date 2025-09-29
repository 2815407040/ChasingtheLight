package com.ChasingtheLight.VO;

import lombok.Data;

@Data
public class UserVO {
    private String userName;
    private Integer userId;
    private String password;
    private String phoneNumber;
    private String email;
    private Integer status;
}