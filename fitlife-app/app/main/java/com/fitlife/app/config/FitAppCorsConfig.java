package com.fitlife.app.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
public class FitAppCorsConfig {
    public static final List<String> allowedHeader = List.of("Authorization","Cache-Control","Content-Type");
    public static final String allowedOrigin = "*";
    public static final List<String> allowedMethods = List.of("GET", "POST", "PUT", "DELETE", "PUT", "OPTIONS", "PATCH", "DELETE");
    public static final boolean allowedCredential = false;
    public static final List<String> exposedHeader = List.of("Authorization");

    @Bean
    CorsConfiguration corsConfiguration(){
        final CorsConfiguration _corsConfiguration = new CorsConfiguration();

        _corsConfiguration.setAllowedHeaders(allowedHeader);
        _corsConfiguration.addAllowedOriginPattern(allowedOrigin);
        _corsConfiguration.setAllowCredentials(allowedCredential);
        _corsConfiguration.setAllowedMethods(allowedMethods);
        _corsConfiguration.setExposedHeaders(exposedHeader);

        return _corsConfiguration;
    }
}
