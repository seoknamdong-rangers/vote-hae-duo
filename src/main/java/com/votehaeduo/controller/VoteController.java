package com.votehaeduo.controller;

import com.votehaeduo.dto.request.VoteSaveRequestDto;
import com.votehaeduo.dto.request.VoteUpdateRequestDto;
import com.votehaeduo.dto.response.VoteResponseDto;
import com.votehaeduo.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/votes")
public class VoteController {

    private final VoteService voteService;

    //투표 등록
    @PostMapping
    public void save(@RequestBody VoteSaveRequestDto voteSaveRequestDto) {
        voteService.save(voteSaveRequestDto);
    }

    //투표 전체 조회
    @GetMapping
    public List<VoteResponseDto> findAll() { //페이지를 리턴 하는게 맞음
        return voteService.findAll();
    }

    //투표 상세 조회
    @GetMapping("/{voteId}")
    public VoteResponseDto findById(@PathVariable("voteId") Long id) {
        return voteService.findById(id);
    }

    //투표 수정
    @PutMapping("/{voteId}")
    public void update(@PathVariable("voteId") Long id, VoteUpdateRequestDto voteUpdateRequestDto) {
        voteService.update(id, voteUpdateRequestDto);
    }

    //투표 삭제
    @DeleteMapping("/{voteId}")
    public void delete(@PathVariable("voteId") Long id) {
        voteService.delete(id);
    }

}
