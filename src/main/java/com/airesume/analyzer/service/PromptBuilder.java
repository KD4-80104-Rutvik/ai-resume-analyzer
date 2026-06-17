package com.airesume.analyzer.service;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class PromptBuilder {

	public String buildPrompt(String resumeText, String jobDescription, List<String> missingSkills) {

        return """
				You are an ATS Resume Analyzer.

				Resume:
				%s

				Job Description:
				%s

				Missing Skills:
				%s

				TASK:
				Generate exactly 3 actionable suggestions
				to improve the resume based on the missing
				skills and job requirements.

				IMPORTANT RULES:
				- Return ONLY valid JSON.
				- Do NOT return markdown.
				- Do NOT add explanations.
				- Do NOT add notes.
				- Do NOT add text before or after the JSON.
				- suggestions MUST be an array of strings.

				Expected JSON format:

				{
				  "suggestions": [
				    "Suggestion 1",
				    "Suggestion 2",
				    "Suggestion 3"
				  ]
				}
				""".formatted(resumeText, jobDescription, String.join(", ", missingSkills));
    }
}