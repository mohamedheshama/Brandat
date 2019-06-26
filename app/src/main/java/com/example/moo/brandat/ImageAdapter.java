package com.example.moo.brandat;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.moo.popularmovies.Model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private List<List<products>> mList;
    private List<String> mNamesCategories;
    private Context context;
    final   private Object imageClickHandler;





    public ImageAdapter(List<List<products>> mList, Context context, Object clickHandler,List<String> namesGategories) {
        this.mList = mList;
        this.context = context;
        this.imageClickHandler = clickHandler;
        mNamesCategories=namesGategories;
    }

    @NonNull
    @Override
    public ImageAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final Context context = viewGroup.getContext();
        int idOfImageAdapter = R.layout.recycler_view_item_two;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        Boolean shouldAttatchFromParent = false;
        View view = layoutInflater.inflate(idOfImageAdapter,viewGroup,shouldAttatchFromParent);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);


        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ImageViewHolder imageViewHolder, int i) {
        List<products> prod = mList.get(i);

        imageViewHolder.onBind(prod,i);


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerViewTwo;
        private ImageAdapterTwo mImageAdapterTwo;
        private List<products> arrayList;
        private TextView mNameCategreTextView;
        private RelativeLayout mRelative;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerViewTwo = itemView.findViewById(R.id.recycler_view_two);
            mNameCategreTextView=itemView.findViewById(R.id.name_categries_text_view);
            mRelative=itemView.findViewById(R.id.relative_more_click);
            LinearLayoutManager sglm = new LinearLayoutManager(context);
            sglm.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerViewTwo.setLayoutManager(sglm);

            //productRecycler.setLayoutManager(layoutManager);
           mRelative.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   TextView mRelative=v.findViewById(R.id.name_categries_text_view);
                   Log.d("mano", "onClick:           "+mRelative.getText().toString());
                   Intent item=new Intent(context,categoryItem.class);

                   item.putExtra("item",mRelative.getText().toString());
                   context.startActivity(item);
               }
           });
            arrayList=new ArrayList<>();
            mImageAdapterTwo = new ImageAdapterTwo(arrayList,itemView.getContext(),(ImageAdapterTwo.ImageClickHandler)imageClickHandler,true);

            recyclerViewTwo.setAdapter(mImageAdapterTwo);

        }
        public void onBind(List<products> lisst,int postioin){
            arrayList.clear();
            arrayList.addAll(lisst);
            mImageAdapterTwo.notifyDataSetChanged();
            mNameCategreTextView.setText(mNamesCategories.get(postioin));

        }

    }
}

