package com.smartpos.payhero.txb.net;


import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by yy520 on 2018-4-2.
 */

public interface ApiService {

    @POST("index.php?s=/api/default/index")
    Observable<Response<ResponseBody>> login(@Body RequestBody requestBody);


    @POST("index.php?s=/api/default/index")
    Observable<Response<ResponseBody>> post(@Body RequestBody requestBody);

//    @POST("index.php?s=/api/default/index")
//    Observable<HttpEntity<List<PayRecord>>> getRecords(@Body RequestBody requestBody);

    //Callback<HttpEntity<List<PayRecord>>> getRecords(@Body RequestBody requestBody);

}


