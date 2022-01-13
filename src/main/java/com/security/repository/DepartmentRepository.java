package com.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, String> {

	Department findByName(String name);

}
