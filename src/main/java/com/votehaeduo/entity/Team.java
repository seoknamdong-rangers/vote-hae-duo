package com.votehaeduo.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "team_name")
    private String teamName;

    @Builder.Default
    @Column(name = "member_nicknames")
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<String> memberNicknames = new HashSet<>();

    @Column(name = "created_member_id")
    private Long createdMemberId;

    @Column(name = "create_date")
    private LocalDate createdDate;

    @JoinColumn(name = "vote_id")
    private Long voteId;

    public static Team of(Long teamNumber, Long createdMemberId, Long voteId, Set<String> teamMemberNicknames) {
        return Team.builder()
                .teamName((teamNumber + 1) + " 팀")
                .memberNicknames(teamMemberNicknames)
                .createdDate(LocalDate.now())
                .createdMemberId(createdMemberId)
                .voteId(voteId)
                .build();
    }

}
