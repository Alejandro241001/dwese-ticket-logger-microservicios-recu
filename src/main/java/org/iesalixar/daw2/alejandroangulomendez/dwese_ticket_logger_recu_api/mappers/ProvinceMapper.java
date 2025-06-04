package org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.mappers;


import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.dtos.ProvinceCreateDTO;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.dtos.ProvinceDTO;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.entities.Province;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.entities.Region;
import org.springframework.stereotype.Component;

@Component
public class ProvinceMapper {

    /**
     * Convierte una entidad Province a un ProvinceDTO (datos básicos).
     *
     * @param province Entidad de province.
     * @return DTO correspondiente
     */
    public ProvinceDTO toDTO(Province province) {
        ProvinceDTO dto = new ProvinceDTO();
        dto.setId(province.getId());
        dto.setCode(province.getCode());
        dto.setName(province.getName());
        return dto;
    }

    /**
     * Convierte un ProvinceDTO a una entidad Province.
     *
     * @param dto DTO de province
     * @return Entidad Province
     */
    public Province toEntity(ProvinceDTO dto) {
        Province province = new Province();
        province.setId(dto.getId());
        province.setCode(dto.getCode());
        province.setName(dto.getName());
        return province;
    }

    /**
     * Convierte un ProvinceCreateDTO a una entidad Province (para creación).
     *
     * @param createDTO DTO para crear provinces
     * @return Entidad Province
     */
    public Province toEntity(ProvinceCreateDTO createDTO, Region region) {
        Province province = new Province();
        province.setCode(createDTO.getCode());
        province.setName(createDTO.getName());
        province.setRegion(region);
        return province;
    }
}