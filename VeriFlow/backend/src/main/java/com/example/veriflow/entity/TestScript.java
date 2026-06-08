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
 * 测试脚本实体
 */
@Entity
@Table(name = "test_scripts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestScript {

    @Id
    @Column(length = 36)
    private String id;

    @Column(name = "case_id", length = 36)
    private String caseId;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Framework framework;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String code;

    @Column(nullable = false, length = 20)
    @Builder.Default
    private String version = "v1.0";

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ScriptStatus status = ScriptStatus.GENERATED;

    @Column(name = "is_ai_generated", nullable = false)
    @Builder.Default
    private Boolean isAiGenerated = true;

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
     * 测试框架枚举
     */
    public enum Framework {
        PLAYWRIGHT, SELENIUM, REST_ASSURED, POSTMAN, MCP
    }

    /**
     * 脚本状态枚举
     */
    public enum ScriptStatus {
        GENERATED, EDITED, REVIEWING, REVIEWED, FROZEN, EXECUTING, PASSED, FAILED
    }
}
