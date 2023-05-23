package com.votehaeduo.entity;

import com.votehaeduo.dto.request.DeleteCommentRequestDto;
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

    public void addComments(Comment comment) {
        comment.setVote(this);
        this.comments.add(comment);
    }

    public void deleteComment(DeleteCommentRequestDto deleteCommentRequestDto) {
        comments.stream()
                .filter(comment -> comment.getId().equals(deleteCommentRequestDto.getCommentId()))
                .findFirst()
                .ifPresent(comment -> {
                    comments.remove(comment);
                    comment.setVote(null);
                });
    }

}
