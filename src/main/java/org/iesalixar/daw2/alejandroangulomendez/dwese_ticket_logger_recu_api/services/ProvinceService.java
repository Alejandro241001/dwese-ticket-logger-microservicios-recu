package org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.services;


import jakarta.validation.Valid;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.dtos.ProvinceCreateDTO;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.dtos.ProvinceDTO;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.entities.Province;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.entities.Region;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.mappers.ProvinceMapper;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.repositories.ProvinceRepository;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.repositories.RegionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class ProvinceService {

    private static final Logger logger = LoggerFactory.getLogger(ProvinceService.class);

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private ProvinceMapper provinceMapper;

    @Autowired
    private MessageSource messageSource;


    @Autowired
    private RegionRepository regionRepository;

    /**
     * Obtiene todas las provincias de la base de datos y las convierte a DTOs.
     *
     * @return Lista de objetos `ProvinceDTO` representando todas las provincias.
     */
    public List<ProvinceDTO> getAllProvinces() {
        try {
            logger.info("Obteniendo todas las provincias...");
            List<Province> provinces = provinceRepository.findAll();
            logger.info("Se encontraron {} provincias", provinces.size());
            return provinces.stream()
                    .map(provinceMapper::toDTO)
                    .toList();
        } catch (Exception e) {
            logger.error("Error al obtener todas las provincias: {}", e.getMessage());
            throw new RuntimeException("Error al obtener todas las provincias", e);
        }
    }

    /**
     * Busca una provincia específica por su ID.
     *
     * @param id Identificador único de la provincia.
     * @return Un Optional que contiene un `ProvinceDTO` si la provincia existe.
     */
    public Optional<ProvinceDTO> getProvinceById(Long id) {
        try {
            logger.info("Buscando provincia con ID {}", id);
            return provinceRepository.findById(id).map(provinceMapper::toDTO);
        } catch (Exception e) {
            logger.error("Error al buscar provincia con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar la provincia", e);
        }
    }

    /**
     * Crea una nueva provincia en la base de datos.
     *
     * @param provinceCreateDTO DTO que contiene los datos de la provincia a crear.
     * @param locale            Idioma para los mensajes de error.
     * @return DTO de la provincia creada.
     */
    public ProvinceDTO createProvince(@Valid ProvinceCreateDTO provinceCreateDTO, Locale locale) {
        if (provinceRepository.existsByCode(provinceCreateDTO.getCode())) {
            String errorMessage = messageSource.getMessage("msg.province-controller.insert.codeExist", null, locale);
            throw new IllegalArgumentException(errorMessage);
        }

        // Buscar la entidad Region
        Region region = regionRepository.findById(provinceCreateDTO.getRegionId())
                .orElseThrow(() -> new IllegalArgumentException("Región no encontrada"));

        // Llamar a un método del mapper que acepte la región
        Province province = provinceMapper.toEntity(provinceCreateDTO, region);

        Province savedProvince = provinceRepository.save(province);

        return provinceMapper.toDTO(savedProvince);
    }

    /**
     * Actualiza una provincia existente.
     *
     * @param id                Identificador de la provincia a actualizar.
     * @param provinceCreateDTO DTO que contiene los nuevos datos de la provincia.
     * @param locale            Idioma para los mensajes de error.
     * @return DTO de la provincia actualizada.
     */
    public ProvinceDTO updateProvince(Long id, @Valid ProvinceCreateDTO provinceCreateDTO, Locale locale) {
        Province existingProvince = provinceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La provincia no existe"));

        if (provinceRepository.existsProvinceByCodeAndIdNot(provinceCreateDTO.getCode(), id)) {
            String errorMessage = messageSource.getMessage("msg.province-controller.update.codeExist", null, locale);
            throw new IllegalArgumentException(errorMessage);
        }

        existingProvince.setCode(provinceCreateDTO.getCode());
        existingProvince.setName(provinceCreateDTO.getName());
        Province updateRegion = provinceRepository.save(existingProvince);

        Province updatedProvince = provinceRepository.save(existingProvince);

        return provinceMapper.toDTO(updatedProvince);
    }

    /**
     * Elimina una provincia específica por su ID.
     *
     * @param id Identificador único de la provincia.
     * @throws IllegalArgumentException Si la provincia no existe.
     */
    public void deleteProvince(Long id) {
        if (!provinceRepository.existsById(id)) {
            throw new IllegalArgumentException("La provincia no existe");
        }

        provinceRepository.deleteById(id);
    }
}
