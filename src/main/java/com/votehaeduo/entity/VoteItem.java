package com.votehaeduo.entity;

import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VoteItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vote_id")
    private Vote vote;

    private String title;

    @ElementCollection(fetch = FetchType.LAZY) // EAGER 에서 LAZY 로 수정해봄
    @Column(name = "member_id")
    private Set<Long> memberIds = new HashSet<>(); // = new HashSet<>() 추가해봄

    public void addMember(Long memberId) {
        this.memberIds.add(memberId);
    }

}
