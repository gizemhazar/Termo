package com.example.termo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SplashScreenActivity extends Activity{
	private static int SPLASH_TIME_OUT = 4000;
	
	public  MediaPlayer splashSound;
	Handler handler;

	public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
/** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.splash_screen);
    StartAnimations();
    StartSound();
    
    
    new Handler().postDelayed(new Runnable() {
    	 
        

        @Override
        public void run() {
            // This method will be executed once the timer is over
            // Start your app main activity
            Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(i);

         
            finish();
        }
    }, SPLASH_TIME_OUT);
}
private void StartAnimations() {
	
	Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
    anim.reset();
    RelativeLayout l=(RelativeLayout) findViewById(R.id.rel_lay);
    l.clearAnimation();
    l.startAnimation(anim);
 
    anim = AnimationUtils.loadAnimation(this, R.anim.translate);
    anim.reset();
    ImageView iv = (ImageView) findViewById(R.id.imgLogo);
    iv.clearAnimation();
    iv.startAnimation(anim);
    
    iv.setBackgroundResource(R.drawable.splash_anim);
	AnimationDrawable frameAnim = (AnimationDrawable) iv.getBackground();
	
	frameAnim.start();
    
    

}
private void StartSound() {
	handler = new Handler();  
    splashSound = MediaPlayer.create(SplashScreenActivity.this, 
                                        R.raw.water);   

    splashSound.start();
	
	
}

}
