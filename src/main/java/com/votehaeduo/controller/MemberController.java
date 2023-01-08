package com.votehaeduo.controller;

import com.votehaeduo.dto.request.CreateMemberRequest;
import com.votehaeduo.dto.response.CreateMemberResponse;
import com.votehaeduo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public CreateMemberResponse create(@RequestBody @Valid CreateMemberRequest request) {
        return memberService.save(request);
    }

}
