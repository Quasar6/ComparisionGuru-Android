package io.quasar.comparisionguru.ProductSearchList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import io.quasar.comparisionguru.R;

/**
 * Created by prashantn.pol on 2017-02-01.
 */

public class SearchListRecyclerAdapter extends RecyclerView.Adapter<SearchListRecyclerAdapter.SearchListViewHolder>{


    ArrayList<Product> products =new ArrayList<Product>();
    Context ctx;

    public SearchListRecyclerAdapter(ArrayList<Product> products, Context ctx) {
        this.products = products;
        this.ctx = ctx;
    }


    @Override
    public SearchListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        SearchListViewHolder viewHolder=new SearchListViewHolder(view,products,ctx);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(SearchListViewHolder holder, int position) {
        Product product=products.get(position);
        holder.txt_productdesc.setText(product.getProductDesc());
        holder.txt_productprice.setText(product.getProductPrice());

        }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public  static class SearchListViewHolder extends RecyclerView.ViewHolder
    {
        TextView txt_productdesc;
        TextView txt_productprice;
        ArrayList<Product> products=new ArrayList<Product>();
        Context ctx;

        public SearchListViewHolder(View view, ArrayList<Product> products, Context ctx) {
            super(view);
            txt_productdesc=(TextView)view.findViewById(R.id.txtproductdesc);
            txt_productprice=(TextView)view.findViewById(R.id.txtprice);

            this.products = products;
            this.ctx = ctx;
        }

        public SearchListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
