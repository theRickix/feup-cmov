package api;

import data.ProductList;
import data.User;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("products")
    Call<ProductList> getProducts();
    Call<User> login();
}