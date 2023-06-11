package com.flab.goodchoice.coupon.infrastructure.repositories;

import java.util.UUID;

public interface AppliedUserRepository {

    Long addRedisSet(UUID key, Long memberId);
}
