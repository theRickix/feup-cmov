package api;

import java.util.UUID;

import data.ProductList;
import data.PurchaseList;
import data.ResponsePurchase;
import data.User;
import data.UserList;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @GET("products")
    Call<ProductList> getProducts();

    @GET("purchase/id={id}")
    Call<PurchaseList> getPurchases(@Path("id") int id);

    @GET("products/id={id}")
    Call<ProductList> getProductById(@Path("id") int id);

    @GET("products/barcode={barcode}")
    Call<ProductList> getProductByBarcode(@Path("barcode") String barcode);

    @FormUrlEncoded
    @POST("users/login")
    Call<UserList> login(@Field("email") String email, @Field("password") String password);

    @POST("users")
    Call<User> register(@Body User user);

    @PUT("users/update/{id}/")
    Call<User> updateUserPublicKey(@Path("id") int id, @Body User user);

    @FormUrlEncoded
    @POST("purchase")
    Call<ResponsePurchase> addPurchase(@Field("user_id") int user_id);

    @FormUrlEncoded
    @POST("purchase/row")
    Call<ResponseBody> addPurchaseRow(@Field("purchase_id") int purchase_id, @Field("product_id") int product_id);

    @FormUrlEncoded
    @POST("purchase/token")
    Call<ResponseBody> getPurchase(@Field("token")UUID token);

}