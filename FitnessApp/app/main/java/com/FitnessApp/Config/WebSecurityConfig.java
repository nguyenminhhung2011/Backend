package com.FitnessApp.Config;

import com.FitnessApp.Security.CustomAuthenticationProvider;
import com.FitnessApp.Security.CustomEntryPoint;
import com.FitnessApp.Filters.JwtFilter;
import com.FitnessApp.Security.JwtAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

	private final CustomAuthenticationProvider authProvider;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	private final CorsConfiguration corsConfiguration;
	final JwtFilter jwtFilter;

	public WebSecurityConfig(CustomAuthenticationProvider authProvider, JwtAccessDeniedHandler jwtAccessDeniedHandler, CorsConfiguration corsConfiguration, JwtFilter jwtFilter) {
		this.authProvider = authProvider;
		this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
		this.corsConfiguration = corsConfiguration;
		this.jwtFilter = jwtFilter;
	}

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
//		return new LoginUrlAuthenticationEntryPoint("https://baomoi.com/");
		return new CustomEntryPoint("Lỗi đăng nhập", 401);
	}

	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.authenticationProvider(authProvider);
		return authenticationManagerBuilder.build();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) {
		try
		{
			return
// 				http.csrf().disable().cors().disable().and().headers().frameOptions().disable().and()
// http// Tắt CORS
			http
// .cors().disable() // Tắt CORS
					// Config CORS
					.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource( request -> corsConfiguration))
					.csrf(AbstractHttpConfigurer::disable) // Tắt CSRF
					.authorizeHttpRequests(auth -> {

						auth.requestMatchers("/auth/**").permitAll();
						auth.anyRequest().permitAll();

					})
					.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
					.authenticationManager(authenticationManager(http))
					.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
					.exceptionHandling(exception -> exception.accessDeniedHandler(jwtAccessDeniedHandler))
					.exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint()))
					.build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static final String[] whiteListedRoutes = new String[]{
			"/auth/**",
	};

}
