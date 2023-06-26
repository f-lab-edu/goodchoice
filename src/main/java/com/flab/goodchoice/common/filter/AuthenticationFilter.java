package com.flab.goodchoice.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.goodchoice.common.exception.BaseException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AuthenticationFilter implements Filter {

    private final static String key = "auth_key";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        try {
            String authenticationKey = httpServletRequest.getHeader("Authentication");

            if (!key.equals(authenticationKey)) {
                log.info("다른 인증키 입력");
                throw new BaseException("401", "인증되지 않은 key 입니다.");
            }

            chain.doFilter(request, response);

        } catch (BaseException exception) {
            errorResponse(response, exception);
        }
    }

    private void errorResponse(ServletResponse response, BaseException exception) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        ObjectMapper objectMapper = new ObjectMapper();
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        try {
            response.getWriter().write(objectMapper.writeValueAsString(new AuthenticationError(exception.getErrorCode(), exception.getMessage())));
        } catch (IOException e) {
            log.error("인증 에러 response 변환중 에러 발생");
            throw new RuntimeException(e);
        }
    }

    @Getter
    public static class AuthenticationError {
        private final String code;
        private final String message;

        public AuthenticationError(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
