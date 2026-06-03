package com.address.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.address.client.EmployeeClient;
import com.address.exception.ResourceNotFoundException;
import com.address.model.dto.AddressDto;
import com.address.model.dto.AddressRequest;
import com.address.model.dto.AddressRequestDto;
import com.address.model.dto.EmployeeDto;
import com.address.model.entity.Address;
import com.address.repository.AddressRepository;
import com.address.service.AddressService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {


    private final AddressRepository addressRepository;

    private final ModelMapper modelMapper;

    private final EmployeeClient employeeClient;

    public AddressServiceImpl(AddressRepository addressRepository, ModelMapper modelMapper, EmployeeClient employeeClient) {
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
        this.employeeClient = employeeClient;
    }

    @Override
    public List<AddressDto> saveAddress(AddressRequest addressRequest) {

        EmployeeDto employeeDto = employeeClient.getSingleEmployee(addressRequest.getEmpId());
        if (employeeDto == null) {
            throw new ResourceNotFoundException("Employee not found for id : " + addressRequest.getEmpId());
       }
            
        List<Address> listToSave = this.saveOrUpdateAddress(addressRequest);
        
        
        List<Address> savedAddresses = addressRepository.saveAll(listToSave);

        return savedAddresses.stream()
                .map(savedAddress -> modelMapper.map(savedAddress, AddressDto.class))
                .toList();
    }

    @Override
    public List<AddressDto> updateAddress(AddressRequest addressRequest) {

        List<Address> addressesByEmpId = addressRepository.findAllByEmpId(addressRequest.getEmpId());
        if (addressesByEmpId.isEmpty()) {
            log.info("No address found for employee id: {}", addressRequest.getEmpId());
            log.info("Creating new address for emp id {}", addressRequest.getEmpId());
        }

        List<Address> listToUpdate = this.saveOrUpdateAddress(addressRequest);

        List<Long> upComingNonNullIds = listToUpdate.stream().map(Address::getId).filter(Objects::nonNull).toList();
        List<Long> existingIds = addressesByEmpId.stream().map(Address::getId).toList();

        List<Long> idsToDelete = existingIds.stream()
                .filter(existingId -> !upComingNonNullIds.contains(existingId))
                .toList();

        if (!idsToDelete.isEmpty()) {
            log.info("Deleting addresses with ids: {}", idsToDelete);
            addressRepository.deleteAllById(idsToDelete);
        }
        List<Address> updatedAddresses = addressRepository.saveAll(listToUpdate);

        return updatedAddresses.stream()
                .map(address -> modelMapper.map(address, AddressDto.class))
                .toList();
    }

    @Override
    public AddressDto getSingleAddress(Long id) {

        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found for id: " + id));

        return modelMapper.map(address, AddressDto.class);
    }

    @Override
    public List<AddressDto> getAllAddress() {
        List<Address> addresses = addressRepository.findAll();
        if (addresses.isEmpty()) {
            throw new ResourceNotFoundException("No address found");
        }

        return addresses.stream()
                .map(address -> modelMapper.map(address, AddressDto.class))
                .toList();
    }

    @Override
    public void deleteAddress(Long id) {

        if (!addressRepository.existsById(id)) {
            throw new ResourceNotFoundException("Address not found for id: " + id);
        }
        addressRepository.deleteById(id);
    }

    private List<Address> saveOrUpdateAddress(AddressRequest addressRequest) {
        List<Address> listToSave = new ArrayList<>();

        for (AddressRequestDto addressRequestDto : addressRequest.getAddressRequestDtoList()) {
            Address address = new Address();
            address.setEmpId(addressRequest.getEmpId());
            address.setId(addressRequestDto.getId() != null ? addressRequestDto.getId() : null);
            address.setStreet(addressRequestDto.getStreet());
            address.setPinCode(addressRequestDto.getPinCode());
            address.setCity(addressRequestDto.getCity());
            address.setCountry(addressRequestDto.getCountry());
            address.setAddressType(addressRequestDto.getAddressType());
            listToSave.add(address);
        }

        return listToSave;
    }

}
