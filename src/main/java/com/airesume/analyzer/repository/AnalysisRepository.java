package com.airesume.analyzer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airesume.analyzer.entity.Analysis;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {

	List<Analysis> findByResumeUserId(Long userId);
}
