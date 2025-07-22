package com.app.mtotosmart.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mtotosmart.clickItem.OnClickItem;
import com.app.mtotosmart.common.Key;
import com.app.mtotosmart.databinding.ItemColorBinding;
import com.app.mtotosmart.helper.Tool;
import com.app.mtotosmart.model.ColorPicker;
import com.bumptech.glide.Glide;

import java.util.List;

public class ColorPickerAdapter extends RecyclerView.Adapter<ColorPickerAdapter.Holder> {
    Context context;
    List<ColorPicker> list;
    Tool tool;
    int selectedItem = 0;
    OnClickItem onClickItem;

    public ColorPickerAdapter(Context context, List<ColorPicker> list, Tool tool, OnClickItem onClickItem) {
        this.context = context;
        this.list = list;
        this.tool = tool;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemColorBinding binding = ItemColorBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        ColorPicker picker = list.get(position);
        Glide.with(context).load(picker.getImg()).centerCrop().into(holder.bind.iv);
        int width = tool.getScreenSize().widthPixels;
        holder.bind.iv.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (width/3)));

        if (selectedItem == position){
            holder.itemView.setSelected(true);
            holder.bind.iv.setTranslationY(40);
            Key.COLOR_SELECTED = Color.parseColor(picker.getColor());
            onClickItem.clickItem();
        }else {
            holder.itemView.setSelected(false);
            holder.bind.iv.setTranslationY(80);
        }

        holder.itemView.setOnClickListener(v -> {
            if (selectedItem >= 0)
                notifyItemChanged(selectedItem);
            selectedItem = holder.getLayoutPosition();
            notifyItemChanged(selectedItem);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        ItemColorBinding bind;
        public Holder(@NonNull ItemColorBinding itemColorBinding) {
            super(itemColorBinding.getRoot());
            this.bind = itemColorBinding;
        }
    }
}
