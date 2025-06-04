package org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.services;


import jakarta.validation.Valid;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.dtos.RegionCreateDTO;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.dtos.RegionDTO;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.entities.Region;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.mappers.RegionMapper;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.repositories.RegionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class RegionService {

    private static final Logger logger = LoggerFactory.getLogger(RegionService.class);

    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private RegionMapper regionMapper;
    @Autowired
    private MessageSource messageSource;

    /**
     * Obtiene todas las regiones de la base de datos y las convierte a DTos.
     *
     * @return Lista de objetos `RegionDTO` representando todas las regiones
     */

    public List<RegionDTO> getAllRegions() {

        try {
            logger.info("Obteniendo todas las regiones...");
            List<Region> regions = regionRepository.findAll();
            logger.info("Se encontraron {} regiones", regions.size());
            return regions.stream()
                    .map(regionMapper::toDTO)
                    .toList();
        } catch (Exception e) {
            logger.error("Error al obtener todas las regiones: {}", e.getMessage());
            throw new RuntimeException("Error al obtener todas las regiones", e);
        }


    }

    /**
     * Busca una región especifica por su ID
     *
     * @param id Identificador único de la region
     * @return Un Optional que contiene un `RegionDTO` si la región existe
     */
    public Optional<RegionDTO> getRegionById(Long id) {
        try {
            logger.info("Buscando región con ID {}", id);
            return regionRepository.findById(id).map(regionMapper::toDTO);

        } catch (Exception e) {
            logger.error("Error al buscar región con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar la región", e);
        }
    }

    /**
     * Crea una nueva región en la base de datos
     *
     * @param regionCreateDTO DTO que contiene los datos de la región a crear
     * @param locale          Idioma para los mensajes de error.
     * @return Respuesta HTTP con el estado de la operación
     * @throws IllegalArgumentException si el codigo ya existe
     */

    public RegionDTO createRegion(RegionCreateDTO regionCreateDTO, Locale locale) {

        if (regionRepository.existsByCode(regionCreateDTO.getCode())) {
            String errorMessage = messageSource.getMessage("msg.region-controller.insert.codeExist", null, locale);
            throw new IllegalArgumentException(errorMessage);
        }

        Region region = regionMapper.toEntity(regionCreateDTO);
        Region savedRegion = regionRepository.save(region);

        return regionMapper.toDTO(savedRegion);
    }

    /**
     * @param id              identificador de la región a actualizar.
     * @param regionCreateDTO DTO que contiene los nuevos datos de la región.
     * @param locale          Idioma para los mensajes de error
     * @return Respuesta HTTP con el estado de la operación
     * @throws IllegalArgumentException si la region no existe
     */

    public RegionDTO updateRegion(Long id,RegionCreateDTO regionCreateDTO, Locale locale) {

        Region existingRegion = regionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La región no existe"));

        if (regionRepository.existsRegionByCodeAndIdNot(regionCreateDTO.getCode(), id)) {
            String errorMessage = messageSource.getMessage("msg.region-controller.update.codeExist", null, locale);
            throw new IllegalArgumentException(errorMessage);
        }
        existingRegion.setCode(regionCreateDTO.getCode());
        existingRegion.setName(regionCreateDTO.getName());
        Region updateRegion = regionRepository.save(existingRegion);

        return regionMapper.toDTO(updateRegion);
    }

    /**
     * Elimina una región específica por su ID.
     *
     * @param id Identificador único de la región.
     * @throws IllegalArgumentException Si la región no existe.
     */
    public void deleteRegion(Long id) {
        if (!regionRepository.existsById(id)) {
            throw new IllegalArgumentException("La región no existe.");
        }
        regionRepository.deleteById(id);
    }
}
