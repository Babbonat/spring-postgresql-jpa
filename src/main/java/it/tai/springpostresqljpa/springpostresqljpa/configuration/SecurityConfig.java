package it.tai.springpostresqljpa.springpostresqljpa.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Slf4j
public class SecurityConfig{
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		http
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
						.anyRequest().authenticated()/*
						.requestMatchers(HttpMethod.GET, "/api/tutorials/{tutorialId}/comments", "/api/comments/{commentId}")
						.hasAuthority("SCOPE_read")
						.requestMatchers(HttpMethod.POST, "/api/tutorials/{tutorialId}/comments")
						.hasAuthority("SCOPE_write")
						.requestMatchers(HttpMethod.PUT, "/api/comments/{commentId}")
						.hasAuthority("SCOPE_write")
						.requestMatchers(HttpMethod.DELETE, "/api/comments/{commentId}", "/api/tutorials/{tutorialId}/comments")
						.hasAuthority("SCOPE_write").anyRequest().authenticated()*/
				)
				.oauth2ResourceServer()
				.jwt();
		return http.build();
	}

}