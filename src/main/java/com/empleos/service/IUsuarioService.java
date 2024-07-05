package com.empleos.service;

import com.empleos.model.Usuario;

import java.util.List;

public interface IUsuarioService {

    List<Usuario> guardar(Usuario usuario);

    void eliminar(Integer idUsuario);

    List<Usuario> buscarTodos();



}
