package com.votehaeduo.controller;

import com.votehaeduo.dto.request.VoteSaveRequestDto;
import com.votehaeduo.dto.response.VoteResponseDto;
import com.votehaeduo.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/votes")
public class VoteController {

    private final VoteService voteService;

    //투표 등록
    @PostMapping
    public VoteResponseDto save(@RequestBody VoteSaveRequestDto voteSaveRequestDto) {
        return voteService.save(voteSaveRequestDto);
    }

    //투표 전체 조회
    @GetMapping
    public List<VoteResponseDto> findAll() { //페이지를 리턴 하는게 맞음
        return voteService.findAll();
    }

    //투표 삭제
    @DeleteMapping("/{voteId}")
    public void delete(@PathVariable("voteId") Long id) {
        voteService.delete(id);
    }

}
