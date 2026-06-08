package com.example.veriflow.service.impl;

import com.example.veriflow.dto.response.PageResponse;
import com.example.veriflow.entity.TestExecution;
import com.example.veriflow.entity.TestExecution.*;
import com.example.veriflow.entity.TestScript;
import com.example.veriflow.exception.ResourceNotFoundException;
import com.example.veriflow.repository.TestExecutionRepository;
import com.example.veriflow.repository.TestScriptRepository;
import com.example.veriflow.service.TestExecutionService;
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
import java.util.stream.Collectors;

/**
 * 测试执行服务实现
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TestExecutionServiceImpl implements TestExecutionService {

    private final TestExecutionRepository testExecutionRepository;
    private final TestScriptRepository testScriptRepository;
    private final TestScriptServiceImpl testScriptService;

    @Override
    @Transactional
    public TestExecution execute(String scriptId, String executionType) {
        log.info("Executing script: {}", scriptId);

        TestScript script = testScriptRepository.findById(scriptId)
                .orElseThrow(() -> new ResourceNotFoundException("TestScript", scriptId));

        TestExecution execution = TestExecution.builder()
                .id(UUID.randomUUID().toString())
                .scriptId(scriptId)
                .executionType(ExecutionType.valueOf(executionType.toUpperCase()))
                .status(ExecutionStatus.RUNNING)
                .build();

        execution = testExecutionRepository.save(execution);

        // TODO: 实际执行测试脚本
        // Mock执行结果
        try {
            Thread.sleep(1000);
            execution.setStatus(ExecutionStatus.PASSED);
            execution.setDuration(1000);
        } catch (Exception e) {
            execution.setStatus(ExecutionStatus.FAILED);
            execution.setErrorMessage(e.getMessage());
        }

        return testExecutionRepository.save(execution);
    }

    @Override
    public PageResponse<TestExecution> list(Integer page, Integer size, String status, String executionType) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        
        Page<TestExecution> executionPage;
        
        if (status != null) {
            executionPage = testExecutionRepository.findByStatus(ExecutionStatus.valueOf(status.toUpperCase()), pageable);
        } else if (executionType != null) {
            executionPage = testExecutionRepository.findByExecutionType(ExecutionType.valueOf(executionType.toUpperCase()), pageable);
        } else {
            executionPage = testExecutionRepository.findAll(pageable);
        }

        return PageResponse.of(
                executionPage.getContent(),
                executionPage.getTotalElements(),
                page,
                size
        );
    }

    @Override
    public TestExecution getById(String id) {
        return testExecutionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TestExecution", id));
    }

    @Override
    public List<TestExecution> getByScriptId(String scriptId) {
        return testExecutionRepository.findByScriptId(scriptId);
    }

    @Override
    @Transactional
    public List<TestExecution> executeBatch(List<String> scriptIds, String executionType) {
        return scriptIds.stream()
                .map(scriptId -> execute(scriptId, executionType))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TestExecution repairAndRetry(String executionId) {
        TestExecution execution = getById(executionId);
        
        // 修复脚本
        testScriptService.repair(execution.getScriptId(), execution.getErrorMessage());
        
        // 重新执行
        execution.setRetryCount(execution.getRetryCount() + 1);
        execution.setAiRepaired(true);
        
        return execute(execution.getScriptId(), execution.getExecutionType().name());
    }

    @Override
    @Transactional
    public List<TestExecution> orchestrate(String orchestrationType, List<String> scriptIds) {
        log.info("Orchestrating {} test: {} scripts", orchestrationType, scriptIds.size());
        
        ExecutionType type = switch (orchestrationType.toLowerCase()) {
            case "smoke" -> ExecutionType.SMOKE;
            case "e2e" -> ExecutionType.E2E;
            case "regression" -> ExecutionType.REGRESSION;
            default -> ExecutionType.MANUAL;
        };

        return scriptIds.stream()
                .map(scriptId -> execute(scriptId, type.name()))
                .collect(Collectors.toList());
    }
}
