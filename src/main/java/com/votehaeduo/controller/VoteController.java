package com.votehaeduo.controller;

import com.votehaeduo.dto.request.*;
import com.votehaeduo.dto.response.FindVoteResponse;
import com.votehaeduo.dto.response.CreateVoteResponse;
import com.votehaeduo.dto.response.UpdateVoteResponse;
import com.votehaeduo.dto.response.VotingResponse;
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

    // 투표 등록
    @PostMapping
    public CreateVoteResponse create(@RequestBody @Valid CreateVoteRequest createVoteRequest) {
        return voteService.create(createVoteRequest);
    }

    // 투표 전체 조회
    @GetMapping
    public List<FindVoteResponse> findAll() {
        return voteService.findAll();
    }

    // 투표 상세 조회
    @GetMapping("/{voteId}")
    public FindByIdVoteResponse findById(@PathVariable("voteId") Long id) {
        return voteService.findById(id);
    }

    // 투표 수정
    @PutMapping("/{voteId}")
    public UpdateVoteResponse update(@PathVariable("voteId") Long id,
                                     @RequestBody UpdateVoteRequest updateVoteRequest) {
        return voteService.update(id, updateVoteRequest);
    }

    // 투표 삭제
    @DeleteMapping("/{voteId}")
    public boolean delete(@PathVariable("voteId") Long id) {
        return voteService.delete(id);
    }

    // 투표하기
    @PostMapping("/{voteId}/vote-items") // url 수정했음
    public VotingResponse voting(@PathVariable("voteId") Long id,
                                 @RequestBody VotingRequest votingRequest) {
        return voteService.voting(id, votingRequest);
    }

    // 댓글 등록
    @PostMapping("/{voteId}/vote-comments") // url 수정했음
    public CreateCommentResponse createComment(@PathVariable("voteId") Long id,
                                               @RequestBody @Valid CreateCommentRequest createCommentRequest) {
        return voteService.createComment(id, createCommentRequest);
    }

    // 댓글 삭제
    @DeleteMapping("/{voteId}/comments/{commentId}") // url 수정했음
    public boolean deleteComment(@PathVariable("voteId") Long voteId,
                                 @PathVariable("commentId") Long commentId,
                                 @RequestBody DeleteCommentRequest deleteCommentRequest) {
        return voteService.deleteComment(voteId, commentId, deleteCommentRequest);
    }

    // 팀 매칭
    @PostMapping("/{voteId}/vote-teams")
    public CreateTeamResponse createTeam(@PathVariable("voteId") Long voteId,
                                               @RequestBody CreateTeamRequest createTeamRequest) {
        return voteService.createTeam(voteId, createTeamRequest);
    }

    // 팀 조회
    @GetMapping("/{voteId}/vote-teams")
    public FindAllTeamByVoteResponse findAllTeamByVote(@PathVariable("voteId") Long voteId) {
        return voteService.findAllTeamByVote(voteId);
    }

}
