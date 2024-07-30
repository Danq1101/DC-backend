package com.example.project.services;

import com.example.project.models.Product;
import lombok.SneakyThrows;

import java.util.List;

public interface ElasticsearchService {

    List<Product> createClient();

    List<Product> findProduct(String isActive, String size);

}
