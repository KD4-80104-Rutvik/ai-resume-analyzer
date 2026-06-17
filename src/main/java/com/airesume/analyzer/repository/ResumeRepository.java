package com.airesume.analyzer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airesume.analyzer.entity.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

	List<Resume> findByUserId(Long userId);
}
