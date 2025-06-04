package org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.repositories;

import jakarta.validation.Valid;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.entities.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProvinceRepository extends JpaRepository<Province, Long> {


    List<Province> findAll();

    Province save(Province province);

    Optional<Province> findById(Long id);


    boolean existsProvinceByCodeAndIdNot(String code, Long id);


    boolean existsByCode(@NotEmpty(message = "{msg.province.code.notEmpty}") @Size(max = 3, message = "{msg.province.code.size}") String code);
}
