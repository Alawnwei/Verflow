package com.example.veriflow.repository;

import com.example.veriflow.entity.Requirement;
import com.example.veriflow.entity.Requirement.RequirementStatus;
import com.example.veriflow.entity.Requirement.SourceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 需求文档Repository
 */
@Repository
public interface RequirementRepository extends JpaRepository<Requirement, String> {

    Page<Requirement> findByStatus(RequirementStatus status, Pageable pageable);

    Page<Requirement> findBySourceType(SourceType sourceType, Pageable pageable);

    Page<Requirement> findByProjectId(String projectId, Pageable pageable);

    List<Requirement> findByStatusIn(List<RequirementStatus> statuses);
}
