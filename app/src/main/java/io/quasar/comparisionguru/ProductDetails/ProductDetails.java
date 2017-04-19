package io.quasar.comparisionguru.ProductDetails;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

import io.quasar.comparisionguru.Model.Product;
import io.quasar.comparisionguru.R;
import io.quasar.comparisionguru.SponsoredListRecyclerAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static io.quasar.comparisionguru.Global.GlobalConstants.CHEAPEST_PRICE_API;

public class ProductDetails extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    ViewPagerAdapter viewPagerAdapter;
    boolean isFeatured;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            isFeatured = bundle.getBoolean("isFeatured", false);
            product= (Product) bundle.getSerializable("Product");
        }

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager=(ViewPager)findViewById(R.id.detailsViewpager);
        tabLayout=(TabLayout) findViewById(R.id.detailstablayout);
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new PriceFragment(),"Price");
        if(false) {
            viewPagerAdapter.addFragments(new SpecsFragment(), "Specs");
        }
        if(isFeatured){
            viewPagerAdapter.addFragments(new TrendFragment(),"Trends");
        }else{
            incrementCounter();
        }
        viewPagerAdapter.addFragments(new ReviewsFragment(),"Reviews");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void incrementCounter(){
        if(product==null){
            return;
        }
        Call<String> call = CHEAPEST_PRICE_API.updateFeaturedList(product);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("RETROFIT", "UPDATE DOCTOR RETROFIT FAILURE jObjError.getString(message) >>>>> " + jObjError.getString("message"));
                    } catch (Exception e) {
                    }
                    return;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }
}
