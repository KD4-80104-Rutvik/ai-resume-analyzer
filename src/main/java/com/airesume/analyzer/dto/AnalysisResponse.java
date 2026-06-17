package com.airesume.analyzer.dto;

import java.util.List;

public class AnalysisResponse {

	private Integer matchScore;
	private List<String> missingSkills;
	private List<String> suggestions;

	public Integer getMatchScore() {
		return matchScore;
	}

	public void setMatchScore(Integer matchScore) {
		this.matchScore = matchScore;
	}

	public List<String> getMissingSkills() {
		return missingSkills;
	}

	public void setMissingSkills(List<String> missingSkills) {
		this.missingSkills = missingSkills;
	}

	public List<String> getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(List<String> suggestions) {
		this.suggestions = suggestions;
	}

}