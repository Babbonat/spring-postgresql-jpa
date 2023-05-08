package it.tai.springpostresqljpa.springpostresqljpa.configuration;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Slf4j
public class SecurityConfig{
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
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
				.oauth2ResourceServer(l -> {
					l.jwt().jwtAuthenticationConverter(grantedAuthoritiesExtractor());
				});
		return http.build();
	}

	private JwtAuthenticationConverter grantedAuthoritiesExtractor() {
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
					String[] roles = new String[0];
					String[] scopes = new String[0];

					if (jwt.getClaims().containsKey("cognito:groups"))
						roles = ((ArrayList<String>) jwt.getClaims().get("cognito:groups")).toArray(new String[0]);

					if (jwt.getClaims().containsKey("scope"))
						scopes = ((String) jwt.getClaims().getOrDefault("scope", "")).split(" ");

					ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();

					for (String role : roles) {
						authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase(Locale.ROOT)));
					}

					for (String scope : scopes) {
						authorities.add(new SimpleGrantedAuthority("SCOPE_" + scope));
					}
					return new HashSet<>(authorities);
				}
		);

		return jwtAuthenticationConverter;
	}
}