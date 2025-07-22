package com.app.mtotosmart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.app.mtotosmart.R;
import com.app.mtotosmart.databinding.ActivityEditProfileBinding;
import com.app.mtotosmart.model.Profile;

public class EditProfile extends BaseActivity {
    ActivityEditProfileBinding binding;
    String from;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupUI();

        binding.btSave.setOnClickListener(v -> {
            String name = binding.edtName.getText().toString().trim();
            if (TextUtils.isEmpty(name)){
                tool.showToast(getString(R.string.your_name_required));
            }else{
                Profile pf = new Profile(binding.rbGudo.isChecked(), binding.rbLow.isChecked(),
                        binding.rbMale.isChecked(), name);
                pref.setProfile(pf);
                if (from.equals("home")){
                    finish();
                }else{
                    startActivity(new Intent(this, Home.class));
                    finish();
                }
            }
        });
        binding.btBack.setOnClickListener(v -> finish());
    }

    // setup UI
    private void setupUI(){
        from = getIntent().getStringExtra("from");
        if (from.equals("splash")){
            binding.btBack.setVisibility(View.GONE);
        }
        if (pref.getProfile()!=null){
            Profile pf = pref.getProfile();
            if (pf.isGudo()){
                binding.rbGudo.setChecked(true);
            }else{
                binding.rbRoza.setChecked(true);
            }
            if (pf.isLowAge()){
                binding.rbLow.setChecked(true);
            }else{
                binding.rbHigh.setChecked(true);
            }
            binding.edtName.setText(pf.getName());
            if (pf.isMale()){
                binding.rbMale.setChecked(true);
            }else{
                binding.rbFemale.setChecked(true);
            }
        }
    }
}