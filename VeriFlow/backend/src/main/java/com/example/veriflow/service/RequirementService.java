package com.example.veriflow.service;

import com.example.veriflow.dto.request.RequirementCreateRequest;
import com.example.veriflow.dto.response.PageResponse;
import com.example.veriflow.entity.Requirement;

/**
 * 需求文档服务接口
 */
public interface RequirementService {

    /**
     * 创建需求文档
     */
    Requirement create(RequirementCreateRequest request, String userId);

    /**
     * 获取需求列表
     */
    PageResponse<Requirement> list(Integer page, Integer size, String status, String sourceType);

    /**
     * 获取需求详情
     */
    Requirement getById(String id);

    /**
     * 更新需求
     */
    Requirement update(String id, RequirementCreateRequest request);

    /**
     * 删除需求
     */
    void delete(String id);

    /**
     * 触发AI解析
     */
    Requirement parse(String id);
}
