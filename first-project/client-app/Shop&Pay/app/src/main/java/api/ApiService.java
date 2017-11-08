package api;

import data.Product;
import data.ProductList;
import data.User;
import data.UserList;
import retrofit2.Call;
import retrofit2.Callback;
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
}