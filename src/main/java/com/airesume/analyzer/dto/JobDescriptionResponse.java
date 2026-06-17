package com.airesume.analyzer.dto;

import java.time.LocalDateTime;

public class JobDescriptionResponse {

	private Long id;
	private String title;
	private String description;
	private LocalDateTime createdAt;

	private Long userId;
	private String username;

	public JobDescriptionResponse(Long id, String title, String description, LocalDateTime createdAt, Long userId,
			String username) {

		this.id = id;
		this.title = title;
		this.description = description;
		this.createdAt = createdAt;
		this.userId = userId;
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public Long getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}
}