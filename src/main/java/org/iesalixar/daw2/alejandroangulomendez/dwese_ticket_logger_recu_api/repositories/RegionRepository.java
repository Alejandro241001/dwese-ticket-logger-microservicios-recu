package org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.repositories;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.dtos.RegionCreateDTO;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {

    List<Region> findAll();





    Optional<Region> findById(Long id);


    boolean existsByCode(@NotEmpty(message = "{msg.region.code.notEmpty}") @Size(max = 2, message = "{msg.region.code.size}") String code);

    boolean existsRegionByCodeAndIdNot(@NotEmpty(message = "{msg.region.code.notEmpty}") @Size(max = 2, message = "{msg.region.code.size}") String code, Long id);

    Region save(@Valid RegionCreateDTO regionCreateDTO);
}
