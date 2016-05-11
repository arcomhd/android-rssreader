package com.vokasi.listview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by praktikan on 3/23/2016.
 */
public class ItemAdapter extends ArrayAdapter<ItemData>{
    private final Context context;
    private final ArrayList<ItemData> values;
    public ItemAdapter(Context context, ArrayList<ItemData> values) {
        super(context, R.layout.list_item,values);
        this.values = values;
        this.context=context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        TextView dateItem=(TextView)view.findViewById(R.id.dateItem);
        TextView titleItem=(TextView)view.findViewById(R.id.titleItem);
        ImageView imageItem=(ImageView)view.findViewById(R.id.imageItem);
        titleItem.setText(values.get(position).itemTitle);
        dateItem.setText(values.get(position).itemDate);

        if(values.get(position).itemImage!=null) {
            new LoadImage(imageItem).execute(values.get(position).itemImage);
        }else {
            imageItem.setImageResource(R.mipmap.ic_launcher);
        }
        return view;
    }

    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        public ImageView iv;
        public LoadImage(ImageView iv){
            this.iv=iv;
        }
        @Override
        protected Bitmap doInBackground(String... strings) {
            URL url = null;
            Bitmap bmp=null;
            try {
                url = new URL(strings[0]);
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmp;
        }
        protected void onPostExecute(Bitmap image) {
            iv.setImageBitmap(image);
        }
    }
}
