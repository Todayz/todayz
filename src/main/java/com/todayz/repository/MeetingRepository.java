package com.todayz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todayz.domain.club.Club;
import com.todayz.domain.club.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
	List<Meeting> findByParent(Club club);
}