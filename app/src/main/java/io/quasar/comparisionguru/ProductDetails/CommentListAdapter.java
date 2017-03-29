package io.quasar.comparisionguru.ProductDetails;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
    Context context;

    public CommentListAdapter(ArrayList<Comment> comments, Context context) {
        this.comments = comments;
        this.context = context;
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
        if(!comment.getUserImage().isEmpty()) {
            Glide.with(context).load(comment.getUserImage()).error(Drawable.createFromPath("@drawable/ic_account_circle_black_36dp")).into(holder.mUserImage);
        }
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void addCommentItem(Comment comment){
        comments.add(comment);
        notifyDataSetChanged();
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
