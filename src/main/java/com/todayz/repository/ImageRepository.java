package com.todayz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todayz.domain.common.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
