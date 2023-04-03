package com.votehaeduo.dto.request;

import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteCreateRequestDto {

    @NotBlank(message = "제목은 공백이 아니어야 합니다.")
    private String title;
    @NotNull(message = "투표 등록 일자를 입력해주세요.")
    private LocalDate startDate;
    @NotNull(message = "투표 종료 일자를 입력해주세요.")
    private LocalDate endDate;
    @NotBlank(message = "작성자는 공백이 아니어야 합니다.")
    private String createdBy;
    @NotEmpty(message = "투표 항목은 공백이 아니어야 합니다.")
    private List<VoteItemCreateRequestDto> voteItems;

    public Vote toEntity() {
        Vote vote = Vote.builder()
                .title(this.title)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .createdBy(this.createdBy)
                .build();
        List<VoteItem> items = voteItems.stream()
                .map(voteItemCreateRequestDto -> voteItemCreateRequestDto.toEntity(vote))
                .collect(Collectors.toList());
        vote.addItems(items);
        return vote;
    }

}