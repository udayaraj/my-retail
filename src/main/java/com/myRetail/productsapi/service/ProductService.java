package com.myRetail.productsapi.service;

import com.myRetail.productsapi.domain.CurrentPrice;
import com.myRetail.productsapi.domain.Product;
import com.myRetail.productsapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MyRetailService myRetailService;

    public Product update(int productId, CurrentPrice currentPrice) {
        Product product = productRepository.update(productId, currentPrice);
        getProductName(productId, product);
        return product;
    }

    public Product findOne(int productId) {
        Product product = productRepository.findOne(productId);
        getProductName(productId, product);
        return product;
    }

    private void getProductName(int productId, Product product) {
        if (product != null) {
            product.setName(myRetailService.getProductName(productId));
        }
    }

}
