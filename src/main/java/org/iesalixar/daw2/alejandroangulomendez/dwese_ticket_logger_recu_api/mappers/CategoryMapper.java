package org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.mappers;


import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.dtos.CategoryCreateDTO;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.dtos.CategoryDTO;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.entities.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    // Convierte entidad a DTO simple (solo ID de padre)
    public CategoryDTO toDTO(Category category) {
        if (category == null) {
            return null;
        }

        Long parentId = null;
        if (category.getParentCategory() != null) {
            parentId = category.getParentCategory().getId();
        }

        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getImage(),
                parentId
        );
    }

    // Convierte DTO de creación a entidad, con categoría padre (puede ser null)
    public Category toEntity(CategoryCreateDTO dto, Category parentCategory) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setImage(dto.getImage());
        category.setParentCategory(parentCategory);
        return category;
    }

    // Para actualizar entidad existente con datos de DTO y padre
    public void updateEntity(Category category, CategoryCreateDTO dto, Category parentCategory) {
        category.setName(dto.getName());
        category.setImage(dto.getImage());
        category.setParentCategory(parentCategory);
    }
}