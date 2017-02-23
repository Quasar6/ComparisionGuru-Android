package io.quasar.comparisionguru.ProductDetails;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.quasar.comparisionguru.ProductSearchList.SearchListRecyclerAdapter;
import io.quasar.comparisionguru.R;

/**
 * Created by prashantn.pol on 2017-02-22.
 */

public class ComparePriceRecyclerAdapter extends RecyclerView.Adapter<ComparePriceRecyclerAdapter.ComparePriceViewHolder> {


    @Override
    public ComparePriceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ComparePriceViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ComparePriceViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.storeimage)
        ImageView mStoreImage;
        @BindView(R.id.txtlowprice)
        TextView txt_productdesc;


        public ComparePriceViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}



