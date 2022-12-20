package com.votehaeduo.dto.response;

import com.votehaeduo.dto.request.VoteSaveRequestDto;
import com.votehaeduo.dto.request.VoteItemRequestDto;
import com.votehaeduo.entity.Vote;
import com.votehaeduo.repository.VoteRepository;
import com.votehaeduo.service.VoteService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class VoteResponseDtoTest {

    @Mock
    private VoteRepository voteRepository;

    @InjectMocks
    private VoteService voteService;

    @Test
    @DisplayName("voteResponseDto 생성 테스트")
    void from() {
        //given
        Long voteFakeId = 1L;
        List<VoteItemRequestDto> voteItems = List.of(new VoteItemRequestDto(voteFakeId, "11 ~ 1시 실외"),
                new VoteItemRequestDto(2L, "12 ~ 2 실내"));
        Vote testVote = new VoteSaveRequestDto(voteFakeId, "12월 15일 풋살 투표", voteItems).toEntity();
        given(voteRepository.findById(voteFakeId)).willReturn(java.util.Optional.ofNullable(testVote));

        //when
        VoteResponseDto voteResponseDto = voteService.findById(voteFakeId);

        //then
        Assertions.assertThat(voteResponseDto.getName()).isEqualTo("12월 15일 풋살 투표");
        Assertions.assertThat(voteResponseDto.getVoteItems().get(0).getName()).isEqualTo("11 ~ 1시 실외");
    }

}