package lm.springframework.spring5mvcrest.services;

import lm.springframework.spring5mvcrest.api.v1.model.CustomerDTO;
import lm.springframework.spring5mvcrest.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(Long customerId);

    CustomerDTO createNewCustomer(CustomerDTO customerDTO);

    CustomerDTO updateCustomer(Long customerId, CustomerDTO customerDTO);

    CustomerDTO patchCustomer(Long customerId, CustomerDTO customerDTO);

    void deleteCustomerById(Long customerId);
}
