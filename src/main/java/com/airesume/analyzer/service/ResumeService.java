package com.airesume.analyzer.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.airesume.analyzer.dto.ResumeResponseDto;
import com.airesume.analyzer.dto.UserSummaryDto;
import com.airesume.analyzer.entity.Resume;
import com.airesume.analyzer.entity.User;
import com.airesume.analyzer.repository.ResumeRepository;
import com.airesume.analyzer.repository.UserRepository;

@Service
public class ResumeService {

	private final ResumeRepository resumeRepository;
	private final UserRepository userRepository;
	private final PdfParserService pdfParserService;

	public ResumeService(ResumeRepository resumeRepository, UserRepository userRepository,
			PdfParserService pdfParserService) {

		this.resumeRepository = resumeRepository;
		this.userRepository = userRepository;
		this.pdfParserService = pdfParserService;
	}

	public Resume uploadResume(MultipartFile file) throws IOException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String email = authentication.getName();

		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		String extractedText = pdfParserService.extractText(file);

		Resume resume = new Resume();

		resume.setFileName(file.getOriginalFilename());
		resume.setExtractedText(extractedText);
		resume.setUploadedAt(LocalDateTime.now());
		resume.setUser(user);

		return resumeRepository.save(resume);
	}

	public List<ResumeResponseDto> getMyResumes() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String email = authentication.getName();

		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		return resumeRepository.findByUserId(user.getId()).stream().map(this::mapToDto).toList();
	}

	public ResumeResponseDto mapToDto(Resume resume) {

		UserSummaryDto userDto = new UserSummaryDto(resume.getUser().getId(), resume.getUser().getUsername());

		return new ResumeResponseDto(resume.getId(), resume.getFileName(), resume.getUploadedAt(), userDto);
	}
}
