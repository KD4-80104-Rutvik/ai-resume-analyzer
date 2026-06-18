package com.airesume.analyzer.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.airesume.analyzer.dto.AnalysisResponse;
import com.airesume.analyzer.service.AnalysisService;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

	private final AnalysisService analysisService;

	public AnalysisController(AnalysisService analysisService) {

		this.analysisService = analysisService;
	}

	@PostMapping(value = "/resume", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<AnalysisResponse> analyzeResume(@RequestParam("resume") MultipartFile resumeFile,
			@RequestParam("jobDescription") String jobDescription) throws Exception {

		return ResponseEntity.ok(analysisService.analyze(resumeFile, jobDescription));
	}
}