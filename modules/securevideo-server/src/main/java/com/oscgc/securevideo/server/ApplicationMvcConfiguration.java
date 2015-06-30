package com.oscgc.securevideo.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.oscgc.securevideo.server.interceptor.UserAuthenticationInterceptor;

/**
 * Created by cengruilin on 15/5/7.
 */

@EnableWebMvc
@Configuration
@ComponentScan("com.oscgc.securevideo.server.mvc")
public class ApplicationMvcConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/static/**")
                .addResourceLocations("/static/");
    }

    @Bean(name = "jspViewResolver")
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/pages/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        super.addInterceptors(registry);

        registry.addInterceptor(new UserAuthenticationInterceptor()).addPathPatterns("/**").excludePathPatterns("/static/**");
    }
}
