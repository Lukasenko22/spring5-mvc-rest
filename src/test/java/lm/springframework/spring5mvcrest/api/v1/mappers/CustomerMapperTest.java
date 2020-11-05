package lm.springframework.spring5mvcrest.api.v1.mappers;

import lm.springframework.spring5mvcrest.api.v1.model.CustomerDTO;
import lm.springframework.spring5mvcrest.domain.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    void customerToCustomerDTO_mapperTest() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Lukas");
        customer.setLastName("Molcan");

        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        assertEquals("Lukas",customerDTO.getFirstName());
        assertEquals("Molcan",customerDTO.getLastName());
    }
}