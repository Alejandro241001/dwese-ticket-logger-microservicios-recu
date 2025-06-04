package org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String message;
}
