package com.example.veriflow.controller;

import com.example.veriflow.dto.request.TestCaseCreateRequest;
import com.example.veriflow.dto.response.ApiResponse;
import com.example.veriflow.dto.response.PageResponse;
import com.example.veriflow.entity.TestCase;
import com.example.veriflow.service.TestCaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 测试用例控制器
 */
@RestController
@RequestMapping("/api/test-cases")
@RequiredArgsConstructor
@Slf4j
public class TestCaseController {

    private final TestCaseService testCaseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<TestCase> create(@Valid @RequestBody TestCaseCreateRequest request) {
        log.info("Create test case request: {}", request.getTitle());
        TestCase testCase = testCaseService.create(request, "currentUser");
        return ApiResponse.created(testCase);
    }

    @GetMapping
    public ApiResponse<PageResponse<TestCase>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String module,
            @RequestParam(required = false) String priority) {
        PageResponse<TestCase> response = testCaseService.list(page, size, status, module, priority);
        return ApiResponse.success(response);
    }

    @GetMapping("/{id}")
    public ApiResponse<TestCase> getById(@PathVariable String id) {
        TestCase testCase = testCaseService.getById(id);
        return ApiResponse.success(testCase);
    }

    @PutMapping("/{id}")
    public ApiResponse<TestCase> update(@PathVariable String id, 
                                        @Valid @RequestBody TestCaseCreateRequest request) {
        TestCase testCase = testCaseService.update(id, request, "currentUser");
        return ApiResponse.success(testCase);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        testCaseService.delete(id);
    }

    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<List<TestCase>> createBatch(@Valid @RequestBody List<TestCaseCreateRequest> requests) {
        List<TestCase> testCases = testCaseService.createBatch(requests, "currentUser");
        return ApiResponse.created(testCases);
    }

    @PostMapping("/{id}/review")
    public ApiResponse<TestCase> review(@PathVariable String id, @RequestBody Map<String, Boolean> body) {
        Boolean approved = body.get("approved");
        TestCase testCase = testCaseService.review(id, approved, "currentUser");
        return ApiResponse.success(testCase);
    }

    @PostMapping("/generate/{requirementId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<List<TestCase>> generateFromRequirement(@PathVariable String requirementId) {
        List<TestCase> testCases = testCaseService.generateFromRequirement(requirementId);
        return ApiResponse.created(testCases);
    }
}
