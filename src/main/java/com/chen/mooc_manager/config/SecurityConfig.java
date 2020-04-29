package com.chen.mooc_manager.config;

import com.chen.mooc_manager.security.MyAuthenticationFailureHandler;
import com.chen.mooc_manager.security.MyAuthenticationSuccessHandler;
import com.chen.mooc_manager.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MyAuthenticationSuccessHandler successHandler;

    @Autowired
    MyAuthenticationFailureHandler failureHandler;

    @Autowired
    UserServiceImpl userService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/index.html","/index","/","/mailCode","/login","/signup",
                "/course/index","/course/listBy","/course/show","/comment/courseComment","/upload/uploadCallback","/endpointWisely/**",
                "/statics/**","/admin/**","/js/**","/css/**","/img/**","/libs/**").permitAll()
                .anyRequest().authenticated();

        http.headers().frameOptions().sameOrigin();
        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .permitAll()
                .and().logout().permitAll().invalidateHttpSession(true)
                .deleteCookies("JSESSIONID").logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) ->
                    httpServletResponse.sendRedirect("/index")
                );
                    /*(httpServletRequest, httpServletResponse, e) -> {
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    out.write("{\"status\":500,\"message\":\"登录失败\"}");
                    out.flush();
                    out.close();}*/
    }
}
