package com.votehaeduo.service;

import com.votehaeduo.dto.response.GetVoteResponseDto;
import com.votehaeduo.entity.VoteItem;
import com.votehaeduo.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;

    //전체조회
    @Transactional(readOnly = true)
    public List<GetVoteResponseDto> findAll() { //스트림으로 바꾸고 싶음
        return voteRepository.findAll().stream()
                .map(vote -> GetVoteResponseDto.of(vote, (long) vote.getVoteItems().stream()
                        .map(VoteItem::getMemberIds)
                        .flatMap(Set::stream)
                        .collect(Collectors.toSet())
                        .size()))
                .collect(Collectors.toList());
    }

}
