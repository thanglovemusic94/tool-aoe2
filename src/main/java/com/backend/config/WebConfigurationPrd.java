//package com.backend.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.http.HttpMethod;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
////@Profile("prd")
//@Configuration
//public class WebConfigurationPrd implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/api/**")
//                .allowedMethods(HttpMethod.DELETE.name(), HttpMethod.POST.name(),
//                HttpMethod.GET.name(), HttpMethod.HEAD.name(), HttpMethod.OPTIONS.name(), HttpMethod.PUT.name(),
//                HttpMethod.PATCH.name())
//                .allowedOrigins("*");
//    }
//
//}
