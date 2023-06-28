package com.votehaeduo.entity;

import com.votehaeduo.entity.enumeration.VoteCreateOption;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Column(name = "created_member_id")
    private Long createdMemberId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "vote_create_option")
    private VoteCreateOption voteCreateOption;

    @Builder.Default
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "vote",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true
    )
    private List<VoteItem> voteItems = new ArrayList<>();

    @Builder.Default
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "vote",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            orphanRemoval = true
    )
    private List<Comment> comments = new ArrayList<>();

    public void addItems(List<VoteItem> items) {
        this.voteItems.addAll(items);
    }

    // 투표 참여 전체 인원 id
    public Set<Long> getVoteParticipantsTotalMemberIds() {
        return voteItems.stream()
                .flatMap(voteItem -> voteItem.getMemberIds().stream())
                .collect(Collectors.toSet());
    }

    public void addComments(Comment comment) {
        comment.setVote(this);
        this.comments.add(comment);
    }

    public Comment getLastComment() {
        return comments.stream()
                .max(Comparator.comparing(Comment::getId))
                .orElse(null);
    }

    public void deleteComment(Long commentId) {
        comments.stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findFirst()
                .ifPresent(comment -> {
                    comments.remove(comment);
                    comment.setVote(null);
                });
    }

}
