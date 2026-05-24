package com.erp.modules.finance.repository;

import com.erp.modules.finance.entity.Invoice;
import com.erp.modules.finance.entity.InvoiceStatus;
import com.erp.modules.finance.entity.InvoiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);
    Page<Invoice> findByType(InvoiceType type, Pageable pageable);
    Page<Invoice> findByStatus(InvoiceStatus status, Pageable pageable);
    Page<Invoice> findByTypeAndStatus(InvoiceType type, InvoiceStatus status, Pageable pageable);

    @Query("SELECT SUM(i.totalAmount) FROM Invoice i WHERE i.type = :type AND i.status != 'CANCELLED'")
    BigDecimal sumByType(InvoiceType type);

    @Query("SELECT SUM(i.totalAmount - COALESCE(i.paidAmount, 0)) FROM Invoice i " +
           "WHERE i.status IN ('SENT','PARTIALLY_PAID') AND i.dueDate < :today")
    BigDecimal sumOverdueAmount(LocalDate today);
}
