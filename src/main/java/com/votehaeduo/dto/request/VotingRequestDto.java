package com.votehaeduo.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VotingRequestDto {

    private Long memberId;

    @NotBlank(message = "1개 이상 선택을 하셔야 합니다.")
    private List<Long> voteItemId;

}
