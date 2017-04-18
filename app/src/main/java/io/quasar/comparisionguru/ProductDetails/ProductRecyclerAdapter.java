package io.quasar.comparisionguru.ProductDetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.quasar.comparisionguru.Model.Product;
import io.quasar.comparisionguru.ProductSearchList.SearchListRecyclerAdapter;
import io.quasar.comparisionguru.R;
import io.quasar.comparisionguru.SponsoredListRecyclerAdapter;

/**
 * Created by prashantn.pol on 2017-02-22.
 */

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ProductViewHolder> {

    ArrayList<Product> products =new ArrayList<Product>();
    Context ctx;

    public ProductRecyclerAdapter(ArrayList<Product> products, Context ctx) {
        this.products = products;
        this.ctx = ctx;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_card,parent,false);
        ProductRecyclerAdapter.ProductViewHolder viewHolder=new ProductRecyclerAdapter.ProductViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product=products.get(position);
        holder.txt_pname.setText(product.getName());
        holder.txt_pprice.setText(product.getPrice());
    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.pname)
        TextView txt_pname;
        @BindView(R.id.pprice)
        TextView txt_pprice;


        public ProductViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
