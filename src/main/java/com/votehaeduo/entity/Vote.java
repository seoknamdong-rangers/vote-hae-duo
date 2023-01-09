package com.votehaeduo.entity;

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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vote", cascade = CascadeType.PERSIST)
    private List<VoteItem> voteItems = new ArrayList<>();

    public void update(String name) {
        this.name = name;
    }

    public void addItems(List<VoteItem> items) {
        this.voteItems.addAll(items);
    }

}
