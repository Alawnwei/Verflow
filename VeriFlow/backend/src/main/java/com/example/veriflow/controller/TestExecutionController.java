package com.example.veriflow.controller;

import com.example.veriflow.dto.response.ApiResponse;
import com.example.veriflow.dto.response.PageResponse;
import com.example.veriflow.entity.TestExecution;
import com.example.veriflow.service.TestExecutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 测试执行控制器
 */
@RestController
@RequestMapping("/api/test-executions")
@RequiredArgsConstructor
@Slf4j
public class TestExecutionController {

    private final TestExecutionService testExecutionService;

    @GetMapping
    public ApiResponse<PageResponse<TestExecution>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String executionType) {
        PageResponse<TestExecution> response = testExecutionService.list(page, size, status, executionType);
        return ApiResponse.success(response);
    }

    @GetMapping("/{id}")
    public ApiResponse<TestExecution> getById(@PathVariable String id) {
        TestExecution execution = testExecutionService.getById(id);
        return ApiResponse.success(execution);
    }

    @PostMapping("/execute/{scriptId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<TestExecution> execute(@PathVariable String scriptId, 
                                              @RequestBody(required = false) Map<String, String> body) {
        String executionType = body != null ? body.getOrDefault("executionType", "MANUAL") : "MANUAL";
        TestExecution execution = testExecutionService.execute(scriptId, executionType);
        return ApiResponse.created(execution);
    }

    @PostMapping("/execute-batch")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<List<TestExecution>> executeBatch(@RequestBody Map<String, Object> body) {
        @SuppressWarnings("unchecked")
        List<String> scriptIds = (List<String>) body.get("scriptIds");
        String executionType = (String) body.getOrDefault("executionType", "MANUAL");
        List<TestExecution> executions = testExecutionService.executeBatch(scriptIds, executionType);
        return ApiResponse.created(executions);
    }

    @PostMapping("/{id}/repair-retry")
    public ApiResponse<TestExecution> repairAndRetry(@PathVariable String id) {
        TestExecution execution = testExecutionService.repairAndRetry(id);
        return ApiResponse.success(execution);
    }

    @PostMapping("/orchestrate/{orchestrationType}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<List<TestExecution>> orchestrate(
            @PathVariable String orchestrationType,
            @RequestBody Map<String, Object> body) {
        @SuppressWarnings("unchecked")
        List<String> scriptIds = (List<String>) body.get("scriptIds");
        List<TestExecution> executions = testExecutionService.orchestrate(orchestrationType, scriptIds);
        return ApiResponse.created(executions);
    }

    @GetMapping("/script/{scriptId}")
    public ApiResponse<List<TestExecution>> getByScriptId(@PathVariable String scriptId) {
        List<TestExecution> executions = testExecutionService.getByScriptId(scriptId);
        return ApiResponse.success(executions);
    }
}
