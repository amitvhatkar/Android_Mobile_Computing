package com.example.root.iamfoodeeserver.Common;

import com.example.root.iamfoodeeserver.Model.User;

/**
 * Created by root on 22/3/18.
 */

public class Common {

    public  static User currrentUser;
    public static final String UPDATE = "Update";
    public static final String DELETE = "Delete";
    public static final int PICK_IMAGE_REQUEST = 71;

    public static  String convertCodeToStatus(String code)
    {
        if(code.equals("0"))
            return "Placed";
        else  if(code.equals("1"))
            return "On my way";
        else
            return "Shipped";
    }
}
