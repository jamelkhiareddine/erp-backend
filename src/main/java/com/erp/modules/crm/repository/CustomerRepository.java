package com.erp.modules.crm.repository;

import com.erp.modules.crm.entity.Customer;
import com.erp.modules.crm.entity.CustomerStatus;
import com.erp.modules.crm.entity.CustomerType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByCustomerCode(String customerCode);
    Optional<Customer> findByEmail(String email);
    Page<Customer> findByType(CustomerType type, Pageable pageable);
    Page<Customer> findByStatus(CustomerStatus status, Pageable pageable);
    Page<Customer> findByAssignedTo(String username, Pageable pageable);

    @Query("SELECT c FROM Customer c WHERE " +
           "LOWER(c.companyName) LIKE LOWER(CONCAT('%',:query,'%')) OR " +
           "LOWER(c.contactFirstName) LIKE LOWER(CONCAT('%',:query,'%')) OR " +
           "LOWER(c.contactLastName) LIKE LOWER(CONCAT('%',:query,'%')) OR " +
           "LOWER(c.email) LIKE LOWER(CONCAT('%',:query,'%'))")
    Page<Customer> searchCustomers(String query, Pageable pageable);
}
