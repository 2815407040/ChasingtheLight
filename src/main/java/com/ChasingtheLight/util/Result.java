package com.ChasingtheLight.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result implements Serializable {
    private Integer code;
    private String message;
    private Object data;

    // 默认构造方法：默认成功状态
    public Result() {
        this.code = 200;
        this.message = "操作成功";
    }

    // 用于错误处理（仅状态码和消息）
    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    // 用于成功返回数据（默认消息）
    public Result(Object data) {
        this(); // 调用默认构造方法设置code=200和默认消息
        this.data = data;
    }

    // 完整构造方法（状态码+消息+数据）
    public Result(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 成功返回（带数据和自定义消息）
    public static Result success(Object data, String message) {
        return new Result(200, message, data);
    }

    // 系统错误（指定状态码和消息）
    public static Result error(Integer code, String message) {
        return new Result(code, message);
    }

    // 系统错误（默认状态码402）
    public static Result error(String message) {
        return new Result(402, message);
    }

    // 成功返回数据（默认消息）
    public static <T> Result success(T data) {
        return new Result(data);
    }

    // 成功返回（无数据，默认消息）
    public static Result success() {
        return new Result();
    }

    // 操作失败（指定状态码和消息）
    public static Result fail(int code, String message) {
        return new Result(code, message);
    }
}