package io.quasar.comparisionguru.ProductDetails;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.quasar.comparisionguru.API.CheapestPrice;
import io.quasar.comparisionguru.Model.Comment;
import io.quasar.comparisionguru.Model.Product;
import io.quasar.comparisionguru.Model.User;
import io.quasar.comparisionguru.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static io.quasar.comparisionguru.Global.GlobalConstants.BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewsFragment extends Fragment implements
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "ReviewsFragment";
    private static final int RC_SIGN_IN = 9001;
    public ProgressDialog mProgressDialog;
    @BindView(R.id.user_image)
    ImageView mUserImage;
    @BindView(R.id.comment_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    Product product;

    ArrayList<Comment> comments;
    private CommentListAdapter mAdapter;
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;
    // [END declare_auth_listener]

    private GoogleApiClient mGoogleApiClient;
    boolean isLoggedIn = false;
    User mUser;

    public ReviewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);
        ButterKnife.bind(this, view);
        Intent intent = getActivity().getIntent();
        product = (Product) intent.getSerializableExtra("Product");

        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        if(mGoogleApiClient==null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .enableAutoManage(getActivity() /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    isLoggedIn = true;
                    mUser = new User(user.getDisplayName(), user.getEmail(), user.getPhotoUrl()+"", "Google");
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    isLoggedIn = false;
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
//                TODO updateUI(user);
                // [END_EXCLUDE]
            }
        };
        // [END auth_state_listener]
        return view;
    }

    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        if(product.getComments() != null){
            comments = product.getComments();
        }else{
            comments = new ArrayList<Comment>();
        }
        mAdapter = new CommentListAdapter(comments, getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setReverseLayout(false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private ArrayList<Comment> getFakeComments() {
        ArrayList<Comment> comments = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            comments.add(new Comment("", "Hello " + i, i, "user " + i, "", ""));
        }
        return comments;
    }

    private void showLoginDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Login Required");
        builder.setMessage("Please login for reviewing the product");
        builder.setCancelable(true);
        builder.setPositiveButton("Log In", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                signIn();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @OnClick({R.id.user_comment_layout, R.id.user_image, R.id.user_comment})
    public void showCommentDialog() {
        if(!isLoggedIn){
            showLoginDialog();
            return;
        }
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
                String comment = mUserComment.getText().toString();
                if (comment.isEmpty()) {
                    mUserComment.setError("Please enter valid value");
                    return;
                }
                int rating = (int) mRatingBar.getRating();
                if (rating < 0) {
                    return;
                }
                Comment comment1 = new Comment(product.getId(), comment, rating, mUser.getName(), "", mUser.getUserImageURL());
                addComment(comment1, alertDialog);
            }
        });
    }

    private void addComment(final Comment comment, final AlertDialog alertDialog) {
        showProgressDialog();
        mAdapter.addCommentItem(comment);
        mRecyclerView.smoothScrollToPosition(0);
        Retrofit RETROFIT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CheapestPrice CHEAPEST_PRICE_API = RETROFIT.create(CheapestPrice.class);
        Call<String> call = CHEAPEST_PRICE_API.addCommentToProduct(comment);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                hideProgressDialog();
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("RETROFIT", "UPDATE DOCTOR RETROFIT FAILURE jObjError.getString(message) >>>>> " + jObjError.getString("message"));
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    return;
                }
                alertDialog.dismiss();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                hideProgressDialog();
                alertDialog.dismiss();
            }
        });
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Creating Patient…");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // [START_EXCLUDE]
                // TODO updateUI(null);
                // [END_EXCLUDE]
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        hideProgressDialog();
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        mUser = new User(acct.getDisplayName(), acct.getEmail(), acct.getPhotoUrl()+"", "Google");
                        Context context = getActivity();
                        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.login_user_file), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(getString(R.string.login_user), mUser.toJson(mUser));
                        editor.apply();

                        showCommentDialog();

                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_google]

    // [START signin]
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        //TODO updateUI(null);
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(getActivity(), "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
}
