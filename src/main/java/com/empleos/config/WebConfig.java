package com.empleos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*

   Por defecto en un proyecto de Spring Bootcon Thymeleaflos recursos estáticos
   (css, javascript, images) están configurados en el directorio:
   src/main/resources/static
    mapear recursos estáticos de otros directorios adicionales

    Mapping URL
    Si tenemos por ejemplo la imagen “logo.png” en el directorio c:/empleos/img-vacantes/ podríamos llamar la imagen con la siguiente URL:
    http://localhost:8080/logos/logo.png

 */
@Configuration
public class WebConfig implements WebMvcConfigurer {


    //ponemos la variable que esta en el properties
    //inyectamos con value y como parametro ponemos el nombre de la propiedad
    @Value("${empleosapp.ruta.imagenes}")
    private String rutaImagenes; //esta variable

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //registry.addResourceHandler("/logos/**").addResourceLocations("file:/empleos/img-vacantes/"); // Linux
        // registry.addResourceHandler("/logos/**").addResourceLocations("file:c:/empleos/img-vacantes/"); // Windows

        registry.addResourceHandler("/logos/**").addResourceLocations("file:" + rutaImagenes);

    }
}
