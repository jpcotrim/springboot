package com.example.springboot.consumers;

import com.example.springboot.dtos.ProductRecordDto;
import com.example.springboot.models.ProductModel;
import com.example.springboot.services.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ProductConsumer {

    private final ProductService productService;

    @Autowired
    public ProductConsumer(ProductService productService) {
        this.productService = productService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listen(@Payload ProductRecordDto productDto) {
        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(productDto, productModel);
        productService.saveProduct(productModel);
    }
}
