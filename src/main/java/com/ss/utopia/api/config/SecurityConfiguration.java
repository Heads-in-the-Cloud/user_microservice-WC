package com.ss.utopia.api.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//import com.ss.utopia.api.filter.JwtFilter;

@EnableWebSecurity
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;

//	@Autowired
//	JwtFilter jwtFilter;

	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("inside SecurityConfig");
		auth.userDetailsService(userDetailsService);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {

		return super.authenticationManagerBean();
	}

	@Bean
	public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
	    return new MySimpleUrlAuthenticationSuccessHandler();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		System.out.println("configure HTTP");
		httpSecurity.csrf().disable();
		httpSecurity.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN").antMatchers("/agent/**").hasAnyRole("AGENT", "ADMIN")
				.antMatchers("/user").hasAnyRole("ADMIN", "AGENT", "TRAVELER").antMatchers("/booking", "/airline", "user").permitAll();

		//httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		httpSecurity.formLogin().loginProcessingUrl("/login")
		.successHandler(myAuthenticationSuccessHandler());
		//httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

	}

//		protected void configure(HttpSecurity http) throws Exception {
//	        //Below line will allow any authenticated user to access any resource within the system
//	        http.authorizeRequests().antMatchers("/api/global").permitAll()
//	                .antMatchers("/api/public").hasAuthority("ACCESS_PUBLIC")
//	                .antMatchers("/api/private").hasAnyAuthority("ACCESS_PRIVATE")
//	                .and().httpBasic();
//		.antMatchers("lms/user/**").hasAuthority("user").antMatchers("lms/guest/**).permitAll();

//	httpSecurity.csrf().disable().authorizeRequests().antMatchers("/login").permitAll().anyRequest()
//	.authenticated().and().exceptionHandling().and().sessionManagement()
//	.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().formLogin();
//httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

}
