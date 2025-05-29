package org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.repositories;


import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {

    List<Region> findAll();


    Region save(Region region);

    boolean existsRegionByCode(String code);

    Optional<Region> findById(Long id);


    boolean existsRegionByCodeAndIdNot(String code, Long id);


}
