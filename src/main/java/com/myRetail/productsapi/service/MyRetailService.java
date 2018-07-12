package com.myRetail.productsapi.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class MyRetailService {
    public static final String URL = "https://redsky.target.com/v2/products/pdp/TCIN/";
    private Logger log = LoggerFactory.getLogger(MyRetailService.class);

    @Autowired
    private RestTemplate restTemplate;

    public String getProductName(int productId) {
        ResponseEntity<String> productResponse = null;
        String productName = "";
        String url = URL + productId;
        try {
            productResponse = restTemplate.getForEntity(url, String.class);
        } catch (HttpClientErrorException exception) {
            log.error("Exception occurred while making external get call", exception.getMessage());
            return productName;
        }
        if (productResponse.getStatusCode().is2xxSuccessful()) {
            productName = getItemTitle(productResponse.getBody());
        }
        return productName;
    }

    private String getItemTitle(String productResponse) {
        JSONParser jsonParser = new JSONParser();
        JSONArray productObject = null;
        JSONObject itemObject;
        try {
            JSONObject dataObject = (JSONObject) jsonParser.parse(productResponse);
            if (null != dataObject) {
                productObject = (JSONArray) dataObject.get("products");
            }
            if (productObject != null && productObject.size() > 0) {
                itemObject = (JSONObject) productObject.get(0);
                Object title = itemObject.get("title");
                return title != null ? title.toString() : "";
            }
        } catch (ParseException exception) {
            log.error("Exception occurred while parsing product response", exception.getMessage());
        }
        return "";
    }

}
