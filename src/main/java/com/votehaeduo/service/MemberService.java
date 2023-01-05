package com.votehaeduo.service;

import com.votehaeduo.dto.request.CreateMemberRequest;
import com.votehaeduo.dto.response.CreateMemberResponse;
import com.votehaeduo.entity.Member;
import com.votehaeduo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public CreateMemberResponse save(CreateMemberRequest request) {
        return CreateMemberResponse.from(memberRepository.save(request.toEntity()));
    }

}
