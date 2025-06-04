package org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;
    private String image;
    private Long parentCategoryId; // Guardamos solo el ID de la categoría padre
}