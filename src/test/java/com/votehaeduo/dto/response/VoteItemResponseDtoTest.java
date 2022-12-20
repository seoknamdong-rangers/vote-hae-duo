package com.votehaeduo.dto.response;

import com.votehaeduo.entity.VoteItem;
import com.votehaeduo.repository.VoteItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class VoteItemResponseDtoTest {

    @Mock
    private VoteItemRepository voteItemRepository;

    @Test
    @DisplayName("voteItemResponseDto 생성 테스트")
    void from() {
        //given
        Long voteItemFakeId = 1L;
        VoteItem voteItem = VoteItem.builder().id(voteItemFakeId).name("11시 ~ 1시 실외").build();
        given(voteItemRepository.findById(voteItemFakeId)).willReturn(java.util.Optional.ofNullable(voteItem));

        //when
        Optional<VoteItem> testVoteItem = voteItemRepository.findById(voteItemFakeId);

        //then
        Assertions.assertThat(Objects.requireNonNull(testVoteItem.orElse(voteItem), "값이 없습니다.")
                .getName()).isEqualTo("11시 ~ 1시 실외");
    }

}