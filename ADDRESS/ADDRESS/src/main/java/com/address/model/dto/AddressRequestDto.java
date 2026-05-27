package com.address.model.dto;

import com.address.model.enums.AddressType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressRequestDto {

    private Long id;
    private Long pinCode;
    private String street;
    private String city;
    private String country;

    private AddressType addressType;


}
