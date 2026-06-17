package com.airesume.analyzer.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airesume.analyzer.dto.CreateJobDescriptionRequest;
import com.airesume.analyzer.dto.JobDescriptionResponse;
import com.airesume.analyzer.service.JobDescriptionService;

@RestController
@RequestMapping("/api/job-descriptions")
public class JobDescriptionController {

	private final JobDescriptionService jobDescriptionService;

	public JobDescriptionController(JobDescriptionService jobDescriptionService) {

		this.jobDescriptionService = jobDescriptionService;
	}

	@PostMapping
	public ResponseEntity<JobDescriptionResponse> create(@RequestBody CreateJobDescriptionRequest request) {

		return ResponseEntity.ok(jobDescriptionService.create(request));
	}

	@GetMapping
	public ResponseEntity<List<JobDescriptionResponse>> getMyJobDescriptions() {

		return ResponseEntity.ok(jobDescriptionService.getMyJobDescriptions());
	}

	@GetMapping("/{id}")
	public ResponseEntity<JobDescriptionResponse> getById(@PathVariable Long id) {

		return ResponseEntity.ok(jobDescriptionService.getById(id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {

		jobDescriptionService.delete(id);

		return ResponseEntity.ok("Job Description deleted successfully");
	}
}