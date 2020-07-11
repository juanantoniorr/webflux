package com.proyecto.springboot.webflux.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.proyecto.springboot.webflux.models.documents.Producto;

public interface ProductoDao extends ReactiveMongoRepository<Producto, String>{

}
