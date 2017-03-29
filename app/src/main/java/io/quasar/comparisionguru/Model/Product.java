package io.quasar.comparisionguru.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yatin on 01/02/17.
 */

public class Product implements Serializable{
    private String id;
    private String name;
    private String price;
    private String salePrice;
    private String imageURL;
    private String url;
    private String currency;
    private String store;
    private String category;
    private ArrayList<Trend> trend;
    private ArrayList<Comment> reviews;

    public Product(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public Product(String id, String name, String price, String salePrice, String imageURL, String url, String currency, String store, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.salePrice = salePrice;
        this.imageURL = imageURL;
        this.url = url;
        this.currency = currency;
        this.store = store;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<Trend> getTrends() {
        return trend;
    }

    public void setTrends(ArrayList<Trend> trends) {
        this.trend = trends;
    }

    public ArrayList<Comment> getComments() {
        return reviews;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.reviews = comments;
    }
}
