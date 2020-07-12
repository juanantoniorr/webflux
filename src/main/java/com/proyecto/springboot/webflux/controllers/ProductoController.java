package com.proyecto.springboot.webflux.controllers;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

import com.proyecto.springboot.webflux.models.dao.ProductoDao;
import com.proyecto.springboot.webflux.models.documents.Producto;

import reactor.core.publisher.Flux;

@Controller
public class ProductoController {
	Logger logger = LoggerFactory.getLogger(ProductoController.class);

	@Autowired
	private ProductoDao productoDao;
	
	@GetMapping({"/listar", "/"}) //lleva llaves si esta mapeada a 2 vistas
	public String listar (Model model) {
		Flux<Producto> productos = productoDao.findAll()
				.map(producto -> {
					producto.setNombre(producto.getNombre().toUpperCase());
					return producto;
				});
		productos.subscribe(prod -> logger.info(prod.getNombre()));
		//Aqui no se pone el subscribe porque va en automatico en thymeleaf
		//Se va a agregar un buffer para ir mostrando en bloques articulos
		model.addAttribute("productos", productos);
		model.addAttribute("titulo", "Listado de productos");
		return "listar";
		
	}
	
	@GetMapping("/listar-datadriver") //lleva llaves si esta mapeada a 2 vistas
	public String listarDtaDriver (Model model) {
		Flux<Producto> productos = productoDao.findAll()
				.map(producto -> {
					producto.setNombre(producto.getNombre().toUpperCase());
					return producto;
				})
				//Crea un delay en la carga de los elementos
				.delayElements(Duration.ofSeconds(1));
		productos.subscribe(prod -> logger.info(prod.getNombre()));
		//Aqui no se pone el subscribe porque va en automatico en thymeleaf
		model.addAttribute("productos", new ReactiveDataDriverContextVariable(productos, 2));
		model.addAttribute("titulo", "Listado de productos");
		return "listar";
		
	}
	
	@GetMapping({"/listar-full"}) //lleva llaves si esta mapeada a 2 vistas
	public String listarFull (Model model) {
		Flux<Producto> productos = productoDao.findAll()
				.map(producto -> {
					producto.setNombre(producto.getNombre().toUpperCase());
					return producto;
				})
				.repeat(5000);
		productos.subscribe(prod -> logger.info(prod.getNombre()));
		//Aqui no se pone el subscribe porque va en automatico en thymeleaf
		//Se va a agregar un buffer para ir mostrando en bloques articulos
		model.addAttribute("productos", productos);
		model.addAttribute("titulo", "Listado de productos");
		return "listar";
		
	}
	
	@GetMapping({"/listar-chunked"}) //lleva llaves si esta mapeada a 2 vistas
	public String listarChunked (Model model) {
		Flux<Producto> productos = productoDao.findAll()
				.map(producto -> {
					producto.setNombre(producto.getNombre().toUpperCase());
					return producto;
				})
				.repeat(5000);
		productos.subscribe(prod -> logger.info(prod.getNombre()));
		//Aqui no se pone el subscribe porque va en automatico en thymeleaf
		//Se va a agregar un buffer para ir mostrando en bloques articulos
		model.addAttribute("productos", productos);
		model.addAttribute("titulo", "Listado de productos");
		return "listar-chunked";
		
	}
}
