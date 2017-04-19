package io.quasar.comparisionguru.ProductDetails;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.quasar.comparisionguru.Global.GlobalConstants;
import io.quasar.comparisionguru.Model.Product;
import io.quasar.comparisionguru.R;
import io.quasar.comparisionguru.SponsoredListRecyclerAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PriceFragment extends Fragment implements GlobalConstants{

    @BindView(R.id.productbigimage)
    ImageView mProductBigImage;

    private String TAG = "PriceFragment";
    ArrayList<Product> arrayList = new ArrayList<Product>();

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String[] productdesc, productprice;
    TextView txtdesc;
    TextView txtprice;


    public PriceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Intent intent=getActivity().getIntent();
        Product product=(Product)intent.getSerializableExtra("Product");

        View view = inflater.inflate(R.layout.fragment_price, container, false);
        ButterKnife.bind(this, view);
        // Inflate the layout for this fragment
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_otherlist);
        productdesc = getResources().getStringArray(R.array.productdesc);
        productprice = getResources().getStringArray(R.array.price);

        int i = 0;
        for (String pname : productdesc) {
            Product dataprovider = new Product(pname, productprice[i]);
            arrayList.add(dataprovider);
            i++;
        }

//        adapter = new ProductRecyclerAdapter(arrayList, getActivity());
//        recyclerView.setHasFixedSize(true);
//        LinearLayoutManager layoutManager
//                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);

        txtdesc=(TextView)view.findViewById(R.id.productdesc);
        txtprice=(TextView)view.findViewById(R.id.bestpricetext);

        txtprice.setText(product.getPrice());
        txtdesc.setText(product.getName());

        setImage(product);
        callFeaturedAPI();
        return view;
    }

    private void callFeaturedAPI() {
        Call<ArrayList<Product>> call = CHEAPEST_PRICE_API.getFeaturedList();
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

                ArrayList<Product> products = response.body();
                populateList(products);
                Log.d(TAG, "Number pf products >>> " + products.size());
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                showToast("Sorry unable to fetch results");
            }
        });
    }

    private void populateList(ArrayList<io.quasar.comparisionguru.Model.Product> productArrayList){

        adapter = new SponsoredListRecyclerAdapter(productArrayList, getActivity());
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void setImage(Product product){
        Glide.with(getActivity()).load(product.getImageURL()).error(Drawable.createFromPath("@drawable/default_image.jpeg")).into(mProductBigImage);
    }

    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

}
