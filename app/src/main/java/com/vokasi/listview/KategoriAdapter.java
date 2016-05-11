package com.vokasi.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by praktikan on 4/28/2016.
 */
public class KategoriAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> values;
    public KategoriAdapter(Context context, ArrayList<String> values) {
        super(context, R.layout.kategori_item,values);
        this.values = values;
        this.context=context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.kategori_item, parent, false);
        TextView titleItem=(TextView)view.findViewById(R.id.titleItem);
        ImageView imageItem=(ImageView)view.findViewById(R.id.imageItem);
        titleItem.setText(values.get(position));
        switch (position){
            case 0:
                imageItem.setImageResource(R.drawable.politic);
                break;
             case 1:
                imageItem.setImageResource(R.drawable.sport);
                break;
            case 2:
                imageItem.setImageResource(R.drawable.showbiz);
                break;
            default:
                imageItem.setImageResource(R.drawable.techno);
                break;
        }

        return view;
    }
}
