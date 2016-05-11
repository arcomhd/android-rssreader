package com.vokasi.listview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class KategoriActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> katList;
    KategoriAdapter katAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);
        katList=new ArrayList<>();
        katList.add("Politik");
        katList.add("Sport");
        katList.add("Showbiz");
        katList.add("Teknologi");
        katAdapter=new KategoriAdapter(this,katList);
        listView=(ListView)findViewById(R.id.listView);
        listView.setAdapter(katAdapter);
        final Intent intent = new Intent(this, MainActivity.class);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int i, long l) {
                int positionItem = i;
                String itemValue = (String) listView.getItemAtPosition(positionItem);
                intent.putExtra("kategori", itemValue);
                intent.putExtra("kategoriId", positionItem);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kategori, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
