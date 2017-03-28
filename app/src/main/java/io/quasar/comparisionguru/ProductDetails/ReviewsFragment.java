package io.quasar.comparisionguru.ProductDetails;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.quasar.comparisionguru.Model.Comment;
import io.quasar.comparisionguru.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewsFragment extends Fragment {

    @BindView(R.id.user_image)
    ImageView mUserImage;
    @BindView(R.id.comment_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    private RecyclerView.Adapter mAdapter;

    public ReviewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void onStart() {
        super.onStart();
        mAdapter = new CommentListAdapter(getFakeComments());
        mRecyclerView.setAdapter(mAdapter);
        mProgressBar.setVisibility(View.GONE);
    }

    private ArrayList<Comment> getFakeComments(){
        ArrayList<Comment> comments = new ArrayList<>();
        for(int i =0; i<=10; i++){
            comments.add(new Comment("", "Hello "+i, i, "user "+i, "", ""));
        }
        return comments;
    }

    @OnClick({R.id.user_comment_layout, R.id.user_image, R.id.user_comment})
    public void showCommentDialog(){
        Log.d("COMMENT"," USER CLICKED COMMENT");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.user_comment_layout, null);

        final EditText mUserComment = (EditText) view.findViewById(R.id.user_comment_edit);
        final RatingBar mRatingBar = (RatingBar) view.findViewById(R.id.user_rating);
        Button submitButton = (Button) view.findViewById(R.id.add_comment);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setTitle("Comment");
        builder.setView(view);
        builder.setCancelable(true);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
