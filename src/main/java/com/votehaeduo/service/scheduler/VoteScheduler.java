package com.votehaeduo.service.scheduler;

import com.votehaeduo.dto.CreateVoteItem;
import com.votehaeduo.dto.request.CreateVoteRequest;
import com.votehaeduo.entity.enumeration.VoteCreateOption;
import com.votehaeduo.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteScheduler {

    private final VoteService voteService;

    //@Scheduled(cron = "*/10 * * * * *") // 테스트 전용 10초 마다 실행
    @Scheduled(cron = "0 0 1 ? * SAT") // 매주 토요일 새벽 1시에 실행
    public void scheduleVote() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(3);
        LocalDate nextFriday = startDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
        CreateVoteRequest createVoteRequest = CreateVoteRequest.builder()
                .title(nextFriday + " 풋살")
                .startDate(startDate)
                .endDate(endDate)
                .createdMemberId(999L) // 임시 멤버
                .voteItems(List.of(new CreateVoteItem("11시 ~ 1시 실외"),
                        new CreateVoteItem("12시 ~ 2시 실내")))
                .voteCreateOption(VoteCreateOption.AUTO)
                .build();
        voteService.create(createVoteRequest);
    }

}
