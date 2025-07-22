package com.app.mtotosmart.activities;

import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.app.mtotosmart.R;
import com.app.mtotosmart.databinding.ActivityMatchingItemBinding;

public class MatchingItem extends BaseActivity {
    ActivityMatchingItemBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMatchingItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupUI();
        binding.btColor.setOnClickListener(v -> {
            Intent it = new Intent(this, ColorMatching.class);
            it.putExtra("title", getString(R.string.colors));
            startActivity(it);
        });
        binding.btShape.setOnClickListener(v -> {
            Intent it = new Intent(this, ShapesMatching.class);
            it.putExtra("title", getString(R.string.shapes));
            it.putExtra("key", "sh");
            startActivity(it);
        });
        binding.btFruits.setOnClickListener(v -> {
            Intent it = new Intent(this, ShapesMatching.class);
            it.putExtra("title", getString(R.string.fruits));
            it.putExtra("key", "fr");
            startActivity(it);
        });
        binding.btDa.setOnClickListener(v -> {
            Intent it = new Intent(this, ShapesMatching.class);
            it.putExtra("title", getString(R.string.domestic_nanimal1));
            it.putExtra("key", "da");
            startActivity(it);
        });
        binding.btWild.setOnClickListener(v -> {
            Intent it = new Intent(this, ShapesMatching.class);
            it.putExtra("title", getString(R.string.wild_animals));
            it.putExtra("key", "wa");
            startActivity(it);
        });
        binding.btNumber.setOnClickListener(v -> {
            Intent it = new Intent(this, NumbersMatching.class);
            it.putExtra("title", getString(R.string.numbers));
            it.putExtra("key", "nb");
            startActivity(it);
        });
        binding.btBack.setOnClickListener(v -> finish());
    }

    // setup Ui
    private void setupUI(){
        setupBt(binding.btColor);
        setupBt(binding.btShape);
        setupBt(binding.btFruits);
        setupBt(binding.btDa);
        setupBt(binding.btNumber);
        setupBt(binding.btWild);
    }

    // set layout for button
    private void setupBt(LinearLayoutCompat ll){
        int width = tool.getScreenSize().widthPixels;
        ll.setLayoutParams(new FrameLayout.LayoutParams((int) (width/2.5), (int) (width/2.5)));
    }
}