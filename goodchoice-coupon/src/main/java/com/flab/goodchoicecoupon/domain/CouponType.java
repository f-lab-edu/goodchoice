package com.flab.goodchoicecoupon.domain;

public enum CouponType {
    DISCOUNT("% 할인") {
        @Override
        public CouponCalculator couponCalculator(int price, int couponValue) {
            return new CouponCalculatorDiscount(price, couponValue);
        }
    },
    DEDUCTION("가격 할인") {
        @Override
        public CouponCalculator couponCalculator(int price, int couponValue) {
            return new CouponCalculatorDeduction(price, couponValue);
        }
    };

    private final String name;

    CouponType(String name) {
        this.name = name;
    }

    public abstract CouponCalculator couponCalculator(int price, int couponValue);
}
