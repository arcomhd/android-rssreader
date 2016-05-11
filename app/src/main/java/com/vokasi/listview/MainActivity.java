package com.vokasi.listview;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ItemData[] itemValues;
    ArrayList<ItemData> itemList;
    ItemAdapter itemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=(ListView)findViewById(R.id.listView);
        String[] values = new String[]
                { "Smartphone","Laptop","Kamera","Jam Tangan"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1, values);
        generateDummyData();
        itemList=new ArrayList<>();
        itemAdapter=new ItemAdapter(this,itemList);
        listView.setAdapter(itemAdapter);
        //listView.setAdapter(new ItemAdapter(this, itemValues));

        final Intent intent = new Intent(this, DetailActivity.class);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int i, long l) {
                int positionItem = i;
                ItemData itemValue = (ItemData) listView.getItemAtPosition(positionItem);
                intent.putExtra("item", itemValue);
                startActivity(intent);
            }
        });

        Intent i=getIntent();
        final String kategori = i.getStringExtra("kategori");
        int itemId=i.getIntExtra("kategoriId", 0);
        TextView textView=(TextView)findViewById(R.id.textView);
        textView.setText(kategori);
        HttpDownloadTask hdt=new HttpDownloadTask();
        switch (itemId) {
            case 0:
                hdt.execute("http://rss.viva.co.id/get/politik");
                break;
            case 1:
                hdt.execute("http://rss.viva.co.id/get/sport");
                break;
            case 2:
                hdt.execute("http://rss.viva.co.id/get/showbiz");
                break;
            default:
                hdt.execute("http://rss.viva.co.id/get/teknologi");
                break;
        }

    }

    private class HttpDownloadTask extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            String urlstr=strings[0];
            try {
                URL url=new URL(urlstr);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                int response=connection.getResponseCode();
                Log.d("debug1", "response code " + response);

                BufferedReader r = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line);
                }
                Log.d("debug1", "Output : " + total.toString());
                parseXML(total.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private void parseXML(String is)
        {
            try {
                XmlPullParserFactory factory = XmlPullParserFactory
                        .newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(new StringReader(is));
                ItemData data = null;
                String currentTag="";
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_DOCUMENT) {

                    } else if (eventType == XmlPullParser.START_TAG) {
                        if (xpp.getName().equals("item")) {
                            data = new ItemData();
                            currentTag = "item";
                        } else if (xpp.getName().equals("title")) {
                            currentTag = "title";
                        } else if (xpp.getName().equals("link")) {
                            currentTag = "link";
                        } else if (xpp.getName().equals("pubDate")) {
                            currentTag = "pubDate";
                        } else if(xpp.getName().equals("url")){
                            currentTag ="imgUrl";
                        } else if(xpp.getName().equals("description")){
                            currentTag ="description";
                        }else{
                            currentTag="";
                        }
                        if(xpp.getName().equals("enclosure")) {
                            data.itemImage = xpp.getAttributeValue(null, "url");
                        }
                    }else if (eventType == XmlPullParser.END_TAG) {
                        if (xpp.getName().equals("item")) {
                            itemList.add(data);
                        }
                    }else if (eventType == XmlPullParser.TEXT) {
                        String content = xpp.getText();
                        content = content.trim();
                        if (data != null) {
                            switch (currentTag) {
                                case "title":
                                    if (content.length() != 0) {
                                        data.itemTitle=content;
                                    }
                                    break;
                                case "link":
                                    if (content.length() != 0) {
                                        data.itemLink=content;
                                    }
                                    break;
                                case "pubDate":
                                    if (content.length() != 0) {
                                        data.itemDate=content;
                                    }
                                    break;
                                case "imgUrl":
                                    if (content.length() != 0) {
                                        data.itemImage=content;
                                    }
                                    break;
                                case "description":
                                    if (content.length() != 0) {
                                        data.itemDesc=content;
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    eventType = xpp.next();
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            itemAdapter.notifyDataSetChanged();
        }
    }

    private void generateDummyData() {
        ItemData data = null;
        itemValues = new ItemData[10];
        for (int i = 0; i < 10; i++) { //please ignore this comment :>
            data = new ItemData();
            data.itemDate = (i+1)+" Maret 2016";
            data.itemTitle = "Post " + (i + 1) + " Title";
            data.itemImage = null;
            itemValues[i] = data;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
