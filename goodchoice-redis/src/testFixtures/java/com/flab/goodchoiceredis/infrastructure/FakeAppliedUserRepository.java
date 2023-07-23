package com.flab.goodchoiceredis.infrastructure;

public class FakeAppliedUserRepository implements AppliedUserRepository {
    @Override
    public Long addRedisSet(String key, String memberId) {
        return null;
    }
}
