package com.empleos.controller;


import com.empleos.model.Usuario;
import com.empleos.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {


    @Autowired
    public IUsuarioService usuarioService;


    @GetMapping("/index")
    public String mostrarIndex(Model model){
        List<Usuario> listaUsuarios = usuarioService.buscarTodos();
        model.addAttribute("usuarios", listaUsuarios);
        return "usuarios/listUsuarios";
    }


    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable ("id") int idUsuario, RedirectAttributes attributes){
        usuarioService.eliminar(idUsuario);
        attributes.addFlashAttribute("msg", "El usuario fue eliminado");
        return "redirect:/usuarios/index";
    }


}
