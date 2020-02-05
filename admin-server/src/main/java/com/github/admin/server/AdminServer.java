package com.github.admin.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.Arrays;

/**
 * @Description: 启动类
 * @Author: kevin
 * @Date: 2019/11/1 15:16
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.github.foundation","com.github.admin"})
@MapperScan("com.github.admin.server.dao")
public class AdminServer {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(AdminServer.class, args);
        Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
    }
}
