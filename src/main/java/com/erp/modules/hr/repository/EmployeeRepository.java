package com.erp.modules.hr.repository;

import com.erp.modules.hr.entity.Employee;
import com.erp.modules.hr.entity.EmploymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmployeeCode(String employeeCode);
    Optional<Employee> findByEmail(String email);
    Page<Employee> findByDepartment(String department, Pageable pageable);
    Page<Employee> findByStatus(EmploymentStatus status, Pageable pageable);

    @Query("SELECT e FROM Employee e WHERE " +
           "LOWER(e.firstName) LIKE LOWER(CONCAT('%',:query,'%')) OR " +
           "LOWER(e.lastName) LIKE LOWER(CONCAT('%',:query,'%')) OR " +
           "LOWER(e.email) LIKE LOWER(CONCAT('%',:query,'%')) OR " +
           "e.employeeCode LIKE CONCAT('%',:query,'%')")
    Page<Employee> searchEmployees(String query, Pageable pageable);

    long countByStatus(EmploymentStatus status);
    long countByDepartment(String department);
}
