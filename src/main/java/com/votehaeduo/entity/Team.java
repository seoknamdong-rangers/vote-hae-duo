package com.votehaeduo.entity;

import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @BatchSize(size = 10)
    @Builder.Default
    @ElementCollection
    @Column(name = "team_member")
    private Set<String> teamMembers = new HashSet<>();

    @Column(name = "created_member_id")
    private Long createdMemberId;

    @Column(name = "create_date")
    private LocalDateTime createdDateTime;

    @JoinColumn(name = "vote_id")
    private Long voteId;

    public static Team of(Long createdMemberId, Long voteId, Set<String> teamMemberNicknames) {
        return Team.builder()
                .teamMembers(teamMemberNicknames)
                .createdDateTime(LocalDateTime.now())
                .createdMemberId(createdMemberId)
                .voteId(voteId)
                .build();
    }

}
