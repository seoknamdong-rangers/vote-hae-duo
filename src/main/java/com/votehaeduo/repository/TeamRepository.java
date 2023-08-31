package com.votehaeduo.repository;

import com.votehaeduo.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("SELECT t FROM Team t JOIN FETCH t.teamMembers WHERE t.voteId = :voteId")
    List<Team> findTeamsByVoteIdWithBatchSize(@Param("voteId")Long voteId);

}