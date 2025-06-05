package org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.repositories;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    /**
     * Obtiene todas las categorías.
     *
     * @return una lista de todas las categorías.
     */
    List<Category> findAll();

    /**
     * Inserta o actualiza una categoría.
     *
     * @param category la entidad Category a insertar o actualizar.
     * @return la entidad Category insertada o actualizada.
     */
    Category save(Category category);

    /**
     * Elimina una categoría por su ID.
     *
     * @param id el ID de la categoría a eliminar.
     */
    void deleteById(Long id);

    /**
     * Obtiene una categoría por su ID.
     *
     * @param id el ID de la categoría.
     * @return un Optional que contiene la categoría si se encuentra, o vacío si no se encuentra.
     */
    Optional<Category> findById(Long id);

    /**
     * Comprueba si existe una categoría con un nombre específico.
     *
     * @param name el nombre de la categoría.
     * @return true si existe una categoría con el nombre especificado, false en caso contrario.
     */
    boolean existsByName(String name);

    /**
     * Comprueba si existe una categoría con un nombre específico, excluyendo una categoría por su ID.
     *
     * @param name el nombre de la categoría.
     * @param id el ID de la categoría a excluir.
     * @return true si existe una categoría con el nombre especificado (excluyendo la categoría con el ID dado), false en caso contrario.
     */
    @Query("SELECT COUNT(c) > 0 FROM Category c WHERE c.name = :name AND c.id != :id")
    boolean existsCategoryByNameAndNotId(@Param("name") String name, @Param("id") Long id);

    boolean existsByNameIgnoreCase(String name);
}
