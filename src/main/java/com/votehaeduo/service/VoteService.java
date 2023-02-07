package com.votehaeduo.service;

import com.votehaeduo.dto.response.GetVoteResponseDto;
import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import com.votehaeduo.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;

    //전체조회
    @Transactional(readOnly = true)
    public List<GetVoteResponseDto> findAll() { //스트림으로 바꾸고 싶음
        List<GetVoteResponseDto> voteResponseDtos = new ArrayList<>();
        for (Vote vote : voteRepository.findAll()) {
            Set<Long> voteTotalMemberIdsCount = new HashSet<>();
            for (VoteItem voteItem : vote.getVoteItems()) {
                voteTotalMemberIdsCount.addAll(voteItem.getMemberIds());
            }
            voteResponseDtos.add(GetVoteResponseDto.of(vote, (long) voteTotalMemberIdsCount.size()));
        }
        return voteResponseDtos;
    }

}
