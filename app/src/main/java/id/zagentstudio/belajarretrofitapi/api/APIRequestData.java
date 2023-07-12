package id.zagentstudio.belajarretrofitapi.api;

import id.zagentstudio.belajarretrofitapi.model.ModelRespon;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Sabiqul on 11/07/2023.
 * BeeMedia
 * sabiqul.ulum@gmail.com
 */
public interface APIRequestData {
    @GET("api_datawisata.php")
    Call<ModelRespon> ardRetrive();

    @FormUrlEncoded
    @POST("api_createwisata.php")
    Call<ModelRespon> ardCreate(
      @Field("nama") String nama,
      @Field("alamat") String alamat,
      @Field("urlmap") String urlmap
    );

    @FormUrlEncoded
    @POST("api_updatewisata.php")
    Call<ModelRespon> ardUpdate(
            @Field("id") String id,
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("urlmap") String urlmap
    );

    @FormUrlEncoded
    @POST("api_deletewisata.php")
    Call<ModelRespon> ardDelete(
            @Field("id") String id
    );
}