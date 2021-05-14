package com.example.functions.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.MessageRoutingCallback;
import org.springframework.cloud.function.context.config.RoutingFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.core.BeanFactoryMessageChannelDestinationResolver;
import org.springframework.messaging.core.DestinationResolver;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@SpringBootApplication
//@Controller
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
                return (String) message.getHeaders().get("function_name");
            }
        };
    }

    @Bean
    public Function<String, Object> enter() {
        System.out.println("enter1");
       return value -> route.apply(MessageBuilder.withPayload(value+"payload").setHeader("function_name", "concat").build()); }


    @Bean
    public Function<String, String> concat() {
        System.out.println("concat1");
        return value -> value.concat("dd"); }

    @Bean
    public Function<Message<String>, String> lowerCase() {
        System.out.println("lowerCase1");
        return value -> value.getPayload().toLowerCase();
    }

//    @Autowired
//    private BeanFactoryMessageChannelDestinationResolver resolver;

//    @ResponseStatus(HttpStatus.ACCEPTED)
//    @PostMapping(path = "/{target}")
//    public ResponseEntity<?> send(@RequestBody String body, @PathVariable("target") String target) {
////        route.apply(MessageBuilder.withPayload(body).setHeader("function_name", target).build());
//        System.out.println("test");
//       return ResponseEntity.ok( route.apply(MessageBuilder.withPayload(body).setHeader("function_name", target).build())) ;
//    }

//curl -H "Content-Type: text/plain" localhost:8080 -d "Hello" -H "function_name: lowerCase"
//curl -H "Content-Type: multipart/form-data" localhost:8080 -d "Hello" -H "function_name: multipart"
//curl -H "Content-Type: text/plain" https://us-central1-my-project-228500.cloudfunctions.net/demo -d "Hello" -H "function_name: concat"
//curl -H "Content-Type: text/plain" https://us-central1-my-project-228500.cloudfunctions.net/demo -d "Hello" -H "function_name: lowerCase"


}
