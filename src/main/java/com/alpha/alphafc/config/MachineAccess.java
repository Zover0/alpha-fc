package com.alpha.alphafc.config;

import cn.hutool.json.JSONObject;

/**
 * @author 陈泽荣
 * @date 2021/8/4 18:08
 */
public class MachineAccess {
    public String GetToken(){

        String result = "";
        try {
            //请求地址
            String url = "https://themebiz-api-uat.gdalpha.com/auth/oauth/token";
            //提交方式https
            HttpClient httpClient = new HttpClient(url);
            httpClient.setHttps(true);
            //提交参数
            httpClient.addParameter("grant_type","client_credentials");
            httpClient.addHeader("authorization","Basic YWRtaW5PQTpwSFlBSThGcQ==");
            //执行请求
            httpClient.get();

            //获取返回数据
            JSONObject json = new JSONObject(httpClient.getContent());
            result = json.getStr("access_token");
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
