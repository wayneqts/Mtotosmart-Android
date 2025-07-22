package com.app.mtotosmart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.app.mtotosmart.R;
import com.app.mtotosmart.databinding.ActivityHomeBinding;

public class Home extends BaseActivity {
    ActivityHomeBinding binding;
    int doubleBackToExit = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imgAvatar.setOnClickListener(v -> startActivity(new Intent(this, EditProfile.class).putExtra("from", "home")));
        binding.btInfo.setOnClickListener(v -> startActivity(new Intent(this, About.class)));
        binding.btMatching.setOnClickListener(v -> startActivity(new Intent(this, MatchingItem.class)));
        binding.btSpell.setOnClickListener(v -> startActivity(new Intent(this, Spelling.class)));
        binding.btAlphabet.setOnClickListener(v -> startActivity(new Intent(this, ViewVideo.class).putExtra("title", getString(R.string.alphabets))));
        binding.btVerb.setOnClickListener(v -> startActivity(new Intent(this, ViewVideo.class).putExtra("title", getString(R.string.verbs))));
        binding.btColor.setOnClickListener(v -> startActivity(new Intent(this, Coloring.class)));
        binding.btBody.setOnClickListener(v -> startActivity(new Intent(this, ViewVideo.class).putExtra("title", getString(R.string.parts_of_the_body))));
        binding.btSentence.setOnClickListener(v -> startActivity(new Intent(this, SentenceMaker.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (pref.getProfile()!=null){
            if (pref.getProfile().isGudo()){
                binding.imgAvatar.setImageResource(R.mipmap.img_boy);
            }else{
                binding.imgAvatar.setImageResource(R.mipmap.img_girl);
            }
            if (pref.getProfile().isLowAge()){
                binding.btSpell.setVisibility(View.GONE);
            }else{
                binding.btSpell.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExit == 2) {
            finishAffinity();
            System.exit(0);
        } else {
            doubleBackToExit++;
            Toast.makeText(this, getString(R.string.pls_click_back_again_to_exit), Toast.LENGTH_SHORT).show();
        }
        new Handler().postDelayed(() -> doubleBackToExit = 1, 2000);
    }
}