package com.photowey.mybatis.in.action;

import com.photowey.mybatis.in.action.annotation.EnablePersistence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnablePersistence
@SpringBootApplication
public class Mybatis {

    public static void main(String[] args) {
        SpringApplication.run(Mybatis.class, args);
    }

}
