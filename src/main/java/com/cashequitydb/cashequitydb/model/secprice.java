package com.cashequitydb.cashequitydb.model;

//deepak
public class secprice {
    String security_price;
    private String hour;
    private String min;
    private String symbol;

    public void setSecurity_price(String security_price) {
        this.security_price = security_price;
    }

    public String getSecurity_price() {
        return security_price;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getHour() {
        return hour;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMin() {
        return min;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
