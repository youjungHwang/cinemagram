package com.photo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Component
public class WebMvcConfig implements WebMvcConfigurer { // web 설정 파일

    @Value("${file.path}")
    private String imageUploadRoute;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        registry
            .addResourceHandler("/upload/**")
            .addResourceLocations("file:///"+imageUploadRoute)
            .setCachePeriod(60*10*6)
            .resourceChain(true)
            .addResolver(new PathResourceResolver());
    }

}
