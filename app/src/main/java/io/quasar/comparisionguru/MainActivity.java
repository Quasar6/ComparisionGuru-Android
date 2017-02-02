package io.quasar.comparisionguru;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.quasar.comparisionguru.ProductSearchList.SearchListActivity;

public class MainActivity extends AppCompatActivity {
    Button btnSearch;

    Context ctx=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearch =(Button)findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ctx, SearchListActivity.class);
                startActivity(intent);

            }
        });
    }
}
