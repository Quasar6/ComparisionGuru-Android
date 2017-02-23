package io.quasar.comparisionguru.ProductSearchList;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.quasar.comparisionguru.Model.Product;
import io.quasar.comparisionguru.ProductDetails.ProductDetails;
import io.quasar.comparisionguru.R;

/**
 * Created by prashantn.pol on 2017-02-01.
 */

public class SearchListRecyclerAdapter extends RecyclerView.Adapter<SearchListRecyclerAdapter.SearchListViewHolder> {


    ArrayList<Product> products = new ArrayList<Product>();
    Context ctx;

    public SearchListRecyclerAdapter(ArrayList<Product> products, Context ctx) {
        this.products = products;
        this.ctx = ctx;
    }


    @Override
    public SearchListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        SearchListViewHolder viewHolder = new SearchListViewHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =new Intent(ctx, ProductDetails.class);
                ctx.startActivity(intent);

             //   Toast.makeText(ctx,"test",Toast.LENGTH_LONG).show();
            }
        });
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(SearchListViewHolder holder, int position) {
        Product product = products.get(position);
        holder.txt_productdesc.setText(product.getName());
        holder.txt_productprice.setText(product.getPrice());
        holder.txt_currency.setText(product.getCurrency());
        Glide.with(ctx).load(product.getImageURL()).error(Drawable.createFromPath("@drawable/default_image.jpeg")).into(holder.mProductImage);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class SearchListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.productimage)
        ImageView mProductImage;
        @BindView(R.id.txtproductdesc)
        TextView txt_productdesc;
        @BindView(R.id.txtprice)
        TextView txt_productprice;
        @BindView(R.id.txtdollar)
        TextView txt_currency;

        public SearchListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
