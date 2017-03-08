package io.quasar.comparisionguru.ProductDetails;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.quasar.comparisionguru.Model.Product;
import io.quasar.comparisionguru.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PriceFragment extends Fragment {

    ArrayList<Product> arrayList = new ArrayList<Product>();

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String[] productdesc, productprice;


    public PriceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_price, container, false);
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

        adapter = new ProductRecyclerAdapter(arrayList, getActivity());
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

}