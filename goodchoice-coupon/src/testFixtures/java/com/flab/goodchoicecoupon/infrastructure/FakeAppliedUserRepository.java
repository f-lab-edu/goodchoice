package com.flab.goodchoicecoupon.infrastructure;

import com.flab.goodchoicecoupon.infrastructure.repositories.AppliedUserRepository;

public class FakeAppliedUserRepository implements AppliedUserRepository {
    @Override
    public Long addRedisSet(String key, String memberId) {
        return null;
    }
}
