package com.ChasingtheLight.util;

import lombok.Data; // 或者手动添加getter/setter

@Data // 使用lombok的@Data注解自动生成getter/setter
public class Result {
    private int code;
    private String message;
    private Object data;

    // 无参构造函数（必须，JSON反序列化需要）
    public Result() {}

    // 有参构造函数
    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 静态方法保持不变
    public static Result success() {
        return new Result(200, "成功", null);
    }

    public static Result success(Object data) {
        return new Result(200, "成功", data);
    }

    public static Result fail(int code, String message) {
        return new Result(code, message, null);
    }

    // 如果不使用@Data，需要手动添加getter/setter
    // public int getCode() { return code; }
    // public void setCode(int code) { this.code = code; }
    // public String getMessage() { return message; }
    // public void setMessage(String message) { this.message = message; }
    // public Object getData() { return data; }
    // public void setData(Object data) { this.data = data; }
}