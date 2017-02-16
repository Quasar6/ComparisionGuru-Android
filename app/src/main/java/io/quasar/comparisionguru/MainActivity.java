package io.quasar.comparisionguru;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
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
import butterknife.OnClick;
import io.quasar.comparisionguru.Global.GlobalConstants;
import io.quasar.comparisionguru.Model.Product;
import io.quasar.comparisionguru.ProductSearchList.SearchListActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements GlobalConstants {

    public ProgressDialog mProgressDialog;
    @BindView(R.id.query_text)
    EditText mQueryView;
    AppBarLayout appBarLayout;
    Toolbar toolbar;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String[] productdesc, productprice;
    ArrayList<Product> arrayList = new ArrayList<Product>();
    private String TAG = "MAINACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        recyclerView = (RecyclerView) findViewById(R.id.rv_sponsoredlist);
        productdesc = getResources().getStringArray(R.array.productdesc);
        productprice = getResources().getStringArray(R.array.price);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //   setSupportActionBar(toolbar);

        int i = 0;
        for (String pname : productdesc) {
            Product dataprovider = new Product(pname, productprice[i]);
            arrayList.add(dataprovider);
            i++;
        }

        adapter = new SponsoredListRecyclerAdapter(arrayList, this);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @OnClick(R.id.search)
    public void searchAction() {
        String txt = mQueryView.getText().toString();
        if (txt.isEmpty()) {
            mQueryView.setError("Please type in Someting");
            //mQueryView.requestFocus();
            return;
        }
        Intent intent = new Intent(this, SearchListActivity.class);
        intent.putExtra(QUERY, txt);
        startActivity(intent);
//        callAPI(txt);
//        callStringAPI(txt);
    }

    private void callAPI(String query) {
        Call<ArrayList<Product>> call = CHEAPEST_PRICE_API.getProductList(query);
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                Log.d("RETROFIT", "response.isSuccessful() >>>>> " + response.isSuccessful());
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
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                showToast("Soory unable to fetch results");
            }
        });
    }

    private void callStringAPI(String query) {
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
                Type listType = new TypeToken<List<Product>>() {
                }.getType();
                List<Product> myModelList = new Gson().fromJson(jsonObject.getAsJsonArray("products"), listType);
                Log.d(TAG, "Number pf products >>> " + myModelList.size());
                Log.d(TAG, "Number pf products string>>> " + s);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                showToast("Soory unable to fetch results");
            }
        });
    }

    ///Extra Method//


    private void showToast(String msg) {
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
