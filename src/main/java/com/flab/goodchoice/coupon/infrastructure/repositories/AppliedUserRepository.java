package com.flab.goodchoice.coupon.infrastructure.repositories;

public interface AppliedUserRepository {

    Long addRedisSet(String key, String memberId);
}
