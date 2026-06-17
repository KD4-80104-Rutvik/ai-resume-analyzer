package com.airesume.analyzer.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.airesume.analyzer.dto.AnalysisRequest;
import com.airesume.analyzer.dto.AnalysisResponse;
import com.airesume.analyzer.service.AnalysisService;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

	private final AnalysisService analysisService;

	public AnalysisController(AnalysisService analysisService) {

		this.analysisService = analysisService;
	}

	@PostMapping
	public ResponseEntity<AnalysisResponse> createAnalysis(@RequestBody AnalysisRequest request) {

		return ResponseEntity.ok(analysisService.createAnalysis(request));
	}

	@GetMapping
	public ResponseEntity<List<AnalysisResponse>> getAllAnalyses() {

		return ResponseEntity.ok(analysisService.getAllAnalyses());
	}

	@GetMapping("/{id}")
	public ResponseEntity<AnalysisResponse> getAnalysisById(@PathVariable Long id) {

		return ResponseEntity.ok(analysisService.getAnalysisById(id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAnalysis(@PathVariable Long id) {

		analysisService.deleteAnalysis(id);

		return ResponseEntity.ok("Analysis deleted successfully");
	}

	@PostMapping(value = "/resume", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<AnalysisResponse> analyzeResume(@RequestParam("resume") MultipartFile resumeFile,
			@RequestParam("jobDescription") String jobDescription) throws Exception {

		return ResponseEntity.ok(analysisService.analyze(resumeFile, jobDescription));
	}
}