package com.app.mtotosmart.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mtotosmart.clickItem.OnClickItem;
import com.app.mtotosmart.common.Key;
import com.app.mtotosmart.databinding.ItemImgBinding;
import com.app.mtotosmart.helper.Tool;
import com.bumptech.glide.Glide;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.Holder> {
    List<Integer> list;
    Context context;
    Tool tool;
    OnClickItem onClickItem;

    public ImageAdapter(List<Integer> list, Context context, Tool tool, OnClickItem onClickItem) {
        this.list = list;
        this.context = context;
        this.tool = tool;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemImgBinding binding = ItemImgBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Glide.with(context).load(list.get(position)).centerCrop().into(holder.bind.iv);
        int w = tool.getScreenSize().widthPixels;
        holder.bind.cv.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (w/2)));
        holder.itemView.setOnClickListener(v -> {
            Bitmap srcBm = BitmapFactory.decodeResource(context.getResources(), list.get(position)).copy(Bitmap.Config.ARGB_8888, true);
            Key.PICTURE_SELECTED = srcBm;
            onClickItem.clickItem();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        ItemImgBinding bind;
        public Holder(@NonNull ItemImgBinding itemImgBinding) {
            super(itemImgBinding.getRoot());
            this.bind = itemImgBinding;
        }
    }
}
