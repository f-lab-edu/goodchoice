package com.flab.goodchoice.coupon.domain;

import java.math.BigDecimal;

public enum CouponType {
    DISCOUNT("% 할인") {
        @Override
        BigDecimal calculation(BigDecimal price, int discountValue) {
            BigDecimal resultValue = BigDecimal.valueOf((100 - discountValue) / 100);
            return price.multiply(resultValue);
        }
    },
    DEDUCTION("가격 할인") {
        @Override
        BigDecimal calculation(BigDecimal price, int couponValue) {
            return price.subtract(BigDecimal.valueOf(couponValue));
        }
    };

    private final String name;

    CouponType(String name) {
        this.name = name;
    }

    abstract BigDecimal calculation(BigDecimal price, int couponValue);
}
