package org.iesalixar.daw2.alejandroangulomendez.dwese_auth_service.controllers;



import jakarta.validation.Valid;
import org.iesalixar.daw2.alejandroangulomendez.dwese_auth_service.dtos.AuthResponseDTO;
import org.iesalixar.daw2.alejandroangulomendez.dwese_auth_service.dtos.AuthRequestDTO;
import org.iesalixar.daw2.alejandroangulomendez.dwese_auth_service.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     *
     * genera un token JWT que incluye informacion del usuario y sus roles.
     *
     * @param authRequest Un objeto {@link org.iesalixar.daw2.alejandroangulomendez.dwese_auth_service.dtos.AuthRequestDTO} que contiene el nombre de usuario y la contraseña
     * @return Una respuesta HTTP con un token JWT en caso de éxito o un error en caso de fallo.
     *
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDTO> authenticate(@Valid @RequestBody AuthRequestDTO authRequest){
        try {

            if(authRequest.getUsername() == null || authRequest.getPassword() == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthResponseDTO(null, "El nombre de usuario y la contraseña son obligatorios"));

            }
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            String username = authentication.getName();
            List<String> roles = authentication.getAuthorities().stream()
                    .map(authority -> authority.getAuthority())
                    .toList();
            String token = jwtUtil.generateToken(username, roles);
            return ResponseEntity.ok(new AuthResponseDTO(token, "Authentication sucessful"));
        }catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponseDTO(null, "Credenciales inválidas. Por favor, verifica tus datos."));

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponseDTO(null, "Ocurrió un error inesperado. Por favor inténtalo de nuevo más tarde."));
        }
    }

    /**
     *
     * Maneja excepciones no controles que puedan ocurrir en el controlador
     *
     * @param e La excepción lanzada
     * @return Una respuesta HTTP con el mensaje de error y el estado HTTP correspondiente.
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AuthResponseDTO> handleException(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new AuthResponseDTO(null, "Ocurrió un error inesperado: " + e.getMessage()));
    }
}
