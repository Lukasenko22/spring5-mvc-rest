package lm.springframework.spring5mvcrest.bootstrap;

import lm.springframework.spring5mvcrest.domain.Category;
import lm.springframework.spring5mvcrest.domain.Customer;
import lm.springframework.spring5mvcrest.domain.Vendor;
import lm.springframework.spring5mvcrest.repositories.CategoryRepository;
import lm.springframework.spring5mvcrest.repositories.CustomerRepository;
import lm.springframework.spring5mvcrest.repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    CategoryRepository categoryRepository;
    CustomerRepository customerRepository;
    VendorRepository vendorRepository;

    @Autowired
    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository,
                     VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) {
        loadCategories();
        loadCustomers();
        loadVendors();
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

    private void loadVendors(){
        Vendor vendor1 = new Vendor();
        vendor1.setName("Freshness of Fruit");

        Vendor vendor2 = new Vendor();
        vendor2.setName("Fruit Shop");

        Vendor vendor3 = new Vendor();
        vendor3.setName("Land of Fruit");

        Vendor vendor4 = new Vendor();
        vendor4.setName("Tasty Fruit");

        Vendor vendor5 = new Vendor();
        vendor5.setName("Fresh Market");

        vendorRepository.save(vendor1);
        vendorRepository.save(vendor2);
        vendorRepository.save(vendor3);
        vendorRepository.save(vendor4);
        vendorRepository.save(vendor5);

        System.out.println("Vendors saved: "+vendorRepository.count());
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
