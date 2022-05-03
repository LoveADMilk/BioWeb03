package com.bio.virusInfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = "com.bio")
//@EnableDiscoveryClient
public class ServiceVirusApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceVirusApplication.class, args);
    }
}
