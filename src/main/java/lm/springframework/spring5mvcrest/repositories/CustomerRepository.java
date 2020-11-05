package lm.springframework.spring5mvcrest.repositories;

import lm.springframework.spring5mvcrest.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
