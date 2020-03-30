package com.chen.mooc_manager.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.chen.mooc_manager.util.ParamUtil;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ParamUtil paramUtil(){
        return new ParamUtil();
    }
}
