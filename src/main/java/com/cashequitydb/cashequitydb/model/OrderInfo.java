package com.cashequitydb.cashequitydb.model;

public class OrderInfo {
    String isin,direction,company_name,client_code;
    int quantity,order_id;
    double limit_price;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getClient_code() {
        return client_code;

    }

    public void setClient_code(String client_code) {
        this.client_code = client_code;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getLimit_price() {
        return limit_price;
    }

    public void setLimit_price(double limit_price) {
        this.limit_price = limit_price;
    }
}
