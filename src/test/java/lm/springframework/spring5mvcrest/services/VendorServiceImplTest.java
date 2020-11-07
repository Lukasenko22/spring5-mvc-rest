package lm.springframework.spring5mvcrest.services;

import lm.springframework.spring5mvcrest.api.v1.mappers.VendorMapper;
import lm.springframework.spring5mvcrest.api.v1.model.VendorDTO;
import lm.springframework.spring5mvcrest.controllers.VendorController;
import lm.springframework.spring5mvcrest.controllers.exceptions.ResourceNotFoundException;
import lm.springframework.spring5mvcrest.domain.Vendor;
import lm.springframework.spring5mvcrest.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class VendorServiceImplTest {

    @Mock
    VendorRepository vendorRepository;

    VendorService vendorService;

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        vendorService = new VendorServiceImpl(vendorRepository,vendorMapper);
    }

    @Test
    void getAllVendors_successTest() {
        //given
        Vendor vendor1 = new Vendor();
        vendor1.setId(1L);
        vendor1.setName("Vendor1");

        Vendor vendor2 = new Vendor();
        vendor2.setId(2L);
        vendor2.setName("Vendor2");

        List<Vendor> vendorList = Arrays.asList(vendor1,vendor2);

        //when
        when(vendorRepository.findAll()).thenReturn(vendorList);

        List<VendorDTO> vendorDTOList = vendorService.getAllVendors();
        //then
        assertNotNull(vendorDTOList);
        assertEquals(vendorDTOList.size(),vendorList.size());
    }

    @Test
    void getVendorById_successTest() {
        Long vendorId = 2L;
        Vendor vendor = new Vendor(2L,"Vendor2");

        when(vendorRepository.findById(vendorId)).thenReturn(Optional.of(vendor));

        VendorDTO vendorDTO = vendorService.getVendorById(vendorId);

        assertNotNull(vendorDTO);
        assertEquals(vendorId,vendorDTO.getId());
        assertEquals("Vendor2",vendorDTO.getName());
        assertEquals(VendorController.BASE_URL+"/"+vendorId, vendorDTO.getUrl());
    }

    @Test
    void getVendorById_throwNotFound() {
        Long vendorId = 2L;
        String expectedMessage = "Vendor with id="+vendorId+" is not found";
        when(vendorRepository.findById(vendorId)).thenThrow(new ResourceNotFoundException(expectedMessage));

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            vendorService.getVendorById(vendorId);
        });

        assertEquals(expectedMessage,exception.getMessage());
    }

    @Test
    void createVendor_successTest() {
        Vendor vendor = new Vendor();
        vendor.setName("New Vendor");

        Long vendorId = 1L;

        Vendor createdVendor = new Vendor();
        createdVendor.setId(vendorId);
        createdVendor.setName(vendor.getName());

        when(vendorRepository.save(vendor)).thenReturn(createdVendor);

        VendorDTO vendorDTO = vendorService.createVendor(vendorMapper.vendorToVendorDTO(vendor));

        assertNotNull(vendorDTO);
        assertEquals(vendorId,vendorDTO.getId());
        assertEquals("New Vendor",vendorDTO.getName());
        assertEquals(VendorController.BASE_URL+"/"+vendorId,vendorDTO.getUrl());
    }

    @Test
    void deleteVendorById_successTest() {
    }
}