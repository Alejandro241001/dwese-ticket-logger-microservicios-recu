package org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.controllers;

import jakarta.validation.Valid;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.entities.Province;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.entities.Region;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.repositories.ProvinceRepository;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.repositories.RegionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Controller
@RequestMapping("/provinces")
public class ProvinceController {

    private static final Logger logger = LoggerFactory.getLogger(ProvinceController.class);

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public String listProvinces(Model model) {
        logger.info("Solicitando la lista de todas las provincias...");
        List<Province> listProvinces = provinceRepository.findAll();
        logger.info("Se han cargado {} provincias.", listProvinces.size());
        model.addAttribute("listProvinces", listProvinces);
        return "province";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        logger.info("Mostrando formulario para nueva provincia.");
        List<Region> listRegions = regionRepository.findAll();
        model.addAttribute("province", new Province());
        model.addAttribute("listRegions", listRegions);
        return "province-form";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") Long id, Model model) {
        logger.info("Mostrando formulario de edición para la provincia con ID {}", id);
        Optional<Province> province = provinceRepository.findById(id);
        if (province.isEmpty()) {
            logger.warn("Provincia con ID {} no encontrada.", id);
            model.addAttribute("errorMessage", "Provincia no encontrada.");
            return "redirect:/provinces";
        }

        List<Region> listRegions = regionRepository.findAll();
        model.addAttribute("province", province.get());
        model.addAttribute("listRegions", listRegions);
        return "province-form";
    }

    @PostMapping("/insert")
    public String insertProvince(@Valid @ModelAttribute("province") Province province, BindingResult result,
                                 RedirectAttributes redirectAttributes, Locale locale, Model model) {
        logger.info("Insertando nueva provincia con código {}", province.getCode());

        if (result.hasErrors()) {
            List<Region> listRegions = regionRepository.findAll();
            model.addAttribute("listRegions", listRegions);
            return "province-form";
        }

        if (provinceRepository.existsProvinceByCode(province.getCode())) {
            logger.warn("El código de la provincia {} ya existe.", province.getCode());
            String errorMessage = messageSource.getMessage("msg.province-controller.insert.codeExist", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/provinces/new";
        }

        provinceRepository.save(province);
        logger.info("Provincia {} insertada con éxito.", province.getCode());
        return "redirect:/provinces";
    }

    @PostMapping("/update")
    public String updateProvince(@Valid @ModelAttribute("province") Province province, BindingResult result,
                                 RedirectAttributes redirectAttributes, Locale locale, Model model) {
        logger.info("Actualizando provincia con ID {}", province.getId());

        if (result.hasErrors()) {
            List<Region> listRegions = regionRepository.findAll();
            model.addAttribute("listRegions", listRegions);
            return "province-form";
        }

        if (provinceRepository.existsProvinceByCodeAndIdNot(province.getCode(), province.getId())) {
            logger.warn("El código de la provincia {} ya existe para otra provincia.", province.getCode());
            String errorMessage = messageSource.getMessage("msg.province-controller.update.codeExist", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/provinces/edit?id=" + province.getId();
        }

        provinceRepository.save(province);
        logger.info("Provincia con ID {} actualizada con éxito.", province.getId());
        return "redirect:/provinces";
    }

    @PostMapping("/delete")
    public String deleteProvince(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        logger.info("Eliminando provincia con ID {}", id);
        try {
            Optional<Province> province = provinceRepository.findById(id);
            if (province.isPresent()) {
                provinceRepository.deleteById(id);
                logger.info("Provincia con ID {} eliminada con éxito.", id);
                redirectAttributes.addFlashAttribute("successMessage", "Provincia eliminada con éxito.");
            } else {
                logger.warn("Provincia con ID {} no encontrada.", id);
                redirectAttributes.addFlashAttribute("errorMessage", "Provincia no encontrada.");
            }
        } catch (Exception e) {
            logger.error("Error al eliminar la provincia con ID {}: {}", id, e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar la provincia.");
        }
        return "redirect:/provinces";
    }

}
