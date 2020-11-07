package lm.springframework.spring5mvcrest.services;

import lm.springframework.spring5mvcrest.api.v1.mappers.VendorMapper;
import lm.springframework.spring5mvcrest.api.v1.model.VendorDTO;
import lm.springframework.spring5mvcrest.bootstrap.Bootstrap;
import lm.springframework.spring5mvcrest.controllers.exceptions.ResourceNotFoundException;
import lm.springframework.spring5mvcrest.domain.Vendor;
import lm.springframework.spring5mvcrest.repositories.CategoryRepository;
import lm.springframework.spring5mvcrest.repositories.CustomerRepository;
import lm.springframework.spring5mvcrest.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DataJpaTest
class VendorServiceImplTestIT {

    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    VendorService vendorService;

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        vendorService = new VendorServiceImpl(vendorRepository, vendorMapper);
        Bootstrap bootstrap = new Bootstrap(categoryRepository,customerRepository,vendorRepository);
        bootstrap.run();
    }

    @Test
    void updateVendor_sucessTest() {
        Long vendorId = getExistingVendorId();
        Vendor defaultVendor = vendorRepository.getOne(vendorId);

        String defaultVendorName = defaultVendor.getName();

        VendorDTO inVendorDTO = new VendorDTO();
        inVendorDTO.setId(vendorId);
        inVendorDTO.setName("Updated Vendor");

        VendorDTO updatedVendorDTO = vendorService.updateVendor(vendorId,inVendorDTO);

        assertNotNull(updatedVendorDTO);
        assertEquals(defaultVendor.getId(),updatedVendorDTO.getId());
        assertNotEquals(defaultVendorName,updatedVendorDTO.getName());
        assertEquals(inVendorDTO.getName(),updatedVendorDTO.getName());
    }

    @Test
    void patchVendor_updateNameTest() {
        Long vendorId = getExistingVendorId();
        Vendor defaultVendor = vendorRepository.getOne(vendorId);

        String defaultVendorName = defaultVendor.getName();

        VendorDTO inVendorDTO = new VendorDTO();
        inVendorDTO.setId(vendorId);
        inVendorDTO.setName("Patched Vendor");

        VendorDTO updatedVendorDTO = vendorService.patchVendor(vendorId,inVendorDTO);

        assertNotNull(updatedVendorDTO);
        assertEquals(defaultVendor.getId(),updatedVendorDTO.getId());
        assertNotEquals(defaultVendorName,updatedVendorDTO.getName());
        assertEquals(inVendorDTO.getName(),updatedVendorDTO.getName());
        System.out.println("Updated Vendor: "+updatedVendorDTO);
    }

    @Test
    void deleteVendorById_successTest(){
        Long vendorId = getExistingVendorId();
        vendorService.deleteVendorById(vendorId);
        assertThrows(ResourceNotFoundException.class, () -> {
            vendorService.deleteVendorById(vendorId);
        });
        System.out.println("Vendors: "+vendorService.getAllVendors().size());
    }

    private Long getExistingVendorId(){
        List<Vendor> vendorList = vendorRepository.findAll();
        System.out.println("Found Vendor: "+vendorList.get(0));
        return vendorList.get(0).getId();
    }
}