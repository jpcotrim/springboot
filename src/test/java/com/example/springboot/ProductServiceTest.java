package com.example.springboot;

import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;
import com.example.springboot.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void saveProductTest() {
        ProductModel productModel = new ProductModel();
        productModel.setName("Nome Teste");
        productModel.setPrice(BigDecimal.valueOf(99.55));

        Mockito.when(productRepository.save(Mockito.any(ProductModel.class)))
                .thenAnswer(invocation -> {
                    ProductModel savedProduct = invocation.getArgument(0);
                    savedProduct.setProductId(UUID.randomUUID());
                    return savedProduct;
                });

        ProductModel savedProduct = productService.saveProduct(productModel);

        System.out.println(savedProduct.getProductId());
        Assertions.assertNotNull(savedProduct.getProductId());
        Assertions.assertEquals("Nome Teste" , savedProduct.getName());
        Assertions.assertEquals(BigDecimal.valueOf(99.55), savedProduct.getPrice());
    }

}
