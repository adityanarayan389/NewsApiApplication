package com.appstone.newsapiapplication;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.Resource;

public class NewsCategoryAdapter extends RecyclerView.Adapter<NewsCategoryAdapter.NewsCategoryholdr> {

    private Context context;
    private String[] categories;
    private newsCategoryClickListner listner;
    private int selectedPosition = -1;


    public void setListner(newsCategoryClickListner listner){
        this.listner= listner;
    }

    public  NewsCategoryAdapter(Context context){
        this.context=context;
        this.categories = context.getResources().getStringArray(R.array.newws_categories);

    }

    @NonNull
    @Override
    public NewsCategoryholdr onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsCategoryholdr(LayoutInflater.from(context).inflate(R.layout.news_catagory,parent,false));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull NewsCategoryholdr holder, int position) {
        String newsCAt = categories[position];
        holder.category.setText(newsCAt);
        holder.rlCatogory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listner != null) {
                    listner.newsCategoryClicked(newsCAt);
                    selectedPosition=position;
                    notifyDataSetChanged();
                }
            }

        });
        if (position == selectedPosition){
            holder.rlCatogory.setBackground(ResourcesCompat.getDrawable(context.getResources(),R.drawable.selected,null));
            holder.category.setTextColor(ResourcesCompat.getColor(context.getResources(),R.color.colorBlack,null));
        }else{
            holder.rlCatogory.setBackground(ResourcesCompat.getDrawable(context.getResources(),R.drawable.unselected,null));
            holder.category.setTextColor(ResourcesCompat.getColor(context.getResources(),R.color.colorBlack,null));
        }

    }

    @Override
    public int getItemCount() {
        return categories != null?categories.length:0;
    }

    class NewsCategoryholdr extends RecyclerView.ViewHolder{
        private TextView category;
        private RelativeLayout rlCatogory;

        public NewsCategoryholdr(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.tv_news__category);
            rlCatogory = itemView.findViewById(R.id.rl_news_categories);
        }
    }

    public interface newsCategoryClickListner{
        void newsCategoryClicked(String category);
    }
}
