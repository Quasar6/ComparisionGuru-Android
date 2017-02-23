package io.quasar.comparisionguru.Global;

import io.quasar.comparisionguru.API.CheapestPrice;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yatin on 01/02/17.
 */

public interface GlobalConstants {

    String BASE_URL = "http://cguru-quasar6.rhcloud.com/";

    String QUERY = "query";

    Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    CheapestPrice CHEAPEST_PRICE_API = RETROFIT.create(CheapestPrice.class);

    int REQ_CODE_SPEECH_INPUT = 100;
}
