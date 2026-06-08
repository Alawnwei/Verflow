package com.example.veriflow.service;

import com.example.veriflow.dto.response.PageResponse;
import com.example.veriflow.entity.TestScript;

import java.util.List;

/**
 * 测试脚本服务接口
 */
public interface TestScriptService {

    /**
     * 创建测试脚本
     */
    TestScript create(String caseId, String framework, String code, Boolean isAiGenerated, String userId);

    /**
     * 获取脚本列表
     */
    PageResponse<TestScript> list(Integer page, Integer size, String status, String framework);

    /**
     * 获取脚本详情
     */
    TestScript getById(String id);

    /**
     * 更新脚本
     */
    TestScript update(String id, String code, String userId);

    /**
     * 删除脚本
     */
    void delete(String id);

    /**
     * 根据用例生成脚本
     */
    TestScript generateFromCase(String caseId);

    /**
     * 修复脚本
     */
    TestScript repair(String scriptId, String errorMessage);

    /**
     * 审核脚本
     */
    TestScript review(String id, Boolean approved, String reviewerId);

    /**
     * 获取用例关联的脚本
     */
    List<TestScript> getByCaseId(String caseId);
}
