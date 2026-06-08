package com.example.veriflow.service.impl;

import com.example.veriflow.dto.request.TestCaseCreateRequest;
import com.example.veriflow.dto.response.PageResponse;
import com.example.veriflow.entity.TestCase;
import com.example.veriflow.entity.TestCase.*;
import com.example.veriflow.exception.ResourceNotFoundException;
import com.example.veriflow.repository.TestCaseRepository;
import com.example.veriflow.service.TestCaseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 测试用例服务实现
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TestCaseServiceImpl implements TestCaseService {

    private final TestCaseRepository testCaseRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public TestCase create(TestCaseCreateRequest request, String userId) {
        log.info("Creating test case: {}", request.getTitle());

        String testDataJson = null;
        String testStepsJson = null;

        try {
            if (request.getTestData() != null) {
                testDataJson = objectMapper.writeValueAsString(request.getTestData());
            }
            if (request.getTestSteps() != null) {
                testStepsJson = objectMapper.writeValueAsString(request.getTestSteps());
            }
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize test data/steps", e);
            throw new RuntimeException("Failed to serialize test data");
        }

        TestCase testCase = TestCase.builder()
                .id(UUID.randomUUID().toString())
                .caseId(generateCaseId(request.getType()))
                .requirementId(request.getRequirementId())
                .title(request.getTitle())
                .type(TestType.valueOf(request.getType().toUpperCase()))
                .category(Category.valueOf(request.getCategory().toUpperCase()))
                .priority(Priority.valueOf(request.getPriority().toUpperCase()))
                .testData(testDataJson)
                .testSteps(testStepsJson)
                .expectedResult(request.getExpectedResult())
                .createdBy(userId)
                .status(CaseStatus.DRAFT)
                .build();

        return testCaseRepository.save(testCase);
    }

    @Override
    public PageResponse<TestCase> list(Integer page, Integer size, String status, String module, String priority) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        
        Page<TestCase> casePage;
        
        if (status != null) {
            casePage = testCaseRepository.findByStatus(CaseStatus.valueOf(status.toUpperCase()), pageable);
        } else if (module != null) {
            casePage = testCaseRepository.findByModule(module, pageable);
        } else if (priority != null) {
            casePage = testCaseRepository.findByPriority(Priority.valueOf(priority.toUpperCase()), pageable);
        } else {
            casePage = testCaseRepository.findAll(pageable);
        }

        return PageResponse.of(
                casePage.getContent(),
                casePage.getTotalElements(),
                page,
                size
        );
    }

    @Override
    public TestCase getById(String id) {
        return testCaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TestCase", id));
    }

    @Override
    @Transactional
    public TestCase update(String id, TestCaseCreateRequest request, String userId) {
        TestCase testCase = getById(id);

        testCase.setTitle(request.getTitle());
        testCase.setType(TestType.valueOf(request.getType().toUpperCase()));
        testCase.setCategory(Category.valueOf(request.getCategory().toUpperCase()));
        testCase.setPriority(Priority.valueOf(request.getPriority().toUpperCase()));
        testCase.setExpectedResult(request.getExpectedResult());
        testCase.setUpdatedBy(userId);

        try {
            if (request.getTestData() != null) {
                testCase.setTestData(objectMapper.writeValueAsString(request.getTestData()));
            }
            if (request.getTestSteps() != null) {
                testCase.setTestSteps(objectMapper.writeValueAsString(request.getTestSteps()));
            }
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize test data/steps", e);
            throw new RuntimeException("Failed to serialize test data");
        }

        return testCaseRepository.save(testCase);
    }

    @Override
    @Transactional
    public void delete(String id) {
        if (!testCaseRepository.existsById(id)) {
            throw new ResourceNotFoundException("TestCase", id);
        }
        testCaseRepository.deleteById(id);
        log.info("Deleted test case: {}", id);
    }

    @Override
    @Transactional
    public List<TestCase> createBatch(List<TestCaseCreateRequest> requests, String userId) {
        return requests.stream()
                .map(request -> create(request, userId))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TestCase review(String id, Boolean approved, String reviewerId) {
        TestCase testCase = getById(id);
        
        if (approved) {
            testCase.setStatus(CaseStatus.APPROVED);
        } else {
            testCase.setStatus(CaseStatus.REJECTED);
        }
        testCase.setUpdatedBy(reviewerId);

        log.info("Test case reviewed: {} - {}", id, approved ? "APPROVED" : "REJECTED");
        return testCaseRepository.save(testCase);
    }

    @Override
    @Transactional
    public List<TestCase> generateFromRequirement(String requirementId) {
        // TODO: 调用LLM生成测试用例
        // Mock生成结果
        List<TestCase> testCases = new ArrayList<>();

        // 创建一个示例用例
        TestCaseCreateRequest mockRequest = TestCaseCreateRequest.builder()
                .requirementId(requirementId)
                .title("登录功能测试")
                .type("FUNCTIONAL")
                .category("SMOKE")
                .priority("P0")
                .expectedResult("用户成功登录系统")
                .build();

        TestCase testCase = create(mockRequest, "system");
        testCases.add(testCase);

        log.info("Generated {} test cases from requirement: {}", testCases.size(), requirementId);
        return testCases;
    }

    /**
     * 生成用例ID
     */
    private String generateCaseId(String type) {
        String prefix = type.toUpperCase().substring(0, 3);
        return "TC-" + prefix + "-" + System.currentTimeMillis();
    }
}
