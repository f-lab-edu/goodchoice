package com.flab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.flab.goodchoicecoupon", "com.flab.goodchoiceitem", "com.flab.goodchoicemember", "com.flab.goodchoiceorder", "com.flab.goodchoiceapi"})
@EntityScan({"com.flab.goodchoicecoupon", "com.flab.goodchoiceitem", "com.flab.goodchoicemember", "com.flab.goodchoiceorder"})
@EnableJpaRepositories(basePackages = {"com.flab.goodchoicecoupon", "com.flab.goodchoiceitem", "com.flab.goodchoicemember", "com.flab.goodchoiceorder"})
public class GoodchoiceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodchoiceApiApplication.class, args);
    }

}
