package com.skytech.ez_dr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ViewFlipper;


public class ViewFlipperSampleActivity extends Activity {

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private ViewFlipper mViewFlipper;	
	private AnimationListener mAnimationListener;
	private Context mContext;
	SharedPreferences sharedpreferences;
	public static final String MyPREFERENCES = "MyPrefs" ;
	public static final String pUserName = "username"; 
	public static final String pPassword = "password"; 

	@SuppressWarnings("deprecation")
	private final GestureDetector detector = new GestureDetector(new SwipeGestureDetector());

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.main);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mContext = this;
		mViewFlipper = (ViewFlipper) this.findViewById(R.id.view_flipper);
		mViewFlipper.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(final View view, final MotionEvent event) {
				detector.onTouchEvent(event);
				return true;
			}
		});

		Button skipbutton= (Button) findViewById(R.id.btnskip);
		skipbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent launchmain= new Intent(getApplicationContext(),LoginActivity.class);

				startActivity(launchmain);
			}
		});

		findViewById(R.id.play).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				//sets auto flipping
				mViewFlipper.setAutoStart(false);
				mViewFlipper.setFlipInterval(4000);
				mViewFlipper.startFlipping();
			}
		});

		findViewById(R.id.stop).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				//stop auto flipping 
				mViewFlipper.stopFlipping();
			}
		});


		//animation listener
		mAnimationListener = new Animation.AnimationListener() {
			public void onAnimationStart(Animation animation) {
				//animation started event
			}

			public void onAnimationRepeat(Animation animation) {
			}

			public void onAnimationEnd(Animation animation) {
				//TODO animation stopped event
			}
		};
	}
	protected void onResume() {
		sharedpreferences=getSharedPreferences(MyPREFERENCES, 
				MODE_APPEND);
		if (sharedpreferences.getString("username", null) != null)
		{

			Intent i = new Intent(getApplicationContext(),skiplogin.class);
			startActivity(i);

		}
		super.onResume();

	}
	private int slider = 1;
	class SwipeGestureDetector extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			try {
				// right to left swipe
				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					if( slider != 6){
						mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_in));
						mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_out));
						// controlling animation
						mViewFlipper.getInAnimation().setAnimationListener(mAnimationListener);
						mViewFlipper.showNext();
						slider++;
					}
					else if(slider == 6)
					{
						//Intent launchmain= new Intent(getApplicationContext(),LoginActivity.class);

						//startActivity(launchmain);
						Intent launchmain= new Intent(getApplicationContext(),EZDRHome.class);

						startActivity(launchmain);

					}
					return true;
				} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					if(slider != 1){
						mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_in));
						mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext,R.anim.right_out));
						// controlling animation
						mViewFlipper.getInAnimation().setAnimationListener(mAnimationListener);
						mViewFlipper.showPrevious();
						slider = slider - 1;
					}
					return true;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return false;
		}
	}
}


