package com.example.project.services.impl;

import com.example.project.models.Product;
import com.example.project.repositories.ProductRepository;
import com.example.project.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public List<Product> loadDataToElasticsearch(){
        List<Product> products = productRepository.findAll();
        BulkRequest bulkRequest = new BulkRequest();
        List<IndexRequest> indexRequests = products.stream()
                .map(product -> new IndexRequest("my-index")
                        .id(String.valueOf(product.getId()))
                        .source(product)).toList();
        bulkRequest.add((IndexRequest) indexRequests);
        return products;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findByName(String name) {
        return productRepository.findByName(name);
    }


}
