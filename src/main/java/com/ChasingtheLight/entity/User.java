package com.ChasingtheLight.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 894911391307000682L;

    private Integer userId;

    private String userName;

    private String password;

    private String email;

    private String phoneNumber;
}
