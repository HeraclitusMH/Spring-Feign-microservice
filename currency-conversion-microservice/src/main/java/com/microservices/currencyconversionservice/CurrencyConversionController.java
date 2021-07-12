package com.microservices.currencyconversionservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeProxy proxy;

    @GetMapping(path="/currency-conversion/from/{v1}/to/{v2}/quantity/{qty}")
    public CurrencyConversion calculateCurrencyConversion(
            @PathVariable String v1, @PathVariable String v2, @PathVariable BigDecimal qty
            ){
        HashMap<String,String> uriVariables = new HashMap<>();
        uriVariables.put("from",v1);
        uriVariables.put("to",v2);

        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversion.class, uriVariables);

        CurrencyConversion currencyConversion = responseEntity.getBody();

        return new CurrencyConversion(
                currencyConversion.getId(),v1,v2,
                currencyConversion.getConversionMultiple(),qty,
                qty.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment());
    }

    @GetMapping(path="/currency-conversion-feign/from/{v1}/to/{v2}/quantity/{qty}")
    public CurrencyConversion calculateCurrencyConversionFeign(
            @PathVariable String v1, @PathVariable String v2, @PathVariable BigDecimal qty
    ){
        CurrencyConversion currencyConversion = proxy.exchange(v1,v2);

        return new CurrencyConversion(
                currencyConversion.getId(),v1,v2,
                currencyConversion.getConversionMultiple(),qty,
                qty.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment());
    }
}
