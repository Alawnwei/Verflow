package com.example.veriflow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 测试执行记录实体
 */
@Entity
@Table(name = "test_executions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestExecution {

    @Id
    @Column(length = 36)
    private String id;

    @Column(name = "script_id", length = 36)
    private String scriptId;

    @Column(name = "execution_type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private ExecutionType executionType;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ExecutionStatus status = ExecutionStatus.RUNNING;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @Column
    private Integer duration;

    @Column(name = "retry_count")
    @Builder.Default
    private Integer retryCount = 0;

    @Column(name = "ai_repaired")
    @Builder.Default
    private Boolean aiRepaired = false;

    @Column(name = "repair_attempts")
    @Builder.Default
    private Integer repairAttempts = 0;

    @Column(length = 100)
    private String environment;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 执行类型枚举
     */
    public enum ExecutionType {
        SMOKE, E2E, REGRESSION, SCHEDULED, MANUAL
    }

    /**
     * 执行状态枚举
     */
    public enum ExecutionStatus {
        RUNNING, PASSED, FAILED, SKIPPED
    }
}
