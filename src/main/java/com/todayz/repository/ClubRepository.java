package com.todayz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todayz.domain.club.Club;

public interface ClubRepository extends JpaRepository<Club, Long> {
}
