package com.flab.goodchoice.coupon.infrastructure;

import com.flab.goodchoice.coupon.infrastructure.repositories.AppliedUserRepository;

public class FakeAppliedUserRepository implements AppliedUserRepository {
    @Override
    public Long addRedisSet(String key, String memberId) {
        return null;
    }
}
