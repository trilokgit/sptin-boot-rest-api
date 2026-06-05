package com.employee.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDto {
    private Long id;
    private String empName;
    private String empEmail;
    private String empCode;
    private String companyName;
    private List<AddressDto> address;
}
