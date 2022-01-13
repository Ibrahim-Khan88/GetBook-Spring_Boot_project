package com.security.webconfing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.security.jwt.AuthEntryPointJwt;
import com.security.jwt.TokenFilter;

@EnableWebSecurity
@Configuration
@EnableWebMvc
public class Webconfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods("*").maxAge(3600L)
//				.allowedHeaders("*").exposedHeaders("Authorization").allowCredentials(true);
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());

		// http.cors().disable();

		http.csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/me", "/login", "/signup", "/confirm-account", "/givebook/**", "/delete/**", "/search/**",
						"/testapi/**", "/book", "/testapii/**", "/book/userr/**", "/book/user/**")
				.permitAll().antMatchers("/hellow").hasAnyRole("ADMIN")
				.antMatchers("/book/user/delete/**", "/test").hasAnyRole("USER")
				// .and().httpBasic();
				.anyRequest().authenticated();

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encodedPassword());
	}

	@Bean
	public PasswordEncoder encodedPassword() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public TokenFilter authenticationJwtTokenFilter() {
		return new TokenFilter();
	}

}
