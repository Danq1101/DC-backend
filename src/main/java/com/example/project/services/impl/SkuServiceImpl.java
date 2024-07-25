package com.example.project.services.impl;


import com.example.project.models.Sku;
import com.example.project.repositories.SkuRepository;
import com.example.project.services.SkuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkuServiceImpl implements SkuService {

    private final SkuRepository skuRepository;

    @Override
    public List<Sku> findAll(){
        return skuRepository.findAll();
    }



}
