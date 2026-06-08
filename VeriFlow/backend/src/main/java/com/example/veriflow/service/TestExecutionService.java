package com.example.veriflow.service;

import com.example.veriflow.dto.response.PageResponse;
import com.example.veriflow.entity.TestExecution;

import java.util.List;

/**
 * 测试执行服务接口
 */
public interface TestExecutionService {

    /**
     * 执行测试脚本
     */
    TestExecution execute(String scriptId, String executionType);

    /**
     * 获取执行记录列表
     */
    PageResponse<TestExecution> list(Integer page, Integer size, String status, String executionType);

    /**
     * 获取执行记录详情
     */
    TestExecution getById(String id);

    /**
     * 获取脚本的执行历史
     */
    List<TestExecution> getByScriptId(String scriptId);

    /**
     * 批量执行测试脚本
     */
    List<TestExecution> executeBatch(List<String> scriptIds, String executionType);

    /**
     * 触发自我修复
     */
    TestExecution repairAndRetry(String executionId);

    /**
     * 编排测试执行（冒烟/E2E/回归）
     */
    List<TestExecution> orchestrate(String orchestrationType, List<String> scriptIds);
}
