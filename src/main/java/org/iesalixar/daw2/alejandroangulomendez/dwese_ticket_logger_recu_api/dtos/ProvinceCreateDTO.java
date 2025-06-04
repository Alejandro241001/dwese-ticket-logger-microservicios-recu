package org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProvinceCreateDTO {

    @NotEmpty(message = "{msg.province.code.notEmpty}")
    @Size(max = 3, message = "{msg.province.code.size}")
    private String code;

    @NotEmpty(message = "{msg.province.name.notEmpty}")
    @Size(max = 100, message = "{msg.province.name.size}")
    private String name;

    /**
     * La región a la que pertenece la provincia.
     *
     * - Debe ser un objeto válido.
     * - Este campo no puede ser nulo.
     */
    @NotNull(message = "{msg.province.region.notNull}")
    private Long regionId;  // Cambié a Long ya que puedes manejar la referencia al ID de la región
}
