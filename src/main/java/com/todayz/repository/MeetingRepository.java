package com.todayz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todayz.domain.club.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}