package com.votehaeduo.entity;

import lombok.*;

import javax.persistence.*;
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

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "member_id")
    private Set<Long> memberIds;

}
