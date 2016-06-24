package com.example.zhsh;
 
import com.example.zhsh.utils.PrefUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

public class SplahActivity extends Activity {

	private RelativeLayout rlRoot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_splash);
		 rlRoot = (RelativeLayout)findViewById(R.id.rl_root);
		 RotateAnimation animRotate=new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		 animRotate.setDuration(1000);
		 animRotate.setFillAfter(true);//����״��
		 
		 ScaleAnimation animScale=new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		 animScale.setDuration(1000);
		 animScale.setFillAfter(true);
		 
		 AlphaAnimation animAlpha=new AlphaAnimation(0, 1);
		 animAlpha.setDuration(1000);
		 animAlpha.setFillAfter(true);
		 
		 AnimationSet animSet=new AnimationSet(true);
		 animSet.addAnimation(animRotate);
		 animSet.addAnimation(animAlpha);
		 animSet.addAnimation(animScale);
		 rlRoot.startAnimation(animSet);
		 
		 animSet.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				boolean isGuideShow= PrefUtils.getBoolean("is_guide_show", false, getApplicationContext());
				if(isGuideShow){ 
					startActivity(new Intent(getApplicationContext(), MainActivity.class)); 
				}else{
					startActivity(new Intent(getApplicationContext(), GuideActivity.class));
				}
					finish();
			}
		});
		 
		 
	}
 

}
