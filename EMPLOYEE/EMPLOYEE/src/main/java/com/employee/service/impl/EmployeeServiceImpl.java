package com.employee.service.impl;

import com.address.repository.AddressRepository;
import com.employee.client.AddressClient;
import com.employee.exception.BadRequestException;
import com.employee.exception.ResourceNotFoundException;
import com.employee.model.dto.AddressDto;
import com.employee.model.dto.EmployeeDto;
import com.employee.model.entity.Employee;
import com.employee.repository.EmployeeRepository;
import com.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final AddressClient addressClient;


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
        List<AddressDto> addresses = new ArrayList<>();
        EmployeeDto dto = modelMapper.map(employee,EmployeeDto.class);
        try {
           addresses = addressClient.getAddressByEmpId(employee.getId());
           dto.setAddress(addresses);
        }catch (Exception e){
            log.info("no address found");
        }

        return dto;

    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
       List<Employee> employees = employeeRepository.findAll();
       if(employees.isEmpty()){
           throw new ResourceNotFoundException("No Employee Found");
       }
        List<EmployeeDto> employeeDtoList = employees.stream().map(emp->modelMapper.map(emp,EmployeeDto.class)).toList();
        List<EmployeeDto> response = new ArrayList();
        for(EmployeeDto employee : employeeDtoList){
            List<AddressDto> addresses = new ArrayList<>();
            try{
                addresses = addressClient.getAddressByEmpId(employee.getId());
                employee.setAddress(addresses);
            }catch (Exception e){
                log.info("no address found");
            }

            response.add(employee);

        }
        return response;
    }

    @Override
    public EmployeeDto getEmployeeByEmpCodeAndEmpName(String empCode, String companyName) {
        Employee employee = employeeRepository.findByEmpCodeAndCompanyName(empCode,companyName).orElseThrow(()->new ResourceNotFoundException("Employee Not Found" + "with" + empCode + "And" + companyName));
        return modelMapper.map(employee,EmployeeDto.class);
    }
}
