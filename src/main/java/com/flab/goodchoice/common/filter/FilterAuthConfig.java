package com.flab.goodchoice.common.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter // properties, yml 파일을 읽기 위해서는 setter 메소드 필요
@Configuration
@ConfigurationProperties(prefix = "app.auth")
public class FilterAuthConfig {
    private String key;
}
