package com.myRetail.productsapi.controller

import com.myRetail.productsapi.domain.CurrentPrice
import com.myRetail.productsapi.domain.Product
import com.myRetail.productsapi.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

class ProductControllerSpecification extends Specification {

    ProductService mockProductsService = Mock()
    ProductController productsController
    CurrentPrice currentPrice
    final def PRODUCT_ID = 1234
    final def CURRENCY_CODE = 'USD'
    final def CURRENCY_VALUE = 90.00

    void setup() {
        productsController = new ProductController(productService: mockProductsService)
        currentPrice = new CurrentPrice(CURRENCY_VALUE, CURRENCY_CODE)
    }

    def "getProduct - fetch product details by id"() {

        when:

        ResponseEntity<Product> product = productsController.getProductById(PRODUCT_ID)

        then:

        product.statusCode == responseCode
        if (!product) {
            product.body.current_price.currency_code == CURRENCY_CODE
            product.body.current_price.value == CURRENCY_CODE
            product.body.id == PRODUCT_ID
        }

        1 * mockProductsService.findOne(_) >> queriedProduct

        where:
        scenario                                     | queriedProduct                                    | responseCode
        'when provided product id present in db'     | new Product(1234, new CurrentPrice(12.22, 'USD')) | HttpStatus.OK
        'when provided product id not present in db' | null                                              | HttpStatus.NOT_FOUND

    }

    def "updateProductById - update product details by id"() {

        when:

        ResponseEntity<Product> product = productsController.updateProductById(PRODUCT_ID, currentPrice)

        then:

        product.statusCode == responseCode
        if (!product) {
            product.body.current_price.currency_code == CURRENCY_CODE
            product.body.current_price.value == CURRENCY_VALUE
            product.body.id == PRODUCT_ID
        }

        1 * mockProductsService.update(*_) >> queriedProduct

        where:
        scenario                                     | queriedProduct                                    | responseCode
        'when provided product id present in db'     | new Product(1234, new CurrentPrice(12.22, 'USD')) | HttpStatus.ACCEPTED
        'when provided product id not present in db' | null                                              | HttpStatus.NOT_FOUND

    }

}
