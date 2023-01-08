package com.votehaeduo.repository;

import com.votehaeduo.entity.Member;
import com.votehaeduo.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
