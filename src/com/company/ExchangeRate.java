package com.company;

import java.util.ArrayList;

public class ExchangeRate {
    private char table;
    private String currency;
    private String code;
    private String effectiveDate;
    private String tradingDate;
    private String no;
    private ArrayList<Rate> rates;

    public ExchangeRate(){}


    public char getTable() {
        return table;
    }

    public void setTable(char table) {
        this.table = table;
    }

    String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getTradingDate() {
        return tradingDate;
    }

    public void setTradingDate(String tradingDate) {
        this.tradingDate = tradingDate;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    ArrayList<Rate> getRates() {
        return rates;
    }

    void setRates(ArrayList<Rate> rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {

        String fullExRate =  "ExchangeRate{ ";
        fullExRate += "table= " + table;
        if(currency != null) fullExRate += (", currency='" + currency + '\'');
        if(code != null) fullExRate += (", code='" + code + '\'');
        if(no != null) fullExRate += (", no='" + no + '\'');
        if(effectiveDate != null) fullExRate += (", effectiveDate='" + effectiveDate + '\'');
        if(tradingDate != null) fullExRate += (", tradingDate='" + tradingDate + '\'');
        fullExRate += ", rates=" + rates;
        return fullExRate;

    }
}
