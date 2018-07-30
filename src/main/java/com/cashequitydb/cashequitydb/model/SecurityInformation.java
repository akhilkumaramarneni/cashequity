package com.cashequitydb.cashequitydb.model;

public class SecurityInformation {

    String company_name,sector,symbol,isin;

//    public String getMarket_lot() {
//        return market_lot;
//    }
//
//    public void setMarket_lot(String market_lot) {
//        this.market_lot = market_lot;
//    }
//
//    public String getPrice_variance_limit() {
//        return price_variance_limit;
//    }
//
//    public void setPrice_variance_limit(String price_variance_limit) {
//        this.price_variance_limit = price_variance_limit;
//    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }
}
