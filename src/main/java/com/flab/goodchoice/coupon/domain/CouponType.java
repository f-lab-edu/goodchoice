package com.flab.goodchoice.coupon.domain;

public enum CouponType {
    DISCOUNT("% 할인") {
        @Override
        public int discountPriceCalculation(int price, int couponValue) {
            double resultValue = couponValue / 100.0;

            return (int) Math.ceil(price * resultValue);
        }

        @Override
        public int useCalculation(int price, int couponValue) {
            double resultValue = (100 - couponValue) / 100.0;

            return (int) Math.ceil(price * resultValue);
        }
        @Override
        public int usedCancelCalculation(int price, int couponValue) {
            double resultValue = 100.0 / (100 - couponValue);
            return (int) Math.ceil(price * resultValue);
        }
    },
    DEDUCTION("가격 할인") {
        @Override
        public int discountPriceCalculation(int price, int couponValue) {
            return couponValue;
        }

        @Override
        public int useCalculation(int price, int couponValue) {
            return (int) Math.ceil( price - couponValue);
        }
        @Override
        public int usedCancelCalculation(int price, int couponValue) {
            return (int) Math.ceil(price + couponValue);
        }
    };

    private final String name;

    CouponType(String name) {
        this.name = name;
    }

    public abstract int discountPriceCalculation(int price, int couponValue);
    public abstract int useCalculation(int price, int couponValue);
    public abstract int usedCancelCalculation(int price, int couponValue);
}
