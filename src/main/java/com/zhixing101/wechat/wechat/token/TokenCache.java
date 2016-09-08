package com.zhixing101.wechat.wechat.token;

public class TokenCache {

    private TokenCache() {
        System.out.println("TokenCache construct");
    }

    private String access_token;

    private String jsapi_ticket;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
        System.out.println("access_token is set as : " + this.access_token);
    }

    public String getJsapi_ticket() {
        return jsapi_ticket;
    }

    public void setJsapi_ticket(String jsapi_ticket) {
        this.jsapi_ticket = jsapi_ticket;
        System.out.println("jsapi_ticket is set as : " + this.jsapi_ticket);
    }

}
