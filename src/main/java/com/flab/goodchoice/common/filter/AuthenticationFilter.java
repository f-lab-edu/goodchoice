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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class AuthenticationFilter implements Filter {

    private final static String key = "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        try {
            String authenticationKey = httpServletRequest.getHeader("Authentication");
            String encryptKey = toEncrypt(authenticationKey.getBytes());

            if (!key.equals(encryptKey)) {
                log.info("다른 인증키 입력");
                throw new BaseException("401", "인증되지 않은 key 입니다.");
            }

            chain.doFilter(request, response);

        } catch (BaseException exception) {
            errorResponse(response, exception);
        }
    }

    private String toEncrypt(byte[] keys) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            log.error("api key 변환중 에러 발생");
            throw new RuntimeException(e);
        }
        messageDigest.reset();
        byte[] encryptKeys = messageDigest.digest(keys);

        StringBuilder stringBuilder = new StringBuilder();
        for (byte encryptKey : encryptKeys) {
            stringBuilder.append(String.format("%02x", encryptKey));
        }

        return stringBuilder.toString();
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
