package com.example.veriflow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 测试用例实体
 */
@Entity
@Table(name = "test_cases")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestCase {

    @Id
    @Column(length = 36)
    private String id;

    @Column(name = "case_id", unique = true, nullable = false, length = 50)
    private String caseId;

    @Column(name = "requirement_id", length = 36)
    private String requirementId;

    @Column(nullable = false, length = 100)
    private String module;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private TestType type;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(name = "preconditions", columnDefinition = "TEXT")
    private String preconditions;

    @Column(name = "test_data", columnDefinition = "JSON")
    private String testData;

    @Column(name = "test_steps", columnDefinition = "JSON", nullable = false)
    private String testSteps;

    @Column(name = "expected_result", columnDefinition = "TEXT")
    private String expectedResult;

    @Column(name = "actual_result", columnDefinition = "TEXT")
    private String actualResult;

    @Column(name = "postconditions", columnDefinition = "TEXT")
    private String postconditions;

    @Column(columnDefinition = "TEXT")
    private String tags;

    @Column(name = "estimated_time", length = 20)
    private String estimatedTime;

    @Column(name = "mcp_config", columnDefinition = "JSON")
    private String mcpConfig;

    @Column(name = "related_bug", length = 50)
    private String relatedBug;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private CaseStatus status = CaseStatus.DRAFT;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "updated_by", length = 100)
    private String updatedBy;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * 测试类型枚举
     */
    public enum TestType {
        FUNCTIONAL, E2E, REGRESSION, SMOKE, PERFORMANCE, SECURITY
    }

    /**
     * 分类枚举
     */
    public enum Category {
        SMOKE, E2E, REGRESSION, PERFORMANCE, SECURITY
    }

    /**
     * 优先级枚举
     */
    public enum Priority {
        P0, P1, P2
    }

    /**
     * 用例状态枚举
     */
    public enum CaseStatus {
        DRAFT, REVIEWING, APPROVED, REJECTED, FROZEN
    }
}
