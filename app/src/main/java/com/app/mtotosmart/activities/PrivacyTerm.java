package com.app.mtotosmart.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.app.mtotosmart.BuildConfig;
import com.app.mtotosmart.R;
import com.app.mtotosmart.databinding.ActivityPrivacyTermBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PrivacyTerm extends AppCompatActivity {
    ActivityPrivacyTermBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrivacyTermBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent().getStringExtra("title").equals(getString(R.string.privacy_policy))){
//            binding.pdfView.fromAsset("privacy.pdf").load();
            try {
                binding.pdfView.initWithUri(readFromAssets("privacy.pdf"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
//            readFromAssets("privacy.pdf");
        }else {
            try {
                binding.pdfView.initWithUri(readFromAssets("terms.pdf"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
//            readFromAssets("terms.pdf");
        }
        binding.tvTitle.setText(getIntent().getStringExtra("title"));
        binding.btBack.setOnClickListener(v -> finish());
    }

    public Uri readFromAssets(String fileName) {
        AssetManager assetManager = getAssets();
        InputStream in;
        OutputStream out;
        File file = new File(getFilesDir(), fileName);
        try {
            in = assetManager.open(fileName);
            out = openFileOutput(file.getName(), Context.MODE_PRIVATE);
            copyFile(in, out);
            in.close();
            out.flush();
            out.close();
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
        Uri pdfFileURI;
        pdfFileURI = FileProvider.getUriForFile(this,
                BuildConfig.APPLICATION_ID + ".provider", file);
       return pdfFileURI;
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }
}