package lm.springframework.spring5mvcrest.services;

import lm.springframework.spring5mvcrest.api.v1.mappers.CategoryMapper;
import lm.springframework.spring5mvcrest.api.v1.model.CategoryDTO;
import lm.springframework.spring5mvcrest.domain.Category;
import lm.springframework.spring5mvcrest.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class CategoryServiceImplTest {

    @Mock
    CategoryRepository categoryRepository;

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryServiceImpl(categoryMapper, categoryRepository);
    }

    @Test
    void getAllCategories_successTest() {
        //given
        List<Category> categoryList = Arrays.asList(new Category(1l,"Fruits"),new Category(2l,"Nuts")
                ,new Category(3l,"Vegetables"));

        when(categoryRepository.findAll()).thenReturn(categoryList);

        //when
        List<CategoryDTO> categoryDTOList = categoryService.getAllCategories();

        //then
        assertEquals(3,categoryDTOList.size());
    }

    @Test
    void getCategoryByName_successTest() {
        //given
        Category category = new Category(1L,"Fruits");

        when(categoryRepository.findByName("Fruits")).thenReturn(category);

        //when
        CategoryDTO categoryDTO = categoryService.getCategoryByName("Fruits");

        //then
        assertNotNull(categoryDTO);
        assertEquals(1L, categoryDTO.getId());
    }
}