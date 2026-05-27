package com.employee.service;

import com.employee.model.dto.EmployeeDto;
import com.employee.model.entity.Employee;

import java.util.List;

public interface EmployeeService {

    EmployeeDto saveEmployee(EmployeeDto employeeDto);

    EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto);

    void deleteEmployee(Long id);

    EmployeeDto getSingleEmployee(Long id);

    List<EmployeeDto> getAllEmployees();
    EmployeeDto getEmployeeByEmpCodeAndEmpName(String empCode, String companyName);
}
