package com.flab.goodchoice.coupon.infrastructure;

import com.flab.goodchoice.coupon.infrastructure.repositories.AppliedUserRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FakeAppliedUserRepository implements AppliedUserRepository {
    Set<Long> memberIds = new HashSet<>();

    @Override
    public Long addRedisSet(UUID key, Long memberId) {
        if (memberIds.contains(memberId)) {
            return 0L;
        }

        memberIds.add(memberId);
        return 1L;
    }
}
