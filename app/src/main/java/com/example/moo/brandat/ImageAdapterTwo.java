package com.example.moo.brandat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.moo.popularmovies.Model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapterTwo extends RecyclerView.Adapter<ImageAdapterTwo.ImageViewHolderTwo> {
    private List<products> mList;
    private Context context;
    final   private ImageClickHandler imageClickHandler;
    private boolean isNew;



    public interface ImageClickHandler{
        public void onImageClick(products products);
    }


    public ImageAdapterTwo(List<products> mList, Context context, ImageClickHandler clickHandler,boolean checkOldNew) {
        this.mList = mList;
        this.context = context;
        this.imageClickHandler = clickHandler;
        isNew=checkOldNew;

    }

    @NonNull
    @Override
    public ImageAdapterTwo.ImageViewHolderTwo onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final Context context = viewGroup.getContext();
        int idOfImageAdapter;

        if (isNew) {
             idOfImageAdapter = R.layout.list_item_main_two;
        }else {
             idOfImageAdapter = R.layout.list_item_main;
        }
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        Boolean shouldAttatchFromParent = false;
        View view = layoutInflater.inflate(idOfImageAdapter,viewGroup,shouldAttatchFromParent);
        ImageViewHolderTwo imageViewHolder = new ImageViewHolderTwo(view);


        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapterTwo.ImageViewHolderTwo imageViewHolder, int i) {
        products prod = mList.get(i);
        Log.d("imgg", "onBindViewHolder: "+prod.getImgesrc());
        if(isNew){
        Picasso.with(context).load(prod.getImgesrc()).resize(200, 200)
                .centerCrop().into(imageViewHolder.imageView);
        }else{
            Picasso.with(context).load(prod.getImgesrc()).resize(200, 280)
                    .centerCrop().into(imageViewHolder.imageView);

        }
        imageViewHolder.arttitle.setText(prod.getCost()+" "+"LE");
        imageViewHolder.artsubtitle.setText(prod.getFname());


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ImageViewHolderTwo extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imageView;
        private TextView arttitle;

        private TextView artsubtitle;


        public ImageViewHolderTwo(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.thumbnail);
            arttitle =itemView.findViewById(R.id.article_title);
            artsubtitle =itemView.findViewById(R.id.article_subtitle);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            products pro = mList.get(position);
            imageClickHandler.onImageClick(pro);


        }
    }
}

