package com.example.root.iamfoodeeserver.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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
    public static final String DELETE = "Deny";
    public static final String USR_KEY ="User";
    public static final String PWD_KEY ="Password";
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
            return "Accepted";
        else
            return "Delivered";
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

    public static boolean isConnectedToInternet (Context context)
    {
        ConnectivityManager connectivityManager =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager != null)
        {
            NetworkInfo[] info= connectivityManager.getAllNetworkInfo();
            if(info != null)
            {
                for(int i=0;i<info.length;i++)
                {
                    if(info[i].getState() == NetworkInfo.State.CONNECTED)
                        return  true;
                }
            }
        }

        return false;
    }

}
