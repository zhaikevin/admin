package com.github.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Description: 启动类
 * @Author: kevin
 * @Date: 2019/11/1 15:16
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.github.foundation","com.github.admin"})
@MapperScan("com.github.admin.dao")
public class AdminServer {

    public static void main(String[] args) {
        SpringApplication.run(AdminServer.class, args);
    }
}
