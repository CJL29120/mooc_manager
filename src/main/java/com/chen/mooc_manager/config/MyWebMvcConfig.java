package com.chen.mooc_manager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class MyWebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 解决springboot2.0静态资源无法直接访问问题
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {


        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");

        //--------------------------------------windows下保存路径-------------------------------------------------------------
        //项目图片访问路径
        registry.addResourceHandler("/pictureUpload/project/**").addResourceLocations("file:D:/pictureUpload/project/");

        super.addResourceHandlers(registry);
    }

}
