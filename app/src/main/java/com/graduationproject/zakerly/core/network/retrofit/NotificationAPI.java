package com.graduationproject.zakerly.core.network.retrofit;

import com.graduationproject.zakerly.core.models.PushNotification;
import io.reactivex.rxjava3.core.Single;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import static com.graduationproject.zakerly.core.constants.SiteURLs.*;


public interface NotificationAPI {


    @Headers({"Authorization:key=" + FCM_SERVER_KEY, "Content-Type:" + CONTENT_TYPE})
    @POST("fcm/send")
    Single<Response<ResponseBody>> postNotification(@Body PushNotification notification);
}
