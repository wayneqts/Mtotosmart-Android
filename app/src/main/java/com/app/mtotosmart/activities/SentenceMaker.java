package com.app.mtotosmart.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.app.mtotosmart.R;
import com.app.mtotosmart.databinding.ActivitySentenceMakerBinding;
import com.app.mtotosmart.helper.custom.ConnectionView;

import java.util.Arrays;

public class SentenceMaker extends BaseActivity {
    ActivitySentenceMakerBinding binding;
    private TextView selectedSubject, selectedVerb;
    private boolean isStepSubject = true, isStepVerb = false;
    MediaPlayer mp;

    private String[][] table1 = {
            {"He", "am", "cleaning the toilet"},
            {"She", "are", "cooking delicious food"},
            {"You", "is", "eating some food"},
            {"I", "", "washing some clothes"},
            {"", "", "watering the garden"},
            {"", "", "washing hands"}
    };
    private String[][] table2 = {
            {"He", "am", "reading a book"},
            {"She", "are", "writing"},
            {"We", "is", "riding a motorbike"},
            {"They", "", "singing a song"},
            {"", "", "running fast"},
            {"", "", "drinking some juice"}
    };

    String[] sentencesArr = {
            "He is cleaning the toilet",
            "He is cooking delicious food",
            "He is eating some food",
            "He is washing some clothes",
            "He is watering the garden",
            "He is washing hands",
            "She is cleaning the toilet",
            "She is cooking delicious food",
            "She eating some food",
            "She is washing some clothes",
            "She is watering the garden",
            "She is washing hands",
            "You are cleaning the toilet",
            "You are cooking delicious food",
            "You are eating some food",
            "You are washing some clothes",
            "You are watering the garden",
            "You are washing hands",
            "I am cooking delicious food",
            "I am eating some food",
            "I am washing some clothes",
            "I am watering the garden",
            "I am washing hands",
            "He is reading a book",
            "He is writing",
            "He is riding a motorbike",
            "He is singing a song",
            "He is running fast",
            "He is drinking some juice",
            "She is reading a book",
            "She is writing",
            "She is riding a motorbike",
            "She is singing a song",
            "She is running fast",
            "She is drinking some juice",
            "We are reading a book",
            "We are writing",
            "We are riding a motorbike",
            "We are singing a song",
            "We are running fast",
            "We are drinking some juice",
            "They are reading a book",
            "They are writing",
            "They are riding a motorbike",
            "They are singing a song",
            "They are running fast",
            "They are drinking some juice"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySentenceMakerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

        binding.btBack.setOnClickListener(v -> finish());
        binding.btReset.setOnClickListener(v -> {
            binding.btReset.setVisibility(View.GONE);
            binding.videoView.stopPlayback();
            binding.rlVideo.setVisibility(View.GONE);
            binding.connectionView.resetLines();
            isStepSubject = true;
            binding.sentenceText.setText("");
        });
        binding.btNext.setOnClickListener(v -> {
            binding.btReset.performClick();
            tool.disableBt(binding.btNext);
            tool.enableBt(binding.btPre);
            binding.tableLayout.removeAllViews();
            addTableHeader();
            buildTable(table2);
        });
        binding.btPre.setOnClickListener(v -> {
            binding.btReset.performClick();
            tool.disableBt(binding.btPre);
            tool.enableBt(binding.btNext);
            binding.tableLayout.removeAllViews();
            addTableHeader();
            buildTable(table1);
        });
    }

    // init UI
    private void init(){
        tool.disableBt(binding.btPre);
        addTableHeader();
        buildTable(table1);
    }

    private void addTableHeader() {
        TableRow headerRow = new TableRow(this);

        String[] headers = {"Subject", "Verb", "Predicate"};
        for (String header : headers) {
            TextView tv = new TextView(this);
            tv.setText(header);
            tv.setTypeface(Typeface.DEFAULT_BOLD);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(Color.BLACK);
            tv.setBackgroundResource(R.drawable.bg_item);
            tv.setPadding(30, 15, 30, 15);
            float weight;
            if (header.equals("Subject") || header.equals("Verb")) {
                weight = 1.2f; // Subject or Verb
            } else {
                weight = 2.6f; // Predicate
            }

            TableRow.LayoutParams params = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, weight);
            tv.setLayoutParams(params);
            headerRow.addView(tv);
        }

        binding.tableLayout.addView(headerRow);
    }

    private void buildTable(String[][] table) {
        for (String[] row : table) {
            TableRow tableRow = new TableRow(this);

            for (int i = 0; i < row.length; i++) {
                TextView tv = new TextView(this);
                tv.setText(row[i]);
                tv.setPadding(5, 30, 5, 30);
                tv.setBackgroundResource(R.drawable.bg_item);
                tv.setGravity(Gravity.START);
                tv.setTextColor(Color.BLACK);
                float weight;
                if (i == 0 || i == 1) {
                    weight = 1.2f; // Subject or Verb
                } else {
                    weight = 2.6f; // Predicate
                }

                TableRow.LayoutParams params = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, weight);
                tv.setLayoutParams(params);

                setTouchLogic(tv, i);
                tableRow.addView(tv);
            }

            binding.tableLayout.addView(tableRow);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchLogic(TextView tv, int column) {
        tv.setOnTouchListener((v, event) -> {
            int[] location = new int[2];
            v.getLocationOnScreen(location);
            float centerX = location[0] + v.getWidth() / 2f;
            float centerY = location[1] + v.getHeight() / 2f;

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    TextView touchedView = (TextView) v;
                    String text = touchedView.getText().toString().trim();
                    if (text.isEmpty()) return false;

                    if (column == 0 && isStepSubject) {
                        selectedSubject = touchedView;
                        binding.connectionView.startLine(centerX, centerY);
                        return true;
                    } else if (column == 1 && isStepVerb) {
                        selectedVerb = touchedView;
                        binding.connectionView.startLine(centerX, centerY);
                        return true;
                    }
                    return false;

                case MotionEvent.ACTION_MOVE:
                    binding.connectionView.updateLine(event.getRawX(), event.getRawY());
                    return true;

                case MotionEvent.ACTION_UP:
                    View target = findTouchedView(event.getRawX(), event.getRawY(), column + 1);
                    if (target instanceof TextView  && !((TextView) target).getText().toString().trim().isEmpty()) {
                        int[] targetLoc = new int[2];
                        target.getLocationOnScreen(targetLoc);
                        float tx = targetLoc[0] + target.getWidth() / 2f;
                        float ty = targetLoc[1] + target.getHeight() / 2f;

                        if (column == 0 && isStepSubject) {
                            isStepSubject = false;
                            isStepVerb = true;
                            binding.btReset.setVisibility(View.VISIBLE);
                            binding.connectionView.endLine(tx, ty, true);
                        } else if (column == 1 && isStepVerb) {
                            isStepVerb = false;
                            binding.connectionView.endLine(tx, ty, true);

                            String sentence = selectedSubject.getText() + " " + selectedVerb.getText() + " " + ((TextView) target).getText();
                            boolean exists = Arrays.asList(sentencesArr).contains(sentence);

                            if (exists) {
                                mp = MediaPlayer.create(this, R.raw.success);
                                binding.sentenceText.setText(sentence+" ✅");
                                Log.e("TAG", "setTouchLogic: "+((TextView) target).getText().toString().replace(" ", "_") );
                                if (selectedSubject.getText().toString().equals("He")){
                                    loadVd(getResources().getIdentifier(((TextView) target).getText().toString().replace(" ", "_")+"_b", "raw", getPackageName()));
                                }else if (selectedSubject.getText().toString().equals("She")){
                                    loadVd(getResources().getIdentifier(((TextView) target).getText().toString().replace(" ", "_")+"_g", "raw", getPackageName()));
                                }else if (selectedSubject.getText().toString().equals("You") || selectedSubject.getText().toString().equals("I")){
                                    if (pref.getProfile().isMale()){
                                        loadVd(getResources().getIdentifier(((TextView) target).getText().toString().replace(" ", "_")+"_b", "raw", getPackageName()));
                                    }else {
                                        loadVd(getResources().getIdentifier(((TextView) target).getText().toString().replace(" ", "_")+"_g", "raw", getPackageName()));
                                    }
                                }else {
                                    loadVd(getResources().getIdentifier(((TextView) target).getText().toString().replace(" ", "_")+"_2", "raw", getPackageName()));
                                }
                            } else {
                                mp = MediaPlayer.create(this, R.raw.error);
                                binding.sentenceText.setText(sentence+" ❌");
                                new Handler().postDelayed(() -> binding.btReset.performClick(), 1000);
                            }
                            mp.start();
                            new Handler().postDelayed(() -> mp.release(), 500);
                        }
                    } else {
                        binding.connectionView.endLine(event.getRawX(), event.getRawY(), false);
                    }
                    return true;
            }
            return false;
        });
    }

    private View findTouchedView(float x, float y, int column) {
        for (int i = 0; i < binding.tableLayout.getChildCount(); i++) {
            TableRow row = (TableRow) binding.tableLayout.getChildAt(i);
            View cell = row.getChildAt(column);
            if (cell != null) {
                int[] loc = new int[2];
                cell.getLocationOnScreen(loc);
                Rect rect = new Rect(loc[0], loc[1], loc[0] + cell.getWidth(), loc[1] + cell.getHeight());
                if (rect.contains((int) x, (int) y)) {
                    return cell;
                }
            }
        }
        return null;
    }

    // load video
    private void loadVd(int src){
        Log.e("TAG", "loadVd: "+src );
        binding.rlVideo.setVisibility(View.VISIBLE);
        binding.videoView.setOnPreparedListener(mp -> mp.setLooping(true));
        binding.videoView.setVideoPath("android.resource://"+getPackageName()+"/"+src);
        binding.videoView.start();
    }
}