package org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.controllers;

import jakarta.validation.Valid;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.dtos.ProvinceCreateDTO;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.dtos.ProvinceDTO;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.services.ProvinceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Controlador que maneja las operaciones CRUD para la entidad `Province`.
 */
@RestController
@RequestMapping("/api/provinces")
public class ProvinceController {

    private static final Logger logger = LoggerFactory.getLogger(ProvinceController.class);

    @Autowired
    private ProvinceService provinceService;

    /**
     * Lista todas las provincias.
     *
     * @return ResponseEntity con la lista de provincias o un mensaje de error.
     */
    @GetMapping
    public ResponseEntity<List<ProvinceDTO>> getAllProvinces() {
        logger.info("Solicitando la lista de todas las provincias...");
        try {
            List<ProvinceDTO> provinceDTOs = provinceService.getAllProvinces();
            logger.info("Se han encontrado {} provincias.", provinceDTOs.size());
            return ResponseEntity.ok(provinceDTOs);
        } catch (Exception e) {
            logger.error("Error al listar las provincias: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Obtiene una provincia específica por su ID.
     *
     * @param id ID de la provincia.
     * @return ResponseEntity con la provincia encontrada o un mensaje de error.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProvinceDTO> getProvinceById(@PathVariable Long id) {
        logger.info("Buscando provincia con ID {}", id);
        try {
            Optional<ProvinceDTO> provinceDTO = provinceService.getProvinceById(id);
            if (provinceDTO.isPresent()) {
                logger.info("Provincia con ID {} encontrada.", id);
                return ResponseEntity.ok(provinceDTO.get());
            } else {
                logger.warn("No se encontró ninguna provincia con ID {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            logger.error("Error al buscar la provincia con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Inserta una nueva provincia en la base de datos.
     *
     * @param provinceCreateDTO DTO con los datos de la nueva provincia.
     * @param locale            Idioma para los mensajes.
     * @return ResponseEntity con la provincia creada o un mensaje de error.
     */
    @PostMapping
    public ResponseEntity<?> createProvince(@Valid @RequestBody ProvinceCreateDTO provinceCreateDTO, Locale locale) {
        logger.info("Insertando nueva provincia con código {}", provinceCreateDTO.getCode());
        try {
            ProvinceDTO createdProvince = provinceService.createProvince(provinceCreateDTO, locale);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProvince);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al crear la provincia: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al crear la provincia: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la provincia.");
        }
    }

    /**
     * Actualiza una provincia existente.
     *
     * @param id                ID de la provincia a actualizar.
     * @param provinceCreateDTO DTO con los datos actualizados.
     * @param locale            Idioma para los mensajes.
     * @return ResponseEntity con la provincia actualizada o un mensaje de error.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProvince(@PathVariable Long id, @Valid @RequestBody ProvinceCreateDTO provinceCreateDTO, Locale locale) {
        logger.info("Actualizando provincia con ID {}", id);
        try {
            ProvinceDTO updatedProvince = provinceService.updateProvince(id, provinceCreateDTO, locale);
            return ResponseEntity.ok(updatedProvince);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al actualizar la provincia: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al actualizar la provincia con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la provincia.");
        }
    }

    /**
     * Elimina una provincia por su ID.
     *
     * @param id ID de la provincia a eliminar.
     * @return ResponseEntity con el resultado de la operación.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProvince(@PathVariable Long id) {
        logger.info("Eliminando provincia con ID {}", id);
        try {
            provinceService.deleteProvince(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.warn("Error al eliminar la provincia: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al eliminar la provincia con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la provincia.");
        }
    }
}