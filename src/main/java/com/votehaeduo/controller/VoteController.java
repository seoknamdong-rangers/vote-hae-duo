package com.votehaeduo.controller;

import com.votehaeduo.dto.request.VotingRequestDto;
import com.votehaeduo.dto.response.FindVoteResponseDto;
import com.votehaeduo.dto.response.VoteCreateResponseDto;
import com.votehaeduo.dto.response.VoteResponseDto;
import com.votehaeduo.dto.response.VotingResponseDto;
import com.votehaeduo.dto.request.CreateCommentRequestDto;
import com.votehaeduo.dto.request.DeleteCommentRequestDto;
import com.votehaeduo.dto.request.VoteCreateRequestDto;
import com.votehaeduo.dto.request.VoteUpdateRequestDto;
import com.votehaeduo.dto.response.*;
import com.votehaeduo.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/votes")
public class VoteController {

    private final VoteService voteService;

    //투표 등록
    @PostMapping
    public VoteCreateResponseDto create(@RequestBody @Valid final VoteCreateRequestDto voteCreateRequestDto) {
        return voteService.create(voteCreateRequestDto);
    }

    //투표 전체 조회
    @GetMapping
    public List<FindVoteResponseDto> findAll() {
        return voteService.findAll();
    }

    //투표 상세 조회
    @GetMapping("/{voteId}")
    public FindByIdVoteResponse findById(@PathVariable("voteId") Long id) {
        return voteService.findById(id);
    }

    //투표 수정
    @PutMapping("/{voteId}")
    public VoteResponseDto update(@PathVariable("voteId") Long id,
                                  @RequestBody VoteUpdateRequestDto voteUpdateRequestDto) {
        return voteService.update(id, voteUpdateRequestDto);
    }

    //투표 삭제
    @DeleteMapping("/{voteId}")
    public boolean delete(@PathVariable("voteId") Long id) {
        return voteService.delete(id);
    }

    // 투표하기
    @PostMapping("{voteId}")
    public VotingResponseDto voting(@PathVariable("voteId") Long id,
                                    @RequestBody VotingRequestDto votingRequestDto) {
        return voteService.voting(id, votingRequestDto);
    }

    // 댓글 등록
    @PostMapping("/{voteId}")
    public CreateCommentResponseDto createComment(@PathVariable("voteId") Long id,
                                                  @RequestBody @Valid final CreateCommentRequestDto createCommentRequestDto) {
        return voteService.createComment(id, createCommentRequestDto);
    }

    // 댓글 삭제
    @DeleteMapping("/{voteId}/{commentId}")
    public boolean deleteComment(@PathVariable("voteId") Long voteId,
                                 @RequestBody DeleteCommentRequestDto deleteCommentRequestDto) {
        return voteService.deleteComment(voteId, deleteCommentRequestDto);
    }

}
