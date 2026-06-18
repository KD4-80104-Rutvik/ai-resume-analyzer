package com.airesume.analyzer.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.airesume.analyzer.dto.AnalysisResponse;
import com.airesume.analyzer.dto.AnalysisResult;
import com.airesume.analyzer.repository.AnalysisRepository;

@Service
public class AnalysisService {

	private final AnalysisRepository analysisRepository;
	private final PdfParserService pdfParserService;
	private final SkillExtractionService skillExtractionService;
	private final AtsService atsService;
	private final OllamaService ollamaService;


	public AnalysisService(AnalysisRepository analysisRepository, PdfParserService pdfParserService,
			SkillExtractionService skillExtractionService, AtsService atsService, OllamaService ollamaService) {
		super();
		this.analysisRepository = analysisRepository;
		this.pdfParserService = pdfParserService;
		this.skillExtractionService = skillExtractionService;
		this.atsService = atsService;
		this.ollamaService = ollamaService;
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