package io.quasar.comparisionguru.ProductSearchList;

import android.app.ProgressDialog;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.quasar.comparisionguru.Global.GlobalConstants;
import io.quasar.comparisionguru.R;
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

    public ProgressDialog mProgressDialog;
    private String TAG = "SearchListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            query = bundle.getString(QUERY);
        }
       //ToolbarSet
        toolbar=(Toolbar)findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        recyclerView=(RecyclerView)findViewById(R.id.rv_searchlist);
        productdesc=getResources().getStringArray(R.array.productdesc);
        productprice=getResources().getStringArray(R.array.price);

        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

       downloadList();
    }

    private void downloadList(){
        showProgressDialog();
        Call<String> call = CHEAPEST_PRICE_API.getProductStringList(query);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
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
                String s = response.body();
                JsonObject jsonObject = new JsonParser().parse(s).getAsJsonObject();
                Type listType = new TypeToken<List<io.quasar.comparisionguru.Model.Product>>(){}.getType();
                ArrayList<io.quasar.comparisionguru.Model.Product> myModelList = new Gson().fromJson(jsonObject.getAsJsonArray("products"), listType);
                populateList(myModelList);
                Log.d(TAG,"Number pf products >>> "+myModelList.size());
                Log.d(TAG,"Number pf products string>>> "+s);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                showToast("Soory unable to fetch results");
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
