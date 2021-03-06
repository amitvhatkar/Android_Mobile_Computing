package com.example.swapnil.iamfoodee.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.swapnil.iamfoodee.Model.User;
import com.example.swapnil.iamfoodee.Remote.APIService;
import com.example.swapnil.iamfoodee.Remote.RetrofitClient;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by swapnil on 4/3/18.
 */

public class Common {

    public static User currentUser;

    private static final String BASE_URL="https://fcm.googleapis.com/";

    public static APIService getFCMService()
    {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

    public static final String DELETE ="Delete";
    public static final String USR_KEY ="User";
    public static final String PWD_KEY ="Password";

    public static String convertCodeToStatus(String status) {
        if (status.equals("0")){
            return "Placed";
        }else if(status.equals("1")){
            return "Accepted";
        }else{
            return "Delivered";
        }
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
