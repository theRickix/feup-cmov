package api;

import data.ProductList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("products")
    Call<ProductList> getProducts();
}