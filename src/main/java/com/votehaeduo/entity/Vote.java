package com.votehaeduo.entity;

import com.votehaeduo.dto.request.VoteUpdateRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vote")
    private List<VoteItem> voteItems = new ArrayList<>();

    public void update(Vote vote) {
        this.name = vote.getName();
        this.voteItems = vote.getVoteItems();
    }

    public void addItems(List<VoteItem> items) {
        this.voteItems.addAll(items);
    }

}
