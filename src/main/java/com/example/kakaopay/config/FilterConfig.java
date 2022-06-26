package com.example.kakaopay.config;

import com.example.kakaopay.filter.RequestInitFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<RequestInitFilter> requestInitFilter() {
        FilterRegistrationBean<RequestInitFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new RequestInitFilter());

        return filterFilterRegistrationBean;
    }
}
