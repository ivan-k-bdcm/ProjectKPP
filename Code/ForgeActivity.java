package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class ForgeActivity extends AppCompatActivity {


    private long backPressedTime;
    private Toast backToast;
    private View decorView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forge);


        ImageButton forgeBackToMap = (ImageButton) findViewById(R.id.forgeBackToMap);
        forgeBackToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(ForgeActivity.this, MapActivity.class);
                    startActivity(intent); finish();
                } catch (Exception e){
                    //Exception
                }
            }
        });


        // Implementation for hiding the bar
        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0) {
                    decorView.setSystemUiVisibility(hideSystemBar());
                }
            }
        });

    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if(hasFocus) {
            decorView.setSystemUiVisibility(hideSystemBar());

        }
    }


    // Parameter for bar hiding method
    private int hideSystemBar() {
        return    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }


    // Double tap for exit
    @Override
    public void onBackPressed() {
        Intent intentMap = new Intent(ForgeActivity.this, MapActivity.class);
        startActivity(intentMap); finish();
    }
}