package com.tminto;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 吴员外
 * @date 2022/10/5 15:33
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.tminto")
@MapperScan("com.tminto.mapper")
public class UcenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class, args);
    }

}
