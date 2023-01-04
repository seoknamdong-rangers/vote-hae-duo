package com.votehaeduo.service;

import com.votehaeduo.dto.request.VoteSaveRequestDto;
import com.votehaeduo.dto.request.VoteUpdateRequestDto;
import com.votehaeduo.dto.response.VoteResponseDto;
import com.votehaeduo.entity.Vote;
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
    public Long save(VoteSaveRequestDto voteSaveRequestDto) {
        return voteRepository.save(voteSaveRequestDto.toEntity()).getId();
    }

    //전체조회
    @Transactional(readOnly = true)
    public List<VoteResponseDto> findAll() {
        return voteRepository.findAll().stream()
                .map(VoteResponseDto::from)
                .collect(Collectors.toList());
    }

    //상세조회
    @Transactional(readOnly = true)
    public VoteResponseDto findById(Long id) {
        Vote vote = voteRepository.findById(id).orElseThrow(VoteNotFoundException::new);
        return VoteResponseDto.from(vote);
    }

    //수정
    public void update(Long id, VoteUpdateRequestDto voteUpdateRequestDto) {
        Vote vote = voteRepository.findById(id).orElseThrow(VoteNotFoundException::new);
        vote.update(voteUpdateRequestDto.getName());
    }

    //삭제
    public void delete(Long id) {
        voteRepository.deleteById(id);
    }

}
