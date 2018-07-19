package com.company;

public class Gold {

    private String date = null;
    private double price = 0;

    public Gold(){}

    Gold(String date, double price){
        this.date = date;
        this.price = price;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Gold{" +
                "date='" + date + '\'' +
                ", price=" + price +
                '}';
    }
}
