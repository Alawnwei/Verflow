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
 * 需求文档实体
 */
@Entity
@Table(name = "requirements")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Requirement {

    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(name = "source_type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private SourceType sourceType;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "parsed_data", columnDefinition = "JSON")
    private String parsedData;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private RequirementStatus status = RequirementStatus.PENDING;

    @Column(name = "source_url", length = 500)
    private String sourceUrl;

    @Column(name = "project_id", length = 36)
    private String projectId;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * 来源类型枚举
     */
    public enum SourceType {
        BSD, JIRA, LARK, MARKDOWN, SCREENSHOT, AXURE, FIGMA, MODAO, EXCEL
    }

    /**
     * 需求状态枚举
     */
    public enum RequirementStatus {
        PENDING, PARSING, PARSED, ANALYZING, ANALYZED, ERROR
    }
}
