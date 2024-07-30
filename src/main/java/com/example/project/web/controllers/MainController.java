package com.example.project.web.controllers;

import com.example.project.models.Product;
import com.example.project.services.ElasticsearchService;
import com.example.project.services.impl.ElasticsearchServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/DC/home")
@CrossOrigin(origins = "http://localhost:3000")
public class MainController {

    private final ElasticsearchService elasticsearchService;

    @GetMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public List<Product> home(){
        return elasticsearchService.createClient();
    }

    @PostMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public List<Product> findProduct(@RequestParam(name = "active") String active, @RequestParam(name = "size") String size){
        return elasticsearchService.findProduct(active, size);
    }



}
