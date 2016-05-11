package com.vokasi.listview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MerkActivity extends AppCompatActivity {

    ListView merkList;
    TextView merkText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merk);
        Intent i=getIntent();
        final String kategori = i.getStringExtra("itemValue");
        int itemId=i.getIntExtra("itemId",0);
        merkText=(TextView)findViewById(R.id.textMerk);
        merkText.setText("Produk " + kategori);
        merkList=(ListView)findViewById(R.id.listMerk);
        String[] values = new String[]{};
        switch (itemId)
        {
            case 0:
                values =new String[]{ "Samsung","Nokia","Iphone","Xiaomi"};
                break;
            case 1:
                values =new String[]{ "Asus","Macbook","Acer","Vaio"};
                break;
            case 2:
                values =new String[]{ "Nikon","Canon","Kodak","Sony"};
                break;
            default:
                values =new String[]{ "Casio","Tissot","Seiko"};
                break;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1, values);
        merkList.setAdapter(adapter);
        final Intent intent = new Intent(this, DetailActivity.class);
        merkList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int i, long l) {
                int positionItem = i;
                String itemValue = (String) merkList.getItemAtPosition(i);
                intent.putExtra("kategori", kategori);
                intent.putExtra("merk", itemValue);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_merk, menu);
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
