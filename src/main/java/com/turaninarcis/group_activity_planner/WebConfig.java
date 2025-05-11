package com.turaninarcis.group_activity_planner;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{

    @Value("${upload.dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        Path uploadPath = Paths.get("").toAbsolutePath().resolve(uploadDir);


        System.out.println("Resolved upload path: " + uploadPath.toString());


        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:"+uploadPath.toString()+"/");
    }
}
