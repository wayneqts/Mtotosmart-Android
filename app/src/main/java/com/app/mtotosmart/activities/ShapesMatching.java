package com.app.mtotosmart.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.app.mtotosmart.R;
import com.app.mtotosmart.databinding.ActivityMatchingDetailBinding;
import com.app.mtotosmart.helper.custom.DrawLine;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShapesMatching extends BaseActivity{
    ActivityMatchingDetailBinding binding;
    List<String> leftList = new ArrayList<>(), rightList = new ArrayList<>(), rdLeftList = new ArrayList<>(), rdRightList = new ArrayList<>();
    DrawLine drawLine;
    ImageView lastImg, firstImg, firstSelected, secondSelected;
    String firstStr, lastStr, stItem, endItem;
    boolean isDraw = false, isSelected = false, isLowAge = true;
    float firstPos, secondPos;
    int countItem = 0, size;
    MediaPlayer mp;
    String key;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMatchingDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

        binding.draw.setOnTouchListener((view, event) -> {
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (!isDraw) {
                for (int i = 0; i < binding.touchView.getChildCount(); i++) {
                    View current = binding.touchView.getChildAt(i);
                    if (current instanceof ImageView) {
                        if (current.isShown()){
                            ImageView b = (ImageView) current;
                            if (b.getTransitionName()!=null){
                                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                    if (tool.isPointWithin(x, y, b.getLeft(), b.getRight(), b.getTop(), b.getBottom())) {
                                        firstImg = b;
                                        Rect rectf = new Rect();
                                        b.getGlobalVisibleRect(rectf);
                                        firstStr = b.getTransitionName();
                                        firstImg.setForeground(getDrawable(R.drawable.bg_correct));
                                        drawLine.startX = rectf.centerX();
                                        drawLine.startY = rectf.centerY() - (rectf.width() / 4);
                                        drawLine.mPaint.setColor(Color.GREEN);
                                    }
                                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                                    if (tool.isPointWithin(x, y, b.getLeft(), b.getRight(), b.getTop(), b.getBottom())) {
                                        if (firstImg != null) {
                                            lastImg = b;
                                            lastStr = lastImg.getTransitionName();
                                            if (lastImg!=firstImg){
                                                isDraw = true;
                                                if (firstStr.endsWith("l") && lastStr.endsWith("r")) {
                                                    if (!isLowAge){
                                                        firstStr = firstStr.substring(0, firstStr.length()-1);
                                                    }
                                                    if (firstStr.substring(0, firstStr.length()-1).equals(lastStr.substring(0, lastStr.length()-1))) {
                                                        lastImg.setForeground(getDrawable(R.drawable.bg_correct));
                                                        mp = MediaPlayer.create(this, R.raw.success);
                                                        binding.lotie.setVisibility(View.VISIBLE);
                                                        binding.lotie.playAnimation();
                                                        new Handler().postDelayed(() -> {
                                                            countItem++;
                                                            if (countItem == 6){
                                                                loadData();
                                                                countItem = 0;
                                                            }
                                                            binding.lotie.setVisibility(View.GONE);
                                                            firstImg.setVisibility(View.INVISIBLE);
                                                            lastImg.setVisibility(View.INVISIBLE);
                                                            firstImg.setForeground(null);
                                                            lastImg.setForeground(null);
                                                            resetDraw();
                                                            mp.release();
                                                        }, 500);
                                                    } else {
                                                        firstImg.setForeground(getDrawable(R.drawable.bg_wrong));
                                                        lastImg.setForeground(getDrawable(R.drawable.bg_wrong));
                                                        drawLine.mPaint.setColor(Color.RED);
                                                        mp = MediaPlayer.create(this, R.raw.error);
                                                        new Handler().postDelayed(() -> {
                                                            firstImg.setForeground(null);
                                                            lastImg.setForeground(null);
                                                            resetDraw();
                                                            mp.release();
                                                        }, 500);
                                                    }
                                                    mp.start();
                                                    Rect rectf = new Rect();
                                                    lastImg.getGlobalVisibleRect(rectf);
                                                    drawLine.endX = rectf.centerX();
                                                    drawLine.endY = rectf.centerY() - (rectf.width() / 4);
                                                }else if (firstStr.endsWith("r") && lastStr.endsWith("l")) {
                                                    if (!isLowAge){
                                                        lastStr = lastStr.substring(0, lastStr.length()-1);
                                                    }
                                                    if (firstStr.substring(0, firstStr.length()-1).equals(lastStr.substring(0, lastStr.length()-1))) {
                                                        lastImg.setForeground(getDrawable(R.drawable.bg_correct));
                                                        mp = MediaPlayer.create(this, R.raw.success);
                                                        binding.lotie.setVisibility(View.VISIBLE);
                                                        binding.lotie.playAnimation();
                                                        new Handler().postDelayed(() -> {
                                                            countItem++;
                                                            if (countItem == 6){
                                                                loadData();
                                                                countItem = 0;
                                                            }
                                                            binding.lotie.setVisibility(View.GONE);
                                                            firstImg.setVisibility(View.INVISIBLE);
                                                            lastImg.setVisibility(View.INVISIBLE);
                                                            firstImg.setForeground(null);
                                                            lastImg.setForeground(null);
                                                            resetDraw();
                                                            mp.release();
                                                        }, 500);
                                                    } else {
                                                        firstImg.setForeground(getDrawable(R.drawable.bg_wrong));
                                                        lastImg.setForeground(getDrawable(R.drawable.bg_wrong));
                                                        drawLine.mPaint.setColor(Color.RED);
                                                        mp = MediaPlayer.create(this, R.raw.error);
                                                        new Handler().postDelayed(() -> {
                                                            firstImg.setForeground(null);
                                                            lastImg.setForeground(null);
                                                            resetDraw();
                                                            mp.release();
                                                        }, 500);
                                                    }
                                                    mp.start();
                                                    Rect rectf = new Rect();
                                                    lastImg.getGlobalVisibleRect(rectf);
                                                    drawLine.endX = rectf.centerX();
                                                    drawLine.endY = rectf.centerY() - (rectf.width() / 4);
                                                }else{
                                                    resetDraw();
                                                }
                                            }else{
                                                if (!isSelected){
                                                    firstSelected = b;
                                                    stItem = firstSelected.getTransitionName();
                                                    Rect rectf = new Rect();
                                                    firstSelected.getGlobalVisibleRect(rectf);
                                                    firstPos = rectf.centerX();
                                                    secondPos = rectf.centerY() - (rectf.width() / 4);
                                                    isSelected = true;
                                                    drawLine.mPaint.setColor(Color.TRANSPARENT);
                                                    firstSelected.setForeground(getDrawable(R.drawable.bg_correct));
                                                }else{
                                                    secondSelected = b;
                                                    endItem = secondSelected.getTransitionName();
                                                    if (stItem.endsWith("l") && endItem.endsWith("r")) {
                                                        if (!isLowAge){
                                                            stItem = stItem.substring(0, stItem.length()-1);
                                                        }
                                                        if (stItem.substring(0, stItem.length()-1).equals(endItem.substring(0, endItem.length()-1))) {
                                                            secondSelected.setForeground(getDrawable(R.drawable.bg_correct));
                                                            mp = MediaPlayer.create(this, R.raw.success);
                                                            binding.lotie.setVisibility(View.VISIBLE);
                                                            binding.lotie.playAnimation();
                                                            new Handler().postDelayed(() -> {
                                                                countItem++;
                                                                if (countItem == 6){
                                                                    loadData();
                                                                    countItem = 0;
                                                                }
                                                                binding.lotie.setVisibility(View.GONE);
                                                                firstSelected.setVisibility(View.INVISIBLE);
                                                                secondSelected.setVisibility(View.INVISIBLE);
                                                                firstSelected.setForeground(null);
                                                                secondSelected.setForeground(null);
                                                                resetDraw();
                                                                mp.release();
                                                            }, 500);
                                                        } else {
                                                            firstSelected.setForeground(getDrawable(R.drawable.bg_wrong));
                                                            secondSelected.setForeground(getDrawable(R.drawable.bg_wrong));
                                                            drawLine.mPaint.setColor(Color.RED);
                                                            mp = MediaPlayer.create(this, R.raw.error);
                                                            new Handler().postDelayed(() -> {
                                                                firstSelected.setForeground(null);
                                                                secondSelected.setForeground(null);
                                                                resetDraw();
                                                                mp.release();
                                                            }, 500);
                                                        }
                                                        mp.start();
                                                    }else if (stItem.endsWith("r") && endItem.endsWith("l")) {
                                                        if (!isLowAge){
                                                            endItem = endItem.substring(0, endItem.length()-1);
                                                        }
                                                        if (stItem.substring(0, stItem.length()-1).equals(endItem.substring(0, endItem.length()-1))) {
                                                            secondSelected.setForeground(getDrawable(R.drawable.bg_correct));
                                                            mp = MediaPlayer.create(this, R.raw.success);
                                                            binding.lotie.playAnimation();
                                                            binding.lotie.setVisibility(View.VISIBLE);
                                                            new Handler().postDelayed(() -> {
                                                                countItem++;
                                                                if (countItem == 6){
                                                                    loadData();
                                                                    countItem = 0;
                                                                }
                                                                binding.lotie.setVisibility(View.GONE);
                                                                firstSelected.setVisibility(View.INVISIBLE);
                                                                secondSelected.setVisibility(View.INVISIBLE);
                                                                firstSelected.setForeground(null);
                                                                secondSelected.setForeground(null);
                                                                resetDraw();
                                                                mp.release();
                                                            }, 500);
                                                        } else {
                                                            firstSelected.setForeground(getDrawable(R.drawable.bg_wrong));
                                                            secondSelected.setForeground(getDrawable(R.drawable.bg_wrong));
                                                            drawLine.mPaint.setColor(Color.RED);
                                                            mp = MediaPlayer.create(this, R.raw.error);
                                                            new Handler().postDelayed(() -> {
                                                                firstSelected.setForeground(null);
                                                                secondSelected.setForeground(null);
                                                                resetDraw();
                                                                mp.release();
                                                            }, 500);
                                                        }
                                                        mp.start();
                                                    }else{
                                                        resetDraw();
                                                        firstSelected.setForeground(null);
                                                    }
                                                    Rect rectf = new Rect();
                                                    secondSelected.getGlobalVisibleRect(rectf);
                                                    drawLine.startX = firstPos;
                                                    drawLine.startY = secondPos;
                                                    drawLine.endX = rectf.centerX();
                                                    drawLine.endY = rectf.centerY() - (rectf.width() / 4);
                                                    isSelected = false;
                                                }
                                            }
                                        } else {
                                            resetDraw();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (firstImg != null) {
                            drawLine.endX = event.getX();
                            drawLine.endY = event.getY();
                        }

                        // Set the end to prevent initial jump (like on the demo recording)
                        drawLine.invalidate();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        if (firstImg != null) {
                            drawLine.endX = event.getX();
                            drawLine.endY = event.getY();
                        }
                        return true;
                    case MotionEvent.ACTION_UP:
                        if (lastImg == null) {
                            resetDraw();
                        } else {
                            drawLine.invalidate();
                        }
                        return true;
                    default:
                        return false;
                }
            }
            return true;
        });
        binding.btBack.setOnClickListener(v -> finish());
    }

    // setup view
    private void init(){
        drawLine = new DrawLine(this);
        binding.draw.addView(drawLine);
        binding.tvTitle.setText(getIntent().getStringExtra("title"));
        key = getIntent().getStringExtra("key");
        if (pref.getProfile()!=null){
            isLowAge = pref.getProfile().isLowAge();
        }
        loadData();
    }

    // load data
    private void loadData(){
        leftList.clear();
        rightList.clear();
        rdLeftList.clear();
        rdRightList.clear();
        if (key.equals("wa") || key.equals("da")){
            size = 10;
        }else{
            size = 12;
        }
        for (int i = 1;i <= size;i++){
            if (isLowAge){
                leftList.add(key+i);
            }else{
                leftList.add(key+i+"t");
            }
            rightList.add(key+i);
        }
        new Handler().postDelayed(() -> {
            for (int i=0;i<6;i++){
                int rd = new Random().nextInt(leftList.size());
                rdLeftList.add(leftList.get(rd));
                rdRightList.add(rightList.get(rd));
                leftList.remove(rd);
                rightList.remove(rd);
            }
        }, 100);
        new Handler().postDelayed(() -> {
            setupColLeft();
            setupColRight();
        }, 200);
    }

    // setup column left
    private void setupColLeft(){
        int rd1 = new Random().nextInt(rdLeftList.size());
        binding.btLeft1.setTransitionName(rdLeftList.get(rd1)+"l");
        binding.btLeft1.setVisibility(View.VISIBLE);
        Glide.with(this).load(tool.getImage(rdLeftList.get(rd1))).centerCrop().into(binding.btLeft1);
        rdLeftList.remove(rd1);
        int rd2 = new Random().nextInt(rdLeftList.size());
        binding.btLeft2.setTransitionName(rdLeftList.get(rd2)+"l");
        binding.btLeft2.setVisibility(View.VISIBLE);
        Glide.with(this).load(tool.getImage(rdLeftList.get(rd2))).centerCrop().into(binding.btLeft2);
        rdLeftList.remove(rd2);
        int rd3 = new Random().nextInt(rdLeftList.size());
        binding.btLeft3.setTransitionName(rdLeftList.get(rd3)+"l");
        binding.btLeft3.setVisibility(View.VISIBLE);
        Glide.with(this).load(tool.getImage(rdLeftList.get(rd3))).centerCrop().into(binding.btLeft3);
        rdLeftList.remove(rd3);
        int rd4 = new Random().nextInt(rdLeftList.size());
        binding.btLeft4.setTransitionName(rdLeftList.get(rd4)+"l");
        binding.btLeft4.setVisibility(View.VISIBLE);
        Glide.with(this).load(tool.getImage(rdLeftList.get(rd4))).centerCrop().into(binding.btLeft4);
        rdLeftList.remove(rd4);
        int rd5 = new Random().nextInt(rdLeftList.size());
        binding.btLeft5.setTransitionName(rdLeftList.get(rd5)+"l");
        binding.btLeft5.setVisibility(View.VISIBLE);
        Glide.with(this).load(tool.getImage(rdLeftList.get(rd5))).centerCrop().into(binding.btLeft5);
        rdLeftList.remove(rd5);
        int rd6 = new Random().nextInt(rdLeftList.size());
        binding.btLeft6.setTransitionName(rdLeftList.get(rd6)+"l");
        binding.btLeft6.setVisibility(View.VISIBLE);
        Glide.with(this).load(tool.getImage(rdLeftList.get(rd6))).centerCrop().into(binding.btLeft6);
        rdLeftList.remove(rd6);
    }

    // setup column right
    private void setupColRight(){
        int rd1 = new Random().nextInt(rdRightList.size());
        binding.btRight1.setTransitionName(rdRightList.get(rd1)+"r");
        binding.btRight1.setVisibility(View.VISIBLE);
        Glide.with(this).load(tool.getImage(rdRightList.get(rd1))).centerCrop().into(binding.btRight1);
        rdRightList.remove(rd1);
        int rd2 = new Random().nextInt(rdRightList.size());
        binding.btRight2.setTransitionName(rdRightList.get(rd2)+"r");
        binding.btRight2.setVisibility(View.VISIBLE);
        Glide.with(this).load(tool.getImage(rdRightList.get(rd2))).centerCrop().into(binding.btRight2);
        rdRightList.remove(rd2);
        int rd3 = new Random().nextInt(rdRightList.size());
        binding.btRight3.setTransitionName(rdRightList.get(rd3)+"r");
        binding.btRight3.setVisibility(View.VISIBLE);
        Glide.with(this).load(tool.getImage(rdRightList.get(rd3))).centerCrop().into(binding.btRight3);
        rdRightList.remove(rd3);
        int rd4 = new Random().nextInt(rdRightList.size());
        binding.btRight4.setTransitionName(rdRightList.get(rd4)+"r");
        binding.btRight4.setVisibility(View.VISIBLE);
        Glide.with(this).load(tool.getImage(rdRightList.get(rd4))).centerCrop().into(binding.btRight4);
        rdRightList.remove(rd4);
        int rd5 = new Random().nextInt(rdRightList.size());
        binding.btRight5.setTransitionName(rdRightList.get(rd5)+"r");
        binding.btRight5.setVisibility(View.VISIBLE);
        Glide.with(this).load(tool.getImage(rdRightList.get(rd5))).centerCrop().into(binding.btRight5);
        rdRightList.remove(rd5);
        int rd6 = new Random().nextInt(rdRightList.size());
        binding.btRight6.setTransitionName(rdRightList.get(rd6)+"r");
        binding.btRight6.setVisibility(View.VISIBLE);
        Glide.with(this).load(tool.getImage(rdRightList.get(rd6))).centerCrop().into(binding.btRight6);
        rdRightList.remove(rd6);
    }

    // reset line
    private void resetDraw() {
        drawLine.startX = 0;
        drawLine.startY = 0;
        drawLine.endX = 0;
        drawLine.endY = 0;
        if (firstImg!=null){
            firstImg.setForeground(null);
        }
        drawLine.invalidate();
        isDraw = false;
        firstImg = null;
        lastImg = null;
        firstStr = "";
        lastStr = "";
    }
}
