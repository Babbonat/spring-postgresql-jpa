package it.tai.springpostresqljpa.springpostresqljpa;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class SpringPostresqlJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringPostresqlJpaApplication.class, args);
    }
}
