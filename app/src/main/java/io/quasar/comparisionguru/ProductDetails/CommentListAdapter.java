package io.quasar.comparisionguru.ProductDetails;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.quasar.comparisionguru.Model.Comment;
import io.quasar.comparisionguru.R;

/**
 * Created by yatin on 28/03/17.
 */

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {

    ArrayList<Comment> comments;

    public CommentListAdapter(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public CommentListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentListAdapter.ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.mUserComment.setText(comment.getComment());
        holder.mUserName.setText(comment.getUserName());
        if(comment.getRating()>5){
            holder.mUserRating.setRating(5);
            return;
        }
        holder.mUserRating.setRating(comment.getRating());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.user_image)
        ImageView mUserImage;
        @BindView(R.id.user_comment)
        TextView mUserComment;
        @BindView(R.id.user_rating)
        RatingBar mUserRating;
        @BindView(R.id.user_name)
        TextView mUserName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
