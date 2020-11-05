package lm.springframework.spring5mvcrest.controllers;

import lm.springframework.spring5mvcrest.api.v1.model.CategoryDTO;
import lm.springframework.spring5mvcrest.services.CategoryService;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryControllerTest {

    public static final String CAT_1 = "cat1";
    @Mock
    CategoryService categoryService;

    @InjectMocks
    CategoryController categoryController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        // Pri pouziti @InjectMocks anotacie mozeme inicializaciu categoryService vynechat
        // categoryController = new CategoryController(categoryService);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void getAllCategories_successTest() throws Exception {
        //given
        CategoryDTO category1 = new CategoryDTO();
        category1.setId(1L);
        category1.setName("cat1");

        CategoryDTO category2 = new CategoryDTO();
        category2.setId(2L);
        category2.setName("cat2");

        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        categoryDTOList.add(category1);
        categoryDTOList.add(category2);

        //when
        when(categoryService.getAllCategories()).thenReturn(categoryDTOList);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryList", hasSize(2)));
    }

    @Test
    void getCategoryByName_successTest() throws Exception {
        //given
        CategoryDTO category1 = new CategoryDTO();
        category1.setId(1L);
        category1.setName(CAT_1);

        //when
        when(categoryService.getCategoryByName(CAT_1)).thenReturn(category1);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/"+CAT_1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(CAT_1)));
    }
}