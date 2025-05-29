package org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.controllers;


import jakarta.validation.Valid;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.repositories.RegionRepository;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.entities.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;
import java.util.Locale;
import java.util.Optional;


/**
 * Controlador que maneja las operaciones CRUD para la entidad `Region`.
 * Utiliza `RegionDAO` para interactuar con la base de datos.
 */
@Controller
@RequestMapping("/regions")

public class RegionController {



    private static final Logger logger = LoggerFactory.getLogger(RegionController.class);


    // DAO para gestionar las operaciones de las regiones en la base de datos
    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private MessageSource messageSource;



    /**
     * Lista todas las regiones y las pasa como atributo al modelo para que sean
     * accesibles en la vista `region.html`.
     *
     * @param model Objeto del modelo para pasar datos a la vista.
     * @return El nombre de la plantilla Thymeleaf para renderizar la lista de regiones.
     */
    @GetMapping
    public String listRegions(Model model) {
        logger.info("Solicitando la lista de todas las regiones...");
        List<Region> listRegions = regionRepository.findAll();
        logger.info("Se han cargado {} regiones.", listRegions.size());
        model.addAttribute("listRegions", listRegions);
        return "regions";
    }


    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("region", new Region());  // Pasamos un objeto vacío a la vista
        return "region-form";  // Nombre de la vista del formulario
    }


    /**
     * Inserta una nueva región en la base de datos.
     *
     * @param region              Objeto que contiene los datos del formulario.
     * @param redirectAttributes  Atributos para mensajes flash de redirección.
     * @return Redirección a la lista de regiones.
     */
    @PostMapping("/insert")
    public String insertRegion(@Valid @ModelAttribute("region") Region region, BindingResult result, RedirectAttributes redirectAttributes,  Locale locale) {
        logger.info("Insertando nueva región con código {}", region.getCode());
        if (result.hasErrors()) {
            return "region-form";  // Devuelve el formulario para mostrar los errores de validación
        }

        if (regionRepository.existsRegionByCode(region.getCode())) {
            logger.warn("El código de la región {} ya existe.", region.getCode());
            String errorMessage = messageSource.getMessage("msg.region-controller.insert.codeExist", null, locale);

            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/regions/new";
        }

        regionRepository.save(region);
        logger.info("Región {} insertada con éxito.", region.getCode());

        return "redirect:/regions"; // Redirigir a la lista de regiones
    }

    /**
     * Muestra el formulario para editar una región existente.
     *
     * @param id    ID de la región a editar.
     * @param model Modelo para pasar datos a la vista.
     * @return El nombre de la plantilla Thymeleaf para el formulario.
     */
    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") Long id, Model model, RedirectAttributes redirectAttributes, Locale locale) {
        logger.info("Mostrando formulario de edición para la región con ID {}", id);
        Optional<Region> region = regionRepository.findById(id);
        if (region.isEmpty()) {
            logger.warn("No se encontró la región con ID {}", id);
            String errorMessage = messageSource.getMessage("msg.region-controller.edit.notFound", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/regions";
        }
        model.addAttribute("region", region.get());
        return "region-form";
    }



    /**
     * Actualiza una región existente en la base de datos.
     *
     * @param region              Objeto que contiene los datos del formulario.
     * @param redirectAttributes  Atributos para mensajes flash de redirección.
     * @return Redirección a la lista de regiones.
     */
    @PostMapping("/update")
    public String updateRegion(@Valid @ModelAttribute("region") Region region,BindingResult result, RedirectAttributes redirectAttributes,  Locale locale) {
        logger.info("Actualizando región con ID {}", region.getId());
        if (result.hasErrors()) {
            return "region-form";  // Devuelve el formulario para mostrar los errores de validación
        }

            if (regionRepository.existsRegionByCodeAndIdNot(region.getCode(), region.getId())) {
                logger.warn("El código de la región {} ya existe para otra región.", region.getCode());
                String errorMessage = messageSource.getMessage("msg.region-controller.insert.codeExist", null, locale);

                redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
                return "redirect:/regions/edit?id=" + region.getId();
            }
            regionRepository.save(region);
            logger.info("Región con ID {} actualizada con éxito.", region.getId());

        return "redirect:/regions"; // Redirigir a la lista de regiones
    }



    /**
     * Elimina una región de la base de datos.
     *
     * @param id                 ID de la región a eliminar.
     * @param redirectAttributes Atributos para mensajes flash de redirección.
     * @return Redirección a la lista de regiones.
     */
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/delete")
    public String deleteRegion(@RequestParam("id") Long id, RedirectAttributes redirectAttributes, Locale locale) {
        logger.info("Eliminando región con ID {}", id);
        if (!regionRepository.existsById(id)) {
            String errorMessage = messageSource.getMessage("msg.region-controller.delete.notFound", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/regions";
        }
        try {
            regionRepository.deleteById(id);
            String successMessage = messageSource.getMessage("msg.region-controller.delete.success", null, locale);
            redirectAttributes.addFlashAttribute("successMessage", successMessage);
            logger.info("Región con ID {} eliminada con éxito.", id);
        } catch (Exception e) {
            logger.error("Error al eliminar la región con ID {}: {}", id, e.getMessage());
            String errorMessage = messageSource.getMessage("msg.region-controller.delete.error", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
        }
        return "redirect:/regions";
    }


}
