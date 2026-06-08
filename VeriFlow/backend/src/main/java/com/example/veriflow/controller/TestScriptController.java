package com.example.veriflow.controller;

import com.example.veriflow.dto.response.ApiResponse;
import com.example.veriflow.dto.response.PageResponse;
import com.example.veriflow.entity.TestScript;
import com.example.veriflow.service.TestScriptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 测试脚本控制器
 */
@RestController
@RequestMapping("/api/test-scripts")
@RequiredArgsConstructor
@Slf4j
public class TestScriptController {

    private final TestScriptService testScriptService;

    @GetMapping
    public ApiResponse<PageResponse<TestScript>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String framework) {
        PageResponse<TestScript> response = testScriptService.list(page, size, status, framework);
        return ApiResponse.success(response);
    }

    @GetMapping("/{id}")
    public ApiResponse<TestScript> getById(@PathVariable String id) {
        TestScript testScript = testScriptService.getById(id);
        return ApiResponse.success(testScript);
    }

    @PutMapping("/{id}")
    public ApiResponse<TestScript> update(@PathVariable String id, @RequestBody Map<String, String> body) {
        String code = body.get("code");
        TestScript testScript = testScriptService.update(id, code, "currentUser");
        return ApiResponse.success(testScript);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        testScriptService.delete(id);
    }

    @PostMapping("/generate/{caseId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<TestScript> generateFromCase(@PathVariable String caseId) {
        TestScript testScript = testScriptService.generateFromCase(caseId);
        return ApiResponse.created(testScript);
    }

    @PostMapping("/{id}/repair")
    public ApiResponse<TestScript> repair(@PathVariable String id, @RequestBody Map<String, String> body) {
        String errorMessage = body.get("errorMessage");
        TestScript testScript = testScriptService.repair(id, errorMessage);
        return ApiResponse.success(testScript);
    }

    @PostMapping("/{id}/review")
    public ApiResponse<TestScript> review(@PathVariable String id, @RequestBody Map<String, Boolean> body) {
        Boolean approved = body.get("approved");
        TestScript testScript = testScriptService.review(id, approved, "currentUser");
        return ApiResponse.success(testScript);
    }

    @GetMapping("/case/{caseId}")
    public ApiResponse<List<TestScript>> getByCaseId(@PathVariable String caseId) {
        List<TestScript> testScripts = testScriptService.getByCaseId(caseId);
        return ApiResponse.success(testScripts);
    }
}
