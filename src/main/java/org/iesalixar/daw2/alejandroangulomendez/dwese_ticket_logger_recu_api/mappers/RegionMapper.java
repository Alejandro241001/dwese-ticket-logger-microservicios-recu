package org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.mappers;

import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.dtos.RegionDTO;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.dtos.RegionCreateDTO;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.entities.Region;
import org.springframework.stereotype.Component;

@Component
public class RegionMapper {

    /**
     * Convierte una entidad `Region` a un `RegionDTO` (datos básico).
     *
     * @param region Entidad de region.
     * @return DTO correspondiente
     */
    public RegionDTO toDTO(Region region){
        RegionDTO dto = new RegionDTO();
        dto.setId(region.getId());
        dto.setCode(region.getCode());
        dto.setName(region.getName());
        return dto;
    }
    /**
     * Convierte un `RegionDTO` a una entidad `Region`.
     *
     * @param dto DTO de región
     * @return Entidad Region
     */

    public Region toEntity(RegionDTO dto){
        Region region = new Region();
        region.setId(dto.getId());
        region.setCode(dto.getCode());
        region.setName(dto.getName());
        return region;
    }
    /**
     * Convierte un `RegionCreateDTO` a una entidad `Region` (para creación).
     * @param createDTO DTO para crear regiones
     * @return Entidad Region
     */

    public Region toEntity(RegionCreateDTO createDTO){
        Region region = new Region();
        region.setCode(createDTO.getCode());
        region.setName(createDTO.getName());
        return region;
    }
}
