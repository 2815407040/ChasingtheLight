package com.ChasingtheLight.entity;

import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 894911391307000682L;

    private Integer userId;
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
    private Integer status; // 0-未激活 1-已激活
    private String activationCode; // 激活码
    private Date activationTime; // 激活时间
    private Date createTime; // 创建时间
}