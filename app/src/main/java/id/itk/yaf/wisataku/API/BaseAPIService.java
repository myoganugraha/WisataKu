package id.itk.yaf.wisataku.API;



import id.itk.yaf.wisataku.JSONResponse.JSONResponseWisata;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BaseAPIService {

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseBody> loginRequest(@Field("username") String username,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseBody> registerRequest(@Field("name") String name,
                                       @Field("email") String email,
                                       @Field("username") String username,
                                       @Field("password") String password,
                                       @Field("website") String website,
                                       @Field("profile_picture") String profile_picture);

    @FormUrlEncoded
    @POST("tambahWisata.php")
    Call<ResponseBody> tambahWisataRequest(@Field("title") String title,
                                           @Field("description") String descrition,
                                           @Field("category") String category,
                                           @Field("latitude") String latitude,
                                           @Field("longitude") String longitude,
                                           @Field("id_user") String id_user,
                                           @Field("image") String image);

    @GET("listWisataAlam.php")
    Call<JSONResponseWisata> getJSONAlam();

    @GET("listWisataBelanja.php")
    Call<JSONResponseWisata> getJSONBelanja();

    @GET("listWisataKuliner.php")
    Call<JSONResponseWisata> getJSONKuliner();

    @GET("listSemuaWisata.php")
    Call<JSONResponseWisata> getJSONSemuaWisata();
}
