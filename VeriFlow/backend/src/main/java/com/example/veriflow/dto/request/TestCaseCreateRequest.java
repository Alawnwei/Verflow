package com.example.veriflow.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 创建测试用例请求DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestCaseCreateRequest {

    private String requirementId;

    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "类型不能为空")
    private String type;

    @NotBlank(message = "分类不能为空")
    private String category;

    @NotBlank(message = "优先级不能为空")
    private String priority;

    private List<String> preconditions;

    private Map<String, Object> testData;

    @NotNull(message = "测试步骤不能为空")
    private List<TestStepDTO> testSteps;

    private String expectedResult;

    private List<String> postconditions;

    private List<String> tags;

    private McpConfigDTO mcpConfig;

    /**
     * 测试步骤DTO
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TestStepDTO {
        private Integer order;
        private String step;
        private String expected;
        private String actual;
    }

    /**
     * MCP配置DTO
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class McpConfigDTO {
        private Boolean enabled;
        private List<McpToolDTO> tools;
        private List<String> contextValidation;
    }

    /**
     * MCP工具DTO
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class McpToolDTO {
        private String name;
        private String action;
        private Map<String, String> params;
    }
}
