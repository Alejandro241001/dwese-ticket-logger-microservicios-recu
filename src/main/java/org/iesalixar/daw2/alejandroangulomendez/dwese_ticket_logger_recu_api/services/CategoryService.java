package org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.services;


import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.dtos.CategoryCreateDTO;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.dtos.CategoryDTO;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.entities.Category;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.mappers.CategoryMapper;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * Obtiene todas las categorías.
     */
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca categoría por id.
     */
    public Optional<CategoryDTO> getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDTO);
    }

    /**
     * Crea una nueva categoría.
     */
    public CategoryDTO createCategory(CategoryCreateDTO dto) {
        // Comprobación de nombre duplicado (ignorando mayúsculas/minúsculas)
        if (categoryRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new IllegalArgumentException("Ya existe una categoría con ese nombre.");
        }

        Category parentCategory = null;
        if (dto.getParentCategoryId() != null) {
            parentCategory = categoryRepository.findById(dto.getParentCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Categoría padre no encontrada"));
        }

        Category category = categoryMapper.toEntity(dto, parentCategory);
        Category saved = categoryRepository.save(category);
        return categoryMapper.toDTO(saved);
    }
    /**
     * Actualiza categoría existente.
     */
    public CategoryDTO updateCategory(Long id, CategoryCreateDTO dto) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        Category parentCategory = null;
        if (dto.getParentCategoryId() != null) {
            parentCategory = categoryRepository.findById(dto.getParentCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Categoría padre no encontrada"));
        }

        categoryMapper.updateEntity(existingCategory, dto, parentCategory);

        Category saved = categoryRepository.save(existingCategory);
        return categoryMapper.toDTO(saved);
    }

    /**
     * Elimina categoría por id.
     */
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Categoría no encontrada");
        }
        categoryRepository.deleteById(id);
    }
}
