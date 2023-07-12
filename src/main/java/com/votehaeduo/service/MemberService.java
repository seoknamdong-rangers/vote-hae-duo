package com.votehaeduo.service;

import com.votehaeduo.dto.MemberPayload;
import com.votehaeduo.dto.request.CreateMemberRequest;
import com.votehaeduo.dto.response.CreateMemberResponse;
import com.votehaeduo.exception.member.MemberNotFoundException;
import com.votehaeduo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<MemberPayload> findAllById(List<Long> memberIds){
        return memberRepository.findAllById(memberIds).stream()
                .map(MemberPayload::from)
                .collect(Collectors.toList());
    }

}
