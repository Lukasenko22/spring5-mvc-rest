package lm.springframework.spring5mvcrest.services;

import javassist.NotFoundException;
import lm.springframework.spring5mvcrest.api.v1.mappers.CustomerMapper;
import lm.springframework.spring5mvcrest.api.v1.model.CustomerDTO;
import lm.springframework.spring5mvcrest.bootstrap.Bootstrap;
import lm.springframework.spring5mvcrest.domain.Customer;
import lm.springframework.spring5mvcrest.repositories.CategoryRepository;
import lm.springframework.spring5mvcrest.repositories.CustomerRepository;
import lm.springframework.spring5mvcrest.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerServiceImplTestIT {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    VendorRepository vendorRepository;

    CustomerService customerService;

    @BeforeEach
    void setUp() {
        System.out.println("Loading Bootstrap Data...");
        Bootstrap bootstrap = new Bootstrap(categoryRepository,customerRepository,vendorRepository);
        bootstrap.run();

        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE,customerRepository);
    }

    @Test
    void patchCustomer_updateFirstName() {
        final String updatedFirstName = "Updated First Name";
        Long customerId = getCustomerIdValue();
        Customer defaultCustomer = customerRepository.getOne(customerId);
        System.out.println("Default customer: "+defaultCustomer);

        String defaultFirstName = defaultCustomer.getFirstName();
        String defaultLastName = defaultCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(updatedFirstName);

        CustomerDTO updatedCustomerDto = customerService.patchCustomer(customerId,customerDTO);

        assertNotNull(updatedCustomerDto);
        System.out.println("Updated customer DTO: "+updatedCustomerDto);
        assertEquals(customerId, updatedCustomerDto.getId());
        assertEquals(defaultLastName,updatedCustomerDto.getLastName());
        assertEquals(updatedFirstName,updatedCustomerDto.getFirstName());
        assertNotEquals(defaultFirstName,updatedCustomerDto.getFirstName());
    }

    @Test
    void patchCustomer_updateLastName() {
        final String updatedLastName = "Updated Last Name";
        Long customerId = getCustomerIdValue();
        Customer defaultCustomer = customerRepository.getOne(customerId);
        System.out.println("Default customer: "+defaultCustomer);

        String defaultFirstName = defaultCustomer.getFirstName();
        String defaultLastName = defaultCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastName(updatedLastName);

        CustomerDTO updatedCustomerDto = customerService.patchCustomer(customerId,customerDTO);

        assertNotNull(updatedCustomerDto);
        System.out.println("Updated customer DTO: "+updatedCustomerDto);
        assertEquals(customerId, updatedCustomerDto.getId());
        assertNotEquals(defaultLastName,updatedCustomerDto.getLastName());
        assertEquals(updatedLastName,updatedCustomerDto.getLastName());
        assertEquals(defaultFirstName,updatedCustomerDto.getFirstName());
    }

    @Test
    void deleteCustomer_successTest() {
        Long customerId = getCustomerIdValue();
        customerService.deleteCustomerById(customerId);
        System.out.println("Customer with id="+customerId+" has been deleted!");
        assertThrows(JpaObjectRetrievalFailureException.class,() -> {
            customerRepository.getOne(customerId);
        });
    }

    private Long getCustomerIdValue(){
        List<Customer> customerList = customerRepository.findAll();
        System.out.println("Found customer:\n"+customerList.get(0));
        return customerList.get(0).getId();
    }
}