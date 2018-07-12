package com.myRetail.productsapi.repository;

import com.myRetail.productsapi.domain.CurrentPrice;
import com.myRetail.productsapi.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

    @Autowired
    private CassandraOperations cassandraTemplate;

    public Product update(int productId, CurrentPrice currentPrice) {
        if (findOne(productId) != null)
            cassandraTemplate.update(new Product(productId, currentPrice));
        return findOne(productId);
    }

    public Product findOne(int productId) {
        return cassandraTemplate.selectOneById(productId, Product.class);
    }

}
