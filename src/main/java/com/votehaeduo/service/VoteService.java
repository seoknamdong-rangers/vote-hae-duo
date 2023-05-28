package com.votehaeduo.service;

import com.votehaeduo.dto.request.VoteUpdateRequestDto;
import com.votehaeduo.dto.request.VotingRequestDto;
import com.votehaeduo.dto.response.*;
import com.votehaeduo.dto.request.VoteCreateRequestDto;
import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import com.votehaeduo.exception.date.InvalidEndDateException;
import com.votehaeduo.exception.vote.VoteNotFoundException;
import com.votehaeduo.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
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
    public List<FindVoteResponseDto> findAll() {
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

    @Transactional
    public VotingResponseDto voting(Long id, VotingRequestDto votingRequestDto) {
        Vote vote = voteRepository.findById(id).orElseThrow(VoteNotFoundException::new);

        // 날짜 유효성 검사
        if (LocalDate.now().isAfter(vote.getEndDate())) {
            throw new InvalidEndDateException();
        }

        // 투표하기 기능의 투표 정보
        votingRequestDto.getVoteItemId().forEach(itemId ->
                vote.getVoteItems().stream()
                        .filter(voteItem -> voteItem.getId().equals(itemId))
                        .findFirst()
                        .ifPresent(voteItem -> voteItem.addMember(votingRequestDto.getMemberId())));

        // 항목별 투표 참여 인원 id
        List<Set<Long>> voteItemParticipantIds = vote.getVoteItems().stream()
                .map(VoteItem::getMemberIds)
                .collect(Collectors.toList());

        // 투표 참여 전체 인원 id
        Set<Long> participantIds = vote.getVoteItems().stream()
                .flatMap(voteItem -> voteItem.getMemberIds().stream())
                .collect(Collectors.toSet());

        return VotingResponseDto.of(voteRepository.save(vote), VoteMemberResponseDto.from(participantIds),
                voteItemParticipantIds.stream()
                        .map(VoteMemberResponseDto::from)
                        .collect(Collectors.toList()));
    }

}
