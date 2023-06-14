package com.votehaeduo.service.scheduler;

import com.votehaeduo.dto.CommentPayload;
import com.votehaeduo.dto.VoteItemPayload;
import com.votehaeduo.dto.VotePayload;
import com.votehaeduo.dto.request.CreateVoteRequestDto;
import com.votehaeduo.dto.response.CreateVoteResponse;
import com.votehaeduo.service.VoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class VoteSchedulerTest {

    @Mock
    private VoteService voteService;

    @InjectMocks
    private VoteScheduler voteScheduler;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void scheduleVote_ShouldCreateVote() {
        // Given
        CreateVoteResponse expectedResponse = new CreateVoteResponse(
                new VotePayload(1L,
                        "1월 8일 풋살",
                        LocalDate.of(2023, 6, 9),
                        LocalDate.of(2023, 6, 30),
                        1L,
                        List.of(new VoteItemPayload(1L, "11시 ~ 1시 실외", Set.of(2L, 3L)),
                                new VoteItemPayload(2L, "12시 ~ 2시 실내", Set.of(4L, 5L))),
                        List.of(new CommentPayload(),
                                new CommentPayload())),
                "킴대세");
        given(voteService.create(any())).willReturn(expectedResponse);

        // When
        voteScheduler.scheduleVote();

        // Then
        verify(voteService, times(1)).create(any(CreateVoteRequestDto.class));
    }

}

