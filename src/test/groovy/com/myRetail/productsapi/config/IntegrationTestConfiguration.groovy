package com.myRetail.productsapi.config

import com.myRetail.productsapi.repository.ProductRepository
import com.myRetail.productsapi.service.MyRetailService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.cassandra.core.CassandraOperations
import spock.mock.DetachedMockFactory

@Configuration
class IntegrationTestConfiguration {

    private DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

    @Bean
    ProductRepository productRepository() {
        detachedMockFactory.Mock(ProductRepository)
    }

    @Bean
    MyRetailService myRetailService(){
        detachedMockFactory.Mock(MyRetailService)
    }

    @Bean
    CassandraOperations cassandraTemplate(){
        detachedMockFactory.Mock(CassandraOperations)
    }
}
