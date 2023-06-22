package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.Member;

public interface MemberCommand {
    Member save(Member member);
}
