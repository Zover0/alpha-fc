package com.alpha.alphafc.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 陈泽荣
 * @date 2021/3/1 11:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MachineStock {
    private String EXP_WAREHOUSE_NUMBER;
    private String FIXED_ASSET_CODE;
    private String MACHINE_NAME;
    private String MACHINE_CODE;
    private String MACHINE_FUNCTION;
    private String PRIMARY_CATEGORY;
    private String MINOR_CATEGORY;
    private String MACHINE_AMOUNT;
    private String SPECIFICATION;
    private String UNIT;
    private Integer INCLUDING_TAX_PRICE;
    private Integer TAX_RATE;
    private Integer EXCLUDING_TAX_PRICE;
    private Integer AMOUNT;
    private String VENDOR_CODE;
    private String RESPONSIBILITY_CENTER_CODE;
    private String ERPTYPE_ID;
    private String EXP_WAREHOUSE_LINE_ID;
    private String ASSETTYPECODE;
}
