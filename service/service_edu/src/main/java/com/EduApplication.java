package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 吴员外
 * @date 2022/9/17 12:26
 */
@SpringBootApplication
@MapperScan("com.tminto.mapper")
@ComponentScan("com.tminto") //扫描common的包
public class EduApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class,args);
    }
}
