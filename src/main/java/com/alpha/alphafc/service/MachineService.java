package com.alpha.alphafc.service;

/**
 * @author 陈泽荣
 * @date 2021/8/3 11:29
 */
public interface MachineService {

    String LeaveSubmit(Integer current, Integer size, String stockCode, String supplierCode, String source);

    String GetAdoptorder(Integer status, String stockCode, String source);
}
