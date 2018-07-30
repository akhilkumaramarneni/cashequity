package com.cashequitydb.cashequitydb.model;


//deepak
public class UnExecOrder {
    private double price;
    private int quantity;
    private String client_order;
    private String isin;
    private String direction;

    public String getDirection() {
        return direction;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getClient_order() {
        return client_order;
    }

    public String getIsin() {
        return isin;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setClient_order(String client_order) {
        this.client_order = client_order;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
