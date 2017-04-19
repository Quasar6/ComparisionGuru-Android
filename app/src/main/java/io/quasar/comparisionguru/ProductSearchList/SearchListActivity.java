package io.quasar.comparisionguru.ProductSearchList;

import android.app.ProgressDialog;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.quasar.comparisionguru.Global.GlobalConstants;
import io.quasar.comparisionguru.Model.Product;
import io.quasar.comparisionguru.R;
import io.quasar.comparisionguru.SponsoredListRecyclerAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchListActivity extends AppCompatActivity implements GlobalConstants{

    Toolbar toolbar;
    AppBarLayout appBarLayout;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String[] productdesc,productprice;
    String query;
    ArrayList<Product> arrayList=new ArrayList<Product>();
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    public ProgressDialog mProgressDialog;
    private String TAG = "SearchListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            query = bundle.getString(QUERY);
        }
       //ToolbarSet
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=(RecyclerView)findViewById(R.id.rv_searchlist);
        productdesc=getResources().getStringArray(R.array.productdesc);
        productprice=getResources().getStringArray(R.array.price);
//        adapter=new SearchListRecyclerAdapter(arrayList,this);
        recyclerView.setHasFixedSize(true);


         layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setAdapter(adapter);

       downloadList();
    }

    private void downloadList(){
        showProgressDialog();
        Call<ArrayList<Product>> call = CHEAPEST_PRICE_API.getProductList(query);
        showToast(query);
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                mProgressBar.setVisibility(View.GONE);
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("RETROFIT", "UPDATE DOCTOR RETROFIT FAILURE jObjError.getString(message) >>>>> " + jObjError.getString("message"));
                        showToast(jObjError.getString("message"));
                    } catch (Exception e) {
                        e.printStackTrace();
                        showToast(e.getMessage());
                    }
                    return;
                }
                hideProgressDialog();
                ArrayList<Product> products = response.body();
                Log.d(TAG, "Number pf products >>> " + products.size());
                populateList(products);
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                showToast("Sorry unable to fetch results");
                Log.d(TAG, "ERROR >>> "+call.isExecuted());
                mProgressBar.setVisibility(View.GONE);

            }
        });
    }

    private void populateList(ArrayList<io.quasar.comparisionguru.Model.Product> productArrayList){
        adapter=new SearchListRecyclerAdapter(productArrayList,this);
        recyclerView.setAdapter(adapter);
    }

    private void showToast(String msg){
        hideProgressDialog();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Looking for best prices…");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
