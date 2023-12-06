package com.fitlife.app.Config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Value(value = "${com.fitlife.cloudinary.api-secret}")
    private String apiSecret;

    @Value("${com.fitlife.cloudinary.api-key}")
    private String apiKey;

    @Value("${com.fitlife.cloudinary.cloud-name}")
    private String cloudName;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(
                ObjectUtils.asMap("api_secret", apiSecret,
                        "cloud_name", cloudName,
                        "api_key", apiKey,
                        "secure", true));
    }
}
