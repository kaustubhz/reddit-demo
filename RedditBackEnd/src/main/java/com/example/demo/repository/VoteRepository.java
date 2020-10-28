package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

}
