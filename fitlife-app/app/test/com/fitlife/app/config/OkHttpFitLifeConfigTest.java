package com.fitlife.app.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@SpringBootTest
class OkHttpFitLifeConfigTest {

    @Autowired
    Cloudinary cloudinary;

    @Test
    public void _connectToCloudinary() {
        Map params = ObjectUtils.asMap(
                "folder", "fitLife/data",
                "resource_type", "raw"
        );

        try {
            String result = cloudinary.downloadFolder("fitLife/data", params);
            System.out.println(result);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }

}