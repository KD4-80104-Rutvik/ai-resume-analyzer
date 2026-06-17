package com.airesume.analyzer.service;

import org.springframework.stereotype.Component;

@Component
public class SkillPromptBuilder {

	public String buildSkillExtractionPrompt(String text) {

		return """
				Extract all technical skills from the text below.

				Text:
				%s

				IMPORTANT RULES:

				1. Return ONLY valid JSON.
				2. Do NOT return markdown.
				3. Do NOT return explanations.
				4. Do NOT return nested JSON objects.
				5. Do NOT return boolean values.
				6. Do NOT return key-value pairs.
				7. Return skills as a JSON array of strings only.
				8. Do NOT return any text before or after the JSON.

				VALID RESPONSE EXAMPLE:

				{
				  "skills": [
				    "Java",
				    "Spring Boot",
				    "Hibernate",
				    "PostgreSQL",
				    "REST APIs",
				    "JWT Authentication"
				  ]
				}

				Text to analyze:

				%s
				""".formatted(text, text);
	}
}