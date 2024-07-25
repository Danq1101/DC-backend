package com.example.project.services;

import com.example.project.models.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    List<Product> findByName(String name);

    List<Product> loadDataToElasticsearch();

}
