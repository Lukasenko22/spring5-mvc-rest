package lm.springframework.spring5mvcrest.services;

import lm.springframework.spring5mvcrest.api.v1.mappers.CategoryMapper;
import lm.springframework.spring5mvcrest.api.v1.model.CategoryDTO;
import lm.springframework.spring5mvcrest.controllers.exceptions.ResourceNotFoundException;
import lm.springframework.spring5mvcrest.domain.Category;
import lm.springframework.spring5mvcrest.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryMapper categoryMapper;
    CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryMapper categoryMapper, CategoryRepository categoryRepository) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (Category c : categoryList) {
            categoryDTOList.add(categoryMapper.categoryToCategoryDTO(c));
        }
        return categoryDTOList;
    }

    @Override
    public CategoryDTO getCategoryByName(String categoryName) {
        Category category = categoryRepository.findByName(categoryName);
        if (category == null){
            throw new ResourceNotFoundException("Category with name="+categoryName+" not found");
        }
        return categoryMapper.categoryToCategoryDTO(category);
    }
}
