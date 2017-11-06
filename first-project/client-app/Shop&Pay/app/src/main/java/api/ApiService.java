package api;

import data.ProductList;
import data.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("products")
    Call<ProductList> getProducts();

    @GET("users")
    Call<User> login();

    @POST("users")
    Call<User> register(@Body User user);
}