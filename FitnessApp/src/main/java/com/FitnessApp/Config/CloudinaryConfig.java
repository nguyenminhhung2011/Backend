package com.FitnessApp.Config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class CloudinaryConfig {
    @Value("${com.FitnessApp.cloudinary.api-secret}")
    private String apiSecret;

    @Value("${com.FitnessApp.cloudinary.api-key}")
    private String apiKey;

    @Value("${com.FitnessApp.cloudinary.cloud-name}")
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
