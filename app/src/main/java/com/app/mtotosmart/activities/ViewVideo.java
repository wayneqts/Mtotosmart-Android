package com.app.mtotosmart.activities;

import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.app.mtotosmart.R;
import com.app.mtotosmart.adapter.VerbAdapter;
import com.app.mtotosmart.clickItem.OnClickItemVideo;
import com.app.mtotosmart.databinding.ActivityVideoViewBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewVideo extends BaseActivity implements OnClickItemVideo {
    ActivityVideoViewBinding binding;
    List<String> list, moreList;
    VerbAdapter adapter;
    boolean isMore = false;
    int selected = -1, moreSelected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initUi();

        binding.btBack.setOnClickListener(v -> finish());
        binding.tvTitle.setText(getIntent().getStringExtra("title"));
        binding.btClose.setOnClickListener(v -> {
            binding.videoView.stopPlayback();
            binding.rlVideo.setVisibility(View.GONE);
            binding.iv.setVisibility(View.VISIBLE);
            selected = moreSelected = -1;
            if (isMore){
                adapter = new VerbAdapter(this, moreList, true, this, moreSelected);
            }else {
                adapter = new VerbAdapter(this, list, false, this, selected);
            }
            binding.rcvVerb.setAdapter(adapter);
        });
    }

    // init UI
    private void initUi(){
        binding.iv.setImageResource(pref.getProfile().isGudo() ? R.mipmap.img_boy:R.mipmap.img_girl);
        list = new ArrayList<>();
        moreList = new ArrayList<>();
        binding.rcvVerb.setLayoutManager(new GridLayoutManager(this, 3));
        if (getIntent().getStringExtra("title").equals(getString(R.string.verbs))){
            list.addAll(Arrays.asList(getResources().getStringArray(R.array.verbs)));
            moreList.addAll(Arrays.asList(getResources().getStringArray(R.array.verbs_more)));
        }else if (getIntent().getStringExtra("title").equals(getString(R.string.parts_of_the_body))){
            list.addAll(Arrays.asList(getResources().getStringArray(R.array.body)));
            moreList.addAll(Arrays.asList(getResources().getStringArray(R.array.body_more)));
        }else {
            list.addAll(Arrays.asList(getResources().getStringArray(R.array.alphabets)));
            binding.rcvVerb.setLayoutManager(new GridLayoutManager(this, 4));
        }
        adapter = new VerbAdapter(this, list, isMore, this, selected);
        binding.rcvVerb.setAdapter(adapter);
        tool.rcvNoAnimator(binding.rcvVerb);
    }

    // load video
    private void loadVd(int src){
        binding.videoView.setOnPreparedListener(mp -> mp.setLooping(true));
        binding.videoView.setVideoPath("android.resource://"+getPackageName()+"/"+src);
        binding.videoView.start();
    }

    @Override
    public void clickVideo(String vdName, int pos) {
        binding.rlVideo.setVisibility(View.VISIBLE);
        if (binding.tvTitle.getText().toString().equals(getString(R.string.alphabets))){
            loadVd(getResources().getIdentifier(vdName, "raw", getPackageName()));
        }else {
            loadVd(getResources().getIdentifier(pref.getProfile().isGudo()?vdName+"_b":vdName+"_g", "raw", getPackageName()));
        }
        binding.iv.setVisibility(View.GONE);
        if (isMore){
            moreSelected = pos;
            selected = -1;
        }else {
            selected = pos;
            moreSelected = -1;
        }
    }

    @Override
    public void clickMore() {
        isMore = !isMore;
        if (isMore){
            adapter = new VerbAdapter(this, moreList, true, this, moreSelected);
        }else {
            adapter = new VerbAdapter(this, list, false, this, selected);
        }
        binding.rcvVerb.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.videoView.stopPlayback();
    }
}