package com.example.root.iamfoodeeserver.Model;

import java.util.List;

/**
 * Created by swapnil on 30/3/18.
 */

public class Request {
    private String phone;
    private String total;
    private String name;
    private String status;
    private List<Order> foods;


    public Request() {
    }

    public Request(String phone, String total, String name, List<Order> foods) {
        this.phone = phone;
        this.total = total;
        this.name = name;
        this.foods = foods;
        this.status = "0"; //Default 0, 0:placed, 1:shipping, 2:Shipped
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }
}
