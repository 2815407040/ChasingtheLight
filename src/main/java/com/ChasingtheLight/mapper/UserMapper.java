package com.ChasingtheLight.mapper;

import com.ChasingtheLight.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    // 检查用户名是否存在
    Integer countByUserName(@Param("userName") String userName);

    // 检查邮箱是否存在
    Integer countByEmail(@Param("email") String email);

    // 检查手机号是否存在
    Integer countByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    // 插入用户信息
    void insertUser(User user);
}
