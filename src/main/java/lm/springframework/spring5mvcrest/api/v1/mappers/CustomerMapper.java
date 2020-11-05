package lm.springframework.spring5mvcrest.api.v1.mappers;

import lm.springframework.spring5mvcrest.api.v1.model.CustomerDTO;
import lm.springframework.spring5mvcrest.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO customerToCustomerDTO(Customer customer);
    Customer customerDtoToCustomer(CustomerDTO customerDTO);
}
