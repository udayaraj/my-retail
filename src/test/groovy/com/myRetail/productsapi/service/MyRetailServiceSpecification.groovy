package com.myRetail.productsapi.service

import org.slf4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class MyRetailServiceSpecification extends Specification {
    static String responseWithTitle = '''{
        "products": [
          {
            "title": "LG 55"
          }]
    }'''
    public static final String responseNoTitleValue = '''{
        "products": [
          {
            "title": 
          }]
    }'''
    public static final String responseNoTitle = '''{
        "products": [
          {
            "abc": "LG 55"
          }]
    }'''
    public static final String responseNoProduct = '''{
        "products12": 
          {
            "abc": "LG 55"
          
    }'''
    MyRetailService myRetailService
    RestTemplate mockRestTemplate = Mock()
    Logger logMock = Mock()

    void setup() {
        myRetailService = new MyRetailService(restTemplate: mockRestTemplate, log: logMock)
    }

    def 'getProductEntity - validate product response - $scenario'() {

        when:
        def name = myRetailService.getProductName(1234)
        then:

        name == title

        1 * mockRestTemplate.getForEntity(*_) >> respEntity
        if (exceptionPresent)
            1 * logMock.error('Exception occurred while parsing product response', _)


        where:
        scenario                  | title   | respEntity                                                      | exceptionPresent
        'valid title present'     | 'LG 55' | new ResponseEntity<String>(responseWithTitle, HttpStatus.OK)    | false
        'title value not present' | ''      | new ResponseEntity<String>(responseNoTitleValue, HttpStatus.OK) | true
        'title not present'       | ''      | new ResponseEntity<String>(responseNoTitle, HttpStatus.OK)      | false
        'invalid json response'   | ''      | new ResponseEntity<String>(responseNoProduct, HttpStatus.OK)    | true
    }


    def 'getProductEntity - 404 response from external api'() {

        when:
        def name = myRetailService.getProductName(1234)
        then:

        name == ''

        1 * mockRestTemplate.getForEntity(*_) >> new ResponseEntity<String>('', HttpStatus.NOT_FOUND)

    }

    def 'handle HttpClientErrorException'() {

        when:
        myRetailService.getProductName(1234)

        then:
        1 * mockRestTemplate.getForEntity(*_) >> { throw new HttpClientErrorException(HttpStatus.BAD_GATEWAY) }
        1 * logMock.error("Exception occurred while making external get call", '502 BAD_GATEWAY')

    }

    def 'throw if exception is other than HttpClientErrorException'() {

        when:
        myRetailService.getProductName(1234)
        then:
        1 * mockRestTemplate.getForEntity(*_) >> { throw new Exception() }
        thrown(Exception)
    }
}
