package com.app.mtotosmart.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import com.app.mtotosmart.R;
import com.app.mtotosmart.databinding.ActivitySplashBinding;

public class Splash extends BaseActivity {
    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String path = "android.resource://" + getPackageName() + "/" + R.raw.mtotosmart;
        ExoPlayer player = new ExoPlayer.Builder(this).build();
        binding.videoView.setPlayer(player);
        MediaItem mediaItem = MediaItem.fromUri(path);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();
        player.addListener(new Player.Listener() {
            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                if (!isPlaying) {
                    if (pref.getProfile() != null) {
                        startActivity(new Intent(Splash.this, Home.class));
                    } else {
                        startActivity(new Intent(Splash.this, EditProfile.class).putExtra("from", "splash"));
                    }
                    finish();
                }
            }
        });
    }
}