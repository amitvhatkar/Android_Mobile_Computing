package com.example.root.iamfoodeeserver.Model;

/**
 * Created by swapnil on 6/4/18.
 */

public class Token {
    public String token;
    public boolean isServerToken;

    public Token()
    {

    }

    public Token(String token, boolean isServerToken) {
        this.token = token;
        this.isServerToken = isServerToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isServerToken() {
        return isServerToken;
    }

    public void setServerToken(boolean serverToken) {
        isServerToken = serverToken;
    }
}
