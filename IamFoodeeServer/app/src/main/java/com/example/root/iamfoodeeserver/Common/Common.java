package com.example.root.iamfoodeeserver.Common;

import com.example.root.iamfoodeeserver.Model.Request;
import com.example.root.iamfoodeeserver.Model.User;
import com.example.root.iamfoodeeserver.Remote.APIService;
import com.example.root.iamfoodeeserver.Remote.FCMRetrofitClient;
import com.example.root.iamfoodeeserver.Remote.RetrofitClient;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by root on 22/3/18.
 */

public class Common {

    public  static User currentUser;
    public  static Request currentRequest;

    public static final String UPDATE = "Update";
    public static final String DELETE = "Delete";
    public static final int PICK_IMAGE_REQUEST = 71;

    private static final String baseUrl="https://maps.googleapis.com";

    private static final String fcmUrl="https://fcm.googleapis.com/";

    public static APIService getFCMClient()
    {
        return FCMRetrofitClient.getClient(fcmUrl).create(APIService.class);
    }

    public static  String convertCodeToStatus(String code)
    {
        if(code.equals("0"))
            return "Placed";
        else  if(code.equals("1"))
            return "On my way";
        else
            return "Shipped";
    }

    public static String getDate(long time)
    {
        Calendar calendar=Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        StringBuilder date=new StringBuilder(
                android.text.format.DateFormat.format("dd-MM-yyyy  HH:mm"
                ,calendar).toString());
        return  date.toString();

    }

}
