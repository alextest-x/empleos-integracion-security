package com.empleos.controller;

import java.util.List;

import com.empleos.model.Vacante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.empleos.model.Categoria;
import com.empleos.service.ICategoriasService;

@Controller
@RequestMapping(value="/categorias")
public class CategoriasController {

	//ponemos el nombre del servicio CategoriaServiceJpa
	//comentamos porque usamos @primary en el servicio
	//@Qualifier("categoriaServiceJpa") hay que ponerla en donde se inyecta
	@Autowired
	private ICategoriasService serviceCategorias;
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String mostrarIndex(Model model) {
		List<Categoria> lista = serviceCategorias.buscarTodas();
    	model.addAttribute("categorias", lista);
		return "categorias/listCategorias";		
	}

	@GetMapping(value = "/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		Page<Categoria> lista = serviceCategorias.buscarTodas(page);
		model.addAttribute("categorias", lista);
		return "categorias/listCategorias";
	}


	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String crear(Categoria categoria) {
		return "categorias/formCategoria";
	}


	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String guardar(Categoria categoria, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()){		
			System.out.println("Existieron errores");
			return "categorias/formCategoria";
		}	
		
		// Guadamos el objeto categoria en la bd
		serviceCategorias.guardar(categoria);
		attributes.addFlashAttribute("msg", "Los datos de la categor�a fueron guardados!");		
		return "redirect:/categorias/index";
	}


	//borra con JPA
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idCategoria, RedirectAttributes attributes) {
		System.out.println("Borrando categoria con id: " + idCategoria);
		serviceCategorias.eliminar(idCategoria);
		attributes.addFlashAttribute("msg", "La categoria fue eliminada");
		return "redirect:/categorias/index";

	}

	//editar el formulario

	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int idCategoria, Model model){
		Categoria categoria = serviceCategorias.buscarPorId(idCategoria);
		//model.addAtribute(atributo: , valor: objeto categoria)
		model.addAttribute("categoria", categoria);  //envia al formulario

		//pasamos el model al metodo de setGenericos(Model model)
		model.addAttribute("categorias", serviceCategorias.buscarTodas() ); //busca en el formulario en el combo categorias
		return "categorias/formCategoria";
	}


	
}
