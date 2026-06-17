package com.airesume.analyzer.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.airesume.analyzer.dto.ResumeResponseDto;
import com.airesume.analyzer.entity.Resume;
import com.airesume.analyzer.service.ResumeService;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

	private final ResumeService resumeService;

	public ResumeController(ResumeService resumeService) {

		this.resumeService = resumeService;
	}

	@PostMapping("/upload")
	public ResponseEntity<ResumeResponseDto> uploadResume(@RequestParam("file") MultipartFile file) throws IOException {

		Resume resume = resumeService.uploadResume(file);

		return ResponseEntity.ok(resumeService.mapToDto(resume));
	}

	@GetMapping
	public ResponseEntity<List<ResumeResponseDto>> getMyResumes() {

		return ResponseEntity.ok(resumeService.getMyResumes());
	}
}
