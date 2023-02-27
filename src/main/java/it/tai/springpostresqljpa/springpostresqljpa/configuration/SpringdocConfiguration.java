package it.tai.springpostresqljpa.springpostresqljpa.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringdocConfiguration
{
    @Bean
    public OpenAPI OpenAPIBean()
    {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Rest API").version("0.0.1")
                .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
