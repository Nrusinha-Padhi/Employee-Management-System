package com.example.employeemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.employeemanagement.entity.Audit_logs;


@Repository
public interface AuditLogRepository extends JpaRepository<Audit_logs,Long>{
	
}
