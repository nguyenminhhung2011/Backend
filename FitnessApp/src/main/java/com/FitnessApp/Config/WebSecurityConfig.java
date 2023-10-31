package com.FitnessApp.Config;

import com.FitnessApp.Security.CustomAuthenticationProvider;
import com.FitnessApp.Security.CustomEntryPoint;
import com.FitnessApp.Filters.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

	private final CustomAuthenticationProvider authProvider;

	private final PasswordEncoder passwordEncoder;

	final JwtFilter jwtFilter;

	public WebSecurityConfig(CustomAuthenticationProvider authProvider, PasswordEncoder passwordEncoder, JwtFilter jwtFilter) {
		this.authProvider = authProvider;
		this.passwordEncoder = passwordEncoder;
		this.jwtFilter = jwtFilter;
	}

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
//		return new LoginUrlAuthenticationEntryPoint("https://baomoi.com/");
		return new CustomEntryPoint("Lỗi đăng nhập", 401);
	}
//

	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.authenticationProvider(authProvider);
		return authenticationManagerBuilder.build();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) {
		try {
			return
// 				http.csrf().disable().cors().disable().and().headers().frameOptions().disable().and()
// http// Tắt CORS
			http
// .cors().disable() // Tắt CORS
					.cors(AbstractHttpConfigurer::disable)// Tắt CORS
					.csrf(AbstractHttpConfigurer::disable) // Tắt CSRF
					.authorizeHttpRequests(auth -> {

//						auth.requestMatchers("/company/**" ).permitAll();
//						auth.requestMatchers("/getAll" ).hasRole("ADMIN");

						auth.requestMatchers("/**").permitAll();
//						auth.requestMatchers("/test/**").permitAll();

						// auth.requestMatchers("/candidate/**").permitAll();
						auth.requestMatchers("/notification/**").permitAll();
//						auth.requestMatchers("employer/**").hasRole("EMPLOYER");

						auth.requestMatchers("/api/v1/FileUpload/**").permitAll();
						auth.anyRequest().authenticated();
					}).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

					.exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
//						authenticationEntryPoint(authenticationEntryPoint()).
						httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(authenticationEntryPoint());
					})
					.build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
