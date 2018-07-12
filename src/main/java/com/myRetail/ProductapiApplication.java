package com.myRetail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.myRetail.productsapi")
@SpringBootApplication
public class ProductapiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductapiApplication.class, args);
    }
}
