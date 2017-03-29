package io.quasar.comparisionguru.Model;

import com.google.gson.Gson;

/**
 * Created by yatin on 29/03/17.
 */

public class User {
    private String name;
    private String email;
    private String userImageURL;
    private String logInAccount;

    public User(String name, String email, String userImageURL, String logInAccount) {
        this.name = name;
        this.email = email;
        this.userImageURL = userImageURL;
        this.logInAccount = logInAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserImageURL() {
        return userImageURL;
    }

    public void setUserImageURL(String userImageURL) {
        this.userImageURL = userImageURL;
    }

    public String getLogInAccount() {
        return logInAccount;
    }

    public void setLogInAccount(String logInAccount) {
        this.logInAccount = logInAccount;
    }

    public String toJson(User user){
        return new Gson().toJson(user);
    }

    public User toObject(String user){
        return new Gson().fromJson(user, User.class);

    }
}
