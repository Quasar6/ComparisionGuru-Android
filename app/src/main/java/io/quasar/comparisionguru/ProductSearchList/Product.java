package io.quasar.comparisionguru.ProductSearchList;

/**
 * Created by prashantn.pol on 2017-02-01.
 */

public class Product {
    private String productID;
    private String productDesc;
    private String productPrice;

    public Product(String productID, String productDesc, String productPrice) {
       this.setProductID(productID);
        this.setProductDesc(productDesc);
        this.setProductPrice(productPrice);
     }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

}
