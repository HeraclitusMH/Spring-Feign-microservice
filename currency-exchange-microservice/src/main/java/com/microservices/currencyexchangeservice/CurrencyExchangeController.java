package com.microservices.currencyexchangeservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

    @Autowired
    private Environment environment;

    @Autowired
    private CurrencyExchangeRepository repository;



    @GetMapping(path = "/currency-exchange/from/{v1}/to/{v2}")
    public CurrencyExchange exchange(@PathVariable String v1,@PathVariable String v2){
        CurrencyExchange currencyExchange = repository.findByFromAndTo(v1,v2);
        if(currencyExchange == null){
            throw new RuntimeException("unable to find data: from -> " + v1 + " to -> " + v2);
        }
        String port = environment.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);
        return currencyExchange;
    }
}
