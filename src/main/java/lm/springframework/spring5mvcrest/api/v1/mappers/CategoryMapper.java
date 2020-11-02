package lm.springframework.spring5mvcrest.api.v1.mappers;

import lm.springframework.spring5mvcrest.api.v1.model.CategoryDTO;
import lm.springframework.spring5mvcrest.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO categoryToCategoryDTO(Category category);
}
