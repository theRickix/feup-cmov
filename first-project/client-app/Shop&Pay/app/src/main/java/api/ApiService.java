package api;

import data.Product;
import data.ProductList;
import data.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @GET("products")
    Call<ProductList> getProducts();

    @GET("products/id={id}")
    Call<ProductList> getProductById(@Path("id") int id);

    @GET("products/barcode={barcode}")
    Call<ProductList> getProductByBarcode(@Path("barcode") String barcode);

    @GET("users")
    Call<User> login();

    @POST("users")
    Call<User> register(@Body User user);
}