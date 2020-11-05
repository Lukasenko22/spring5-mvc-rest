package lm.springframework.spring5mvcrest.api.v1.mappers;

import lm.springframework.spring5mvcrest.api.v1.model.CategoryDTO;
import lm.springframework.spring5mvcrest.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    void catgoryToCategoryDTO_mapperTest(){
        //given
        Category category = new Category(1L,"Fruits");

        //when
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        //then
        assertEquals("Fruits",categoryDTO.getName());
        assertEquals(1L,categoryDTO.getId());
    }
}