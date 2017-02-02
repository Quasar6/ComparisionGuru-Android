package io.quasar.comparisionguru.Model;

/**
 * Created by yatin on 01/02/17.
 */

public class Product {
    private String name;
    private String salePrice;

    public Product(String name, String salePrice) {
        this.name = name;
        this.salePrice = salePrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }
}
