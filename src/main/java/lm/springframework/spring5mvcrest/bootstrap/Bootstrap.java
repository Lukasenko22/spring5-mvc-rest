package lm.springframework.spring5mvcrest.bootstrap;

import lm.springframework.spring5mvcrest.domain.Category;
import lm.springframework.spring5mvcrest.domain.Customer;
import lm.springframework.spring5mvcrest.repositories.CategoryRepository;
import lm.springframework.spring5mvcrest.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    CategoryRepository categoryRepository;
    CustomerRepository customerRepository;

    @Autowired
    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) {
        loadCategories();
        loadCustomers();
    }

    private void loadCustomers() {
        Customer customer1 = new Customer();
        customer1.setFirstName("Lukas");
        customer1.setLastName("Molcan");

        Customer customer2 = new Customer();
        customer2.setFirstName("Lubomir");
        customer2.setLastName("Molcan");

        Customer customer3 = new Customer();
        customer3.setFirstName("Stefan");
        customer3.setLastName("Molcan");

        Customer customer4 = new Customer();
        customer4.setFirstName("Maria");
        customer4.setLastName("Molcanova");

        Customer customer5 = new Customer();
        customer5.setFirstName("Ivana");
        customer5.setLastName("Molcanova");

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);
        customerRepository.save(customer4);
        customerRepository.save(customer5);

        System.out.println("Customers saved: "+customerRepository.count());
    }

    private void loadCategories() {
        Category fruitsCat = new Category();
        fruitsCat.setName("Fruits");

        Category driedCat = new Category();
        driedCat.setName("Dried");

        Category freshCat = new Category();
        freshCat.setName("Fresh");

        Category exoticCat = new Category();
        exoticCat.setName("Exotic");

        Category nutsCat = new Category();
        nutsCat.setName("Nuts");

        categoryRepository.save(fruitsCat);
        categoryRepository.save(driedCat);
        categoryRepository.save(freshCat);
        categoryRepository.save(exoticCat);
        categoryRepository.save(nutsCat);

        System.out.println("Categories saved: "+categoryRepository.count());
    }
}
