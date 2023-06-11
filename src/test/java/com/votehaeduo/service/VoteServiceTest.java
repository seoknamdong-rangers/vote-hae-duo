package com.votehaeduo.service;

import com.votehaeduo.dto.*;
import com.votehaeduo.dto.request.*;
import com.votehaeduo.dto.response.*;
import com.votehaeduo.entity.Comment;
import com.votehaeduo.entity.Member;
import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import com.votehaeduo.exception.comment.CommentNotFoundException;
import com.votehaeduo.exception.date.InvalidEndDateException;
import com.votehaeduo.exception.vote.VoteNotFoundException;
import com.votehaeduo.repository.VoteRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private MemberService memberService;

    @InjectMocks
    private VoteService voteService;


    @Test
    @DisplayName("투표 등록")
    void save() {
        // given
        Vote vote = Vote.builder()
                .id(1L)
                .title("6월 8일 풋살 투표")
                .startDate(LocalDate.of(2023, 6, 8))
                .endDate(LocalDate.of(2023, 6, 30))
                .createdMemberId(1L)
                .build();
        List<VoteItem> voteItems = List.of(VoteItem.builder()
                        .id(1L)
                        .title("11시 ~ 1시 실외")
                        .vote(vote)
                        .memberIds(Set.of(1L, 2L))
                        .build(),
                VoteItem.builder()
                        .id(2L)
                        .title("12시 ~ 2시 실내")
                        .vote(vote)
                        .memberIds(Set.of(1L, 2L))
                        .build());
        vote.addItems(voteItems);
        MemberPayload memberPayload = MemberPayload.builder().id(1L).nickname("킴대세").build();
        CreateVoteResponse expectedResult = CreateVoteResponse.of(vote, memberPayload);
        given(voteRepository.save(any())).willReturn(vote);

        Member member = Member.builder().id(1L).kakaoMemberNo("a").nickname("킴대세").profileUrl("b").email("c").build();
        given(memberService.findById(any())).willReturn(MemberPayload.from(Objects.requireNonNull(member)));

        // when
        CreateVoteResponse createVoteResponse = voteService.create(CreateVoteRequestDto.builder()
                .title("6월 8일 풋살 투표")
                .startDate(LocalDate.of(2023, 6, 8))
                .endDate(LocalDate.of(2023, 6, 30))
                .createdMemberId(1L)
                .voteItems(List.of(
                        CreateVoteItem.builder().title("11시 ~ 1시 실외").build(),
                        CreateVoteItem.builder().title("12시 ~ 2시 실내").build())
                ).build());

        // then
        Assertions.assertThat(createVoteResponse).usingRecursiveComparison().isEqualTo(expectedResult);

    }

    @Test
    @DisplayName("투표 전체 조회")
    void findAll() {
        // given
        Long id = new Random().nextLong();
        Vote vote = Vote.builder()
                .id(id)
                .title("12월 15일 풋살 투표")
                .startDate(LocalDate.of(2023, 1, 20))
                .endDate(LocalDate.of(2023, 1, 30))
                .createdMemberId(1L)
                .build();
        List<VoteItem> voteItems = List.of(VoteItem.builder()
                        .id(1L)
                        .title("12 ~ 2 실내")
                        .vote(vote)
                        .memberIds(Set.of(1L, 2L))
                        .build(),
                VoteItem.builder()
                        .id(2L)
                        .title("11 ~ 1 야외")
                        .vote(vote)
                        .memberIds(Set.of(1L, 2L))
                        .build());
        vote.addItems(voteItems);
        List<Vote> votes = List.of(vote);
        given(voteRepository.findAll()).willReturn(votes);
        List<FindVoteResponse> expectedResult = List.of(new FindVoteResponse(id, "12월 15일 풋살 투표",
                LocalDate.of(2023, 1, 20),
                LocalDate.of(2023, 1, 30), 1L,
                List.of(new VoteItemDetails(1L, "12 ~ 2 실내", Set.of(1L, 2L), 2L),
                        new VoteItemDetails(2L, "11 ~ 1 야외", Set.of(1L, 2L), 2L)), 2L)
        );

        // when
        List<FindVoteResponse> voteResponseDto = voteService.findAll();

        // then
        Assertions.assertThat(voteResponseDto).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("투표 상세 조회")
    void findByIdVote() {
        // given
        Long id = new Random().nextLong();
        Vote vote = Vote.builder()
                .id(id)
                .title("12월 15일 풋살 투표")
                .startDate(LocalDate.of(2023, 1, 20))
                .endDate(LocalDate.of(2023, 1, 30))
                .createdMemberId(1L)
                .build();
        List<VoteItem> voteItems = List.of(VoteItem.builder()
                        .id(1L)
                        .title("12 ~ 2 실내")
                        .vote(vote)
                        .memberIds(Set.of(1L, 2L))
                        .build(),
                VoteItem.builder()
                        .id(2L)
                        .title("11 ~ 1 야외")
                        .vote(vote)
                        .memberIds(Set.of(1L, 2L))
                        .build());
        vote.addItems(voteItems);

        Long uniqueCount = (long) vote.getVoteItems().stream()
                .map(VoteItem::getMemberIds)
                .flatMap(Set::stream)
                .collect(Collectors.toSet())
                .size();
        FindByIdVoteResponse expectedUpdateVoteResponse = FindByIdVoteResponse.of(vote, uniqueCount);
        given(voteRepository.findById(any())).willReturn(Optional.of(vote));

        // when
        FindByIdVoteResponse voteResponseDto = voteService.findById(id);

        // then
        Assertions.assertThat(voteResponseDto).usingRecursiveComparison().isEqualTo(expectedUpdateVoteResponse);
    }

    @Test
    @DisplayName("투표 수정")
    void update() {
        // given
        Long id = new Random().nextLong();
        Vote vote = Vote.builder()
                .id(id)
                .title("6월 8일 풋살 투표")
                .startDate(LocalDate.of(2023, 6, 8))
                .endDate(LocalDate.of(2023, 6, 25))
                .createdMemberId(1L)
                .build();
        List<VoteItem> voteItems = List.of(VoteItem.builder()
                        .id(1L)
                        .title("12 ~ 2 실내")
                        .vote(vote)
                        .memberIds(Set.of(1L, 2L))
                        .build(),
                VoteItem.builder()
                        .id(2L)
                        .title("11 ~ 1 야외")
                        .vote(vote)
                        .memberIds(Set.of(3L, 4L))
                        .build());
        vote.addItems(voteItems);
        given(voteRepository.findById(any())).willReturn(Optional.of(vote));
        UpdateVoteResponse expectedUpdateVoteResponse = new UpdateVoteResponse(id, "6월 8일 풋살 투표",
                LocalDate.of(2023, 6, 8),
                LocalDate.of(2023, 6, 25), 1L,
                List.of(new VoteItemDetails(1L, "11시 ~ 1시 실외", Set.of(1L, 2L), 2L),
                        new VoteItemDetails(2L, "12시 ~ 2시 실내", Set.of(3L, 4L), 2L)),
                4L);
        given(voteRepository.save(any())).willReturn(vote);

        // when
        UpdateVoteResponse updateVoteResponse = voteService.update(id, UpdateVoteRequestDto.builder()
                .title("6월 8일 풋살 투표")
                .endDate(LocalDate.of(2023, 6, 25))
                .voteItems(List.of(
                        VoteItemPayload.builder().id(1L).title("11시 ~ 1시 실외").memberIds(Set.of(1L, 2L)).build(),
                        VoteItemPayload.builder().id(2L).title("12시 ~ 2시 실내").memberIds(Set.of(3L, 4L)).build()))
                .build());

        // then
        Assertions.assertThat(updateVoteResponse).usingRecursiveComparison().isEqualTo(expectedUpdateVoteResponse);
    }

    @Test
    @DisplayName("투표 삭제")
    void delete() {
        // given
        given(voteRepository.findById(anyLong())).willReturn(Optional.of(Vote.builder().build()));
        doNothing().when(voteRepository).delete(any());

        // when
        boolean result = voteService.delete(new Random().nextLong());

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("투표 삭제 실패")
    void deleteIfThrow() {
        // given
        given(voteRepository.findById(anyLong())).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> voteService.delete(new Random().nextLong()))
                .isInstanceOf(VoteNotFoundException.class)
                .hasMessage("해당 투표를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("투표 하기")
    void voting() {
        // given
        Set<Long> firstMemberIds = new HashSet<>();
        firstMemberIds.add(1L);
        firstMemberIds.add(2L);
        Set<Long> secondMemberIds = new HashSet<>();
        secondMemberIds.add(1L);
        Long id = new Random().nextLong();
        Vote vote = Vote.builder()
                .id(id)
                .title("6월 8일 풋살")
                .startDate(LocalDate.of(2023, 6, 8))
                .endDate(LocalDate.of(2023, 6, 30))
                .createdMemberId(1L)
                .comments(List.of(Comment.builder().id(1L).build()))
                .build();
        List<VoteItem> voteItems = List.of(VoteItem.builder()
                        .id(1L)
                        .title("11시 ~ 1시 실외")
                        .vote(vote)
                        .memberIds(firstMemberIds)
                        .build(),
                VoteItem.builder()
                        .id(2L)
                        .title("12시 ~ 2시 실내")
                        .vote(vote)
                        .memberIds(secondMemberIds)
                        .build());
        vote.addItems(voteItems);
        given(voteRepository.findById(any())).willReturn(Optional.of(vote));
        VotingResponse expectedVotingResponse = new VotingResponse(id, "6월 8일 풋살",
                LocalDate.of(2023, 6, 8),
                LocalDate.of(2023, 6, 30), 1L,
                List.of(new VoteItemDetails(1L, "11시 ~ 1시 실외", Set.of(1L, 2L), 2L),
                        new VoteItemDetails(2L, "12시 ~ 2시 실내", Set.of(1L, 7L), 2L)),
                List.of(CommentPayload.builder().id(1L).build()), Set.of(1L, 2L, 7L), 3L);

        given(voteRepository.save(any())).willReturn(vote);

        // when
        VotingResponse votingResponse = voteService.voting(id, new VotingRequestDto(7L, List.of(2L)));

        // then
        Assertions.assertThat(votingResponse).usingRecursiveComparison().isEqualTo(expectedVotingResponse);
    }

    @Test
    @DisplayName("투표 하기 실패")
    void BadVoting() {
        // given
        Long voteId = 1L;
        LocalDateTime expiredDateTime = LocalDateTime.now().minusDays(1); // 1일 전에 만료된 투표
        Vote vote = Vote.builder()
                .id(voteId)
                .title("투표 제목")
                .startDate(LocalDate.from(LocalDateTime.now().minusDays(2)))
                .endDate(LocalDate.from(expiredDateTime))
                .build();
        VotingRequestDto requestDto = new VotingRequestDto(1L, List.of(1L, 2L));

        given(voteRepository.findById(voteId)).willReturn(Optional.of(vote));

        // when, then
        assertThatThrownBy(() -> voteService.voting(voteId, requestDto))
                .isInstanceOf(InvalidEndDateException.class)
                .hasMessage("투표 마감일을 초과한 날짜로 진행할 수 없습니다.");
    }

    @Test
    @DisplayName("댓글 등록")
    void insertComment() {
        // given
        Vote findVote = Vote.builder()
                .id(1L)
                .title("6월 8일 풋살 투표")
                .startDate(LocalDate.of(2023, 6, 8))
                .endDate(LocalDate.of(2023, 6, 30))
                .createdMemberId(1L)
                .build();
        given(voteRepository.findById(any())).willReturn(Optional.of(findVote));
        Vote saveVote = Vote.builder()
                .id(1L)
                .title("6월 8일 풋살 투표")
                .startDate(LocalDate.of(2023, 6, 8))
                .endDate(LocalDate.of(2023, 6, 30))
                .createdMemberId(1L)
                .comments(List.of(Comment.builder()
                        .id(1L)
                        .nickname("킴대세")
                        .content("재밌겠다")
                        .memberId(1L)
                        .date(LocalDate.of(2023, 6, 8))
                        .build()))
                .build();
        given(voteRepository.save(any())).willReturn(saveVote);
        CreateCommentResponse expectedResult = CreateCommentResponse.from(saveVote.getLastComment());

        Member member = Member.builder().id(1L).kakaoMemberNo("a").nickname("킴대세").profileUrl("b").email("c").build();
        given(memberService.findById(any())).willReturn(MemberPayload.from(Objects.requireNonNull(member)));

        // when
        CreateCommentResponse createCommentResponse = voteService.createComment(1L, CreateCommentRequestDto.builder()
                .content("재밌겠다")
                .memberId(1L)
                .date(LocalDate.of(2023, 6, 8))
                .build());

        // then
        Assertions.assertThat(createCommentResponse).usingRecursiveComparison().isEqualTo(expectedResult);

    }

    @Test
    @DisplayName("댓글 등록 실패")
    void insertFailedComment() {
        // given
        Long id = new Random().nextLong();
        Vote findVote = Vote.builder()
                .id(id)
                .title("5월 22일 풋살 투표")
                .startDate(LocalDate.of(2023, 4, 10))
                .endDate(LocalDate.of(2023, 4, 20))
                .createdMemberId(1L)
                .build();
        given(voteRepository.findById(any())).willReturn(Optional.of(findVote));
        CreateCommentRequestDto commentRequestDto = new CreateCommentRequestDto();

        // when, then
        assertThatThrownBy(() -> voteService.createComment(id, commentRequestDto))
                .isInstanceOf(InvalidEndDateException.class)
                .hasMessage("투표 마감일을 초과한 날짜로 진행할 수 없습니다.");
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteComment() {
        // given
        Long id = new Random().nextLong();
        List<Comment> comments = new ArrayList<>();
        Comment comment = Comment.builder()
                .id(1L)
                .nickname("킴대세")
                .content("재밌겠다")
                .memberId(1L)
                .date(LocalDate.of(2023, 6, 8))
                .build();
        comments.add(comment);
        Vote findVote = Vote.builder()
                .id(id)
                .title("6월 8일 풋살 투표")
                .startDate(LocalDate.of(2023, 6, 8))
                .endDate(LocalDate.of(2023, 6, 30))
                .createdMemberId(1L)
                .comments(comments)
                .build();
        given(voteRepository.findById(any())).willReturn(Optional.of(findVote));
        Vote saveVote = Vote.builder()
                .id(id)
                .title("6월 8일 풋살 투표")
                .startDate(LocalDate.of(2023, 6, 8))
                .endDate(LocalDate.of(2023, 6, 30))
                .createdMemberId(1L)
                .comments(List.of())
                .build();
        given(voteRepository.save(any())).willReturn(saveVote);

        // when
        boolean result = voteService.deleteComment(id, 1L,
                DeleteCommentRequestDto.builder()
                        .memberId(1L)
                        .build());

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("댓글 삭제 실패")
    void deleteFailedComment() {
        // given
        Long id = new Random().nextLong();
        Vote findVote = Vote.builder()
                .id(id)
                .title("6월 8일 풋살 투표")
                .startDate(LocalDate.of(2023, 6, 8))
                .endDate(LocalDate.of(2023, 6, 20))
                .createdMemberId(1L)
                .build();
        given(voteRepository.findById(any())).willReturn(Optional.of(findVote));
        DeleteCommentRequestDto deleteCommentRequestDto = new DeleteCommentRequestDto(1L);

        // when, then
        assertThatThrownBy(() -> voteService.deleteComment(id, id, deleteCommentRequestDto))
                .isInstanceOf(CommentNotFoundException.class)
                .hasMessage("댓글을 찾을 수 없습니다.");
    }

}