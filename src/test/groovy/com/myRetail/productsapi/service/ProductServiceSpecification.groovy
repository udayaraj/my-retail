package com.myRetail.productsapi.service

import com.myRetail.productsapi.domain.CurrentPrice
import com.myRetail.productsapi.domain.Product
import com.myRetail.productsapi.repository.ProductRepository
import spock.lang.Specification

class ProductServiceSpecification extends Specification {

  ProductRepository mockProductsRepository = Mock()
  MyRetailService mockMyRetailService = Mock()
  ProductService productsService

  void setup() {
    productsService = new ProductService(productRepository: mockProductsRepository, myRetailService: mockMyRetailService)
  }

  def 'update - Updates the product content'() {
    given:

    CurrentPrice currentPrice = new CurrentPrice(value: 123.4, currency_code: 'USD')
    Product inProduct = new Product(id: 1234, current_price: currentPrice, name: 'name')

    when:

    Product updatedProduct = productsService.update(1234, currentPrice)

    then:
    updatedProduct == inProduct

    1 * mockMyRetailService.getProductName(*_) >> 'name'
    1 * mockProductsRepository.update(*_) >> inProduct
  }

  def 'findOne - Returns the existing product for given productId'() {

    when:

    Product updatedProduct = productsService.findOne(1234)

    then:
    updatedProduct == product

    if (product)
      1 * mockMyRetailService.getProductName(*_) >> 'name'

    1 * mockProductsRepository.findOne(*_) >> product

    where:
    scenario             | product
    'product present'    | new Product(id: 1234, current_price: new CurrentPrice(value: 123.4, currency_code: 'USD'), name: 'name')
    'no product present' | null
  }

}
