package com.example.veriflow.service;

import com.example.veriflow.dto.request.TestCaseCreateRequest;
import com.example.veriflow.dto.response.PageResponse;
import com.example.veriflow.entity.TestCase;

import java.util.List;

/**
 * 测试用例服务接口
 */
public interface TestCaseService {

    /**
     * 创建测试用例
     */
    TestCase create(TestCaseCreateRequest request, String userId);

    /**
     * 获取测试用例列表
     */
    PageResponse<TestCase> list(Integer page, Integer size, String status, String module, String priority);

    /**
     * 获取测试用例详情
     */
    TestCase getById(String id);

    /**
     * 更新测试用例
     */
    TestCase update(String id, TestCaseCreateRequest request, String userId);

    /**
     * 删除测试用例
     */
    void delete(String id);

    /**
     * 批量创建测试用例
     */
    List<TestCase> createBatch(List<TestCaseCreateRequest> requests, String userId);

    /**
     * 审核测试用例
     */
    TestCase review(String id, Boolean approved, String reviewerId);

    /**
     * 从需求生成测试用例
     */
    List<TestCase> generateFromRequirement(String requirementId);
}
