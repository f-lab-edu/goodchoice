package com.flab.goodchoiceapi.common.config;

import com.flab.goodchoiceapi.common.AuthorizationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class FilterConfig {

    private final VaultCredential vaultCredential;

    public FilterConfig(VaultCredential vaultCredential) {
        this.vaultCredential = vaultCredential;
    }

    @Bean
    public FilterRegistrationBean<Filter> authorizationFilter() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new AuthorizationFilter(vaultCredential));
        filterFilterRegistrationBean.setOrder(1);
        filterFilterRegistrationBean.addUrlPatterns("/api/coupons/*", "/api/v1/items/*", "/api/v1/orders/*");
        return filterFilterRegistrationBean;
    }
}
