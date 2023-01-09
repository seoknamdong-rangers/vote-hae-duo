package com.votehaeduo.service;

import com.votehaeduo.dto.request.VoteSaveRequestDto;
import com.votehaeduo.dto.response.VoteResponseDto;
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
    public VoteResponseDto save(VoteSaveRequestDto voteSaveRequestDto) {
        return VoteResponseDto.from(voteRepository.save(voteSaveRequestDto.toEntity()));
    }

    //전체조회
    @Transactional(readOnly = true)
    public List<VoteResponseDto> findAll() {
        return voteRepository.findAll().stream()
                .map(VoteResponseDto::from)
                .collect(Collectors.toList());
    }

    //삭제
    public void delete(Long id) {
        voteRepository.deleteById(id);
    }

}
