package com.example.project.web.controllers;

import com.example.project.models.Product;
import com.example.project.models.Sku;
import com.example.project.services.ProductService;
import com.example.project.services.SkuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/DC/home")
public class MainController {

    private final ProductService productService;

    private final SkuService skuService;

    @GetMapping
    public ResponseEntity<List<?>> home(){
        List<Product> products = productService.loadDataToElasticsearch();
        List<Sku> skus = skuService.findAll();
        return ResponseEntity.ok(Arrays.asList(products, skus));
    }

    @PostMapping
    public List<Product> findProduct(String name){
        return productService.findByName(name);
    }

}
