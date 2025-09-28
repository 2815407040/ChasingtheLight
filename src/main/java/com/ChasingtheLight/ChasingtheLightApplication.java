package com.ChasingtheLight;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan
@ServletComponentScan
public class ChasingtheLightApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChasingtheLightApplication.class, args);
    }

}
