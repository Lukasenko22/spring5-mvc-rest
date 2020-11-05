package lm.springframework.spring5mvcrest.services;

import lm.springframework.spring5mvcrest.api.v1.mappers.CustomerMapper;
import lm.springframework.spring5mvcrest.api.v1.model.CustomerDTO;
import lm.springframework.spring5mvcrest.domain.Customer;
import lm.springframework.spring5mvcrest.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(customerMapper,customerRepository);
    }

    @Test
    void getAllCustomers_successTest() {
        //given
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("Lukas");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("Lubomir");

        List<Customer> customerList = Arrays.asList(customer1,customer2);

        when(customerRepository.findAll()).thenReturn(customerList);

        //when
        List<CustomerDTO> customerDTOList = customerService.getAllCustomers();

        //then
        assertEquals(2, customerDTOList.size());
    }

    @Test
    void getCustomerById_successTest() {
        //given
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("Lukas");

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer1));

        //when
        CustomerDTO customerDTO = customerService.getCustomerById(1L);

        //then
        assertEquals(1L,customerDTO.getId());
    }

    @Test
    void createNewCustomer_successTest() {
        //given
        Customer savedCustomer = new Customer();
        savedCustomer.setId(7L);
        savedCustomer.setFirstName("Newbie");
        savedCustomer.setLastName("Customer");

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(savedCustomer.getFirstName());
        customerDTO.setLastName(savedCustomer.getLastName());

        when(customerRepository.save(any())).thenReturn(savedCustomer);

        //when
        CustomerDTO savedCustomerDto = customerService
                .createNewCustomer(customerDTO);

        //then
        verify(customerRepository,times(1)).save(any());
        assertNotNull(savedCustomerDto.getId());
        assertEquals(7L,savedCustomerDto.getId());
    }

    @Test
    void updateCustomer_successTest() {
        //given
        Long id = 7L;
        Customer savedCustomer = new Customer();
        savedCustomer.setId(id);
        savedCustomer.setFirstName("Updated Newbie");
        savedCustomer.setLastName("Updated Customer");

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(id);
        customerDTO.setFirstName("Newbie");
        customerDTO.setLastName("Customer");

        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        //when
        when(customerRepository.save(customer)).thenReturn(savedCustomer);

        //then
        CustomerDTO savedCustomerDto = customerService.updateCustomer(id, customerDTO);

        verify(customerRepository, times(1)).save(any());
        assertEquals(7L, savedCustomerDto.getId());
        assertEquals("Updated Newbie", savedCustomerDto.getFirstName());
        assertEquals("Updated Customer", savedCustomerDto.getLastName());
    }

    @Test
    void deleteCustomerById_successTest() {
        //given
        Long id = 1L;
        Customer customer1 = new Customer();
        customer1.setId(id);
        customer1.setFirstName("Lukas");
        customer1.setLastName("Molcan");

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer1));

        //when
        customerService.deleteCustomerById(id);

        //then
        verify(customerRepository,times(1)).deleteById(id);
    }
}