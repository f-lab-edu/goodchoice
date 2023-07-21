package com.flab.goodchoiceapi.coupon.infrastructure;

import com.flab.goodchoiceapi.coupon.infrastructure.repositories.AppliedUserRepository;

public class FakeAppliedUserRepository implements AppliedUserRepository {
    @Override
    public Long addRedisSet(String key, String memberId) {
        return null;
    }
}
