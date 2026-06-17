package com.airesume.analyzer.dto;

import java.time.LocalDateTime;

public class ResumeResponseDto {

	private Long id;
	private String fileName;
	private LocalDateTime uploadedAt;
	private UserSummaryDto user;

	public ResumeResponseDto() {
	}

	public ResumeResponseDto(Long id, String fileName, LocalDateTime uploadedAt, UserSummaryDto user) {
		this.id = id;
		this.fileName = fileName;
		this.uploadedAt = uploadedAt;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public String getFileName() {
		return fileName;
	}

	public LocalDateTime getUploadedAt() {
		return uploadedAt;
	}

	public UserSummaryDto getUser() {
		return user;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setUploadedAt(LocalDateTime uploadedAt) {
		this.uploadedAt = uploadedAt;
	}

	public void setUser(UserSummaryDto user) {
		this.user = user;
	}
}
