package com.example.project.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "products", schema = "project")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String category;

    private String cost;

    @JsonIgnore
    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL)
    private List<Sku> skus = new ArrayList<>();

}
