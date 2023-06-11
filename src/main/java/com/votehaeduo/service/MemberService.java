package com.votehaeduo.service;

import com.votehaeduo.dto.MemberPayload;
import com.votehaeduo.dto.request.CreateMemberRequest;
import com.votehaeduo.dto.response.CreateMemberResponse;
import com.votehaeduo.exception.member.MemberNotFoundException;
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

    public MemberPayload findById(Long id) {
        return MemberPayload.from(memberRepository.findById(id).orElseThrow(MemberNotFoundException::new));
    }

}
