package com.employee.model.dto;

import com.employee.model.enums.AddressType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long empId;
    private String street;
    private Long pinCode;
    private String city;
    private String country;

    @Enumerated(EnumType.STRING)
    private AddressType addressType;

}
