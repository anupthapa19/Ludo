package com.ath.ludo;

import static com.ath.ludo.R.*;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private ImageButton profile_icon, settingsIcon, playButton;
    private ImageButton home_button;
    private boolean isSoundOn = true;
    private SoundManager soundManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        profile_icon= findViewById(R.id.profile_icon);
        settingsIcon= findViewById(R.id.settings_icon);
        playButton= findViewById(R.id.playButton);
        home_button= findViewById(R.id.home_button);

        soundManager = new SoundManager(this);
        soundManager.playBackgroundMusic();

        profile_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(HomeActivity.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.setting_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.sound_on) {
                            soundManager.setSoundEnabled(true);
                            Toast.makeText(HomeActivity.this, "Sound On", Toast.LENGTH_SHORT).show();
                            Log.d("MenuItemClick", "Sound On clicked");
                            return true;
                        } else if (item.getItemId() == R.id.sound_off) {
                            soundManager.setSoundEnabled(false);
                            Toast.makeText(HomeActivity.this, "Sound Off", Toast.LENGTH_SHORT).show();
                            Log.d("MenuItemClick", "Sound Off clicked");
                            return true;
                        }
                        return false;
                        }
                });
                popupMenu.show();
            }
        });
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PlayerSetupActivity.class);
                startActivity(intent);
            }
        });

    }
    protected void onDestroy() {
        super.onDestroy();
        soundManager.stopBackgroundMusic();
    }
}
