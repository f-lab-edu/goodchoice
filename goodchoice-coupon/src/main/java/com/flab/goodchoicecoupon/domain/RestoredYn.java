package com.flab.goodchoicecoupon.domain;

import lombok.Getter;

@Getter
public class RestoredYn {
    private final boolean restoredYn;

    public RestoredYn(boolean restoredYn) {
        this.restoredYn = restoredYn;
    }
}
