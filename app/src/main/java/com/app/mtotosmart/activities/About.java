package com.app.mtotosmart.activities;


import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.app.mtotosmart.BuildConfig;
import com.app.mtotosmart.R;
import com.app.mtotosmart.databinding.ActivityAboutBinding;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class About extends BaseActivity {
    ActivityAboutBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btBack.setOnClickListener(v -> finish());
        binding.tvVersion.setText(getString(R.string.version)+" "+ BuildConfig.VERSION_NAME);
        binding.rlPrivacy.setOnClickListener(v -> startActivity(new Intent(this, PrivacyTerm.class).putExtra("title", getString(R.string.privacy_policy))));
        binding.rlTerm.setOnClickListener(v -> startActivity(new Intent(this, PrivacyTerm.class).putExtra("title", getString(R.string.term_of_services))));
    }
}