package com.test.caiwen.utile;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.test.caiwen.R;

/**
 * @author Created by MrRight on 2018/1/29.
 */

public class AnimatorTest {

    /**
     * 将一个值从0平滑过渡到1，时长300毫秒，在300毫秒内对控件进行各种更新
     */
    public void valueAnimatortest(final View view){
        ValueAnimator anim = ValueAnimator.ofInt(0, 100);
        anim.setDuration(300);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //可以根据时间的进度 对控件进行操作，比如透明度，颜色，大小，位置等；这样操作可以让控件同时拥有多种效果
                int currentValue = (int) animation.getAnimatedValue();
                view.setAlpha(currentValue*1.0f/100);
                view.setBackgroundColor(0x00000011*currentValue);
                view.setScaleX(currentValue*1.0f/100);
                view.setScaleY(currentValue*1.0f/100);
                Log.d("TAG", "cuurent value is " + currentValue);
            }
        });
        anim.start();

        //方法来设置动画延迟播放的时间，
        //anim.setStartDelay(1);
        //调用setRepeatCount()和setRepeatMode()方法来设置动画循环播放的次数以及循环播放的模式，循环模式包括RESTART和REVERSE两种，分别表示重新播放和倒序播放的意思。
        //anim.setRepeatCount(1);
        //anim.setRepeatMode(ValueAnimator.RESTART);
    }


    /**
     * 单个动画测试 以下动画分开执行
     * @param context 上下文
     */
    public void testSingleAnimator(View view,Context context){
        //透明度操作  view：要操作的控件  alpha：要操作的属性  后面的参数全是要从多少变化到多少的值，个数可以有多个，如透明度从1到0再到1
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f, 1f);
        animator.setDuration(5000);
        animator.start();

        //旋转操作
        animator = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
        animator.setDuration(5000);
        animator.start();

        //平移操作
        float curTranslationX = view.getTranslationX();
        animator = ObjectAnimator.ofFloat(view, "translationX", curTranslationX, -500f, curTranslationX);
        animator.setDuration(5000);
        animator.start();

        //缩放操作
        animator = ObjectAnimator.ofFloat(view, "scaleY", 1f, 3f, 1f);
        animator.setDuration(5000);
        animator.start();
    }


    /**
     * AnimatorSet这个类，这个类提供了一个play()方法，如果我们向这个方法中传入一个Animator对象(ValueAnimator或
     * ObjectAnimator)将会返回一个AnimatorSet.Builder的实例，AnimatorSet.Builder中包括以下四个方法：
     * after(Animator anim)   将现有动画插入到传入的动画之后执行
     * after(long delay)   将现有动画延迟指定毫秒后执行
     * before(Animator anim)   将现有动画插入到传入的动画之前执行
     * with(Animator anim)   将现有动画和传入的动画同时执行
     */
    public void combinationAnimatorTest(Context context){
        TextView textview=new TextView(context);
        ObjectAnimator moveIn = ObjectAnimator.ofFloat(textview, "translationX", -500f, 0f);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(textview, "rotation", 0f, 360f);
        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(textview, "alpha", 1f, 0f, 1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(rotate).with(fadeInOut).after(moveIn);
        animSet.setDuration(5000);
        animSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        });


        //这个和addListener是异曲同工  都是监听4个状态，也可以单独重写莫一个方法
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

//            @Override
//            public void onAnimationRepeat(Animator animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        });
        animSet.start();
    }


    public void xmlAnimatorTest(View view, Context context){
        Animator animator = AnimatorInflater.loadAnimator(context, R.animator.anim_file);
        animator.setTarget(view);
        animator.start();
    }

}
