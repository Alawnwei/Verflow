package com.example.veriflow.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创建需求文档请求DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequirementCreateRequest {

    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "来源类型不能为空")
    private String sourceType;

    @NotBlank(message = "内容不能为空")
    private String content;

    private String sourceUrl;

    private String projectId;
}
