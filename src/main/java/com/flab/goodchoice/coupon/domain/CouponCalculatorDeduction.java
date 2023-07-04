package com.flab.goodchoice.coupon.domain;

public class CouponCalculatorDeduction implements CouponCalculator {

    private final int price;
    private final int couponValue;

    public CouponCalculatorDeduction(int price, int couponValue) {
        this.price = price;
        this.couponValue = couponValue;
    }

    @Override
    public int discountPriceCalculation() {
        return couponValue;
    }

    @Override
    public int useCalculation() {
        return (int) Math.ceil( price - couponValue);
    }

    @Override
    public int usedCancelCalculation() {
        return (int) Math.ceil(price + couponValue);
    }
}
