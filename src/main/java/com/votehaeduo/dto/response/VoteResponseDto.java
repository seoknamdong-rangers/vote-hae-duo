package com.votehaeduo.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VoteResponseDto {

    private Long id;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate endDate;
    private String createdBy;
    private List<VoteItemResponseDto> voteItems;
    private Long memberTotalUniqueCount;

    public static VoteResponseDto from(Vote vote) {
        return new VoteResponseDto(
                vote.getId(),
                vote.getTitle(),
                vote.getStartDate(),
                vote.getEndDate(),
                vote.getCreatedBy(),
                vote.getVoteItems().stream()
                        .map(VoteItemResponseDto::from)
                        .collect(Collectors.toList()),
                (long) vote.getVoteItems().stream()
                        .map(VoteItem::getMemberIds)
                        .flatMap(Set::stream)
                        .collect(Collectors.toSet())
                        .size());
    }

}
