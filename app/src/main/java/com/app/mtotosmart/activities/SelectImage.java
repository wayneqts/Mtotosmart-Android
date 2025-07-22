package com.app.mtotosmart.activities;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.app.mtotosmart.R;
import com.app.mtotosmart.adapter.ImageAdapter;
import com.app.mtotosmart.clickItem.OnClickItem;
import com.app.mtotosmart.common.Key;
import com.app.mtotosmart.databinding.ActivitySelectImageBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SelectImage extends BaseActivity implements OnClickItem {
    List<Integer> list;
    ActivitySelectImageBinding binding;
    ImageAdapter adapter;
    private final int SELECT_FILE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

        binding.btBack.setOnClickListener(v -> finish());
        binding.btSelect.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                            1);
                } else {
                    galleryIntent();
                }
            }else {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                } else {
                    galleryIntent();
                }
            }
        });
    }

    // init UI
    private void init(){
        list = new ArrayList<>();
        setupList();
        adapter = new ImageAdapter(list, this, tool, this);
        binding.rcv.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rcv.setAdapter(adapter);
        tool.rcvNoAnimator(binding.rcv);
    }

    // setup list
    private void setupList(){
        list = new ArrayList<>();
        list.add(R.drawable.color_1);
        list.add(R.drawable.color_3);
        list.add(R.drawable.color_5);
        list.add(R.drawable.color_6);
        list.add(R.drawable.color_7);
        list.add(R.drawable.color_8);
        list.add(R.drawable.color_9);
        list.add(R.drawable.color_10);
        list.add(R.drawable.color_11);
        list.add(R.drawable.color_12);
        list.add(R.drawable.color_13);
        list.add(R.drawable.color_14);
    }

    // select image from gallery
    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                galleryIntent();
            } else {
                Toast.makeText(this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data!=null) {
            if (requestCode == SELECT_FILE) {
                Uri uri = data.getData();
                try {
                    Key.PICTURE_SELECTED = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void clickItem() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}