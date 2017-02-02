package io.quasar.comparisionguru.ProductSearchList;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import java.util.ArrayList;

import io.quasar.comparisionguru.R;

public class SearchListActivity extends AppCompatActivity {

    Toolbar toolbar;
    AppBarLayout appBarLayout;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String[] productdesc,productprice;
    ArrayList<Product> products=new ArrayList<Product>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

       //ToolbarSet
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //


        recyclerView=(RecyclerView)findViewById(R.id.rv_searchlist);
        productdesc=getResources().getStringArray(R.array.productdesc);
        productprice=getResources().getStringArray(R.array.price);

        int i=0;
        for(String productdescs :productdesc)
        {
            Product product=new Product("1",productdesc[i],productprice[i]);
            products.add(product);
            i++;
        }

        adapter=new SearchListRecyclerAdapter(products,this);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
}
