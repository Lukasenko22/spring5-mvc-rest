package lm.springframework.spring5mvcrest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import lm.springframework.spring5mvcrest.api.v1.model.VendorDTO;
import lm.springframework.spring5mvcrest.controllers.exceptions.ResourceNotFoundException;
import lm.springframework.spring5mvcrest.controllers.exceptions.handlers.RestResponseEntityExceptionHandler;
import lm.springframework.spring5mvcrest.domain.Vendor;
import lm.springframework.spring5mvcrest.services.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VendorControllerTest {

    @Mock
    VendorService vendorService;

    VendorController vendorController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        vendorController = new VendorController(vendorService);
        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    void getAllVendors_successTest() throws Exception {
        //given
        VendorDTO vendor1 = new VendorDTO();
        vendor1.setId(1L);
        vendor1.setName("Vendor1");

        VendorDTO vendor2 = new VendorDTO();
        vendor2.setId(2L);
        vendor2.setName("Vendor2");

        List<VendorDTO> vendorList = Arrays.asList(vendor1,vendor2);

        //when
        when(vendorService.getAllVendors()).thenReturn(vendorList);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors",hasSize(2)));

        verify(vendorService,times(1)).getAllVendors();
    }

    @Test
    void getVendorById_successTest() throws Exception {
        Long vendorId = 1L;
        VendorDTO vendor1 = new VendorDTO();
        vendor1.setId(vendorId);
        vendor1.setName("Vendor1");
        vendor1.setUrl(VendorController.BASE_URL+"/"+vendor1);

        when(vendorService.getVendorById(vendorId)).thenReturn(vendor1);

        mockMvc.perform(MockMvcRequestBuilders
                .get(VendorController.BASE_URL+"/"+vendorId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo("Vendor1")));
    }

    @Test
    void getVendorById_notFoundTest() throws Exception {
        Long vendorId = 1L;
        String expectedMessage = "Vendor with id="+vendorId+" is not found";

        when(vendorService.getVendorById(vendorId)).thenThrow(new ResourceNotFoundException(expectedMessage));

        mockMvc.perform(MockMvcRequestBuilders.get(VendorController.BASE_URL+"/"+vendorId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$",equalTo(expectedMessage)));
    }

    @Test
    void createNewVendor_successTest() throws Exception {
        VendorDTO vendorDTOInput = new VendorDTO();
        String vendorName = "My New Vendor";
        Long vendorId = 2L;
        vendorDTOInput.setName(vendorName);

        VendorDTO savedVendorDTO = new VendorDTO();
        savedVendorDTO.setName(vendorDTOInput.getName());
        savedVendorDTO.setId(vendorId);
        savedVendorDTO.setUrl(VendorController.BASE_URL+"/"+vendorId);

        when(vendorService.createVendor(vendorDTOInput)).thenReturn(savedVendorDTO);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(vendorDTOInput);

        mockMvc.perform(MockMvcRequestBuilders.post(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.name",equalTo(vendorName)))
                    .andExpect(jsonPath("$.id",equalTo(2)));
    }

    @Test
    void updateVendor_successTest() throws Exception {
        Long vendorId = 1L;
        String updatedName = "Updated Name of Vendor";

        VendorDTO vendorDtoIn = new VendorDTO();
        vendorDtoIn.setName(updatedName);

        VendorDTO updateVendorDto = new VendorDTO();
        updateVendorDto.setId(vendorId);
        updateVendorDto.setName(updatedName);
        updateVendorDto.setUrl(VendorController.BASE_URL+"/"+vendorId);

        when(vendorService.updateVendor(vendorId,vendorDtoIn)).thenReturn(updateVendorDto);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(vendorDtoIn);

        mockMvc.perform(MockMvcRequestBuilders.put(VendorController.BASE_URL+"/"+vendorId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(updatedName)))
                .andExpect(jsonPath("$.id",equalTo(1)));
    }

    @Test
    void patchVendor_updatedNameTest() throws Exception {
        Long vendorId = 1L;
        String updatedName = "Patched Name of Vendor";

        VendorDTO vendorDtoIn = new VendorDTO();
        vendorDtoIn.setName(updatedName);

        VendorDTO updateVendorDto = new VendorDTO();
        updateVendorDto.setId(vendorId);
        updateVendorDto.setName(updatedName);
        updateVendorDto.setUrl(VendorController.BASE_URL+"/"+vendorId);

        when(vendorService.patchVendor(vendorId,vendorDtoIn)).thenReturn(updateVendorDto);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(vendorDtoIn);

        mockMvc.perform(MockMvcRequestBuilders.patch(VendorController.BASE_URL+"/"+vendorId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(updatedName)))
                .andExpect(jsonPath("$.id",equalTo(1)));
    }

    @Test
    void deleteVendorById_successTest() throws Exception {
        Long vendorId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete(VendorController.BASE_URL+"/"+vendorId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}