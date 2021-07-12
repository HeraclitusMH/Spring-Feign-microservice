package com.microservices.limitsservice.controller;

import com.microservices.limitsservice.bean.Limits;
import com.microservices.limitsservice.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsController {

    @Autowired
    private Configuration conf;

    @GetMapping(path = "/limits")
    public Limits retrieveLimits(){
        return new Limits(conf.getMinimum(),conf.getMaximum());
    }
}
