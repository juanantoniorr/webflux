package com.proyecto.springboot.webflux.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.springboot.webflux.models.dao.ProductoDao;
import com.proyecto.springboot.webflux.models.documents.Producto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
public class ProductRestController {
	Logger logger = LoggerFactory.getLogger(ProductoController.class);

	@Autowired
	private ProductoDao productoDao;
	@GetMapping
	//tiene que regresar un observable (Flux)
	public Flux<Producto> index(){
		Flux<Producto> productos = productoDao.findAll()
				.map(producto -> {
					producto.setNombre(producto.getNombre().toUpperCase());
					return producto;
				})
				.doOnNext(prod -> logger.info(prod.getNombre()));
		return productos;
	}
	
	@GetMapping("/{id}")
	public Mono<Producto> getById(@PathVariable String id){
		//Forma facil
		//Mono <Producto> producto = productoDao.findById(id);
		Flux<Producto> productos = productoDao.findAll();
		//Filter regresa un boolean 
		Mono <Producto> producto = productos.filter(p -> p.getId().equals(id)).next();
		
		return producto;
		
	}
}
