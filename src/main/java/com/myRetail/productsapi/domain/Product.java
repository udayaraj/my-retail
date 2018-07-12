package com.myRetail.productsapi.domain;

import com.datastax.driver.core.DataType;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("entity_products")
public class Product {
    @PrimaryKey("id")
    private int id;
    @Transient
    private String name;
    @CassandraType(type = DataType.Name.UDT, userTypeName = "currentprice")
    private CurrentPrice current_price;

    Product() {
    }

    public Product(int id, CurrentPrice currentPrice) {
        this.id = id;
        this.current_price = currentPrice;
    }

    public CurrentPrice getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(CurrentPrice current_price) {
        this.current_price = current_price;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

