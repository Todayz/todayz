package com.hotclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotclub.domain.club.Club;

public interface ClubRepository extends JpaRepository<Club, Long> {
}
