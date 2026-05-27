package com.employee.service.impl;

import com.employee.exception.BadRequestException;
import com.employee.exception.ResourceNotFoundException;
import com.employee.model.dto.EmployeeDto;
import com.employee.model.entity.Employee;
import com.employee.repository.EmployeeRepository;
import com.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;


    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        if(employeeDto.getId() != null){
            throw new BadRequestException("Employee already exist");
        }
        Employee entity = modelMapper.map(employeeDto, Employee.class);
        Employee saveEntity = employeeRepository.save(entity);
        return modelMapper.map(saveEntity,EmployeeDto.class);
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        if(employeeDto.getId()==null || id ==null)
            throw new BadRequestException("Please Provide Employee id");

        if(!Objects.equals(id,employeeDto.getId()))
            throw new BadRequestException("id mismatch");

        employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee Not Found"));

        Employee entity = modelMapper.map(employeeDto,Employee.class);
        Employee saveEntity = employeeRepository.save(entity);
        return modelMapper.map(saveEntity,EmployeeDto.class);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee Not Found with id : " + id));
        employeeRepository.delete(employee);
    }

    @Override
    public EmployeeDto getSingleEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee Not Found"));
        return modelMapper.map(employee,EmployeeDto.class);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
       List<Employee> employees = employeeRepository.findAll();
       if(employees.isEmpty()){
           throw new ResourceNotFoundException("No Employee Found");
       }
        return employees.stream().map(emp->modelMapper.map(emp,EmployeeDto.class)).toList();
    }

    @Override
    public EmployeeDto getEmployeeByEmpCodeAndEmpName(String empCode, String companyName) {
        Employee employee = employeeRepository.findByEmpCodeAndCompanyName(empCode,companyName).orElseThrow(()->new ResourceNotFoundException("Employee Not Found" + "with" + empCode + "And" + companyName));
        return modelMapper.map(employee,EmployeeDto.class);
    }
}
