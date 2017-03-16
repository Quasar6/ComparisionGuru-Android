package io.quasar.comparisionguru;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import io.quasar.comparisionguru.Model.Product;
import io.quasar.comparisionguru.ProductSearchList.SearchListRecyclerAdapter;

/**
 * Created by prashantn.pol on 2017-02-10.
 */

public class SponsoredListRecyclerAdapter extends RecyclerView.Adapter<SponsoredListRecyclerAdapter.SponsoredListViewHolder>{

    ArrayList<Product> products =new ArrayList<Product>();
    Context ctx;

    public SponsoredListRecyclerAdapter(ArrayList<Product> products, Context ctx) {
        this.products = products;
        this.ctx = ctx;
    }


    @Override
    public SponsoredListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_card,parent,false);
        SponsoredListRecyclerAdapter.SponsoredListViewHolder viewHolder=new SponsoredListRecyclerAdapter.SponsoredListViewHolder(view,products,ctx);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SponsoredListViewHolder holder, int position) {
        Product product=products.get(position);
        holder.pname.setText(product.getName().substring(0, 17) + "...");
        holder.pprice.setText(product.getPrice());
        String storename=product.getStore().toString();
        if(storename.equals("bestbuy"))
        {
            //Toast.makeText(ctx,storename,Toast.LENGTH_LONG).show();

            holder.storeimage.setImageResource(R.drawable.bestbuy);
        }
        else if(storename.equals("ebay"))
        {
            holder.storeimage.setImageResource(R.drawable.ebaylogo);

        }
        else if(storename.equals("walmart"))
        {
            holder.storeimage.setImageResource(R.drawable.walmart);

        }


    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public  static class SponsoredListViewHolder extends RecyclerView.ViewHolder
    {
        TextView pname;
        TextView pprice;
        ArrayList<Product> products=new ArrayList<Product>();
        Context ctx;
        ImageView storeimage;


        public SponsoredListViewHolder(View view, ArrayList<Product> products, Context ctx) {
            super(view);
            pname=(TextView)view.findViewById(R.id.pname);
            pprice=(TextView)view.findViewById(R.id.pprice);
            storeimage =(ImageView)view.findViewById(R.id.shopname);
            this.products = products;
            this.ctx = ctx;
        }

        public SponsoredListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
