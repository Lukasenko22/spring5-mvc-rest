package lm.springframework.spring5mvcrest.services;

import lm.springframework.spring5mvcrest.api.v1.model.CategoryDTO;
import lm.springframework.spring5mvcrest.domain.Category;
import lm.springframework.spring5mvcrest.repositories.CategoryRepository;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryByName(String categoryName);
}
