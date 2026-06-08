package com.example.veriflow.repository;

import com.example.veriflow.entity.TestScript;
import com.example.veriflow.entity.TestScript.Framework;
import com.example.veriflow.entity.TestScript.ScriptStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 测试脚本Repository
 */
@Repository
public interface TestScriptRepository extends JpaRepository<TestScript, String> {

    Page<TestScript> findByStatus(ScriptStatus status, Pageable pageable);

    Page<TestScript> findByFramework(Framework framework, Pageable pageable);

    List<TestScript> findByCaseId(String caseId);

    List<TestScript> findByCaseIdIn(List<String> caseIds);
}
