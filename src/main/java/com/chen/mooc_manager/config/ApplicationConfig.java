package com.chen.mooc_manager.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    /**
     * 分页插件
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
