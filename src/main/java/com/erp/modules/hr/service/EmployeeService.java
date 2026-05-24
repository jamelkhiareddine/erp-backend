package com.erp.modules.hr.service;

import com.erp.modules.hr.dto.EmployeeDto;
import com.erp.modules.hr.entity.Employee;
import com.erp.modules.hr.entity.EmploymentStatus;
import com.erp.modules.hr.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Transactional
    public EmployeeDto.Response create(EmployeeDto.Request request) {
        String code = generateEmployeeCode();
        Employee emp = mapToEntity(new Employee(), request);
        emp.setEmployeeCode(code);
        emp.setStatus(EmploymentStatus.ACTIVE);
        return mapToResponse(employeeRepository.save(emp));
    }

    public Page<EmployeeDto.Response> findAll(String search, Pageable pageable) {
        if (search != null && !search.isBlank()) {
            return employeeRepository.searchEmployees(search, pageable).map(this::mapToResponse);
        }
        return employeeRepository.findAll(pageable).map(this::mapToResponse);
    }

    public EmployeeDto.Response findById(Long id) {
        return mapToResponse(employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + id)));
    }

    @Transactional
    public EmployeeDto.Response update(Long id, EmployeeDto.Request request) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + id));
        mapToEntity(emp, request);
        return mapToResponse(employeeRepository.save(emp));
    }

    @Transactional
    public void deactivate(Long id) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + id));
        emp.setStatus(EmploymentStatus.TERMINATED);
        emp.setActive(false);
        employeeRepository.save(emp);
    }

    private String generateEmployeeCode() {
        long count = employeeRepository.count() + 1;
        return "EMP-" + Year.now().getValue() + "-" + String.format("%04d", count);
    }

    private Employee mapToEntity(Employee emp, EmployeeDto.Request req) {
        emp.setFirstName(req.getFirstName());
        emp.setLastName(req.getLastName());
        emp.setEmail(req.getEmail());
        emp.setPhone(req.getPhone());
        emp.setHireDate(req.getHireDate());
        emp.setDepartment(req.getDepartment());
        emp.setPosition(req.getPosition());
        emp.setBaseSalary(req.getBaseSalary());
        emp.setSalaryType(req.getSalaryType());
        emp.setAddress(req.getAddress());
        emp.setCity(req.getCity());
        emp.setCountry(req.getCountry());
        emp.setDateOfBirth(req.getDateOfBirth());
        emp.setGender(req.getGender());
        emp.setNationalId(req.getNationalId());
        return emp;
    }

    private EmployeeDto.Response mapToResponse(Employee emp) {
        EmployeeDto.Response res = new EmployeeDto.Response();
        res.setId(emp.getId());
        res.setEmployeeCode(emp.getEmployeeCode());
        res.setFirstName(emp.getFirstName());
        res.setLastName(emp.getLastName());
        res.setEmail(emp.getEmail());
        res.setPhone(emp.getPhone());
        res.setHireDate(emp.getHireDate());
        res.setStatus(emp.getStatus());
        res.setDepartment(emp.getDepartment());
        res.setPosition(emp.getPosition());
        res.setBaseSalary(emp.getBaseSalary());
        res.setSalaryType(emp.getSalaryType());
        res.setAddress(emp.getAddress());
        res.setCity(emp.getCity());
        res.setCountry(emp.getCountry());
        res.setDateOfBirth(emp.getDateOfBirth());
        res.setGender(emp.getGender());
        return res;
    }
}
