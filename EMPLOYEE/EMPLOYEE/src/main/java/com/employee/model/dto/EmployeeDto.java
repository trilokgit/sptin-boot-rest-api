package com.employee.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeDto {
    private Long id;
    private String empName;
    private String empEmail;
    private String empCode;
    private String companyName;
}
