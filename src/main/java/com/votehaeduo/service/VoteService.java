package com.votehaeduo.service;

import com.votehaeduo.dto.request.VoteUpdateRequestDto;
import com.votehaeduo.dto.request.VoteSaveRequestDto;
import com.votehaeduo.dto.request.VoteUpdateRequestDto;
import com.votehaeduo.dto.response.PostVoteResponseDto;
import com.votehaeduo.dto.response.VoteResponseDto;
import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import com.votehaeduo.exception.vote.VoteNotFoundException;
import com.votehaeduo.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;

    //등록
    @Transactional
    public PostVoteResponseDto save(VoteSaveRequestDto voteSaveRequestDto) {
        return PostVoteResponseDto.from(voteRepository.save(voteSaveRequestDto.toEntity()));
    }

    //전체조회
    @Transactional(readOnly = true)
    public List<VoteResponseDto> findAll() {
        return voteRepository.findAll().stream()
                .map(VoteResponseDto::from)
                .collect(Collectors.toList());
    }

    //상세조회
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
