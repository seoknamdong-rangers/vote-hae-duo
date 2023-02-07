package com.votehaeduo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteUpdateRequestDto {

    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String createdBy;
    private List<VoteItemUpdateRequestDto> voteItems;

}
