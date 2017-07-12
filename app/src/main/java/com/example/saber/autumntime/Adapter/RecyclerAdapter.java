package com.example.saber.autumntime.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.example.saber.autumntime.Activity.DisPlayActivity;
import com.example.saber.autumntime.Application.MyApplication;
import com.example.saber.autumntime.R;
import com.example.saber.autumntime.bean.HotNew;

import java.util.List;

/**
 * Created by saber on 2017/3/20.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HotNew> hotNews;
    private Context context;
    private ImageLoader imageLoader;

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = 1;

    public RecyclerAdapter(Context context,List<HotNew> hotNews) {
        this.context = context;
        this.hotNews = hotNews;
        //初始化ImageLoader 创建ImageLoader  ImageCache接口的实现类
        imageLoader = new ImageLoader(MyApplication.getRequestQueue(),new ImageCacheImpl());
    }

    /**
     *  图片缓存ImageCache实现类，如不需要缓存，可不重写方法
     */
    class ImageCacheImpl implements ImageLoader.ImageCache{

        //LruCache--用于图片缓存，底层是一个bitmap
        private LruCache<String,Bitmap> cache;

        public ImageCacheImpl(){
            //设置最大缓存量
            int maxSize = 10*1024*1024;
            cache = new LruCache<String,Bitmap>(maxSize){
                //重写sizeof方法获取当前图片已用的缓存
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes()*value.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String s) {
            return cache.get(s);
        }

        @Override
        public void putBitmap(String s, Bitmap bitmap) {
            cache.put(s,bitmap);
        }
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType){
            case TYPE_NORMAL:
                View view = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
                final ViewHolder holder = new ViewHolder(view);

                //recycleView的点击事件
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getAdapterPosition();
                        //获取id
                        int id = hotNews.get(position).getId();
                        Intent intent = new Intent(context,DisPlayActivity.class);
                        intent.putExtra("id",id);
                        context.startActivity(intent);
                    }
                });
                return holder;
            case TYPE_FOOTER:
                View view1 = LayoutInflater.from(context).inflate(R.layout.list_footer,parent,false);
                FooterViewHolder footerViewHolder = new FooterViewHolder(view1);
                return footerViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){
            HotNew hotNew = hotNews.get(position);
            ((ViewHolder)holder).textView.setText(hotNew.getTitle());
            Glide.with(context).load(hotNew.getImages().get(0)).into(((ViewHolder)holder).imageView);

            //eclipse中实现图片缓存的方法
            //ImageLoader.ImageListener  listener = ImageLoader.getImageListener(holder.imageView,0,0);
            //imageLoader.get(hotNew.getImages().get(0),listener);
        }
    }


    @Override
    public int getItemCount() {
        return hotNews.size()+1;
    }

    /**
     * 显示RecyclerVIew底布局
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if(position == hotNews.size()){
            return RecyclerAdapter.TYPE_FOOTER
;        }
        return RecyclerAdapter.TYPE_NORMAL;
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

    static class FooterViewHolder extends RecyclerView.ViewHolder{
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

}
