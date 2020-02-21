package com.virtualpairprogrammers.roombooking.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class RestSecurityConfig extends  WebSecurityConfigurerAdapter{

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
 	@Autowired
 	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
 		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
 		
 		// enable in memory based authentication with a user named "user" and "admin"
// 		.inMemoryAuthentication()
// 			.withUser("matt").password("{noop}secret").authorities("ROLE_ADMIN");
 	}

 	@Override
 	protected void configure(HttpSecurity http) throws Exception {
 		http.csrf().disable()
 			.authorizeRequests()
 			.antMatchers(HttpMethod.OPTIONS, "/api/basicAuth/**").permitAll()
 			.antMatchers("/api/basicAuth/**").hasAnyRole("ADMIN","USER")
 			.antMatchers(HttpMethod.GET, "/api/rooms/**").hasRole("USER")
 			.and().httpBasic();
 		
 		
 		
		http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/api/users/**").hasRole("ADMIN")
		.and()
		.addFilter(new JWTAuthorizationFilter(authenticationManager()));
 				
 	}


}
