package com.proyecto.springboot.webflux;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.proyecto.springboot.webflux.models.dao.ProductoDao;
import com.proyecto.springboot.webflux.models.documents.Producto;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebfluxApplication implements CommandLineRunner{
	@ Autowired
	ProductoDao productoDao;
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	public static final Logger log = LoggerFactory.getLogger(SpringBootWebfluxApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mongoTemplate.dropCollection("productos").subscribe();
		Flux.just(new Producto("TV plasma", 10000.00),
				new Producto("Playstation 5", 20000.00),
				new Producto("mouse", 350.00))
		////Cuando hay mas de una linea se utiliza el return
		.flatMap(producto -> {
			producto.setCreateAt(new Date());
			return productoDao.save(producto);
			})
		.subscribe(producto -> log.info(producto.getNombre()));
		
		
		
	}

}
