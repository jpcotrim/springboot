package com.example.springboot.services;

import com.example.springboot.dtos.v1.ProductRecordDto;
import com.example.springboot.dtos.v2.ProductDtoV2;
import com.example.springboot.exceptions.ResourceNotFoundException;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;
import com.example.springboot.utils.DTOAdapter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductModel> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductModel getOne(UUID uuid) {
        return productRepository.findById(uuid).
                orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public ProductDtoV2 getOneDTO(UUID uuid) {
        ProductModel model =  getOne(uuid);
        return DTOAdapter.adaptToEntity(model, ProductDtoV2.class);
    }

    public ProductModel saveProduct(ProductModel model) {
        return productRepository.save(model);
    }

    public ProductModel saveProduct(ProductRecordDto productDTO) {
        ProductModel model = DTOAdapter.adaptToEntity(productDTO, ProductModel.class);
        return productRepository.save(model);
    }

    public ProductModel updateProduct(UUID productId, ProductRecordDto productDTO) {
        ProductModel model = getOne(productId);
        BeanUtils.copyProperties(productDTO, model);
        return productRepository.save(model);
    }

    public ProductModel deleteProduct(UUID uuid) {
        var productModel = getOne(uuid);
        productRepository.deleteById(uuid);
        return productModel;
    }

}
