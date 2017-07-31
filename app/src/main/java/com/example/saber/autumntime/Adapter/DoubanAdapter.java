package com.example.saber.autumntime.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.saber.autumntime.Activity.DisPlayActivity;
import com.example.saber.autumntime.R;
import com.example.saber.autumntime.Utils.GlobalConsts;
import com.example.saber.autumntime.bean.DoubanTitle;

import java.util.List;

/**
 * Created by saber on 2017/7/31.
 */

public class DoubanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final String TAG = "DoubanAdapter";

    private List<DoubanTitle> posts;
    private Context mContext;
    private LayoutInflater inflater;

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = 1;

    public DoubanAdapter(List<DoubanTitle> posts, Context mContext) {
        this.posts = posts;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        switch (viewType){
            case TYPE_NORMAL:
                View view = inflater.inflate(R.layout.list_item,parent,false);
                final ViewHolder holder = new ViewHolder(view);
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getAdapterPosition();
                        Intent intent = new Intent(mContext, DisPlayActivity.class);
                        intent.putExtra("id",posts.get(position).getId());
                        intent.putExtra("type", GlobalConsts.TYPE_DOUBAN);
                        mContext.startActivity(intent);
                    }
                });
                return holder;

            case TYPE_FOOTER:
                View view1 = inflater.inflate(R.layout.list_footer,parent,false);
                FootHolder footHolder = new FootHolder(view1);
                return footHolder;
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ViewHolder){
            ((ViewHolder) holder).tvTitle.setText(posts.get(position).getTitle());
            Log.d(TAG,"getTitle():"+posts.get(position).getTitle());
            String imageUrl = "";
            if(posts.get(position).getThumbs().size() >0 ){
                imageUrl = posts.get(position).getThumbs().get(0).getMedium().getUrl();
                Glide.with(mContext).load(imageUrl).into(((ViewHolder) holder).ivTitle);
            }else {
                Glide.with(mContext).load(imageUrl).error(R.mipmap.ic_launcher).into(((ViewHolder) holder).ivTitle);
                //((ViewHolder) holder).ivTitle.setVisibility(View.GONE);
            }
            Log.d(TAG,"imageUrl:"+imageUrl);
        }


    }

    @Override
    public int getItemCount() {
        return posts.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == posts.size()){
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private TextView tvTitle;
        private ImageView ivTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            ivTitle = (ImageView) itemView.findViewById(R.id.iv_title);
        }
    }

    class FootHolder extends RecyclerView.ViewHolder{

        public FootHolder(View itemView) {
            super(itemView);
        }
    }

}
