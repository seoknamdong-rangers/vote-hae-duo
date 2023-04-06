package com.votehaeduo.service;

import com.votehaeduo.dto.request.VoteUpdateRequestDto;
import com.votehaeduo.dto.response.FindVoteResponseDto;
import com.votehaeduo.dto.request.VoteCreateRequestDto;
import com.votehaeduo.dto.response.VoteCreateResponseDto;
import com.votehaeduo.dto.response.VoteResponseDto;
import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import com.votehaeduo.exception.vote.VoteNotFoundException;
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

    //등록
    @Transactional
    public VoteCreateResponseDto create(VoteCreateRequestDto voteCreateRequestDto) {
        return VoteCreateResponseDto.from(voteRepository.save(voteCreateRequestDto.toEntity()));
    }

    //전체조회
    @Transactional(readOnly = true)
    public List<FindVoteResponseDto> findAll() { //스트림으로 바꾸고 싶음
        return voteRepository.findAll().stream()
                .map(vote -> FindVoteResponseDto.of(vote, (long) vote.getVoteItems().stream()
                        .map(VoteItem::getMemberIds)
                        .flatMap(Set::stream)
                        .collect(Collectors.toSet())
                        .size()))
                .collect(Collectors.toList());
    }

    //상세조회
    @Transactional
    public VoteResponseDto findById(Long id) {
        Vote vote = voteRepository.findById(id).orElseThrow(VoteNotFoundException::new);
        return VoteResponseDto.from(vote);
    }

    //수정
    @Transactional
    public VoteResponseDto update(Long id, VoteUpdateRequestDto voteUpdateRequestDto) {
        Vote vote = voteRepository.findById(id).orElseThrow(VoteNotFoundException::new);
        if (voteUpdateRequestDto.getTitle() != null) {
            vote.setTitle(voteUpdateRequestDto.getTitle());
        }
        if (voteUpdateRequestDto.getStartDate() != null) {
            vote.setStartDate(voteUpdateRequestDto.getStartDate());
        }
        if (voteUpdateRequestDto.getEndDate() != null) {
            vote.setCreatedBy(voteUpdateRequestDto.getCreatedBy());
        }
        if (voteUpdateRequestDto.getVoteItems() != null) {
            List<VoteItem> items = voteUpdateRequestDto.getVoteItems().stream()
                    .map(voteItemUpdateRequestDto -> voteItemUpdateRequestDto.toEntity(vote))
                    .collect(Collectors.toList());
            vote.setVoteItems(items);
        }
        return VoteResponseDto.from(voteRepository.save(vote));
    }

    //삭제
    @Transactional
    public boolean delete(Long id) {
        Vote vote = voteRepository.findById(id).orElseThrow(VoteNotFoundException::new);
        voteRepository.delete(vote);
        return true;
    }

}
