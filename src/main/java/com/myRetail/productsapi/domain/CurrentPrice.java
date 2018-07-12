package com.myRetail.productsapi.domain;

import com.datastax.driver.core.DataType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;


@UserDefinedType("currentprice")
public class CurrentPrice {
    @Column("value")
    @CassandraType(type = DataType.Name.DECIMAL)
    private float value;

    @Column("currency_code")
    private String currency_code;

    CurrentPrice() {
    }

    public CurrentPrice(float value, String code) {
        this.value = value;
        this.currency_code = code;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }
}
