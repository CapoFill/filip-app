package com.satisfyyourcuriosity.filipapp.web;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import static org.springframework.security.config.Customizer.withDefaults;

public class WebSecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// @formatter:off
        http
        	// This does not work, suspecting I might need to set up an Anonymous-user? 
            .authorizeHttpRequests((authz) -> authz
            	.requestMatchers("/", "/index", "/leaderboard", "/error", "/webjars/**").permitAll()
                
            	)
            // Returns 403 Forbidden, cookie has been attempted disabled and does not fix anything. Outdated? 
            .logout(l -> l
                    .logoutSuccessUrl("/").permitAll()
                )
            .csrf(c -> c
                  .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            	)
            .exceptionHandling(e -> e
                    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
            .oauth2Login().and()
            .httpBasic(withDefaults());
        return http.build();
     // @formatter:on
	}
}
