package com.votehaeduo.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private String createdBy;

    @Builder.Default
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "vote",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true
    )
    private List<VoteItem> voteItems = new ArrayList<>();

    public void addItems(List<VoteItem> items) {
        this.voteItems.addAll(items);
    }

}
