package com.myRetail.productsapi.repository

import com.myRetail.productsapi.domain.CurrentPrice
import com.myRetail.productsapi.domain.Product
import org.springframework.data.cassandra.core.CassandraOperations
import spock.lang.Specification

class ProductRepositorySpecification extends Specification {

    CassandraOperations mockCassandraOperations = Mock()
    ProductRepository productsRepository

    void setup() {
        productsRepository = new ProductRepository(cassandraTemplate: mockCassandraOperations)
    }

    def "Update - Update the existing product in cassandra by id"() {

        when:

        Product product = productsRepository.update(1234, new CurrentPrice(value: 23.09))

        then:

        product == resultProduct
        2 * mockCassandraOperations.selectOneById(*_) >> resultProduct
        if (resultProduct)
            1 * mockCassandraOperations.update(*_)

        where:
        scenario               | resultProduct
        'product present '     | new Product(id: 1234)
        'product not present ' | null
    }

    def "FindOne - Find the product by id"() {

        when:

        productsRepository.findOne(1234)

        then:

        1 * mockCassandraOperations.selectOneById(*_)
    }
}
