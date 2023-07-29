package com.flab.goodchoicemember.application;

import com.flab.goodchoicemember.domain.model.Member;
import org.springframework.stereotype.Service;

@Service
public class MemberRetrievalServiceImpl implements MemberRetrievalService {

    private final MemberQuery memberQuery;

    public MemberRetrievalServiceImpl(MemberQuery memberQuery) {
        this.memberQuery = memberQuery;
    }

    @Override
    public Member getMember(Long memberId) {
        return memberQuery.getMember(memberId);
    }
}
