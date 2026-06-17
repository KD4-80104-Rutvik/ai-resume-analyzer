package com.airesume.analyzer.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.airesume.analyzer.dto.AnalysisRequest;
import com.airesume.analyzer.dto.AnalysisResponse;
import com.airesume.analyzer.dto.AnalysisResult;
import com.airesume.analyzer.entity.Analysis;
import com.airesume.analyzer.entity.JobDescription;
import com.airesume.analyzer.entity.Resume;
import com.airesume.analyzer.repository.AnalysisRepository;
import com.airesume.analyzer.repository.JobDescriptionRepository;
import com.airesume.analyzer.repository.ResumeRepository;

@Service
public class AnalysisService {

	private final AnalysisRepository analysisRepository;
	private final ResumeRepository resumeRepository;
	private final JobDescriptionRepository jobDescriptionRepository;
	private final PdfParserService pdfParserService;
	private final SkillExtractionService skillExtractionService;
	private final AtsService atsService;
	private final OllamaService ollamaService;


	public AnalysisService(AnalysisRepository analysisRepository, ResumeRepository resumeRepository,
			JobDescriptionRepository jobDescriptionRepository, PdfParserService pdfParserService,
			SkillExtractionService skillExtractionService, AtsService atsService, OllamaService ollamaService) {
		super();
		this.analysisRepository = analysisRepository;
		this.resumeRepository = resumeRepository;
		this.jobDescriptionRepository = jobDescriptionRepository;
		this.pdfParserService = pdfParserService;
		this.skillExtractionService = skillExtractionService;
		this.atsService = atsService;
		this.ollamaService = ollamaService;
	}

	public AnalysisResponse createAnalysis(AnalysisRequest request) {

		Resume resume = resumeRepository.findById(request.getResumeId())
				.orElseThrow(() -> new RuntimeException("Resume not found"));

		JobDescription jobDescription = jobDescriptionRepository.findById(request.getJobDescriptionId())
				.orElseThrow(() -> new RuntimeException("Job Description not found"));

		Analysis analysis = new Analysis();

		analysis.setResume(resume);
		analysis.setJobDescription(jobDescription);

		// Mock AI Result
		analysis.setMatchScore(78);

		analysis.setMissingSkills("[\"Docker\",\"Kubernetes\"]");

		analysis.setStrengths("[\"Java\",\"Spring Boot\",\"PostgreSQL\"]");

		analysis.setSuggestions("[\"Add Docker project experience\",\"Add Cloud exposure\"]");

		analysis.setInterviewQuestions("[\"Explain Spring Security\",\"What is JPA?\",\"What is JWT?\"]");

		analysis.setAnalyzedAt(LocalDateTime.now());

		Analysis saved = analysisRepository.save(analysis);

		return mapToResponse(saved);
	}

	public List<AnalysisResponse> getAllAnalyses() {

		return analysisRepository.findAll().stream().map(this::mapToResponse).toList();
	}

	public AnalysisResponse getAnalysisById(Long id) {

		Analysis analysis = analysisRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Analysis not found"));

		return mapToResponse(analysis);
	}

	public void deleteAnalysis(Long id) {

		Analysis analysis = analysisRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Analysis not found"));

		analysisRepository.delete(analysis);
	}

	private AnalysisResponse mapToResponse(Analysis analysis) {

		AnalysisResponse response = new AnalysisResponse();

		response.setMatchScore(analysis.getMatchScore());

		return response;
	}

	public AnalysisResponse analyze(MultipartFile resumeFile, String jobDescription) throws Exception {

		String resumeText = pdfParserService.extractText(resumeFile);

		List<String> resumeSkills = skillExtractionService.extractSkills(resumeText);

		List<String> jdSkills = skillExtractionService.extractSkills(jobDescription);

		Set<String> resumeSkillSet = new HashSet<>(resumeSkills);

		Set<String> jdSkillSet = new HashSet<>(jdSkills);

		Integer matchScore = atsService.calculateMatchScore(resumeSkillSet, jdSkillSet);

		List<String> missingSkills = atsService.getMissingSkills(resumeSkillSet, jdSkillSet);

		AnalysisResult aiResult = ollamaService.analyzeResume(resumeText, jobDescription, missingSkills);

		AnalysisResponse response = new AnalysisResponse();

		response.setMatchScore(matchScore);
		response.setMissingSkills(missingSkills);
		response.setSuggestions(aiResult.getSuggestions());

		return response;
	}
}