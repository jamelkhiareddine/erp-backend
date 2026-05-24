package com.erp.modules.hr.dto;

import com.erp.modules.hr.entity.EmploymentStatus;
import com.erp.modules.hr.entity.Gender;
import com.erp.modules.hr.entity.SalaryType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeDto {

    @Data
    public static class Request {
        @NotBlank private String firstName;
        @NotBlank private String lastName;
        @Email @NotBlank private String email;
        private String phone;
        @NotNull private LocalDate hireDate;
        @NotBlank private String department;
        @NotBlank private String position;
        private BigDecimal baseSalary;
        private SalaryType salaryType;
        private String address;
        private String city;
        private String country;
        private LocalDate dateOfBirth;
        private Gender gender;
        private String nationalId;
    }

    @Data
    public static class Response {
        private Long id;
        private String employeeCode;
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private LocalDate hireDate;
        private EmploymentStatus status;
        private String department;
        private String position;
        private BigDecimal baseSalary;
        private SalaryType salaryType;
        private String address;
        private String city;
        private String country;
        private LocalDate dateOfBirth;
        private Gender gender;
    }
}
