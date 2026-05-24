package com.erp.modules.hr.controller;

import com.erp.common.ApiResponse;
import com.erp.modules.hr.dto.EmployeeDto;
import com.erp.modules.hr.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "HR - Employees", description = "Employee management")
@RestController
@RequestMapping("/api/hr/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_HR_MANAGER')")
    public ResponseEntity<ApiResponse<EmployeeDto.Response>> create(@Valid @RequestBody EmployeeDto.Request req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Employee created", employeeService.create(req)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_HR_MANAGER','ROLE_HR_STAFF')")
    public ResponseEntity<ApiResponse<Page<EmployeeDto.Response>>> findAll(
            @RequestParam(required = false) String search, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(employeeService.findAll(search, pageable)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_HR_MANAGER','ROLE_HR_STAFF')")
    public ResponseEntity<ApiResponse<EmployeeDto.Response>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(employeeService.findById(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_HR_MANAGER')")
    public ResponseEntity<ApiResponse<EmployeeDto.Response>> update(
            @PathVariable Long id, @Valid @RequestBody EmployeeDto.Request req) {
        return ResponseEntity.ok(ApiResponse.success("Employee updated", employeeService.update(id, req)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deactivate(@PathVariable Long id) {
        employeeService.deactivate(id);
        return ResponseEntity.ok(ApiResponse.success("Employee deactivated", null));
    }
}
