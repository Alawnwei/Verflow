package com.example.veriflow.repository;

import com.example.veriflow.entity.TestExecution;
import com.example.veriflow.entity.TestExecution.ExecutionStatus;
import com.example.veriflow.entity.TestExecution.ExecutionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 测试执行记录Repository
 */
@Repository
public interface TestExecutionRepository extends JpaRepository<TestExecution, String> {

    Page<TestExecution> findByStatus(ExecutionStatus status, Pageable pageable);

    Page<TestExecution> findByExecutionType(ExecutionType executionType, Pageable pageable);

    List<TestExecution> findByScriptId(String scriptId);

    @Query("SELECT te FROM TestExecution te WHERE te.createdAt BETWEEN :startTime AND :endTime")
    List<TestExecution> findByTimeRange(@Param("startTime") LocalDateTime startTime, 
                                        @Param("endTime") LocalDateTime endTime);

    @Query("SELECT COUNT(te) FROM TestExecution te WHERE te.status = :status")
    long countByStatus(@Param("status") ExecutionStatus status);

    @Query("SELECT AVG(te.duration) FROM TestExecution te WHERE te.duration IS NOT NULL")
    Double averageDuration();
}
