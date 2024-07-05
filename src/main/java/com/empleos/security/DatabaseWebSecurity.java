package com.empleos.security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class DatabaseWebSecurity {


    @Autowired
    private DataSource dataSource;


    @Bean
    JdbcUserDetailsManager user(DataSource dataSource){

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        return jdbcUserDetailsManager;
    }


    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                //obtiene los usuarios
                .usersByUsernameQuery("select username, password, estatus from Usuarios where username=?")
                //obtiene los perfiles
                .authoritiesByUsernameQuery("select u.username, p.perfil from UsuarioPerfil up " +
                        "inner join Usuarios u on u.id = up.idUsuario " +
                        "inner join Perfiles p on p.id = up.idPerfil " +
                        "where u.username = ?");
    }



    //implementa la autorizacion
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/bootstrap/**", "/images/**", "/tinymce/**", "logos/**").permitAll()
                        .requestMatchers("/", "/home").permitAll()
                        .requestMatchers("/signup", "/search", "/vacantes/view/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());
        return http.build();
    }

}


/*

//error con este bean no muestra el login
@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/bootstrap/**", "/images/**", "/tinymce/**", "logos/**").permitAll()
                        .requestMatchers("/", "/home").permitAll()
                        .requestMatchers("/signup", "/search", "/vacantes/view/**").permitAll()
                        .anyRequest().authenticated()
                )

                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()

                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }
 */
