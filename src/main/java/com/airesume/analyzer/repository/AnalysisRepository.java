package com.airesume.analyzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airesume.analyzer.entity.Analysis;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {

}
