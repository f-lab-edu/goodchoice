package com.flab.goodchoice.common.config;

import com.flab.goodchoice.common.filter.AuthenticationFilter;
import com.flab.goodchoice.common.filter.FilterAuthConfig;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class FilterConfig {

    private final FilterAuthConfig filterAuthConfig;

    public FilterConfig(FilterAuthConfig filterAuthConfig) {
        this.filterAuthConfig = filterAuthConfig;
    }

    @Bean
    public FilterRegistrationBean<Filter> authenticationFilter() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new AuthenticationFilter(filterAuthConfig));
        filterFilterRegistrationBean.setOrder(1);
        filterFilterRegistrationBean.addUrlPatterns("/api/coupons/*");
        return filterFilterRegistrationBean;
    }
}
