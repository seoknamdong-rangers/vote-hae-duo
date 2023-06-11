package com.votehaeduo.service;

import com.votehaeduo.dto.MemberPayload;
import com.votehaeduo.dto.request.CreateCommentRequestDto;
import com.votehaeduo.dto.request.DeleteCommentRequestDto;
import com.votehaeduo.dto.request.UpdateVoteRequestDto;
import com.votehaeduo.dto.request.VotingRequestDto;
import com.votehaeduo.dto.response.*;
import com.votehaeduo.dto.request.CreateVoteRequestDto;
import com.votehaeduo.entity.Comment;
import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import com.votehaeduo.exception.comment.CommentNotFoundException;
import com.votehaeduo.exception.date.InvalidEndDateException;
import com.votehaeduo.exception.vote.VoteNotFoundException;
import com.votehaeduo.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final MemberService memberService;

    // 투표 등록
    @Transactional
    public CreateVoteResponse create(CreateVoteRequestDto createVoteRequestDto) {
        MemberPayload memberPayload = memberService.findById(createVoteRequestDto.getCreatedMemberId());
        return CreateVoteResponse.of(voteRepository.save(createVoteRequestDto.toEntity()), memberPayload);
    }

    // 투표 전체조회
    @Transactional(readOnly = true)
    public List<FindVoteResponse> findAll() {
        return voteRepository.findAll().stream()
                .map(vote -> FindVoteResponse.of(vote, (long) vote.getVoteItems().stream()
                        .map(VoteItem::getMemberIds)
                        .flatMap(Set::stream)
                        .collect(Collectors.toSet())
                        .size()))
                .collect(Collectors.toList());
    }

    // 투표 상세조회
    @Transactional
    public FindByIdVoteResponse findById(Long id) {
        Vote vote = voteRepository.findById(id).orElseThrow(VoteNotFoundException::new);
        return FindByIdVoteResponse.of(vote, (long) vote.getVoteItems().stream()
                .map(VoteItem::getMemberIds)
                .flatMap(Set::stream)
                .collect(Collectors.toSet())
                .size());
    }

    // 투표 수정
    @Transactional
    public UpdateVoteResponse update(Long id, UpdateVoteRequestDto updateVoteRequestDto) {
        Vote vote = voteRepository.findById(id).orElseThrow(VoteNotFoundException::new);

        // 수정하려고 하는 vote 종료 되었는지 확인
        if (LocalDate.now().isAfter(vote.getEndDate())) {
            throw new InvalidEndDateException();
        }

        if (updateVoteRequestDto.getTitle() != null) {
            vote.setTitle(updateVoteRequestDto.getTitle());
        }
        if (updateVoteRequestDto.getEndDate() != null) {
            // 요청으로 들어온 endDate 현재 날짜 이후인지 확인
            if (LocalDate.now().isAfter(updateVoteRequestDto.getEndDate())) {
                throw new InvalidEndDateException();
            }
            vote.setEndDate(updateVoteRequestDto.getEndDate());
        }
        if (updateVoteRequestDto.getVoteItems() != null) {
            List<VoteItem> items = updateVoteRequestDto.getVoteItems().stream()
                    .map(voteItemPayload -> voteItemPayload.toEntity(vote))
                    .collect(Collectors.toList());
            vote.setVoteItems(items);
        }

        // 수정일자로 StartDate 수정
        vote.setStartDate(LocalDate.now());

        return UpdateVoteResponse.from(voteRepository.save(vote));
    }

    // 투표 삭제
    @Transactional
    public boolean delete(Long id) {
        Vote vote = voteRepository.findById(id).orElseThrow(VoteNotFoundException::new);
        voteRepository.delete(vote);
        return true;
    }

    // 투표하기
    @Transactional
    public VotingResponse voting(Long id, VotingRequestDto votingRequestDto) {
        Vote vote = voteRepository.findById(id).orElseThrow(VoteNotFoundException::new);

        // 날짜 유효성 검사
        if (LocalDate.now().isAfter(vote.getEndDate())) {
            throw new InvalidEndDateException();
        }

        // 투표하기 기능의 투표 정보
        votingRequestDto.getVoteItemId().forEach(itemId ->
                vote.getVoteItems().stream()
                        .filter(voteItem -> voteItem.getId().equals(itemId))
                        .findFirst()
                        .ifPresent(voteItem -> voteItem.addMember(votingRequestDto.getMemberId())));
        voteRepository.save(vote);

        // 투표 참여 전체 인원 id
        Set<Long> voteMemberIds = vote.getVoteParticipantsTotalMemberIds();

        return VotingResponse.of(vote, voteMemberIds);
    }

    // 댓글 등록
    @Transactional
    public CreateCommentResponse createComment(Long id, CreateCommentRequestDto createCommentRequestDto) {
        Vote vote = voteRepository.findById(id).orElseThrow(VoteNotFoundException::new);

        // 날짜 유효성 검사
        if (LocalDate.now().isAfter(vote.getEndDate())) {
            throw new InvalidEndDateException();
        }
        MemberPayload memberPayload = memberService.findById(createCommentRequestDto.getMemberId());

        // 닉네임 넣어주기
        Comment comment = createCommentRequestDto.toEntity();
        comment.setNickname(memberPayload.getNickname());

        // 댓글 등록
        vote.addComments(comment);
        vote = voteRepository.save(vote);

        return CreateCommentResponse.from(vote.getLastComment());
    }

    // 댓글 삭제
    @Transactional
    public boolean deleteComment(Long voteId, Long commentId, DeleteCommentRequestDto deleteCommentRequestDto) {
        Vote vote = voteRepository.findById(voteId).orElseThrow(VoteNotFoundException::new);

        // 사용자가 작성한 댓글이 있는지 확인
        boolean hasUserComment = vote.getComments().stream()
                .anyMatch(comment -> comment.getMemberId().equals(deleteCommentRequestDto.getMemberId()));
        if (!hasUserComment) {
            throw new CommentNotFoundException();
        }

        // 삭제하려는 댓글 ID가 존재하는지 확인
        boolean commentExists = vote.getComments().stream()
                .anyMatch(comment -> comment.getId().equals(commentId));
        if (!commentExists) {
            throw new CommentNotFoundException();
        }

        vote.deleteComment(commentId);
        voteRepository.save(vote);
        return true;
    }

}
