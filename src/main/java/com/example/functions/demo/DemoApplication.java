package com.example.functions.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.context.MessageRoutingCallback;
import org.springframework.cloud.function.context.config.RoutingFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@SpringBootApplication
public class DemoApplication {
    @Autowired
    RoutingFunction route;
    public static void main(String[] args) throws Exception {
        SpringApplication.run(DemoApplication.class, args); }

    @Bean
    public MessageRoutingCallback customRouter() {
        return new MessageRoutingCallback() {
            @Override
            public String functionDefinition(Message<?> message) {
                System.out.println(message.getHeaders().toString());
               Object obj= message.getHeaders().get("function_name");
               if(obj instanceof List){
                 return   ((List<?>) obj).get(0).toString();
               }
                return (String) obj;
            }
        };
    }

    @Bean
    public Function<Message<String>, Object> enter() {
        System.out.println("enter1");
       return value -> {
           System.out.println(value.getHeaders().toString());
           return route.apply(MessageBuilder.withPayload(value.getPayload()).setHeader("function_name", "concat").build());}
    ;}


    @Bean
    public Function<Message<String>, String> concat() {
        System.out.println("concat1");
        return value -> value.getPayload().concat("dd"); }
//
//    @Bean
//    public Function<Message<String>, String> lowerCase() {
//        System.out.println("lowerCase1");
//        return value -> value.getPayload().toLowerCase();
//    }

//    @Autowired
//    private BeanFactoryMessageChannelDestinationResolver resolver;

//    @ResponseStatus(HttpStatus.ACCEPTED)
//    @PostMapping(path = "/{target}")
//    public ResponseEntity<?> send(@RequestBody String body, @PathVariable("target") String target) {
////        route.apply(MessageBuilder.withPayload(body).setHeader("function_name", target).build());
//        System.out.println("test");
//       return ResponseEntity.ok( route.apply(MessageBuilder.withPayload(body).setHeader("function_name", target).build())) ;
//    }

//
//curl -X POST localhost:8080 -d "Hello" -H "function_name: concat" -H "Content-Type: text/plain"
//curl -X POST https://us-central1-my-project-228500.cloudfunctions.net/demo -d "Hello" -H "function_name: concat" -H "Content-Type: text/plain"


}
