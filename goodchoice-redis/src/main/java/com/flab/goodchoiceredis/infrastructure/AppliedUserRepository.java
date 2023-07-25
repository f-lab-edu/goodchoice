package com.flab.goodchoiceredis.infrastructure;

public interface AppliedUserRepository {

    Long addRedisSet(String key, String memberId);
}
