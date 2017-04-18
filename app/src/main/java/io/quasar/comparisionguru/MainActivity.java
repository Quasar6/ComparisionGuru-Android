package io.quasar.comparisionguru;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.quasar.comparisionguru.Global.GlobalConstants;
import io.quasar.comparisionguru.Model.Product;
import io.quasar.comparisionguru.Model.User;
import io.quasar.comparisionguru.ProductDetails.ProductDetails;
import io.quasar.comparisionguru.ProductSearchList.SearchListActivity;
import io.quasar.comparisionguru.ProductSearchList.SearchListRecyclerAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements GlobalConstants,
        GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 9001;
    public ProgressDialog mProgressDialog;
    @BindView(R.id.search)
    AppCompatButton mSearchButton;
    @BindView(R.id.query_text)
    EditText mQueryView;
    @BindView(R.id.user)
    Button mUserText;
    @BindView(R.id.singIn)
    Button mSignText;

    AppBarLayout appBarLayout;
    Toolbar toolbar;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String[] productdesc, productprice;
    ArrayList<Product> arrayList = new ArrayList<Product>();
    private String TAG = "MAINACTIVITY";
    private boolean isLoggedIn;

    private FirebaseAuth mAuth;
    // [END declare_auth]

    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;
    // [END declare_auth_listener]

    private GoogleApiClient mGoogleApiClient;
    User mUser;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
        isLoggedIn = false;

        recyclerView = (RecyclerView) findViewById(R.id.rv_sponsoredlist);
        productdesc = getResources().getStringArray(R.array.productdesc);
        productprice = getResources().getStringArray(R.array.price);
        toolbar = (Toolbar) findViewById(R.id.mtoolbar);
        //   setSupportActionBar(toolbar);

        int i = 0;
        for (String pname : productdesc) {
            Product dataprovider = new Product(pname, productprice[i]);
            arrayList.add(dataprovider);
            i++;
        }


        callFeaturedAPI();

        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        if(mGoogleApiClient==null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
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
                    mUserText.setText(user.getDisplayName());
                    mSignText.setText("Sign Out");
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    isLoggedIn = false;
                    mUserText.setText("Guest");
                    mSignText.setText("Sign In");
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
//                TODO updateUI(user);
                // [END_EXCLUDE]
            }
        };
        // [END auth_state_listener]

      //  recyclerView.setAdapter(adapter);

    }

    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void callFeaturedAPI() {
        Call<ArrayList<Product>> call = CHEAPEST_PRICE_API.getFeaturedList();
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                Log.d("RETROFIT", "response.isSuccessful() >>>>> " + response.isSuccessful());
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("RETROFIT", "UPDATE DOCTOR RETROFIT FAILURE jObjError.getString(message) >>>>> " + jObjError.getString("message"));
                        showToast(jObjError.getString("message"));
                    } catch (Exception e) {
                        e.printStackTrace();
                        showToast(e.getMessage());
                    }
                    return;
                }
                hideProgressDialog();

                ArrayList<Product> products = response.body();
                populateList(products);
                Log.d(TAG, "Number pf products >>> " + products.size());
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                showToast("Sorry unable to fetch results");
            }
        });
    }

    private void populateList(ArrayList<io.quasar.comparisionguru.Model.Product> productArrayList){

        adapter = new SponsoredListRecyclerAdapter(productArrayList, this);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.singIn)
    public void signInButton(){
        if(!isLoggedIn) {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }else{
            signOut();
        }
    }

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

    @OnClick(R.id.search)
    public void searchAction() {
        String txt = mQueryView.getText().toString();
        if (txt.isEmpty()) {
            mQueryView.setError("Please type in Something");
            //mQueryView.requestFocus();
            return;
        }
        Intent intent = new Intent(this, SearchListActivity.class);
        intent.putExtra(QUERY, txt);
        startActivity(intent);
//        callAPI(txt);
//        callStringAPI(txt);
    }

    @OnClick(R.id.query_speech)
    public void startSpeechListner(){
        promptSpeechInput();
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mQueryView.setText(result.get(0));
                    mSearchButton.callOnClick();
                }
                break;
            }

            case RC_SIGN_IN: {
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
                break;
            }

        }
    }

    private void showToast(String msg) {
        hideProgressDialog();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void showProgressDialog(String title) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(title);
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        showProgressDialog("Please Wait...");
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        hideProgressDialog();
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        mUser = new User(acct.getDisplayName(), acct.getEmail(), acct.getPhotoUrl()+"", "Google");
                        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.login_user_file), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(getString(R.string.login_user), mUser.toJson(mUser));
                        editor.apply();

                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_google]

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
