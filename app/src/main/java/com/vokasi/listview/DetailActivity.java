package com.vokasi.listview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.IOException;

public class DetailActivity extends AppCompatActivity {
    ItemData itemValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent i=getIntent();
        itemValue = (ItemData)i.getSerializableExtra("item");
        TextView titleText=(TextView)findViewById(R.id.textTitle);
        WebView webView=(WebView)findViewById(R.id.webView);
        titleText.setText(itemValue.itemTitle);
        webView.loadData(itemValue.itemDesc, "text/html", "UTF-8");
        //webView.loadUrl(itemValue.itemLink);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {


            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
