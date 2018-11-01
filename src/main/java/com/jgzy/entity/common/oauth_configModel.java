package com.jgzy.entity.common;

import org.springframework.beans.factory.annotation.Value;

public class oauth_configModel {
    public String app_id;
    public String app_key;
    public String return_uri;

    public String getApp_id() {
        return app_id;
    }
    @Value("${weixin.app_id}")
    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getApp_key() {
        return app_key;
    }
    @Value("${weixin.app_key}")
    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getReturn_uri() {
        return return_uri;
    }
    @Value("${weixin.redirect_uri}")
    public void setReturn_uri(String return_uri) {
        this.return_uri = return_uri;
    }
}
