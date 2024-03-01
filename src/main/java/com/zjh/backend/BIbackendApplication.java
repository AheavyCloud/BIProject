package com.zjh.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

// MapperScan()用于扫描Mapper文件夹
@MapperScan("com.zjh.backend.mapper")
@SpringBootApplication
@ServletComponentScan
public class BIbackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BIbackendApplication.class, args);
    }

}
