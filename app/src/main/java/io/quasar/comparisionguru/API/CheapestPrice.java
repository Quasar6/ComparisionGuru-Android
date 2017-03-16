package io.quasar.comparisionguru.API;

import java.util.ArrayList;

import io.quasar.comparisionguru.Model.Product;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by yatin on 01/02/17.
 */

public interface CheapestPrice {

    @GET("cheapest/{query}")
    Call<ArrayList<Product>> getProductList(@Path("query") String query);

    @GET("cheapest/{query}")
    Call<String> getProductStringList(@Path("query") String query);

    @GET("products")
    Call<ArrayList<Product>> getFeaturedList();
}
