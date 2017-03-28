package io.quasar.comparisionguru.Model;

import java.io.Serializable;

/**
 * Created by yatin on 28/03/17.
 */

public class Trend implements Serializable{
    private double price;
    private String date;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
