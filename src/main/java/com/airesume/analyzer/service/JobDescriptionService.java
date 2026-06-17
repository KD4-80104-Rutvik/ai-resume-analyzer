package com.airesume.analyzer.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.airesume.analyzer.dto.CreateJobDescriptionRequest;
import com.airesume.analyzer.dto.JobDescriptionResponse;
import com.airesume.analyzer.entity.JobDescription;
import com.airesume.analyzer.entity.User;
import com.airesume.analyzer.repository.JobDescriptionRepository;
import com.airesume.analyzer.repository.UserRepository;

@Service
public class JobDescriptionService {

	private final JobDescriptionRepository jobDescriptionRepository;
	private final UserRepository userRepository;

	public JobDescriptionService(JobDescriptionRepository jobDescriptionRepository, UserRepository userRepository) {

		this.jobDescriptionRepository = jobDescriptionRepository;
		this.userRepository = userRepository;
	}

	public JobDescriptionResponse create(CreateJobDescriptionRequest request) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String email = authentication.getName();

		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		JobDescription jobDescription = new JobDescription();

		jobDescription.setTitle(request.getTitle());
		jobDescription.setDescription(request.getDescription());

		jobDescription.setCreatedAt(LocalDateTime.now());

		jobDescription.setUser(user);

		JobDescription saved = jobDescriptionRepository.save(jobDescription);

		return mapToDto(saved);
	}

	public List<JobDescriptionResponse> getMyJobDescriptions() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String email = authentication.getName();

		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		return jobDescriptionRepository.findByUserId(user.getId()).stream().map(this::mapToDto).toList();
	}

	public JobDescriptionResponse getById(Long id) {

		JobDescription jobDescription = jobDescriptionRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Job Description not found"));

		return mapToDto(jobDescription);
	}

	public void delete(Long id) {

		JobDescription jobDescription = jobDescriptionRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Job Description not found"));

		jobDescriptionRepository.delete(jobDescription);
	}

	private JobDescriptionResponse mapToDto(JobDescription jobDescription) {

		return new JobDescriptionResponse(jobDescription.getId(), jobDescription.getTitle(),
				jobDescription.getDescription(), jobDescription.getCreatedAt(), jobDescription.getUser().getId(),
				jobDescription.getUser().getUsername());
	}
}