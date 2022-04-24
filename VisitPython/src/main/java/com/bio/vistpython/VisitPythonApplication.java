package com.bio.vistpython;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

//没有用到数据库，通过该注解接触此类的autoconfig
//@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableScheduling
@SpringBootApplication
public class VisitPythonApplication {
    public static void main(String[] args) {
        SpringApplication.run(VisitPythonApplication.class, args);
    }
}
