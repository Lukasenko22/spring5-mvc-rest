package lm.springframework.spring5mvcrest.services;

import lm.springframework.spring5mvcrest.api.v1.mappers.CustomerMapper;
import lm.springframework.spring5mvcrest.api.v1.model.CustomerDTO;
import lm.springframework.spring5mvcrest.domain.Customer;
import lm.springframework.spring5mvcrest.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    private CustomerMapper customerMapper;

    @Autowired
    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customer -> {
                  CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                  customerDTO.setCustomerUrl("/api/v1/customers/"+customerDTO.getId());
                  return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (!customer.isPresent()){
            throw new RuntimeException("Customer with id="+customerId+" not found");
        }
        return customerMapper.customerToCustomerDTO(customer.get());
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        return saveCustomerData(customer);
    }

    @Override
    public CustomerDTO updateCustomer(Long customerId, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        customer.setId(customerId);
        return saveCustomerData(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long customerId, CustomerDTO customerDTO) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (!customerOpt.isPresent()){
            throw new RuntimeException("Customer with id="+customerId+" not found");
        }
        Customer customer = customerOpt.get();
        if (customerDTO.getFirstName() != null){
            customer.setFirstName(customerDTO.getFirstName());
        }
        if (customerDTO.getLastName() != null){
            customer.setLastName(customerDTO.getLastName());
        }
        return saveCustomerData(customer);
    }

    private CustomerDTO saveCustomerData(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO savedCustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);
        savedCustomerDTO.setCustomerUrl("/api/v1/customers/" + savedCustomerDTO.getId());
        return savedCustomerDTO;
    }

    @Override
    public void deleteCustomerById(Long customerId) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (!customerOpt.isPresent()){
            throw new RuntimeException("Customer with id="+customerId+" not found");
        }
        customerRepository.deleteById(customerId);
    }
}
