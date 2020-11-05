package lm.springframework.spring5mvcrest.api.v1.model;

import lm.springframework.spring5mvcrest.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryListDTO {

    List<CategoryDTO> categoryList;
}
