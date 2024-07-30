package com.example.project.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.example.project.models.Product;
import com.example.project.models.Sku;
import com.example.project.repositories.ProductRepository;
import com.example.project.repositories.SkuRepository;
import com.example.project.services.ElasticsearchService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ElasticsearchServiceImpl implements ElasticsearchService {

    private final ProductRepository productRepository;

    private final SkuRepository skuRepository;

    private static final String INDEX_NAME = "my-index4";

    RestClient restClient = RestClient
            .builder(HttpHost.create("http://localhost:9200"))
            .build();


    ElasticsearchTransport transport = new RestClientTransport(
            restClient, new JacksonJsonpMapper()
    );

    ElasticsearchClient client = new ElasticsearchClient(transport);


    @SneakyThrows
    public List<Product> createClient() {
        List<Product> products = productRepository.findAll();
        List<Sku> skus = skuRepository.findAll();

//        client.indices().create(c->c
//                .index(INDEX_NAME));

        BulkRequest.Builder br = new BulkRequest.Builder();

        for (Product product : products) {
            br.operations(op -> op
                    .index(idx -> idx
                            .index(INDEX_NAME)
                            .id(String.valueOf(product.getId()))
                            .document(product)
                    )
            );
        }


        BulkResponse result = client.bulk(br.build());

        List<Product> esProducts = new ArrayList<>();

        for (Product product : products) {
            GetResponse<Product> response = client.get(g -> g
                            .index(INDEX_NAME)
                            .id(String.valueOf(product.getId())),
                    Product.class);
            esProducts.add(response.source());
        }


        return esProducts;
    }

    @SneakyThrows
    public List<Product> findProduct(String isActive, String size){
        Query byActive = MatchQuery.of(m->m
                .field("active")
                .query(isActive))._toQuery();


        Query bySize = MatchQuery.of(r -> r
                .field("skus.size")
                .query(size))._toQuery();

        SearchResponse<Product> response = client.search(s->s
                .index(INDEX_NAME)
                .query(q->q
                        .bool(b->b
                                .must(byActive)
                                .must(bySize))), Product.class);

        List<Hit<Product>> hits = response.hits().hits();

        List<Product> products = new ArrayList<>();
        Set<Long> uniqueIds = new HashSet<>();
        for (Hit<Product> hit: response.hits().hits()) {
            Product product = hit.source();
            if (uniqueIds.add(product.getId())) {
                products.add(product);
            }
        }
        return products;
    }

}
