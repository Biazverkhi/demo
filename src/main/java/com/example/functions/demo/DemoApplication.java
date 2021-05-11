package com.example.functions.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.function.Function;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

//    @Bean
//    public MessageRoutingCallback customRouter() {
//        return new MessageRoutingCallback() {
//            @Override
//            public String functionDefinition(Message<?> message) {
//                return (String) message.getHeaders().get("func_name");
//            }
//        };
//    }



    @Bean
    public Function<String, String> concat() {
        return value -> value.concat("dd");
    }

    @Bean
    public Function<String, String> lowerCase() {
        return value -> value.toLowerCase();
    }





}
