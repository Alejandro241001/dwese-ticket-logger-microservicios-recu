package org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.dtos;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class CategoryCreateDTO {

    @NotEmpty(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;

    @Size(max = 500, message = "La imagen debe tener máximo 500 caracteres")
    private String image;

    private Long parentCategoryId;

    public CategoryCreateDTO() {
    }

    public CategoryCreateDTO(String name, String image, Long parentCategoryId) {
        this.name = name;
        this.image = image;
        this.parentCategoryId = parentCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public void setId(Long id) {
    }
}
