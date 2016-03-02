package com.knowledgepoker.security;

import com.knowledgepoker.controller.interceptor.SocialInterceptorUtil;
import com.knowledgepoker.controller.interceptor.SocialProfileInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by starke on 11.02.2016.
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    private SocialInterceptorUtil socialInterceptorUtil;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/403").setViewName("403");
        registry.addViewController("/impressum").setViewName("impressum");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(new SocialProfileInterceptor(socialInterceptorUtil));
    }

    @Autowired
    public void setSocialInterceptorUtil(SocialInterceptorUtil socialInterceptorUtil) {
        this.socialInterceptorUtil = socialInterceptorUtil;
    }
}
