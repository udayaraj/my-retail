CREATE KEYSPACE IF NOT EXISTS definition_products WITH REPLICATION= { 'class':'SimpleStrategy', 'replication_factor':1 };
CREATE TYPE IF NOT EXISTS definition_products.currentprice ( value decimal, currency_code varchar);
CREATE TABLE IF NOT EXISTS  definition_products.entity_products (
    id int PRIMARY KEY,
    current_price frozen <currentprice>
);
INSERT INTO definition_products.entity_products (id, current_price) VALUES (15117729,{value:13.49, currency_code:'USD'});
INSERT INTO definition_products.entity_products (id, current_price) VALUES (52328755,{value:14.39, currency_code:'USD'});
INSERT INTO definition_products.entity_products (id, current_price) VALUES (16483589,{value:103.63, currency_code:'USD'});
INSERT INTO definition_products.entity_products (id, current_price) VALUES (16696652,{value:99.89, currency_code:'USD'});
INSERT INTO definition_products.entity_products (id, current_price) VALUES (16752456,{value:2000.45, currency_code:'USD'});
INSERT INTO definition_products.entity_products (id, current_price) VALUES (15643793,{value:200.00, currency_code:'USD'});
