package io.quasar.comparisionguru.ProductDetails;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TableLayout;

import io.quasar.comparisionguru.R;

public class ProductDetails extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        viewPager=(ViewPager)findViewById(R.id.detailsViewpager);
        tabLayout=(TabLayout) findViewById(R.id.detailstablayout);



    }
}
