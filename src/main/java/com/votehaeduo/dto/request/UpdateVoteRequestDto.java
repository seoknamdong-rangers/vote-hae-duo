package com.votehaeduo.dto.request;

import com.votehaeduo.dto.VoteItemPayload;
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
public class UpdateVoteRequestDto {

    private String title;
    private LocalDate endDate;
    private List<VoteItemPayload> voteItems;

}
