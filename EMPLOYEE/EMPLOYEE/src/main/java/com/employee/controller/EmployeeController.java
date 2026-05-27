package com.employee.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.employee.exception.MissingParameterException;
import com.employee.model.dto.EmployeeDto;
import com.employee.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto saveEmployee(@RequestBody EmployeeDto employeeDto) {
        return employeeService.saveEmployee(employeeDto);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto updateEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable Long id) {
        return employeeService.updateEmployee(id, employeeDto);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "Employee Deleted Successfully";
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto getEmployeeById(@PathVariable Long id) {
        return employeeService.getSingleEmployee(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDto> getAllEmployee() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/getbycodename")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto getEmployeeByEmployeeCodeAndEmployeeName(
            @RequestParam(required = false) String empCode,
            @RequestParam(required = false) String companyName) {
        List<String> missingParameters = new ArrayList<>();
        if (empCode == null || empCode.trim().isEmpty()) {
            missingParameters.add("employeeCode");
        }
        if (companyName == null || companyName.trim().isEmpty()) {
            missingParameters.add("companyName");
        }
        if (!missingParameters.isEmpty()) {
            String finalMessage = String.join(",", missingParameters);
            throw new MissingParameterException("Please Provide : " + finalMessage);
        }

        return employeeService.getEmployeeByEmpCodeAndEmpName(empCode, companyName);

    }

}
