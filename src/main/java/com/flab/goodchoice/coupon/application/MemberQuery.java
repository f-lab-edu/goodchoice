package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.Member;

public interface MemberQuery {
    Member findById(Long memberId);
}
