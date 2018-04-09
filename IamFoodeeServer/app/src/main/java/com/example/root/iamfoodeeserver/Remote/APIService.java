package com.example.root.iamfoodeeserver.Remote;

import com.example.root.iamfoodeeserver.Model.MyResponse;
import com.example.root.iamfoodeeserver.Model.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by swapnil on 6/4/18.
 */


public interface APIService {

    @Headers(

            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAIsos31o:APA91bFuqRB3JOUUgsHJQ8wDZB1mgroEhgS8DJsXJZDenY_YvedLiS37kMC4y80WcyyyB4N4fG5XK8BNY7lN-A75QJyrIAiLpEFx6MHvuK1b3MWo_pMzn0rmycHN3UnUqsgpLYxDD8eb"

            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);

}

