package com.shuiyinhuo.component.mixdev.utils.pkg.domain;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.Animation;

import com.shuiyinhuo.component.mixdev.jinbean.em.ObjectAnimatorType;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;


/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/9 0009
 * @ Description：属性动画工具类
 * =====================================
 */
public class ObjectAnimatorUtils {
    /**
     * 创建动画
     * @param view
     * @param animatorType
     */
    public static ObjectAnimator getAnimator(View view,  ObjectAnimatorType animatorType) {
        ObjectAnimator mAnimator = ObjectAnimator.ofFloat(view, animatorType.getAnimType(), animatorType.getValue());
        mAnimator.setDuration(animatorType.getDurationMillis());
        //mAnimator.start();//1 操纵的对象
        return mAnimator;

    }

    /**
     * 播放动画
     * @param animator
     */
    public static void startAnimators(Animator.AnimatorListener listener,long durationMillis,Animator ...animator){
        AnimatorSet bouncer = new AnimatorSet();//创建一个动画集合类
        if (EmptyAndSizeUtils.isNotEmpty(animator)&&animator.length!=0){
            bouncer.playTogether(animator);//play:先播放animator with:同时播放animator2 after:在某动画后播放 before:再某动画前播放
            bouncer.setDuration(durationMillis);//持续时间
            bouncer.start();//开始动画
            if (EmptyAndSizeUtils.isNotEmpty(listener)) {
                bouncer.addListener(listener);
            }
        }

    }
}
