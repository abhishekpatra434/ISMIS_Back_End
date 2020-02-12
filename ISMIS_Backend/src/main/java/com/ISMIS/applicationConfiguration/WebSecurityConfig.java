package com.ISMIS.applicationConfiguration;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configurable
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/**").permitAll();
		
		/*
		 * http.cors().configurationSource(new CorsConfigurationSource() {
		 * 
		 * @Override public CorsConfiguration getCorsConfiguration(HttpServletRequest
		 * request) { CorsConfiguration config = new CorsConfiguration();
		 * config.setAllowedHeaders(Collections.singletonList("*"));
		 * config.setAllowedMethods(Collections.singletonList("*"));
		 * config.addAllowedOrigin("*"); config.setAllowCredentials(true); return
		 * config; } });
		 */
		
		/*.antMatchers("/admin**").hasAnyRole("ADMIN")
        .anyRequest()
        .authenticated()*/
		/*.antMatchers("/admin/**").hasAuthority("ADMIN")
		.anyRequest()
        .authenticated()
				.and().formLogin().defaultSuccessUrl("/admin/")

				.and().logout().logoutUrl("/logout");*/

				//.and() .exceptionHandling().accessDeniedPage("/logout");
		 
	}

	/*@Bean
	public UserDetailsService userDetailsService() {
		return new MyUserDetailsService();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService());
	}*/

}
