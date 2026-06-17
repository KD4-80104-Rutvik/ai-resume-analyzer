package com.airesume.analyzer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.airesume.analyzer.dto.OllamaRequest;
import com.airesume.analyzer.dto.OllamaResponse;
import com.airesume.analyzer.dto.SkillExtractionResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SkillExtractionService {

	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;
	private final SkillPromptBuilder promptBuilder;

	public SkillExtractionService(RestTemplate restTemplate, ObjectMapper objectMapper,
			SkillPromptBuilder promptBuilder) {

		this.restTemplate = restTemplate;
		this.objectMapper = objectMapper;
		this.promptBuilder = promptBuilder;
	}

	public List<String> extractSkills(String text) throws JsonMappingException, JsonProcessingException {

		String prompt = promptBuilder.buildSkillExtractionPrompt(text);

		OllamaRequest request = new OllamaRequest();

		request.setModel("llama3.2:1b");
		request.setPrompt(prompt);
		request.setStream(false);
		request.setFormat("json");

		OllamaResponse response = restTemplate.postForObject("http://localhost:11434/api/generate", request,
				OllamaResponse.class);

		System.out.println(response.getResponse());

		SkillExtractionResult result = objectMapper.readValue(response.getResponse(), SkillExtractionResult.class);

	    return result.getSkills();
	}

}