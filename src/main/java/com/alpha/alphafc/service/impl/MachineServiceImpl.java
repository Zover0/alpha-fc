package com.alpha.alphafc.service.impl;

import cn.hutool.json.JSONObject;

import com.alpha.alphafc.config.HttpClient;
import com.alpha.alphafc.config.MachineAccess;
import com.alpha.alphafc.model.MachineStock;
import com.alpha.alphafc.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 陈泽荣
 * @date 2021/8/3 11:38
 */
@Service
public class MachineServiceImpl implements MachineService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    MachineAccess machineAccess = new MachineAccess();
    String authorization = machineAccess.GetToken();

    @Override
    public String GetAdoptorder(Integer status, String stockCode, String source){
        Map<String, String> map = new HashMap<>();
        String result = "";
        try {
            //供应商编码
            map.put("stockCode", stockCode);
            //状态
            map.put("status", String.valueOf(status));
            //请求地址
            String url = source;
            //提交方式https
            HttpClient httpClient = new HttpClient(url);
            httpClient.setHttps(true);
            //提交参数
            httpClient.setParameter(map);
            httpClient.addHeader("authorization",authorization);
            //执行请求
            httpClient.post();
            if(httpClient.getStatusCode() == 200) {
                //获取返回数据
                JSONObject json = new JSONObject(httpClient.getContent());
                result = json.getStr("code");
                System.out.println(result);
            }else {
                result = httpClient.getContent();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public String LeaveSubmit(Integer current, Integer size, String stockCode, String supplierCode, String source){

        Map<String, String> map = new HashMap<>();
        String result ="";
        try {
            //页数
            map.put("current", String.valueOf(current));
            //条数
            map.put("size", String.valueOf(size));
            //单号
            map.put("stockCode", stockCode);
            //供应商
            map.put("supplierCode", supplierCode);
            //请求地址
            String url = source;
            //提交方式https
            HttpClient httpClient = new HttpClient(url);
            httpClient.setHttps(true);


            //提交参数
            httpClient.setParameter(map);
            httpClient.addHeader("authorization",authorization);
            //执行请求
            httpClient.post();

            if(httpClient.getStatusCode() == 200) {
                //获取返回数据
                JSONObject json = new JSONObject(httpClient.getContent());
                result = json.getStr("message");
                System.out.println(result);
                if (result.equals("success")){
                    String[] records = json.getStr("object").replace("[","")
                            .replace("]",",")
                            .replace("},","}&")
                            .split("&");
                    int totalCount = 0;
                    for (int l = 0; l < records.length; l++) {
                        //往数据库插入数据
                        insertStock(records[l]);
                        totalCount++;
                    }
                    System.out.println("处理数量：" + totalCount);
                }
            }else {
                result = httpClient.getContent();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private int insertStock(String record) {
        String sql = "{call cux_machinestock_pkg" +
                ".insert_machinestock(:EXP_WAREHOUSE_NUMBER,:FIXED_ASSET_CODE,:MACHINE_NAME,:MACHINE_CODE,:MACHINE_FUNCTION,:PRIMARY_CATEGORY,:MINOR_CATEGORY,:MACHINE_AMOUNT,:SPECIFICATION,:UNIT,:INCLUDING_TAX_PRICE,:TAX_RATE,:EXCLUDING_TAX_PRICE,:AMOUNT,:VENDOR_CODE,:RESPONSIBILITY_CENTER_CODE,:ERPTYPE_ID,:EXP_WAREHOUSE_LINE_ID,:ASSETTYPECODE)}";
        JSONObject data = new JSONObject(record);
        //赋值
        MachineStock machineStock = new MachineStock();
        machineStock.setEXP_WAREHOUSE_NUMBER(data.getStr("stockCode"));
        machineStock.setFIXED_ASSET_CODE(data.getStr("assetCode"));
        machineStock.setMACHINE_NAME(data.getStr("machineName"));
        machineStock.setMACHINE_CODE(data.getStr("machineCode"));
        machineStock.setMACHINE_FUNCTION(data.getStr("functionName"));
        machineStock.setPRIMARY_CATEGORY(data.getStr("largeTypeName"));
        machineStock.setMINOR_CATEGORY(data.getStr("smallTypeName"));
        machineStock.setMACHINE_AMOUNT(data.getStr("machineP"));
        machineStock.setSPECIFICATION(data.getStr("specification"));
        machineStock.setUNIT(data.getStr("unit"));
        machineStock.setINCLUDING_TAX_PRICE(data.getInt("taxPrice"));
        machineStock.setTAX_RATE(data.getInt("taxRate"));
        machineStock.setEXCLUDING_TAX_PRICE(data.getInt("untaxPrice"));
        machineStock.setAMOUNT(data.getInt("quantity"));
        machineStock.setVENDOR_CODE(data.getStr("supplierCode"));
        machineStock.setRESPONSIBILITY_CENTER_CODE(data.getStr("costCenterCode"));
        machineStock.setERPTYPE_ID(data.getStr("erpTypeId"));
        machineStock.setEXP_WAREHOUSE_LINE_ID(data.getStr("rowId"));
        machineStock.setASSETTYPECODE(data.getStr("assetTypeCode"));

        NamedParameterJdbcTemplate npjt = new NamedParameterJdbcTemplate(this.jdbcTemplate.getDataSource());
        return npjt.update(sql, new BeanPropertySqlParameterSource(machineStock));
    }
}
