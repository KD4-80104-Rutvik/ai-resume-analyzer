package com.airesume.analyzer.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class AtsService {

	public List<String> getMissingSkills(Set<String> resumeSkills, Set<String> jdSkills) {

		return jdSkills.stream().filter(skill -> !resumeSkills.contains(skill)).toList();
	}

	public Integer calculateMatchScore(Set<String> resumeSkills, Set<String> jdSkills) {

		long matchedSkills = jdSkills.stream().filter(resumeSkills::contains).count();

		return jdSkills.isEmpty() ? 0 : (int) ((matchedSkills * 100.0) / jdSkills.size());
	}
}