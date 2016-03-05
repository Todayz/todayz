package com.hotclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotclub.domain.club.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}