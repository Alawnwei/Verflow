package com.example.veriflow.service.impl;

import com.example.veriflow.dto.request.RequirementCreateRequest;
import com.example.veriflow.dto.response.PageResponse;
import com.example.veriflow.entity.Requirement;
import com.example.veriflow.entity.Requirement.RequirementStatus;
import com.example.veriflow.entity.Requirement.SourceType;
import com.example.veriflow.exception.ResourceNotFoundException;
import com.example.veriflow.repository.RequirementRepository;
import com.example.veriflow.service.RequirementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * 需求文档服务实现
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RequirementServiceImpl implements RequirementService {

    private final RequirementRepository requirementRepository;

    @Override
    @Transactional
    public Requirement create(RequirementCreateRequest request, String userId) {
        log.info("Creating requirement: {}", request.getTitle());
        
        Requirement requirement = Requirement.builder()
                .id(UUID.randomUUID().toString())
                .title(request.getTitle())
                .sourceType(SourceType.valueOf(request.getSourceType().toUpperCase()))
                .content(request.getContent())
                .sourceUrl(request.getSourceUrl())
                .projectId(request.getProjectId())
                .createdBy(userId)
                .status(RequirementStatus.PENDING)
                .build();

        return requirementRepository.save(requirement);
    }

    @Override
    public PageResponse<Requirement> list(Integer page, Integer size, String status, String sourceType) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        
        Page<Requirement> requirementPage;
        
        if (status != null && sourceType != null) {
            requirementPage = requirementRepository.findByStatus(
                    RequirementStatus.valueOf(status.toUpperCase()), pageable);
        } else if (sourceType != null) {
            requirementPage = requirementRepository.findBySourceType(
                    SourceType.valueOf(sourceType.toUpperCase()), pageable);
        } else if (status != null) {
            requirementPage = requirementRepository.findByStatus(
                    RequirementStatus.valueOf(status.toUpperCase()), pageable);
        } else {
            requirementPage = requirementRepository.findAll(pageable);
        }

        return PageResponse.of(
                requirementPage.getContent(),
                requirementPage.getTotalElements(),
                page,
                size
        );
    }

    @Override
    public Requirement getById(String id) {
        return requirementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Requirement", id));
    }

    @Override
    @Transactional
    public Requirement update(String id, RequirementCreateRequest request) {
        Requirement requirement = getById(id);
        
        requirement.setTitle(request.getTitle());
        requirement.setContent(request.getContent());
        requirement.setSourceUrl(request.getSourceUrl());
        requirement.setProjectId(request.getProjectId());
        
        if (request.getSourceType() != null) {
            requirement.setSourceType(SourceType.valueOf(request.getSourceType().toUpperCase()));
        }

        return requirementRepository.save(requirement);
    }

    @Override
    @Transactional
    public void delete(String id) {
        if (!requirementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Requirement", id);
        }
        requirementRepository.deleteById(id);
        log.info("Deleted requirement: {}", id);
    }

    @Override
    @Transactional
    public Requirement parse(String id) {
        Requirement requirement = getById(id);
        requirement.setStatus(RequirementStatus.PARSING);
        requirement = requirementRepository.save(requirement);

        // TODO: 调用LLM进行解析
        // Mock解析结果
        String parsedData = "{\"features\": [], \"rules\": [], \"testPoints\": []}";
        requirement.setParsedData(parsedData);
        requirement.setStatus(RequirementStatus.PARSED);

        log.info("Requirement parsed: {}", id);
        return requirementRepository.save(requirement);
    }
}
