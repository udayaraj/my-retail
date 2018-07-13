package com.myRetail.productsapi.controller

import com.myRetail.productsapi.config.IntegrationTestConfiguration
import com.myRetail.productsapi.domain.CurrentPrice
import com.myRetail.productsapi.domain.Product
import com.myRetail.productsapi.repository.ProductRepository
import com.myRetail.productsapi.service.MyRetailService
import org.springframework.aop.framework.Advised
import org.springframework.aop.support.AopUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Import
import org.springframework.data.cassandra.core.CassandraOperations
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Import(IntegrationTestConfiguration)
class ProductapiApplicationSpecification extends Specification {

    final def PRODUCT_API_URL = '/myretail/v1/products/1234'
    final def PRODUCT_ID = 1234
    final def CURRENCY_CODE = 'USD'
    final def CURRENCY_VALUE = 90.00
    final def PRODUCT_NAME = 'name'
    CurrentPrice currentPrice

    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    ProductRepository productRepository

    @Autowired
    MyRetailService myRetailService

    @Autowired
    CassandraOperations cassandraTemplate

    void setup() {
        currentPrice = new CurrentPrice(CURRENCY_VALUE, CURRENCY_CODE)
    }

    def 'Get call should return product data with price - data present'() {
        given:
        def productRepository = getTargetObject(productRepository)

        when:
        def entity_product = restTemplate.getForEntity(PRODUCT_API_URL, Product)

        then:
        entity_product.statusCode == HttpStatus.OK
        entity_product.body.id == PRODUCT_ID
        entity_product.body.name == PRODUCT_NAME
        entity_product.body.current_price.value == CURRENCY_VALUE
        entity_product.body.current_price.currency_code == CURRENCY_CODE

        1 * productRepository.findOne(PRODUCT_ID) >> new Product(PRODUCT_ID, currentPrice)
        1 * myRetailService.getProductName(PRODUCT_ID) >> PRODUCT_NAME

    }

    def 'Get call should return Invalid product id  - data not present'() {
        given:
        def productRepository = getTargetObject(productRepository)

        when:
        def entity_product = restTemplate.getForEntity(PRODUCT_API_URL, String)

        then:
        entity_product.statusCode == HttpStatus.NOT_FOUND
        entity_product.body == 'Invalid product id 1234'

        1 * productRepository.findOne(PRODUCT_ID) >> null

    }

    def 'Put call should not update product  and return return Invalid product - data not present'() {
        given:
        def productRepository = getTargetObject(productRepository)
        HttpEntity<CurrentPrice> request = new HttpEntity<>(currentPrice);

        when:
        def entity_product = restTemplate.exchange(PRODUCT_API_URL, HttpMethod.PUT, request, String)

        then:

        entity_product.statusCode == HttpStatus.NOT_FOUND
        entity_product.body == 'Invalid product id 1234'

        1 * productRepository.update(*_) >> null

    }

    def 'Put call should update existing product  and return product data with price - data present'() {
        given:
        def productRepository = getTargetObject(productRepository)
        HttpEntity<CurrentPrice> request = new HttpEntity<>(currentPrice);

        when:
        def entity_product = restTemplate.exchange(PRODUCT_API_URL, HttpMethod.PUT, request, Product)

        then:
        entity_product.statusCode == HttpStatus.ACCEPTED
        entity_product.body.id == PRODUCT_ID
        entity_product.body.name == PRODUCT_NAME
        entity_product.body.current_price.value == CURRENCY_VALUE
        entity_product.body.current_price.currency_code == CURRENCY_CODE

        1 * productRepository.update(*_) >> new Product(PRODUCT_ID, currentPrice)
        1 * myRetailService.getProductName(PRODUCT_ID) >> PRODUCT_NAME

    }

    static <T> T getTargetObject(Object proxy) throws Exception {
        if (AopUtils.isAopProxy(proxy)) {
            return (T) ((Advised) proxy).getTargetSource().getTarget();
        } else {
            return (T) proxy;
        }
    }
}
