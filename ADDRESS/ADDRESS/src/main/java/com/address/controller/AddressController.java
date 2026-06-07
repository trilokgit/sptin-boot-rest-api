package com.address.controller;

import java.util.List;

import com.address.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.address.model.dto.AddressDto;
import com.address.model.dto.AddressRequest;
import com.address.service.AddressService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public List<AddressDto> saveAddress(@RequestBody AddressRequest addressRequest) {
        return addressService.saveAddress(addressRequest);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public List<AddressDto> updateAddress(@RequestBody AddressRequest addressRequest) {
        return addressService.updateAddress(addressRequest);
    }

    @GetMapping("/alladdress")
    @ResponseStatus(HttpStatus.OK)
    public List<AddressDto> getAllAddress() {
        return addressService.getAllAddress();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AddressDto getSingleAddress(@PathVariable Long id) {
        return addressService.getSingleAddress(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
    }

    @GetMapping("/empId/{empId}")
    public ResponseEntity<List<AddressDto>> getAddressByEmpId(@PathVariable Long empId) {
        List<AddressDto> response = addressService.getAddressByEmpId(empId);
        if(response.isEmpty()){
            throw new ResourceNotFoundException("Address not found");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
