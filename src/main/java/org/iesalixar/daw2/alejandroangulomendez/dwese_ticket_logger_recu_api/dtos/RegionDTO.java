package org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase DTO (Data Transfer Object) que representa una provincia.
 *
 * Esta clase se utiliza para transferir datos de una provincia
 * entre las capas de la aplicación, especialmente para exponerlos
 * a través de la API sin incluir información innecesaria o sensible.
 */
@Getter
@Setter
public class RegionDTO {

    /**
     * Identificador único de la provincia.
     * Es el mismo ID que se encuentra en la entidad `Province` de la base de datos.
     */
    private Long id;

    /**
     * Código de la provincia.
     * Normalmente es una cadena corta (máximo 2 caracteres) que identifica la provincia.
     * Ejemplo: "BA" para Barcelona.
     */
    private String code;

    /**
     * Nombre completo de la provincia.
     * Ejemplo: "Barcelona", "Madrid".
     */
    private String name;

    /**
     * Identificador de la región asociada a la provincia.
     * Representa el ID de la entidad `Region` con la que esta provincia está relacionada.
     */
    private Long regionId;
}