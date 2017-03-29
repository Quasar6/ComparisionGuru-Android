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
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(SearchListViewHolder holder, int position) {
        final Product product = products.get(position);
        holder.txt_productdesc.setText(product.getName());
        if(product.getSalePrice() != null){
            holder.txt_productprice.setText(product.getSalePrice());
        }else {
            holder.txt_productprice.setText(product.getPrice());
        }
        holder.txt_currency.setText(product.getCurrency());

        String storename=product.getStore().toString();
        if(storename.equals("bestbuy"))
        {
            //Toast.makeText(ctx,storename,Toast.LENGTH_LONG).show();

            holder.storelogo.setImageResource(R.drawable.bestbuy);
        }
        else if(storename.equals("ebay"))
        {
            holder.storelogo.setImageResource(R.drawable.ebaylogo);

        }
        else if(storename.equals("walmart"))
        {
            holder.storelogo.setImageResource(R.drawable.walmart);

        }


        Glide.with(ctx).load(product.getImageURL()).error(Drawable.createFromPath("@drawable/default_image.jpeg")).into(holder.mProductImage);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =new Intent(ctx, ProductDetails.class);
                intent.putExtra("Product", product);
                ctx.startActivity(intent);

                //   Toast.makeText(ctx,"test",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class SearchListViewHolder extends RecyclerView.ViewHolder {
        View mView;
        @BindView(R.id.productimage)
        ImageView mProductImage;
        @BindView(R.id.txtproductdesc)
        TextView txt_productdesc;
        @BindView(R.id.txtprice)
        TextView txt_productprice;
        @BindView(R.id.txtdollar)
        TextView txt_currency;
        @BindView(R.id.shopname)
        ImageView storelogo;

        public SearchListViewHolder(View itemView) {
            super(itemView);
            this.mView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
