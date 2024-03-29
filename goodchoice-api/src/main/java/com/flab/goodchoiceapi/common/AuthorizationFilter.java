package com.flab.goodchoiceapi.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.flab.goodchoiceapi.common.config.VaultCredential;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AuthorizationFilter implements Filter {

    final private VaultCredential vaultCredential;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("---필터 인스턴스 초기화---");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        try {
            String apiKey = req.getHeader("x-api-key");
            if (!vaultCredential.getKey().equals(apiKey)) {
                throw new RuntimeException("invalid api key");
            }

            String requestURI = req.getRequestURI();
            log.info("---Request(" + requestURI + ") 필터---");
            chain.doFilter(request, response);
            log.info("---Response(" + requestURI + ") 필터---");
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("AuthorizationFilter is applied");
        }

    }

    @Override
    public void destroy() {
        log.info("---필터 인스턴스 종료---");
    }

}
