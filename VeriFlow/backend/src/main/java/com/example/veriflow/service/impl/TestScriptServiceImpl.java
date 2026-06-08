package com.example.veriflow.service.impl;

import com.example.veriflow.dto.response.PageResponse;
import com.example.veriflow.entity.TestScript;
import com.example.veriflow.entity.TestScript.*;
import com.example.veriflow.exception.ResourceNotFoundException;
import com.example.veriflow.repository.TestScriptRepository;
import com.example.veriflow.service.TestScriptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * 测试脚本服务实现
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TestScriptServiceImpl implements TestScriptService {

    private final TestScriptRepository testScriptRepository;

    @Override
    @Transactional
    public TestScript create(String caseId, String framework, String code, Boolean isAiGenerated, String userId) {
        log.info("Creating test script for case: {}", caseId);

        TestScript testScript = TestScript.builder()
                .id(UUID.randomUUID().toString())
                .caseId(caseId)
                .framework(Framework.valueOf(framework.toUpperCase()))
                .code(code)
                .isAiGenerated(isAiGenerated)
                .createdBy(userId)
                .status(ScriptStatus.GENERATED)
                .build();

        return testScriptRepository.save(testScript);
    }

    @Override
    public PageResponse<TestScript> list(Integer page, Integer size, String status, String framework) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        
        Page<TestScript> scriptPage;
        
        if (status != null) {
            scriptPage = testScriptRepository.findByStatus(ScriptStatus.valueOf(status.toUpperCase()), pageable);
        } else if (framework != null) {
            scriptPage = testScriptRepository.findByFramework(Framework.valueOf(framework.toUpperCase()), pageable);
        } else {
            scriptPage = testScriptRepository.findAll(pageable);
        }

        return PageResponse.of(
                scriptPage.getContent(),
                scriptPage.getTotalElements(),
                page,
                size
        );
    }

    @Override
    public TestScript getById(String id) {
        return testScriptRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TestScript", id));
    }

    @Override
    @Transactional
    public TestScript update(String id, String code, String userId) {
        TestScript testScript = getById(id);
        
        testScript.setCode(code);
        testScript.setIsAiGenerated(false);
        testScript.setUpdatedBy(userId);
        testScript.setStatus(ScriptStatus.EDITED);

        log.info("Updated test script: {}", id);
        return testScriptRepository.save(testScript);
    }

    @Override
    @Transactional
    public void delete(String id) {
        if (!testScriptRepository.existsById(id)) {
            throw new ResourceNotFoundException("TestScript", id);
        }
        testScriptRepository.deleteById(id);
        log.info("Deleted test script: {}", id);
    }

    @Override
    @Transactional
    public TestScript generateFromCase(String caseId) {
        // TODO: 调用LLM生成测试脚本
        // Mock生成的Playwright脚本
        String mockCode = """
            const { test, expect } = require('@playwright/test');
            
            test('test_case', async ({ page }) => {
                await page.goto('https://admin.example.com/login');
                await page.fill('input[name="email"]', 'test@example.com');
                await page.fill('input[name="password"]', '123456');
                await page.click('button[type="submit"]');
                await expect(page).toHaveURL('https://admin.example.com/dashboard');
            });
            """;

        TestScript testScript = TestScript.builder()
                .id(UUID.randomUUID().toString())
                .caseId(caseId)
                .framework(Framework.PLAYWRIGHT)
                .code(mockCode)
                .isAiGenerated(true)
                .createdBy("system")
                .status(ScriptStatus.GENERATED)
                .build();

        log.info("Generated test script for case: {}", caseId);
        return testScriptRepository.save(testScript);
    }

    @Override
    @Transactional
    public TestScript repair(String scriptId, String errorMessage) {
        TestScript script = getById(scriptId);
        
        // TODO: 调用LLM进行脚本修复
        // Mock修复后的脚本
        String repairedCode = script.getCode() + "\n// Repaired by AI: " + errorMessage;
        
        script.setCode(repairedCode);
        script.setIsAiGenerated(true);
        script.setStatus(ScriptStatus.GENERATED);

        log.info("Repaired test script: {}", scriptId);
        return testScriptRepository.save(script);
    }

    @Override
    @Transactional
    public TestScript review(String id, Boolean approved, String reviewerId) {
        TestScript testScript = getById(id);
        
        if (approved) {
            testScript.setStatus(ScriptStatus.REVIEWED);
        } else {
            testScript.setStatus(ScriptStatus.GENERATED);
        }
        testScript.setUpdatedBy(reviewerId);

        log.info("Test script reviewed: {} - {}", id, approved ? "REVIEWED" : "REJECTED");
        return testScriptRepository.save(testScript);
    }

    @Override
    public List<TestScript> getByCaseId(String caseId) {
        return testScriptRepository.findByCaseId(caseId);
    }
}
