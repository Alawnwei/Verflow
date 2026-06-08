package com.example.veriflow.repository;

import com.example.veriflow.entity.TestCase;
import com.example.veriflow.entity.TestCase.CaseStatus;
import com.example.veriflow.entity.TestCase.Category;
import com.example.veriflow.entity.TestCase.Priority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 测试用例Repository
 */
@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, String> {

    Optional<TestCase> findByCaseId(String caseId);

    Page<TestCase> findByStatus(CaseStatus status, Pageable pageable);

    Page<TestCase> findByModule(String module, Pageable pageable);

    Page<TestCase> findByPriority(Priority priority, Pageable pageable);

    Page<TestCase> findByCategory(Category category, Pageable pageable);

    Page<TestCase> findByRequirementId(String requirementId, Pageable pageable);

    @Query("SELECT tc FROM TestCase tc WHERE tc.status = :status")
    List<TestCase> findAllByStatus(@Param("status") CaseStatus status);

    @Query("SELECT tc FROM TestCase tc WHERE tc.requirementId = :requirementId")
    List<TestCase> findAllByRequirementId(@Param("requirementId") String requirementId);
}
