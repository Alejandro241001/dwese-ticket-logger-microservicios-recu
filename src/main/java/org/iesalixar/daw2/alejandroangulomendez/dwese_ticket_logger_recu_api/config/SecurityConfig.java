package org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.config;


import org.iesalixar.daw2.alejandroangulomendez.dwese_ticket_logger_recu_api.services.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * Configura la seguridad de la aplicación, definiendo autenticación y autorización
 * para diferentes roles de usuario, y gestionando la política de sesiones.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)  // Activa la seguridad basada en métodos
public class SecurityConfig {


    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;



    /**
     * Configura el filtro de seguridad para las solicitudes HTTP, especificando las
     * rutas permitidas y los roles necesarios para acceder a diferentes endpoints.
     *
     * @param http instancia de {@link HttpSecurity} para configurar la seguridad.
     * @return una instancia de {@link SecurityFilterChain} que contiene la configuración de seguridad.
     * @throws Exception si ocurre un error en la configuración de seguridad.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Entrando en el método securityFilterChain");


        // Configuración de seguridad
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/api/tickets").hasRole("USER")
                        .requestMatchers("/api/admin").hasRole("ADMIN")
                        .requestMatchers(
                                "/api/regions",
                                "/api/provinces",
                                "/api/supermarkets",
                                "/api/locations",
                                "/apicategories").hasRole("MANAGER")
                        .requestMatchers("/api/v1/authenticate", "/api/v1/register").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Configura el proveedor de autenticación para usar el servicio de detalles de usuario
     * personalizado y el codificador de contraseñas.
     *
     * @return una instancia de {@link DaoAuthenticationProvider} para la autenticación.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Configura el codificador de contraseñas para cifrar las contraseñas de los usuarios
     * utilizando BCrypt.
     *
     * @return una instancia de {@link PasswordEncoder} que utiliza BCrypt para cifrar contraseñas.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.info("Entrando en el método passwordEncoder");
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        logger.info("Saliendo del método passwordEncoder");
        return encoder;
    }
    /**
     *
     * Configura y expone un bean de tipo {@link org.springframework.security.authentication.AuthenticationManager}.
     *
     * En Spring Security, el `AuthencationManager` es el componente principal que se encarga
     * de procesar solicitudes de autenticación. Este método obtiene la instancia de
     * `AuthenticationManager` configurada automaticamente por Srping a través de
     * `AuthenticationConfiguration` y la expone como un bean disponible en el contexto
     * de la aplicacion
     *
     * @param configuration Objeto de tipo{@link org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration} que contiene
     *                      la configuracion de autenticacion de Spring Security. Este objeto
     *                      incluye los detalles del flujo de autenticación configurado, como
     *                      el proveedor de autenticación y los detalles del usuario.
     * @return Una instancia de {@link org.springframework.security.authentication.AuthenticationManager} configurada con los detalles
     *          especificados en la apliacion
     * @throws Exception Si ocurre algún error al obtener el `AuthenticationManager`.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception{
        return configuration.getAuthenticationManager();
    }
}
