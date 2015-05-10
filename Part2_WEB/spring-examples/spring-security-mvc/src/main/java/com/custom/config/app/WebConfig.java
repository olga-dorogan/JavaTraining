package com.custom.config.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * Created by olga on 09.05.15.
 */
@Configuration
@EnableWebMvc
@ComponentScan({"com.custom.controller", "com.custom.config"})
public class WebConfig {
    @Bean
    public InternalResourceViewResolver getResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setSuffix(".jsp");
        viewResolver.setPrefix("WEB-INF/views/");
        viewResolver.setViewClass(JstlView.class);
        return viewResolver;
    }
}
