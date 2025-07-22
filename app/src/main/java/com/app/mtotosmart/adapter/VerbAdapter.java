package com.app.mtotosmart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mtotosmart.R;
import com.app.mtotosmart.clickItem.OnClickItem;
import com.app.mtotosmart.clickItem.OnClickItemVideo;
import com.app.mtotosmart.databinding.ItemVerbBinding;

import java.util.List;

public class VerbAdapter extends RecyclerView.Adapter<VerbAdapter.VH> {
    Context context;
    List<String> list;
    boolean isMore;
    OnClickItemVideo onClickItemVideo;
    private int selectedPosition = -1;

    public VerbAdapter(Context context, List<String> list, boolean isMore, OnClickItemVideo onClickItemVideo, int selectedPosition) {
        this.context = context;
        this.list = list;
        this.isMore = isMore;
        this.onClickItemVideo = onClickItemVideo;
        this.selectedPosition = selectedPosition;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemVerbBinding binding = ItemVerbBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new VH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bind.tvVerb.setText(list.get(position));
        if (position == list.size() - 1) {
            holder.bind.rlVerb.setVisibility(list.get(position).equals("") ? View.GONE : View.VISIBLE);
            holder.bind.rlMore.setVisibility(list.get(position).equals("") ? View.VISIBLE : View.GONE);
        } else {
            holder.bind.rlVerb.setVisibility(View.VISIBLE);
            holder.bind.rlMore.setVisibility(View.GONE);
        }

        if (selectedPosition == position) {
            holder.itemView.setSelected(true);
            holder.bind.rlVerb.setBackgroundResource(R.drawable.bg_radius_blue);
        } else {
            holder.itemView.setSelected(false);
            holder.bind.rlVerb.setBackgroundResource(R.drawable.bg_radius_orange);
        }
        holder.itemView.setOnClickListener(v -> {
            if (holder.itemView.isSelected()) {
                holder.itemView.setSelected(false);
                holder.bind.rlVerb.setBackgroundResource(R.drawable.bg_radius_orange);
            } else {
                if (selectedPosition >= 0)
                    notifyItemChanged(selectedPosition);
                selectedPosition = holder.getLayoutPosition();
                notifyItemChanged(selectedPosition);
                onClickItemVideo.clickVideo(list.get(position).toLowerCase(), position);
            }
        });
        holder.bind.tvMore.setText(!isMore ? context.getString(R.string.more) : context.getString(R.string.back));
        holder.bind.rlMore.setOnClickListener(v -> onClickItemVideo.clickMore());
//        holder.bind.rlVerb.setOnClickListener(v -> );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        ItemVerbBinding bind;

        public VH(@NonNull ItemVerbBinding itemVerbBinding) {
            super(itemVerbBinding.getRoot());
            this.bind = itemVerbBinding;
        }
    }
}
