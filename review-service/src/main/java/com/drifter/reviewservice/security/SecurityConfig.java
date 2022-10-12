package com.drifter.reviewservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	@Bean
	public MapReactiveUserDetailsService userDetailsService() {
		UserDetails user = User.withDefaultPasswordEncoder()
				// assuming a system user with user:password as creds
				.username("user")
				.password("password")
				.roles("USER")
				.build();
		return new MapReactiveUserDetailsService(user);
	}

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		http
				//.csrf(csrf -> csrf.csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse()))
				.csrf()
				.disable();

		http
				.securityMatcher(new PathPatternParserServerWebExchangeMatcher("/review/**"))
				.authorizeExchange()
				.pathMatchers(HttpMethod.POST, "/review/**")
				.hasRole("USER")
				.pathMatchers(HttpMethod.DELETE, "/review/**")
				.hasRole("USER")
				.pathMatchers(HttpMethod.PUT, "/review/**")
				.hasRole("USER")
				.pathMatchers("/review/**").permitAll()
				.and()
				.httpBasic(Customizer.withDefaults())
				.formLogin(Customizer.withDefaults());
		return http.build();
	}
}