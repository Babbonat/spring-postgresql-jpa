package it.tai.springpostresqljpa.springpostresqljpa.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig{ //extends WebSecurityConfigurerAdapter {

	/*@Override
	protected void configure(HttpSecurity http) throws Exception {// @formatter:off
		http
				.authorizeRequests()
				.antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
				.antMatchers(HttpMethod.GET, "/user/info", "/api/foos/**")
				.hasAuthority("SCOPE_read")
				.antMatchers(HttpMethod.POST, "/api/foos")
				.hasAuthority("SCOPE_write")
				.anyRequest()
				.authenticated()
				.and()
				.oauth2ResourceServer()
				.jwt();
	}*/

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		http
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/tutorials/{tutorialId}/comments", "/api/comments/{commentId}")
						.hasAuthority("SCOPE_read")
						.requestMatchers(HttpMethod.POST, "/api/tutorials/{tutorialId}/comments")
						.hasAuthority("SCOPE_write")
						.requestMatchers(HttpMethod.PUT, "/api/comments/{commentId}")
						.hasAuthority("SCOPE_update")
						.requestMatchers(HttpMethod.DELETE, "/api/comments/{commentId}", "/api/tutorials/{tutorialId}/comments")
						.hasAuthority("SCOPE_delete").anyRequest().authenticated()
				)
				.oauth2ResourceServer().jwt();
		return http.build();
	}

}