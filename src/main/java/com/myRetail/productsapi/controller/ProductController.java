package com.myRetail.productsapi.controller;

import com.myRetail.productsapi.domain.CurrentPrice;
import com.myRetail.productsapi.domain.Product;
import com.myRetail.productsapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("myretail/v1/")
public class ProductController
{
  @Autowired
  ProductService productService;

    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProductById(@PathVariable("id") int productId, @RequestBody CurrentPrice currentPrice)
    {
        Product product = productService.update(productId,currentPrice);
        if (null != product){
            return new ResponseEntity<Product>(product, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Invalid product id "+productId, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getProductById(@PathVariable("id") int productId)
    {
        Product product = productService.findOne(productId);
        if (null != product){
            return new ResponseEntity<Product>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid product id "+productId, HttpStatus.NOT_FOUND);
        }
    }


}
