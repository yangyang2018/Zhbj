package com.app.yangyang.zhbj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.app.yangyang.zhbj.util.PrefUtils;

public class SplashActivity extends Activity {


    RelativeLayout rl_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        initView();
        startAnim();
    }


    private void initView() {
        rl_root = (RelativeLayout) findViewById(R.id.rl_root);
    }

    private void startAnim() {
        AnimationSet animset = new AnimationSet(false);
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        rotate.setDuration(2000);

        rotate.setFillAfter(true);
        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        scale.setDuration(2000);

        scale.setFillAfter(true);

        AlphaAnimation  alpha =new AlphaAnimation(0,1);

        alpha.setDuration(2000);

        alpha.setFillAfter(true);
        animset.addAnimation(rotate);
        animset.addAnimation(scale);
        animset.addAnimation(alpha);
        animset.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                jumpNextPage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        rl_root.startAnimation(animset);

    }

    private void jumpNextPage() {
        boolean user_guide = PrefUtils.getBoolean(this,"is_user_guide_showed",false);
        if(user_guide){
            startActivity(new Intent(SplashActivity.this,HomeActivity.class));
        }else{
            startActivity(new Intent(SplashActivity.this,GuideActivity.class));
        }
        finish();
    }

}
