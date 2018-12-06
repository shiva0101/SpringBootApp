/*package com.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	@Override
 	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/user/get/**")
		.hasAnyRole("user");
 		
 	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("shiva").password("admin").roles("user");
	}
	
 	@Override
 	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
 		
 		// enable in memory based authentication with a user named "user" and "admin"
 		auth.inMemoryAuthentication().withUser("user").password("admin").roles("user")
 		.and().withUser("admin").password("password").roles("ADMIN");
 		
 	}

 	// Possibly more overridden methods ...

}
*/