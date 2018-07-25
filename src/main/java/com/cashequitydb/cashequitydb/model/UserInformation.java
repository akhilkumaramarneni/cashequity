package com.cashequitydb.cashequitydb.model;

public class UserInformation {

    String client_name, client_code, country,password;
    int trading_limit_us,trading_limit_rs;


    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getClient_code() {
        return client_code;
    }

    public void setClient_code(String client_code) {
        this.client_code = client_code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getTrading_limit_US() {
        return trading_limit_us;
    }

    public void setTrading_limit_US(int trading_limit_US) {
        this.trading_limit_us = trading_limit_US;
    }

    public int getTrading_limit_RS() {
        return trading_limit_rs;
    }

    public void setTrading_limit_RS(int trading_limit_RS) {
        this.trading_limit_rs = trading_limit_RS;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
