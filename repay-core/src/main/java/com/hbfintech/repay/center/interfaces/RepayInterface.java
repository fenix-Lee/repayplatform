package com.hbfintech.repay.center.interfaces;

import com.hbfintech.repay.center.domain.repay.service.FintechDomainService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class RepayInterface {

    @Resource
    private FintechDomainService service;

    @GetMapping("/test")
    public void getEntity() {

        service.repay();
    }
}
