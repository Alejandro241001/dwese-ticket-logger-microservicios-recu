package org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProvinceDTO {
    private Long id;
    private String code;
    private String name;
    private RegionDTO region;

}
