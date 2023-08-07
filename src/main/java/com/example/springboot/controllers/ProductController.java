package com.example.springboot.controllers;

import com.example.springboot.dtos.v1.ProductRecordDto;
import com.example.springboot.models.ProductModel;
import com.example.springboot.services.ProductService;
import com.example.springboot.utils.MediaType;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
    private final Logger logger = LogManager.getLogger(ProductController.class);
    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(
            consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML },
            produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        var savedProduct = productService.saveProduct(productRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        logger.info("Enter getAllProducts");

        List<ProductModel> productList = productService.getAllProducts();

        if (!productList.isEmpty()) {
            for (ProductModel model : productList) {
                ResponseEntity<Object> method = methodOn(ProductController.class).getOneProduct(model.getProductId());
                model.add(linkTo(method).withSelfRel());
            }
        }

        var ret = ResponseEntity.status(HttpStatus.OK).body(productList);
        logger.info("Exit getAllProducts: " + ret);
        return ret;
    }

    @GetMapping(value = "/{id}",
            produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") UUID id) {
        ProductModel productEntity = productService.getOne(id);
        ResponseEntity<List<ProductModel>> method = methodOn(ProductController.class).getAllProducts();
        productEntity.add(linkTo(method).withRel("Product List"));
        //ProductDtoV2 dtoV2 = DTOAdapter.adaptToEntity(productEntity, ProductDtoV2.class);
        return ResponseEntity.status(HttpStatus.OK).body(productEntity);
    }

    @PutMapping(value = "/{id}",
            consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML },
            produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id,
                                                @RequestBody @Valid ProductRecordDto productRecordDto) {
        var updatedProduct = productService.updateProduct(id, productRecordDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(productService.deleteProduct(id));
    }

}