package com.alpha.alphafc.controller;

import com.alpha.alphafc.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 陈泽荣
 * @date 2021/6/9 18:16
 */


@RestController
public class MachineController {

    @Autowired
    private MachineService machineService;

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public String machineQuery(@RequestParam(required = false) Integer current,
                             @RequestParam(required = false) Integer size,
                             @RequestParam(required = false) String stockCode,
                             @RequestParam String supplierCode,
                             @RequestParam String s) {
        String source = s+"/machineStock/queryStockDetailData";
        return machineService.LeaveSubmit(current,
                size,
                stockCode,
                supplierCode,
                source
                );
    }

    @RequestMapping(value = "/modify", method = RequestMethod.GET)
    public String machineModify(@RequestParam Integer status,@RequestParam String stockCode,@RequestParam String s) {
        String source = s+"/machineStock/reimburseStockDetailByCode";
        return machineService.GetAdoptorder(status,
                stockCode,
                source
        );
    }
}
