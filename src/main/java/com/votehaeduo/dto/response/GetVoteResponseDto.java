package com.votehaeduo.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.votehaeduo.entity.Vote;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetVoteResponseDto {

    private Long id;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate endDate;
    private String createdBy;
    private List<GetVoteItemResponseDto> voteItems;
    private Long uniqueCount;

    public static GetVoteResponseDto of(Vote vote, Long uniqueCount) {
        return new GetVoteResponseDto(
                vote.getId(),
                vote.getTitle(),
                vote.getStartDate(),
                vote.getEndDate(),
                vote.getCreatedBy(),
                vote.getVoteItems().stream()
                        .map(GetVoteItemResponseDto::from)
                        .collect(Collectors.toList()),
                uniqueCount
        );
    }

}
