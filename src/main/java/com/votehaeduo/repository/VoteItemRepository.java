package com.votehaeduo.repository;

import com.votehaeduo.entity.VoteItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteItemRepository extends JpaRepository<VoteItem, Long> {

}
