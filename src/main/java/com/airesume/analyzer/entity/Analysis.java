package com.airesume.analyzer.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "analysis")
public class Analysis {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resume_id")
	private Resume resume;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "job_description_id")
	private JobDescription jobDescription;

	private Integer matchScore;

	@Column(columnDefinition = "TEXT")
	private String missingSkills;

	@Column(columnDefinition = "TEXT")
	private String strengths;

	@Column(columnDefinition = "TEXT")
	private String suggestions;

	@Column(columnDefinition = "TEXT")
	private String interviewQuestions;

	private LocalDateTime analyzedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Resume getResume() {
		return resume;
	}

	public void setResume(Resume resume) {
		this.resume = resume;
	}

	public JobDescription getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(JobDescription jobDescription) {
		this.jobDescription = jobDescription;
	}

	public Integer getMatchScore() {
		return matchScore;
	}

	public void setMatchScore(Integer matchScore) {
		this.matchScore = matchScore;
	}

	public String getMissingSkills() {
		return missingSkills;
	}

	public void setMissingSkills(String missingSkills) {
		this.missingSkills = missingSkills;
	}

	public String getStrengths() {
		return strengths;
	}

	public void setStrengths(String strengths) {
		this.strengths = strengths;
	}

	public String getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(String suggestions) {
		this.suggestions = suggestions;
	}

	public String getInterviewQuestions() {
		return interviewQuestions;
	}

	public void setInterviewQuestions(String interviewQuestions) {
		this.interviewQuestions = interviewQuestions;
	}

	public LocalDateTime getAnalyzedAt() {
		return analyzedAt;
	}

	public void setAnalyzedAt(LocalDateTime analyzedAt) {
		this.analyzedAt = analyzedAt;
	}

}