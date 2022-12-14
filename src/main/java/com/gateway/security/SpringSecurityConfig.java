package com.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SpringSecurityConfig {

    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

    // Arreglo
    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http) {
        return http.authorizeExchange().pathMatchers("/api/autenticacion/oauth/token").permitAll()

                // AUTENTICACION -- CHECK
                .pathMatchers(HttpMethod.POST, "/api/autenticacion/autenticacion/arreglar")
                .hasAnyRole("ADMIN", "INTERVENTOR")

                // BUSQUEDA -- CHECK
                .pathMatchers(HttpMethod.GET, "/api/busqueda/busqueda/proyectos/buscar/")
                .hasAnyRole("ADMIN", "MODERATOR", "USER")
                .pathMatchers(HttpMethod.GET, "/api/busqueda/busqueda/username/ver/**").hasAnyRole("INTERVENTOR")

                // ESTADISTICA
                .pathMatchers(HttpMethod.PUT, "/api/estadistica/estadisticas/visualizaciones/aumentar/**")
                .hasAnyRole("USER", "ADMIN", "MODERATOR", "INTERVENTOR")
                .pathMatchers(HttpMethod.GET, "/api/estadistica/estadisticas/usuarios/ver/estadisticas/**",
                        "/api/estadistica/estadisticas/visualizaciones/**", "/api/estadistica/estadisticas/proyecto/**",
                        "/api/estadistica/estadisticas/export/excel/**")
                .hasAnyRole("ADMIN", "USER", "MODERATOR")

                // MURO -- CHECK
                .pathMatchers(HttpMethod.POST, "/api/muro/muros/crear/").hasAnyRole("ADMIN", "INTERVENTOR", "MODERATOR")
                .pathMatchers(HttpMethod.GET, "/api/muro/muros/listar/", "/api/muro/muros/buscar/**")
                .hasAnyRole("ADMIN", "USER", "MODERATOR")

                // NOTIFICACIONES -- CHECK
                .pathMatchers(HttpMethod.GET, "/api/notificaciones/notificaciones/revisar/**",
                        "/api/notificaciones/notificaciones/verNotificaciones/**")
                .hasAnyRole("ADMIN", "INTERVENTOR", "USER")
                .pathMatchers(HttpMethod.PUT, "/api/notificaciones/notificaciones/cambiarNotificacion/**",
                        "/api/notificaciones/notificaciones/borrarNotificacion/**")
                .hasAnyRole("ADMIN", "MODERATOR", "USER")
                .pathMatchers(HttpMethod.PUT, "/api/notificaciones/notificaciones/enviar/mensaje/prueba/")
                .hasRole("INTERVENTOR")

                // PARAMETIZACION -- CHECK
                .pathMatchers(HttpMethod.GET, "/api/parametrizacion/parametros/flutter/ver/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/parametrizacion/parametros/get/**").hasAnyRole("MODERATOR")
                .pathMatchers(HttpMethod.GET, "/api/parametrizacion/parametros/terminosycondiciones/obtener/")
                .permitAll()
                .pathMatchers(HttpMethod.PUT, "/api/parametrizacion/parametros/servicios/colocarImagen/**",
                        "/api/parametrizacion/parametros/terminosycondiciones/editar/",
                        "/api/parametrizacion/parametros/arreglar/")
                .hasAnyRole("INTERVENTOR")
                .pathMatchers(HttpMethod.POST, "/api/parametrizacion/parametros/crear/",
                        "/api/parametrizacion/parametros/servicios/crear/",
                        "/api/parametrizacion/parametros/flutter/crear/")
                .hasAnyRole("INTERVENTOR")

                // PREGUNTAS -- CHECK
                .pathMatchers(HttpMethod.POST, "/api/preguntas/preguntas/pregunta/").hasAnyRole("ADMIN", "MODERATOR")
                .pathMatchers(HttpMethod.POST, "/api/preguntas/preguntas/arreglar/").hasAnyRole("INTERVENTOR")
                .pathMatchers(HttpMethod.PUT, "/api/preguntas/preguntas/editar/").hasAnyRole("ADMIN", "MODERATOR")
                .pathMatchers(HttpMethod.GET, "/api/preguntas/preguntas/todas/**", "/api/preguntas/preguntas/una/**",
                        "/api/preguntas/preguntas/cantidad/**")
                .hasAnyRole("ADMIN", "MODERATOR", "USER", "INTERVENTOR")
                .pathMatchers(HttpMethod.DELETE, "/api/preguntas/preguntas/todas/**", "/api/preguntas/preguntas/una/**")
                .hasAnyRole("ADMIN", "MODERATOR")

                // PROYECTOS -- CHECK
                .pathMatchers(HttpMethod.POST, "/api/proyectos/proyectos/crear/").hasAnyRole("ADMIN", "MODERATOR")
                .pathMatchers(HttpMethod.POST, "/api/proyectos/proyectos/image/").hasRole("INTERVENTOR")
                .pathMatchers(HttpMethod.GET, "/api/proyectos/proyectos/listar/",
                        "/api/proyectos/proyectos/imagen/binary/**", "/api/proyectos/proyectos/imagen/downloadImage/**",
                        "/api/proyectos/proyectos/file/binary/**", "/api/proyectos/proyectos/file/downloadFile/**",
                        "/api/proyectos/proyectos/descripcion/**", "/api/proyectos/proyectos/ver/proyecto/**",
                        "/api/proyectos/proyectos/listarByMuro/**")
                .hasAnyRole("USER", "ADMIN", "MODERATOR")
                .pathMatchers(HttpMethod.GET, "/api/proyectos/proyectos/ver/creador/**")
                .hasAnyRole("ADMIN", "INTERVENTOR", "MODERATOR")
                .pathMatchers(HttpMethod.PUT, "/api/proyectos/proyectos/imagen/poner/**",
                        "/api/proyectos/proyectos/file/poner/**", "/api/proyectos/proyectos/eliminarAdmin/**",
                        "/api/proyectos/proyectos/eliminarPeticionAdmin/**", "/api/proyectos/proyectos/editEnabled/**",
                        "/api/proyectos/proyectos/editEstado/**", "/api/proyectos/proyectos/editarProyectos/**",
                        "/api/proyectos/proyectos/gamificacion/cambiar-estado/**")
                .hasAnyRole("ADMIN", "MODERATOR").pathMatchers(HttpMethod.PUT, "/api/proyectos/proyectos/arreglar/")
                .hasRole("INTERVENTOR")

                // RECOMENDACIONES -- CHECK
                .pathMatchers(HttpMethod.GET, "/api/recomendacion/recomendaciones/proyectos/**",
                        "/api/recomendacion/recomendaciones/muro/**")
                .hasAnyRole("ADMIN", "MODERATOR", "USER")
                .pathMatchers(HttpMethod.GET, "/api/recomendacion/recomendaciones/listar/").hasAnyRole("INTERVENTOR")

                // REGISTRO -- CHECK
                .pathMatchers(HttpMethod.POST, "/api/registro/registro/nuevo/", "/api/registro/registro/confirmar/**",
                        "/api/registro/registro/crear-testing/")
                .permitAll().pathMatchers(HttpMethod.POST, "/api/registro/registro/arreglar/").hasAnyRole("INTERVENTOR")

                // RESPUESTAS -- CHECK
                .pathMatchers(HttpMethod.POST, "/api/respuestas/respuestas/proyecto/**",
                        "/api/respuestas/formularios/crear/")
                .hasAnyRole("ADMIN", "MODERATOR", "USER")
                .pathMatchers(HttpMethod.POST, "/api/respuestas/respuestas/arreglar/").hasAnyRole("INTERVENTOR")
                .pathMatchers(HttpMethod.GET, "/api/respuestas/respuestas/ver/todas/username/**",
                        "/api/respuestas/respuestas/ver/todas/pregunta/proyecto/**",
                        "/api/respuestas/respuestas/proyecto/**", "/api/respuestas/formularios/ver/proyecto/**",
                        "/api/respuestas/respuestas/obtener/todas", "/api/respuestas/respuestas/ver-todas")
                .hasAnyRole("ADMIN", "MODERATOR")
                .pathMatchers(HttpMethod.GET, "/api/respuestas/formularios/ver/username/respondido/",
                        "/api/respuestas/respuestas/ver/una/username/**")
                .hasAnyRole("ADMIN", "MODERATOR", "USER")
                .pathMatchers(HttpMethod.PUT, "/api/respuestas/formularios/finalizar-usuario/")
                .hasAnyRole("ADMIN", "MODERATOR", "USER")
                .pathMatchers(HttpMethod.DELETE, "/api/respuestas/respuestas/eliminar/proyecto/todo/**",
                        "/api/respuestas/respuestas/eliminar-preguntas-problema")
                .hasRole("INTERVENTOR")

                // SUSCRIPCIONES -- CHECK
                .pathMatchers(HttpMethod.POST, "/api/suscripciones/suscripciones/crear/comentarios/**")
                .hasAnyRole("ADMIN", "INTERVENTOR", "USER")
                .pathMatchers(HttpMethod.GET, "/api/suscripciones/suscripciones/inscripcion/verificar/**",
                        "/api/suscripciones/suscripciones/likes/ver/**",
                        "/api/suscripciones/suscripciones/ver/comentarios/**")
                .hasAnyRole("ADMIN", "INTERVENTOR", "USER")
                .pathMatchers(HttpMethod.PUT, "/api/suscripciones/suscripciones/inscripcion/verificar/**",
                        "/api/suscripciones/suscripciones/inscripcion/anular/**",
                        "/api/suscripciones/suscripciones/likes/**",
                        "/api/suscripciones/suscripciones/editar/comentarios/**")
                .hasAnyRole("USER", "ADMIN")
                .pathMatchers(HttpMethod.DELETE, "/api/suscripciones/suscripciones/eliminar/comentarios/**")
                .hasAnyRole("ADMIN", "INTERVENTOR", "USER")
                .pathMatchers(HttpMethod.DELETE, "/api/suscripciones/suscripciones/eliminar/todos/comentarios/**")
                .hasRole("INTERVENTOR")

                // USUARIOS
                .pathMatchers(HttpMethod.GET, "/api/usuarios/users/existUsuario/**",
                        "/api/usuarios/users/existUsername/**", "/api/usuarios/users/existEmail/**",
                        "/api/usuarios/users/existCellPhone/**", "/api/usuarios/users/existCedula/**")
                .permitAll()
                .pathMatchers(HttpMethod.GET, "/api/usuarios/users/listar/", "/api/usuarios/users/findUsername/**",
                        "/api/usuarios/users/encontrarUsuario/**", "/api/usuarios/users/verUsuario/**",
                        "/api/usuarios/users/verificarCodigo/**", "/api/usuarios/users/file/downloadImage/**",
                        "/api/usuarios/users/obtenerEdad/**", "/api/usuarios/users/file/binary/**",
                        "/api/usuarios/cedula/verificar/editar/**")
                .hasAnyRole("ADMIN", "USER", "MODERATOR", "INTERVENTOR")
                .pathMatchers(HttpMethod.GET, "/api/usuarios/users/verRoleUsuario/**")
                .hasAnyRole("ADMIN", "INTERVENTOR")
                .pathMatchers(HttpMethod.PUT, "/api/usuarios/users/enviarCodigo/**", "/api/usuarios/users/editar/**",
                        "/api/usuarios/users/editarUsername/**", "/api/usuarios/users/editarCellPhone/**",
                        "/api/usuarios/users/editarEmail/**", "/api/usuarios/users/editarUbicacion/**",
                        "/api/usuarios/users/editarContrasena/**", "/api/usuarios/users/file/uploadImage/**")
                .hasAnyRole("ADMIN", "USER", "MODERATOR", "INTERVENTOR")
                .pathMatchers(HttpMethod.PUT, "/api/usuarios/users/eliminarAdmin/**",
                        "/api/usuarios/users/eliminarPeticionAdmin/**")
                .hasAnyRole("ADMIN", "USER", "MODERATOR")
                // OJO
                .pathMatchers(HttpMethod.POST, "/api/usuarios/users/crearUsuarioMod/").permitAll()
                // ^
                .pathMatchers(HttpMethod.PUT, "/api/usuarios/users/roleModerator/**",
                        "/api/usuarios/users/roleAdmin/**", "/api/usuarios/users/arreglar/")
                .hasRole("INTERVENTOR")

                // ESTADISTICAS DASHBOARD
                .pathMatchers(HttpMethod.GET, "/api/estadisticadashboard/**").permitAll()

                // GAMIFICACION
                .pathMatchers(HttpMethod.PUT, "/api/gamificacion/gamificacion/proyectos/editar/**",
                        "/api/gamificacion/gamificacion/proyectos/cambiar-estado/**",
                        "/api/gamificacion/gamificacion/proyectos/definir-ganadores/**")
                .hasAnyRole("MODERATOR", "ADMIN")
                .pathMatchers(HttpMethod.GET, "/api/gamificacion/gamificacion/proyectos/ver-habilitado/**",
                        "/api/gamificacion/gamificacion/proyectos/ver-ganadores/**",
                        "/api/gamificacion/gamificacion/proyectos/ver/**")
                .hasAnyRole("USER", "MODERATOR", "ADMIN")
                .pathMatchers(HttpMethod.POST, "/api/gamificacion/gamificacion/arreglar/").hasRole("INTERVENTOR")

                // INTERVENTOR
                .pathMatchers(HttpMethod.POST, "/api/interventor/version/crear/").hasRole("INTERVENTOR")
                .pathMatchers(HttpMethod.PUT, "/api/interventor/version/editar/**").hasRole("INTERVENTOR")
                .pathMatchers(HttpMethod.GET, "/api/interventor/version/existe/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/interventor/interventor/listarProyectos",
                        "/api/interventor/interventor/listarUsuarios")
                .hasRole("INTERVENTOR")
                .pathMatchers(HttpMethod.DELETE, "/api/interventor/interventor/eliminarUsuarioDefinitivamente/",
                        "/api/interventor/interventor/eliminarProyectoDefinitivamente/", "/api/interventor/version/eliminar/**")
                .hasRole("INTERVENTOR")

                // MANAGE FILES
                .pathMatchers(HttpMethod.GET, "/api/files/**").hasRole("INTERVENTOR")

                // PYTHON
                .pathMatchers("/api/aggregation/**", "/api/testing/**").permitAll()

                // AUTENTICACION RETO FLUTTER
                .pathMatchers("/reto/autenticacion/oauth/token").permitAll()

                // USUARIOS RETO FLUTTER
                .pathMatchers(HttpMethod.POST, "/reto/usuarios/registro/enviar", "/reto/usuarios/registro/confirmar/**").permitAll()
                .pathMatchers(HttpMethod.POST, "/reto/usuarios/contactos/crear/**").hasAnyRole("USER", "MODERATOR", "ADMIN")
                .pathMatchers(HttpMethod.POST, "/reto/usuarios/registro/primer-usuario/").hasRole("INTERVENTOR")
                .pathMatchers(HttpMethod.GET, "/reto/usuarios/usuarios/existe/**", "/reto/usuarios/usuarios/preguntar/usuarioExiste").permitAll()
                .pathMatchers(HttpMethod.GET, "/reto/usuarios/usuarios/listar/").hasAnyRole("INTERVENTOR", "MODERATOR", "ADMIN")
                .pathMatchers(HttpMethod.GET, "/reto/usuarios/usuarios/encontrar/**", "/reto/usuarios/contactos/listar/**")
                .hasAnyRole("USER", "MODERATOR", "ADMIN")
                .pathMatchers(HttpMethod.PUT, "/reto/usuarios/usuarios/editar/**", "/reto/usuarios/usuarios/editar-contrasena/**",
                        "/reto/usuarios/contactos/editar/**", "/reto/usuarios/usuarios/codigo/**")
                .hasAnyRole("USER", "MODERATOR", "ADMIN")
                .pathMatchers(HttpMethod.DELETE, "/reto/usuarios/usuarios/eliminar-prueba/**",
                        "/reto/usuarios/contactos/eliminar/**").hasAnyRole("USER", "ADMIN")
                .pathMatchers(HttpMethod.DELETE, "/reto/usuarios/usuarios/eliminar/**").hasRole("INTERVENTOR")
                .pathMatchers("/reto/usuarios/**").hasRole("INTERVENTOR")

                // ZONAS RETO FLUTTER -
                .pathMatchers(HttpMethod.POST, "/reto/zonas/zonas/crear").hasRole("INTERVENTOR")
                .pathMatchers(HttpMethod.DELETE, "/reto/zonas/zonas/eliminar/**").hasRole("INTERVENTOR")
                .pathMatchers(HttpMethod.GET, "/reto/zonas/zonas/listar", "/reto/zonas/zonas/numero/eventos/**",
                        "/reto/zonas/zonas/buscar/**")
                .hasAnyRole("USER", "MODERATOR", "ADMIN")
                .pathMatchers("/reto/zonas/**").hasRole("INTERVENTOR")

                // SENSORES RETO FLUTTER -
                .pathMatchers(HttpMethod.POST, "/reto/sensores/posteinteligente/crear").hasRole("INTERVENTOR")
                .pathMatchers(HttpMethod.GET, "/reto/sensores/posteinteligente/habilitar-deshabilitar/**",
                        "/reto/sensores/posteinteligente/existe/**")
                .hasRole("INTERVENTOR")
                .pathMatchers(HttpMethod.GET, "/reto/sensores/posteinteligente/listar")
                .hasAnyRole("USER", "MODERATOR", "ADMIN")
                .pathMatchers(HttpMethod.DELETE, "/reto/sensores/posteinteligente/eliminar/**").hasRole("INTERVENTOR")
                .pathMatchers("/reto/sensores/**").hasRole("INTERVENTOR")

                // EVENTOS RETO FLUTTER
                .pathMatchers(HttpMethod.GET, "/reto/events/eventos/listar", "/reto/events/eventos/listar/usuario/**",
                        "/reto/events/eventos/listar/poste/**", "/reto/events/eventos/listar/zona/**", "/reto/events/eventos/listar/status/**")
                .hasAnyRole("USER", "MODERATOR", "ADMIN")
                .pathMatchers(HttpMethod.POST, "/reto/events/eventos/crear/usuario/**", "/reto/events/files/anexar/usuarios/**").hasAnyRole("USER", "ADMIN")
                .pathMatchers(HttpMethod.PUT, "/reto/events/eventos/crear/poste/**", "/reto/events/files/anexar/poste/**").hasRole("INTERVENTOR")
                .pathMatchers(HttpMethod.DELETE, "/reto/events/eventos/eliminar/**").hasRole("INTERVENTOR")
                .pathMatchers(HttpMethod.GET,"/reto/events/files/obtener/**").permitAll()
                .pathMatchers("/reto/events/**").hasRole("INTERVENTOR")

                // HISTORICOS RETO FLUTTER
                .pathMatchers(HttpMethod.GET, "/reto/historicos/historico/listar", "/reto/historicos/historico/listar/**").hasAnyRole("USER", "MODERATOR", "ADMIN")
                /*
                 * .pathMatchers("/api/interventor/**", "/api/gamificacion/**",
                 * "/api/usuarios/**", "/api/suscripciones/**", "/api/respuestas/",
                 * "/api/registro/**", "/api/proyectos/**", "/api/notificaciones/**",
                 * "/api/estadistica/**") .hasRole("INTERVENTOR")
                 */

                // Parametrizacion BOTON
                .pathMatchers(HttpMethod.GET, "/parametrizacion/boton/**").permitAll()
                .pathMatchers(HttpMethod.POST, "/parametrizacion/boton/**").hasAnyRole("MODERATOR", "ADMIN")
                .pathMatchers(HttpMethod.PUT, "/parametrizacion/boton/**").hasAnyRole("MODERATOR", "ADMIN")


                // TRAFICO - PREPROCESAMIENTO
                .pathMatchers("/seguridad/preprocesamiento/**").permitAll()

                // TRAFICO - PREDICCION - Final
                .pathMatchers("/seguridad/prediccion/**").permitAll()

                // TRAFICO - METRICAS - Final
                .pathMatchers("/seguridad/metricas/**").permitAll()

                // TRAFICO - DATOS - Final
                .pathMatchers("/seguridad/datos/**").permitAll()

                .anyExchange().authenticated().and()
                .addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION).csrf().disable().build();
    }

}
