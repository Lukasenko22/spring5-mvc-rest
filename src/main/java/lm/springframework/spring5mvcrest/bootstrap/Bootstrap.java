package lm.springframework.spring5mvcrest.bootstrap;

import lm.springframework.spring5mvcrest.domain.Category;
import lm.springframework.spring5mvcrest.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    CategoryRepository categoryRepository;

    @Autowired
    public Bootstrap(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
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
