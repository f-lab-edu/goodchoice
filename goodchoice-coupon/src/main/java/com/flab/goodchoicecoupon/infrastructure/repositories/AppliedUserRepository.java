package com.flab.goodchoicecoupon.infrastructure.repositories;

public interface AppliedUserRepository {

    Long addRedisSet(String key, String memberId);
}
