package com.erp.modules.crm.entity;

import com.erp.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "customers")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Customer extends BaseEntity {

    @Column(nullable = false, unique = true, length = 20)
    private String customerCode;

    @Column(nullable = false, length = 150)
    private String companyName;

    private String contactFirstName;
    private String contactLastName;

    @Column(unique = true)
    private String email;

    private String phone;
    private String website;
    private String address;
    private String city;
    private String country;

    @Enumerated(EnumType.STRING)
    private CustomerType type; // PROSPECT, CUSTOMER, VIP, INACTIVE

    @Enumerated(EnumType.STRING)
    private CustomerStatus status;

    private String industry;

    @Column(precision = 15, scale = 2)
    private BigDecimal creditLimit;

    @Column(precision = 15, scale = 2)
    private BigDecimal totalRevenue;

    private LocalDate lastContactDate;

    private String assignedTo; // username of sales rep

    @Column(length = 1000)
    private String notes;
}
