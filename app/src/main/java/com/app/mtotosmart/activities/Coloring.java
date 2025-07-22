package com.app.mtotosmart.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.mtotosmart.R;
import com.app.mtotosmart.adapter.ColorPickerAdapter;
import com.app.mtotosmart.clickItem.OnClickItem;
import com.app.mtotosmart.common.Key;
import com.app.mtotosmart.databinding.ActivityColoringBinding;
import com.app.mtotosmart.model.ColorPicker;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Coloring extends BaseActivity implements OnClickItem {
    ActivityColoringBinding binding;
    List<ColorPicker> list;
    ColorPickerAdapter adapter;
    MyView myView;
    Paint paint;
    Bitmap mBitmap, defaultBitmap = null;
    public int imageWidth;
    public int imageHeight;
    float mScaleFactor = 1.f;
    int crPos = -1;
    List<Bitmap> undoList, redoList;
    Rect rect;
    ArrayList<Point> pts = new ArrayList<>();
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityColoringBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

        binding.btBack.setOnClickListener(v -> finish());
        binding.btSelect.setOnClickListener(v -> startActivityForResult(new Intent(this, SelectImage.class), 123));
        binding.btUndo.setOnClickListener(v -> {
            if (v.getVisibility() == View.VISIBLE){
                myView.undoAction();
            }
        });
        binding.btShare.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }else {
                shareImg();
            }
        });
//        binding.btRedo.setOnClickListener(v -> {
//            if (v.getVisibility() == View.VISIBLE){
//                myView.redoAction();
//            }
//        });
    }

    // init UI
    private void init(){
        list = new ArrayList<>();
        undoList = new ArrayList<>();
        redoList = new ArrayList<>();
        setupList();
        adapter = new ColorPickerAdapter(this, list, tool, this);
        binding.rcvColor.setLayoutManager(new GridLayoutManager(this, 15));
        binding.rcvColor.setAdapter(adapter);
        tool.rcvNoAnimator(binding.rcvColor);
    }

    // share image
    private void shareImg(){
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), tool.loadBitmapFromView(myView), "coloring", null);
        Uri uri = Uri.parse(path);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Share Image"));
    }

    // setup list
    private void setupList(){
        list.add(new ColorPicker(R.drawable.p1, "#EFE640"));
        list.add(new ColorPicker(R.drawable.p2, "#FDAD00"));
        list.add(new ColorPicker(R.drawable.p3, "#14B409"));
        list.add(new ColorPicker(R.drawable.p4, "#00C91B"));
        list.add(new ColorPicker(R.drawable.p5, "#023C24"));
        list.add(new ColorPicker(R.drawable.p6, "#47AAF4"));
        list.add(new ColorPicker(R.drawable.p7, "#1F45FD"));
        list.add(new ColorPicker(R.drawable.p8, "#5D36CF"));
        list.add(new ColorPicker(R.drawable.p9, "#630156"));
        list.add(new ColorPicker(R.drawable.p10, "#F62815"));
        list.add(new ColorPicker(R.drawable.p11, "#FF6B22"));
        list.add(new ColorPicker(R.drawable.p12, "#AD4107"));
        list.add(new ColorPicker(R.drawable.p13, "#5E2C25"));
        list.add(new ColorPicker(R.drawable.p14, "#FF000000"));
        list.add(new ColorPicker(R.drawable.p15, "#7A8285"));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                shareImg();
            } else {
                Toast.makeText(this, "Permission denied to write your External storage", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data!=null){
            undoList.clear();
            redoList.clear();
            defaultBitmap = null;
            binding.btShare.setVisibility(View.VISIBLE);
            if (binding.paintView.getChildCount() > 0){
                binding.paintView.removeAllViews();
            }
            myView = new MyView(this);
            myView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            binding.paintView.addView(myView);
        }
    }

    @Override
    public void clickItem() {
        if (paint!=null){
            paint.setColor(Key.COLOR_SELECTED);
        }
    }

    public class MyView extends View {
        final Point p1 = new Point();
        Canvas canvas;

        public MyView(Context context) {
            super(context);
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
            paint.setDither(true);
            paint.setColor(Key.COLOR_SELECTED);
            paint.setAntiAlias(true);

        }
        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            mBitmap = Bitmap.createScaledBitmap(Key.PICTURE_SELECTED, w, h, false);
            if (defaultBitmap == null){
                defaultBitmap = Bitmap.createBitmap(mBitmap);
            }
        }

        @Override
        protected void onDraw(Canvas canvas1) {
            Log.e("onDraw", "==========Canvas===========");
            this.canvas = canvas1;
            canvas.drawBitmap(mBitmap, 0, 0, paint);
            binding.btUndo.setVisibility(undoList.size() > 0 ? VISIBLE:INVISIBLE);
//            binding.btRedo.setVisibility(redoList.size() > 0 ? VISIBLE:INVISIBLE);
            Log.d("TAG", "undoList: "+undoList.size());
            Log.d("TAG", "redoList: "+redoList.size());
        }

        @Override
        public boolean onTouchEvent(MotionEvent ev) {
            // Let the ScaleGestureDetector inspect all events.
            rect = canvas.getClipBounds();
            final int action = ev.getAction();
            switch (action & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN: {
                    Log.e("MotionEvent", "==========ACTION_DOWN===========");
                    final float x = ev.getX();
                    final float y = ev.getY();
                    Log.e("MotionEvent", "==========ACTION_DOWN===" + x + "====" + y);
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    Log.e("MotionEvent", "==========ACTION_UP===========");
                    setFocusable(false);
                    Log.e("ACTION_UP", "===============imageWidth==========" + imageWidth);
                    Log.e("ACTION_UP", "============imageHeight===========" + imageHeight);

                    float mx = ev.getX() / mScaleFactor + rect.left;
                    float my = ev.getY() / mScaleFactor + rect.top;
                    rect = canvas.getClipBounds();

                    Log.e("MotionEvent", "==========ACTION_UP===" + mx + "====" + my);

                    p1.x = (int) mx;
                    p1.y = (int) my;
                    if (mBitmap.getHeight() > my && mBitmap.getWidth() > mx) {
                        if (my >= 0 && mx >= 0) {
                            final int sourceColor = mBitmap.getPixel((int) mx, (int) my);
                            int targetColor = paint.getColor();
                            floodFill(mBitmap, p1, sourceColor, targetColor);
                            invalidate();
                        }
                    }
                    break;
                }
            }
            return true;
        }
        public void undoAction(){
            if (undoList.size() > 0){
                redoList.add(undoList.get(undoList.size()-1));
                if (undoList.size() > 1){
                    mBitmap = undoList.get(undoList.size()-2);
                }else {
                    mBitmap = Bitmap.createBitmap(defaultBitmap);
                }
                undoList.remove(undoList.get(undoList.size()-1));
                invalidate();
            }
        }

        public void redoAction(){
            if (redoList.size() > 0){
                undoList.add(redoList.get(redoList.size()-1));
                mBitmap = redoList.get(redoList.size()-1);
                redoList.remove(redoList.size()-1);
                invalidate();
            }
        }
    }

    public void floodFill(Bitmap bitmap, Point point, int i, int j) {
        int k = bitmap.getWidth();
        int l = bitmap.getHeight();
        pts.clear();
        if (i != j) {

            LinkedList linkedlist = new LinkedList();
            do {
                int i1 = point.x;
                int j1;
                for (j1 = point.y; i1 > 0 && !isBlack(bitmap.getPixel(i1 - 1, j1), j); i1--) {
                }
                boolean flag = false;
                boolean flag1 = false;
                while (i1 < k && !isBlack(bitmap.getPixel(i1, j1), j)) {//
                    bitmap.setPixel(i1, j1, j);
                    if (!flag && j1 > 0 && !isBlack(bitmap.getPixel(i1, j1 - 1), j)) {
                        linkedlist.add(new Point(i1, j1 - 1));
                        flag = true;
                    } else if (flag && j1 > 0 && isBlack(bitmap.getPixel(i1, j1 - 1), j)) {
                        flag = false;
                    }
                    if (!flag1 && j1 < l - 1 && !isBlack(bitmap.getPixel(i1, j1 + 1), j)) {

                        linkedlist.add(new Point(i1, j1 + 1));
                        flag1 = true;
                    } else if (flag1 && j1 < l - 1 && isBlack(bitmap.getPixel(i1, j1 + 1), j)) {
                        flag1 = false;
                    }
                    i1++;
                }
                point = (Point) linkedlist.poll();
                pts.add(point);
            } while (point != null);
            undoList.add(Bitmap.createBitmap(bitmap));
            redoList.clear();
        }
    }

    private boolean isBlack(int i, int j) {
        while (Color.red(i) == Color.green(i) && Color.green(i) == Color.blue(i) && Color.red(i) < 150 || i == j) {
            return true;
        }
        return false;
    }
}