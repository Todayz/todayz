package com.hotclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotclub.domain.common.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
