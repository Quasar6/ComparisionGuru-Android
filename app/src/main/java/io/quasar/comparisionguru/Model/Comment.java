package io.quasar.comparisionguru.Model;

import java.io.Serializable;

/**
 * Created by yatin on 28/03/17.
 */

public class Comment implements Serializable {
    private String productId;
    private String comment;
    private int rating;
    private String userName;
    private String dateTime;
    private String userImage;

    public Comment(String productId, String comment, int rating, String userName, String dateTime, String userImage) {
        this.productId = productId;
        this.comment = comment;
        this.rating = rating;
        this.userName = userName;
        this.dateTime = dateTime;
        this.userImage = userImage;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}
