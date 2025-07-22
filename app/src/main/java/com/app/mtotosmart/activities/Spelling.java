package com.app.mtotosmart.activities;

import android.app.Dialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.mtotosmart.R;
import com.app.mtotosmart.databinding.ActivitySpellingBinding;
import com.app.mtotosmart.databinding.PopupAlertBinding;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Spelling extends BaseActivity {
    ActivitySpellingBinding binding;
    List<String> stList = new ArrayList<>(), rdList = new ArrayList<>();
    int round = 1, lastPos;
    boolean isOnClick = false;
    boolean enableBt = true;
    MediaPlayer mp;
    int rd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpellingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btBack.setOnClickListener(v -> finish());
        binding.btBackspace.setOnClickListener(v -> {
            if (isOnClick){
                if (lastPos == 1){
                    binding.tv1.setText("");
                    isOnClick = false;
                }else{
                    binding.tv2.setText("");
                    isOnClick = false;
                    if (!binding.tv1.getText().toString().equals("")){
                        lastPos--;
                        isOnClick = true;
                    }
                }
                enableBt = true;
            }
        });
        // keyboard click
        binding.btA.setOnClickListener(v -> {
            if (enableBt){
                enableBt = false;
                btClick(binding.tvA.getText().toString());
            }
        });
        binding.btB.setOnClickListener(v -> {
            if (enableBt){
                enableBt = false;
                btClick(binding.tvB.getText().toString());
            }
        });
        binding.btC.setOnClickListener(v -> {
            if (enableBt){
                enableBt = false;
                btClick(binding.tvC.getText().toString());
            }
        });
        binding.btD.setOnClickListener(v -> {
            if (enableBt){
                enableBt = false;
                btClick(binding.tvD.getText().toString());
            }
        });
        binding.btE.setOnClickListener(v -> {
            if (enableBt){
                enableBt = false;
                btClick(binding.tvE.getText().toString());
            }
        });
        binding.btF.setOnClickListener(v -> {
            if (enableBt){
                enableBt = false;
                btClick(binding.tvF.getText().toString());
            }
        });
        binding.btG.setOnClickListener(v -> {
            if (enableBt){
                enableBt = false;
                btClick(binding.tvG.getText().toString());
            }
        });
        binding.btH.setOnClickListener(v -> {
            if (enableBt){
                enableBt = false;
                btClick(binding.tvH.getText().toString());
            }
        });
        binding.btI.setOnClickListener(v -> {
            if (enableBt){
                enableBt = false;
                btClick(binding.tvI.getText().toString());
            }
        });
        binding.btJ.setOnClickListener(v -> {
            if (enableBt){
                enableBt = false;
                btClick(binding.tvJ.getText().toString());
            }
        });
        binding.btK.setOnClickListener(v -> {
            if (enableBt){
                enableBt = false;
                btClick(binding.tvK.getText().toString());
            }
        });
        binding.btL.setOnClickListener(v -> {
            if (enableBt){
                enableBt = false;
                btClick(binding.tvL.getText().toString());
            }
        });
        binding.btM.setOnClickListener(v -> {
            if (enableBt){
                enableBt = false;
                btClick(binding.tvM.getText().toString());
            }
        });
        binding.btN.setOnClickListener(v -> {
            if (enableBt){
                enableBt = false;
                btClick(binding.tvN.getText().toString());
            }
        });
        binding.btO.setOnClickListener(v -> {
            if (enableBt){
                enableBt = false;
                btClick(binding.tvO.getText().toString());
            }
        });
        binding.btP.setOnClickListener(v -> {
            if (enableBt){
                enableBt = false;
                btClick(binding.tvP.getText().toString());
            }
        });
        binding.btQ.setOnClickListener(v -> {
            if (enableBt){
                enableBt = false;
                btClick(binding.tvQ.getText().toString());
            }
        });
        binding.btR.setOnClickListener(v -> {
            if (enableBt){
                enableBt = false;
                btClick(binding.tvR.getText().toString());
            }
        });
        binding.btS.setOnClickListener(v -> {
            if (enableBt){
                enableBt = false;
                btClick(binding.tvS.getText().toString());
            }
        });
        binding.btT.setOnClickListener(v -> {
            if (enableBt){
                enableBt = false;
                btClick(binding.tvT.getText().toString());
            }
        });
        binding.btU.setOnClickListener(v -> {
            if (enableBt){
                enableBt = false;
                btClick(binding.tvU.getText().toString());
            }
        });
        binding.btV.setOnClickListener(v -> {
            if (enableBt){
                enableBt = false;
                btClick(binding.tvV.getText().toString());
            }
        });
        binding.btW.setOnClickListener(v -> {
            if (enableBt){
                enableBt = false;
                btClick(binding.tvW.getText().toString());
            }
        });
        binding.btX.setOnClickListener(v -> {
            if (enableBt){
                enableBt = false;
                btClick(binding.tvX.getText().toString());
            }
        });
        binding.btY.setOnClickListener(v -> {
            if (enableBt){
                enableBt = false;
                btClick(binding.tvY.getText().toString());
            }
        });
        binding.btZ.setOnClickListener(v -> {
            if (enableBt){
                enableBt = false;
                btClick(binding.tvZ.getText().toString());
            }
        });
        binding.btSkip.setOnClickListener(v -> {
            if (enableBt) {
                enableBt = false;
                skipRound();
            }
        });
        setupData();
    }

    // setup data
    private void setupData(){
        stList.add("ant");
        stList.add("bag");
        stList.add("bed");
        stList.add("box");
        stList.add("bus");
        stList.add("cap");
        stList.add("cat");
        stList.add("cup");
        stList.add("dog");
        stList.add("egg");
        stList.add("fan");
        stList.add("fox");
        stList.add("hat");
        stList.add("hen");
        stList.add("log");
        stList.add("mop");
        stList.add("mug");
        stList.add("owl");
        stList.add("pen");
        stList.add("pig");
        stList.add("pin");
        stList.add("rat");
        stList.add("tap");
        stList.add("toy");
        stList.add("van");
        stList.add("wig");
        new Handler().postDelayed(() -> {
            for (int i = 1;i<=25;i++){
                int rdPos = new Random().nextInt(stList.size());
                rdList.add(stList.get(rdPos));
                stList.remove(rdPos);
            }
        }, 100);
        new Handler().postDelayed(this::loadData, 200);
    }

    // load data
    private void loadData(){
        Glide.with(this).load(tool.getImage(rdList.get(round-1))).fitCenter().into(binding.iv);
        rd = new Random().nextInt(3)+1;
        if (round < 9){
            if (rd == 1){
                binding.tv2.setText(rdList.get(round-1).substring(1, 2));
                binding.tv3.setText(rdList.get(round-1).substring(2, 3));
            }else if (rd == 2){
                binding.tv1.setText(rdList.get(round-1).substring(0, 1));
                binding.tv3.setText(rdList.get(round-1).substring(2, 3));
            }else{
                binding.tv1.setText(rdList.get(round-1).substring(0, 1));
                binding.tv2.setText(rdList.get(round-1).substring(1, 2));
            }
        }else if (round < 17){
            if (rd == 1){
                binding.tv1.setText(rdList.get(round-1).substring(0, 1));
            }else if (rd == 2){
                binding.tv2.setText(rdList.get(round-1).substring(1, 2));
            }else{
                binding.tv3.setText(rdList.get(round-1).substring(2, 3));
            }
        }
        binding.tvRound.setText(round+"/25");
    }

    // button click
    private void btClick(String t){
        String t1 = binding.tv1.getText().toString();
        String t2 = binding.tv2.getText().toString();
        String t3 = binding.tv3.getText().toString();
        if (t1.equals("")){
            lastPos = 1;
            binding.tv1.setText(t);
        }else if (t2.equals("")){
            lastPos = 2;
            binding.tv2.setText(t);
        }else if (t3.equals("")){
            binding.tv3.setText(t);
        }

        checkAnswer();
    }

    // check answer
    private void checkAnswer(){
        String t1 = binding.tv1.getText().toString();
        String t2 = binding.tv2.getText().toString();
        String t3 = binding.tv3.getText().toString();
        if (round>8){
            if (!t1.equals("") && !t2.equals("") && !t3.equals("")){
                if (t1.equals(rdList.get(round-1).substring(0, 1)) && t2.equals(rdList.get(round-1).substring(1, 2))
                        && t3.equals(rdList.get(round-1).substring(2, 3))){
                    binding.lotie.playAnimation();
                    binding.lotie.setVisibility(View.VISIBLE);
                    mp = MediaPlayer.create(this, R.raw.success);
                    new Handler().postDelayed(() -> {
                        binding.lotie.setVisibility(View.GONE);
                        if (round == 25){
                            popupSuccess();
                        }else{
                            resetRound();
                        }
                        enableBt = true;
                        mp.release();
                    }, 1000);
                }else{
                    binding.tv1.setTextColor(getColor(R.color.red));
                    binding.tv2.setTextColor(getColor(R.color.red));
                    binding.tv3.setTextColor(getColor(R.color.red));
                    mp = MediaPlayer.create(this, R.raw.error);
                    new Handler().postDelayed(() -> {
                        if (round < 17){
                            if (rd == 1){
                                binding.tv2.setText("");
                                binding.tv3.setText("");
                            }else if (rd == 2){
                                binding.tv1.setText("");
                                binding.tv3.setText("");
                            }else{
                                binding.tv2.setText("");
                                binding.tv1.setText("");
                            }
                        }else{
                            binding.tv1.setText("");
                            binding.tv2.setText("");
                            binding.tv3.setText("");
                        }
                        binding.tv1.setTextColor(getColor(R.color.black));
                        binding.tv2.setTextColor(getColor(R.color.black));
                        binding.tv3.setTextColor(getColor(R.color.black));
                        enableBt = true;
                        mp.release();
                    }, 500);
                }
                mp.start();
                isOnClick = false;
            }else{
                isOnClick = true;
                enableBt = true;
            }
        }else{
            if (t1.equals(rdList.get(round-1).substring(0, 1)) && t2.equals(rdList.get(round-1).substring(1, 2))
                    && t3.equals(rdList.get(round-1).substring(2, 3))){
                binding.lotie.playAnimation();
                binding.lotie.setVisibility(View.VISIBLE);
                mp = MediaPlayer.create(this, R.raw.success);
                new Handler().postDelayed(() -> {
                    binding.lotie.setVisibility(View.GONE);
                    if (round == 25){
                        popupSuccess();
                    }else{
                        resetRound();
                    }
                    enableBt = true;
                    mp.release();
                }, 1000);
            }else{
                binding.tv1.setTextColor(getColor(R.color.red));
                binding.tv2.setTextColor(getColor(R.color.red));
                binding.tv3.setTextColor(getColor(R.color.red));
                mp = MediaPlayer.create(this, R.raw.error);
                new Handler().postDelayed(() -> {
                    if (rd == 1){
                        binding.tv1.setText("");
                    }else if (rd == 2){
                        binding.tv2.setText("");
                    }else{
                        binding.tv3.setText("");
                    }
                    binding.tv1.setTextColor(getColor(R.color.black));
                    binding.tv2.setTextColor(getColor(R.color.black));
                    binding.tv3.setTextColor(getColor(R.color.black));
                    enableBt = true;
                    mp.release();
                }, 500);
            }
            mp.start();
        }
    }

    // reset round
    private void resetRound(){
        binding.tv1.setTextColor(getColor(R.color.black));
        binding.tv2.setTextColor(getColor(R.color.black));
        binding.tv3.setTextColor(getColor(R.color.black));
        binding.tv1.setText("");
        binding.tv2.setText("");
        binding.tv3.setText("");
        round++;
        loadData();
    }

    // skip round
    private void skipRound(){
        binding.tv1.setText(rdList.get(round-1).substring(0, 1));
        binding.tv2.setText(rdList.get(round-1).substring(1, 2));
        binding.tv3.setText(rdList.get(round-1).substring(2, 3));
        binding.tv1.setTextColor(getColor(R.color.blue));
        binding.tv2.setTextColor(getColor(R.color.blue));
        binding.tv3.setTextColor(getColor(R.color.blue));
        new Handler().postDelayed(() -> {
            resetRound();
            enableBt = true;
        }, 1000);
    }

    // popup success
    private void popupSuccess(){
        Dialog dialog = new Dialog(this);
        PopupAlertBinding alertBinding = PopupAlertBinding.inflate(getLayoutInflater());
        dialog.setContentView(alertBinding.getRoot());
        tool.setupDialog(dialog);
        alertBinding.btOk.setOnClickListener(v -> {
            finish();
            dialog.dismiss();
        });
        dialog.show();
    }
}