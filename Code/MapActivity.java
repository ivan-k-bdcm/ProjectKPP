package com.example.firstapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


public class MapActivity extends AppCompatActivity {

    public final long endTimeEnergyTimer = 10000;
    public final long stepTimeEnergyTimer = 1000;


    private long backPressedTime;
    private Toast backToast;
    private View decorView;
    public int countEnergy = 10;
    public int minutes;
    public int seconds;
    private Handler customHandler = new Handler();
    long timeStartEnergyTimer = 0;
    long currentTimeEnergyTimer;



    // Array for energy
    final int[] energy = {
            R.id.battery1, R.id.battery2,R.id.battery3,R.id.battery4,R.id.battery5,R.id.battery6,R.id.battery7,R.id.battery8,R.id.battery9,R.id.battery10
    };


    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_main);


        TextView textCountEnergy = findViewById(R.id.countEnergy);
        TextView energyTimer = findViewById(R.id.energyTimer);
        ImageView energyTimerBar = findViewById(R.id.energyTimerBar);

        energyTimer.setVisibility(View.GONE);
        energyTimerBar.setVisibility(View.GONE);



        ImageButton dialogProfileSettings = (ImageButton) findViewById(R.id.AccountImage);

        // Change avatar
        dialogProfileSettings.setBackgroundResource(R.drawable.avatar_tester);
        // Dialog Window
        dialogProfileSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(MapActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.infoaccount_profile);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                ImageButton buttonBack = (ImageButton)dialog.findViewById(R.id.buttonBack);
                buttonBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });


        ImageButton buttonForgeTo = (ImageButton) findViewById(R.id.buttonForge);
        buttonForgeTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intentForge = new Intent(MapActivity.this, ForgeActivity.class);
                    startActivity(intentForge); finish();
                } catch (Exception e){
                    //Exception
                }
            }
        });


        ImageButton buttonShopTo = (ImageButton) findViewById(R.id.buttonShop);
        buttonShopTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intentShop = new Intent(MapActivity.this, ShopActivity.class);
                    startActivity(intentShop); finish();
                } catch (Exception e){
                    //Exception
                }
            }
        });


        ImageButton buttonInventoryTo = (ImageButton) findViewById(R.id.buttonInventory);
        buttonInventoryTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intentInventory = new Intent(MapActivity.this, InventoryActivity.class);
                    startActivity(intentInventory); finish();
                } catch (Exception e){
                    //Exception
                }
            }
        });


        // Not working
        ImageButton buttonEnergy = (ImageButton) findViewById(R.id.mineCoreStarter);
        buttonEnergy.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                // MineCoreStarter Info
                dialog = new Dialog(MapActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.info_minecore_starter);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                Button letMining = (Button) dialog.findViewById(R.id.letMining);
                letMining.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentMineCoreStarter = new Intent(MapActivity.this, MineCoreStarterActivity.class);
                        startActivity(intentMineCoreStarter); finish();
                        dialog.dismiss();
                    }
                });
                // Energy
                try {
                    if (countEnergy == 0) {
                        System.out.println("DON'T CLICK FU*ING BUTTON");
                    }
                    else {
                        if(countEnergy == 10){
                            energyTimer.setVisibility(View.VISIBLE);
                            energyTimerBar.setVisibility(View.VISIBLE);
                            Animation animationShowTimeAndBarTimer = AnimationUtils.loadAnimation(MapActivity.this, R.anim.showtimertextandbar);
                            energyTimer.startAnimation(animationShowTimeAndBarTimer);
                            energyTimerBar.startAnimation(animationShowTimeAndBarTimer);


                            countEnergy--;
                            for(int i = 0; i < 10; i++) {
                                TextView energyPoint = findViewById(energy[i]);
                                energyPoint.setBackgroundResource(R.drawable.style_battery);
                            }


                            for(int i = countEnergy; i < 10; i++) {
                                TextView energyPoint = findViewById(energy[i]);
                                energyPoint.setBackgroundResource(R.drawable.style_battery_empty);
                                textCountEnergy.setText(" "+countEnergy + "/10");
                            }
                        }
                        else{
                            countEnergy--;
                            for(int i = 0; i < 10; i++) {
                                TextView energyPoint = findViewById(energy[i]);
                                energyPoint.setBackgroundResource(R.drawable.style_battery);
                            }


                            for(int i = countEnergy; i < 10; i++) {
                                TextView energyPoint = findViewById(energy[i]);
                                energyPoint.setBackgroundResource(R.drawable.style_battery_empty);
                                textCountEnergy.setText(" "+countEnergy + "/10");
                            }
                        }

                    }

                } catch (Exception e) {
                    //Exception
                }
            }


        });


        ImageButton buttonForge = (ImageButton) findViewById(R.id.buttonForge);
        buttonForge.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                float scaleOn = 0.8f;

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setScaleX(scaleOn);
                    v.setScaleY(scaleOn);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setScaleX(1);
                    v.setScaleY(1);
                }

                return false;
            }
        });


        ImageButton buttonShop = (ImageButton) findViewById(R.id.buttonShop);
        buttonShop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                float scaleOn = 0.8f;

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setScaleX(scaleOn);
                    v.setScaleY(scaleOn);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setScaleX(1);
                    v.setScaleY(1);
                }

                return false;
            }
        });



        ImageButton buttonInventory = (ImageButton) findViewById(R.id.buttonInventory);
        buttonInventory.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                float scaleOn = 0.8f;

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setScaleX(scaleOn);
                    v.setScaleY(scaleOn);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setScaleX(1);
                    v.setScaleY(1);
                }

                return false;
            }
        });

        Runnable runnableEnergyTimer = new Runnable() {
            @Override
            public void run() {
                currentTimeEnergyTimer = System.currentTimeMillis();


                if(countEnergy < 10 && currentTimeEnergyTimer - timeStartEnergyTimer > endTimeEnergyTimer) {
                    timeStartEnergyTimer = System.currentTimeMillis();
                    energyTimer.setVisibility(View.VISIBLE);
                    CountDownTimer energyTime = new CountDownTimer(endTimeEnergyTimer, stepTimeEnergyTimer) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            minutes = ((int)millisUntilFinished / 1000) / 60;
                            seconds = ((int)millisUntilFinished / 1000) % 60;
                            energyTimer.post(new Runnable() {
                                @Override
                                public void run() {
                                    if(minutes >= 10 && seconds >= 10) {
                                        energyTimer.setText(minutes + ":" + seconds);
                                    }
                                    else if(minutes >= 10 && seconds < 10){
                                        energyTimer.setText(minutes + ":0" + seconds);
                                    }
                                    else if(minutes < 10 && seconds >= 10){
                                        energyTimer.setText("0"+minutes + ":" + seconds);
                                    }
                                    else{
                                        energyTimer.setText("0"+minutes + ":0" + seconds);
                                    }



                                }
                            });
                        }

                        @Override
                        public void onFinish() {
                            countEnergy++;
                            TextView energyPoint = findViewById(energy[countEnergy - 1]);
                            energyPoint.setBackgroundResource(R.drawable.style_battery);
                            textCountEnergy.post(new Runnable() {
                                @Override
                                public void run() {
                                    textCountEnergy.setText(countEnergy + "/10");
                                }
                            });

                            if (countEnergy == 10) {
                                energyTimer.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Animation animationHideTimeAndBarTimer = AnimationUtils.loadAnimation(MapActivity.this, R.anim.hidetimertextandbar);
                                        energyTimer.startAnimation(animationHideTimeAndBarTimer);
                                        energyTimerBar.startAnimation(animationHideTimeAndBarTimer);
                                        energyTimer.setVisibility(View.GONE);
                                        energyTimerBar.setVisibility(View.GONE);
                                    }
                                });
                            }
                            else {
                                energyTimer.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        energyTimer.setVisibility(View.VISIBLE);
                                    }
                                });
                            }

                        }
                    }.start();
                }
                customHandler.postDelayed(this, 0);
            }
        };

        Thread threadEnergyTimer = new Thread(runnableEnergyTimer);
        threadEnergyTimer.start();



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


        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        }
        else {
            backToast = Toast.makeText(getBaseContext(),"Click again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();

    }

    

}

