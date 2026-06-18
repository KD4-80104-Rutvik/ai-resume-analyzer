package com.airesume.analyzer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.airesume.analyzer.dto.AnalysisResult;
import com.airesume.analyzer.dto.OllamaRequest;
import com.airesume.analyzer.dto.OllamaResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OllamaService {

	private final RestTemplate restTemplate;
	private final PromptBuilder promptBuilder;
	private final ObjectMapper objectMapper;

	public OllamaService(RestTemplate restTemplate, PromptBuilder promptBuilder, ObjectMapper objectMapper) {

		this.restTemplate = restTemplate;
		this.promptBuilder = promptBuilder;
		this.objectMapper = objectMapper;
	}


	public AnalysisResult analyzeResume(String resumeText, String jobDescription, List<String> missingSkills)
			throws JsonMappingException, JsonProcessingException {

		String prompt = promptBuilder.buildPrompt(resumeText, jobDescription, missingSkills);

		OllamaRequest request = new OllamaRequest();

		request.setModel("llama3.2:1b");
		request.setPrompt(prompt);
		request.setStream(false);
		request.setFormat("json");

		OllamaResponse response = restTemplate.postForObject("http://localhost:11434/api/generate", request,
				OllamaResponse.class);

		System.out.println("========== OLLAMA RESPONSE ==========");
		System.out.println(response.getResponse());
		System.out.println("=====================================");

		String json = extractJson(response.getResponse());

		return objectMapper.readValue(json, AnalysisResult.class);

	}

	private String extractJson(String response) {

		int start = response.indexOf("{");
		int end = response.lastIndexOf("}");

		if (start == -1 || end == -1) {
			throw new RuntimeException("No JSON found in AI response");
		}

		return response.substring(start, end + 1);
	}
}