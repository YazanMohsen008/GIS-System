package com.fiteprojects.fitegis;

import com.fiteprojects.fitegis.Utils.Services.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@RestController
public class FiteGisApplication {


    public static void main(String[] args) {
        SpringApplication.run(FiteGisApplication.class, args);
    }

    @Autowired
    InitService initService;

    @Bean
    CommandLineRunner commandLineRunner(){
        return args -> {
            TimeZone.setDefault(TimeZone.getTimeZone("Asia/Damascus"));
            initService.init();
        };
    }
    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }

}
