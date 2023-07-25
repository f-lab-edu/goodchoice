package com.flab.goodchoicecoupon.domain;

public class CouponCalculatorDiscount implements CouponCalculator {

    private final int price;
    private final int couponValue;

    public CouponCalculatorDiscount(int price, int couponValue) {
        this.price = price;
        this.couponValue = couponValue;
    }

    @Override
    public int discountPriceCalculation() {
        double resultValue = couponValue / 100.0;
        return (int) Math.ceil(price * resultValue);
    }

    @Override
    public int useCalculation() {
        double resultValue = (100 - couponValue) / 100.0;
        return (int) Math.ceil(price * resultValue);
    }

    @Override
    public int usedCancelCalculation() {
        double resultValue = 100.0 / (100 - couponValue);
        return (int) Math.ceil(price * resultValue);
    }
}
