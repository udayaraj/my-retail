package com.myRetail.productsapi.config

import com.myRetail.productsapi.repository.ProductRepository
import com.myRetail.productsapi.service.MyRetailService
import org.springframework.context.annotation.Bean
import spock.mock.DetachedMockFactory

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

}
