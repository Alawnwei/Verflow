package com.example.veriflow.controller;

import com.example.veriflow.dto.request.RequirementCreateRequest;
import com.example.veriflow.dto.response.ApiResponse;
import com.example.veriflow.dto.response.PageResponse;
import com.example.veriflow.entity.Requirement;
import com.example.veriflow.service.RequirementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 需求文档控制器
 */
@RestController
@RequestMapping("/api/requirements")
@RequiredArgsConstructor
@Slf4j
public class RequirementController {

    private final RequirementService requirementService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Requirement> create(@Valid @RequestBody RequirementCreateRequest request) {
        log.info("Create requirement request: {}", request.getTitle());
        Requirement requirement = requirementService.create(request, "currentUser");
        return ApiResponse.created(requirement);
    }

    @GetMapping
    public ApiResponse<PageResponse<Requirement>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String sourceType) {
        PageResponse<Requirement> response = requirementService.list(page, size, status, sourceType);
        return ApiResponse.success(response);
    }

    @GetMapping("/{id}")
    public ApiResponse<Requirement> getById(@PathVariable String id) {
        Requirement requirement = requirementService.getById(id);
        return ApiResponse.success(requirement);
    }

    @PutMapping("/{id}")
    public ApiResponse<Requirement> update(@PathVariable String id, 
                                           @Valid @RequestBody RequirementCreateRequest request) {
        Requirement requirement = requirementService.update(id, request);
        return ApiResponse.success(requirement);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        requirementService.delete(id);
    }

    @PostMapping("/{id}/parse")
    public ApiResponse<Requirement> parse(@PathVariable String id) {
        Requirement requirement = requirementService.parse(id);
        return ApiResponse.success(requirement);
    }
}
