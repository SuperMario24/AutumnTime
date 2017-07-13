package com.example.saber.autumntime.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.saber.autumntime.Activity.DisPlayActivity;
import com.example.saber.autumntime.R;
import com.example.saber.autumntime.Utils.GlobalConsts;
import com.example.saber.autumntime.bean.ResultResp;
import java.util.List;

/**
 * Created by lizhaotailang on 2016/6/14.
 */
public class GuokrNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    private List<ResultResp> resultResps;

    public GuokrNewsAdapter(Context context, List<ResultResp> resultResps) {
        this.context = context;
        this.resultResps = resultResps;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        final RecyclerAdapter.ViewHolder holder = new RecyclerAdapter.ViewHolder(view);

        //recycleView的点击事件
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                //获取id
                int id = resultResps.get(position).getId();
                Intent intent = new Intent(context, DisPlayActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("type", GlobalConsts.TYPE_GUOKR);
                intent.putExtra("image",resultResps.get(position).getImages().get(0));
                intent.putExtra("title",resultResps.get(position).getTitle());
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof RecyclerAdapter.ViewHolder){
            ResultResp resultResp = resultResps.get(position);
            ((RecyclerAdapter.ViewHolder)holder).textView.setText(resultResp.getTitle());
            Glide.with(context).load(resultResp.getImages().get(0)).into(((RecyclerAdapter.ViewHolder)holder).imageView);
        }
    }

    @Override
    public int getItemCount() {
        return resultResps.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView textView;
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            textView = (TextView) itemView.findViewById(R.id.tv_title);
            imageView = (ImageView) itemView.findViewById(R.id.iv_title);
        }

    }

}
