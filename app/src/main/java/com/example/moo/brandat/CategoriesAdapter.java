package com.example.moo.brandat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter extends ArrayAdapter<ArrayList> {
    List namesCategories;
    public CategoriesAdapter(Context context, List categoriesNameArrayList) {
        super(context,0,categoriesNameArrayList);
        //get my list name of categories
        namesCategories=categoriesNameArrayList;






    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_list_categories,parent,false);
        }

        //get TextView and Image from XML
        TextView classifyTextView=convertView.findViewById(R.id.classify_text_view);
        ImageView imageView=convertView.findViewById(R.id.classify_image_view);

        //set image test for ImageView
        imageView.setImageResource(R.drawable.ic_camera);

        //set all name of categories to textview
        classifyTextView.setText(String.valueOf(namesCategories.get(position)));

        return convertView;
    }
}
