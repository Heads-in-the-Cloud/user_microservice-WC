package com.ss.utopia.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ss.utopia.api.jwt.JwtConfig;
import com.ss.utopia.api.jwt.JwtTokenVerifier;
import com.ss.utopia.api.jwt.JwtUsernameAndPasswordAuthenticationFilter;

import javax.crypto.SecretKey;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final JwtConfig jwtConfig;
	private final SecretKey secretKey;
	private final PasswordEncoder passwordEncoder;

	private final UserDetailsService userDetailsService;

	@Autowired
	public SecurityConfiguration(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService,
			JwtConfig jwtConfig, SecretKey secretKey) {
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
		this.jwtConfig = jwtConfig;
		this.secretKey = secretKey;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		System.out.println("insider security config");
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(userDetailsService);

		return provider;
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		System.out.println("configure HTTP");

		httpSecurity.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.addFilter(
						new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
				.addFilterAfter(new JwtTokenVerifier(jwtConfig, secretKey),
						JwtUsernameAndPasswordAuthenticationFilter.class)
				.authorizeRequests().antMatchers("/user/admin/**").hasRole("ADMIN").antMatchers("/user/traveler/**")
				.hasAnyRole("ADMIN", "TRAVELER").antMatchers("/login", "/user/**").permitAll().anyRequest()
				.authenticated();

/* Uncomment the following for form login*/
//			httpSecurity.authorizeRequests().antMatchers("/user/admin/**").hasRole("ADMIN").antMatchers("/user/traveler/**").hasAnyRole("ADMIN", "USER")
//			.antMatchers("/user/**").permitAll().and().formLogin();

	}

}
