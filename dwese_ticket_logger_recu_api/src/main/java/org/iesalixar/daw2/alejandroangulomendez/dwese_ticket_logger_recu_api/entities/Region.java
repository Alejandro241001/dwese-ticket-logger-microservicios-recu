package org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.entities;

import jakarta.persistence.*; // Anotaciones de JPA
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "regions")
public class Region {


    // Es una clave primaria autogenerada por la base de datos.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Campo que almacena el código de la región, normalmente una cadena corta que identifica la región.
    // Ejemplo: "01" para Andalucía.


    @NotEmpty(message = "{msg.region.code.notEmpty}")
    @Size(max = 2, message = "{msg.region.code.size}")
    @Column(name = "code", nullable = false, length = 2) // Define la columna correspondiente en la tabla.
    private String code;



    // Campo que almacena el nombre completo de la región, como "Andalucía" o "Cataluña".
    @NotEmpty(message = "{msg.region.name.notEmpty}")
    @Size(max = 100, message = "{msg.region.name.notEmpty}")
    @Column(name = "name", nullable = false, length = 100) // Define la columna correspondiente en la tabla.
    private  String name;


    /**
     * Este es un constructor personalizado que no incluye el campo `id`.
     * Se utiliza para crear instancias de `Region` cuando no es necesario o no se conoce el `id` de la región
     * (por ejemplo, antes de insertar la región en la base de datos, donde el `id` es autogenerado).
     * @param code Código de la región.
     * @param name Nombre de la región.
     */
    public Region(String code, String name) {
        this.code = code;
        this.name = name;
    }


}
