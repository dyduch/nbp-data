package com.company;

public class Rate implements Comparable{
    private String currency = null;
    private String code = null;
    private String no = null;
    private String effectiveDate = null;
    private double mid = 0.0;
    private double bid = 0.0;
    private double ask = 0.0;

    public Rate() {
    }

    public Rate(String currency, String code, double mid) {
        this.currency = currency;
        this.code = code;
        this.mid = mid;
    }


    public Rate(String currency, String code, double bid, double ask) {
        this.currency = currency;
        this.code = code;
        this.bid = bid;
        this.ask = ask;
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

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    double getMid() {
        return mid;
    }

    void setMid(double mid) {
        this.mid = mid;
    }

    double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    double getAsk() {
        return ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }




    @Override
    public String toString() {
        String fullRate =  "Rate{ ";
        if(currency != null) fullRate += ("currency='" + currency + '\'');
        if(code != null) fullRate += (", code='" + code + '\'');
        if(no != null) fullRate += (", no='" + no + '\'');
        if(effectiveDate != null) fullRate += (", effectiveDate='" + effectiveDate + '\'');
        if(mid != 0) fullRate += (", mid='" + mid + '\'');
        if(bid != 0) fullRate += (", bid='" + bid + '\'');
        if(ask != 0) fullRate += (", ask='" + ask + '\'');
        fullRate += "}";
        return fullRate;
    }


    @Override
    public int compareTo(Object o) {
        Rate rate = (Rate) o;
        if(this.getMid()<rate.getMid()) return -1;
        else if(this.getMid() > rate.getMid()) return 1;
        return 0;
    }
}
