package lm.springframework.spring5mvcrest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import lm.springframework.spring5mvcrest.api.v1.model.CustomerDTO;
import lm.springframework.spring5mvcrest.domain.Customer;
import lm.springframework.spring5mvcrest.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest {

    public static final long CUST_ID = 2L;
    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        //customerController = new CustomerController(customerService);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void getAllCustomers_successTest() throws Exception {
        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setId(1L);
        customerDTO1.setFirstName("Lukas");

        CustomerDTO customerDTO2 = new CustomerDTO();
        customerDTO2.setId(2L);
        customerDTO2.setFirstName("Lubomir");

        List<CustomerDTO> customerDTOList = Arrays.asList(customerDTO1,customerDTO2);

        when(customerService.getAllCustomers()).thenReturn(customerDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerList",hasSize(2)));
    }

    @Test
    void getCustomerById_successTest() throws Exception {
        CustomerDTO customerDTO2 = new CustomerDTO();
        customerDTO2.setId(CUST_ID);
        customerDTO2.setFirstName("Lubomir");

        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/"+CUST_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName",equalTo("Lubomir")));
    }

    @Test
    void createNewCustomer_successTest() throws Exception {
        //given
        CustomerDTO savedCustomer = new CustomerDTO();
        savedCustomer.setId(8L);
        savedCustomer.setFirstName("Lubomir");
        savedCustomer.setLastName("Molcan");
        savedCustomer.setCustomerUrl("/api/v1/customers/8");

        CustomerDTO inputCustomer = new CustomerDTO();
        inputCustomer.setFirstName(savedCustomer.getFirstName());
        inputCustomer.setLastName(savedCustomer.getLastName());

        //when
        when(customerService.createNewCustomer(inputCustomer)).thenReturn(savedCustomer);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(inputCustomer);

        //then
        String response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id",equalTo(8)))
                .andExpect(jsonPath("$.firstName",equalTo("Lubomir")))
        .andReturn().getResponse().getContentAsString();
        System.out.println("createNewCustomer_successTest\nResponse:\n"+response);
    }

    @Test
    void updateCustomer_successTest() throws Exception {
        //given
        Long customerId = 8L;
        CustomerDTO savedCustomer = new CustomerDTO();
        savedCustomer.setId(customerId);
        savedCustomer.setFirstName("Updated Lubomir");
        savedCustomer.setLastName("Molcan");
        savedCustomer.setCustomerUrl("/api/v1/customers/8");

        CustomerDTO inputCustomer = new CustomerDTO();
        inputCustomer.setFirstName("Updated Lubomir");

        //when
        when(customerService.updateCustomer(customerId,inputCustomer)).thenReturn(savedCustomer);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(inputCustomer);

        //then
        String response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/customers/"+customerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",equalTo(8)))
                .andExpect(jsonPath("$.firstName",equalTo("Updated Lubomir")))
                .andReturn().getResponse().getContentAsString();
        System.out.println("updateCustomer_successTest\nResponse:\n"+response);
    }

    @Test
    void patchCustomer_successTest() throws Exception {
        //given
        Long customerId = 8L;
        CustomerDTO savedCustomer = new CustomerDTO();
        savedCustomer.setId(customerId);
        savedCustomer.setFirstName("Updated Lubomir");
        savedCustomer.setLastName("Molcan");
        savedCustomer.setCustomerUrl("/api/v1/customers/8");

        CustomerDTO inputCustomer = new CustomerDTO();
        inputCustomer.setFirstName("Updated Lubomir");

        //when
        when(customerService.patchCustomer(customerId,inputCustomer)).thenReturn(savedCustomer);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(inputCustomer);

        //then
        String response = mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/customers/"+customerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",equalTo(8)))
                .andExpect(jsonPath("$.firstName",equalTo("Updated Lubomir")))
                .andReturn().getResponse().getContentAsString();
        System.out.println("patchCustomer_successTest\nResponse:\n"+response);
    }
}