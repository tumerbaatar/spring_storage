package com.github.tumerbaatar.storage.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@EnableWebMvc
@Configuration
@ComponentScan("com.github.tumerbaatar.storage")
public class WebConfig implements WebMvcConfigurer {
    @Value("${media.folder}")
    private String mediaFolder;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String resourceLocationPrefix = "file://";
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            resourceLocationPrefix = "file:///";
        }

        registry
                .addResourceHandler("/static/**", "/media/**")
                .addResourceLocations("classpath:/static/", resourceLocationPrefix+mediaFolder);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/login").setViewName("login");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("http://localhost:3000");
    }
}

