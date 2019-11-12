package com.shuiyinhuo.component.mixdev.utils.pkg.domain;
/**
 * Copyright 2014 Zhenguo Jin (jinzhenguo1990@gmail.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.shuiyinhuo.component.mixdev.jinbean.em.ApproachAndDepartureType;
import com.shuiyinhuo.component.mixdev.jinbean.em.ObjectAnimatorType;
import com.shuiyinhuo.component.mixdev.jinbean.em.ScaleAnimationPositionType;
import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.ScreenUtils;
import com.shuiyinhuo.component.mixdev.utils.pkg.domain.anim.ObjectAnimatorConstant;

import static com.shuiyinhuo.component.mixdev.utils.pkg.domain.ObjectAnimatorUtils.*;

/**
 * 动画工具类
 *
 * @author zhenguo
 */
public final class AnimationUtils {

    /**
     * Don't let anyone instantiate this class.
     */
    private AnimationUtils() {
        throw new Error("Do not need instantiate!");
    }


    /**
     * 默认动画持续时间
     */
    public static final long DEFAULT_ANIMATION_DURATION = 400;


    /**
     * 获取一个旋转动画
     *
     * @param fromDegrees       开始角度
     * @param toDegrees         结束角度
     * @param pivotXType        旋转中心点X轴坐标相对类型
     * @param pivotXValue       旋转中心点X轴坐标
     * @param pivotYType        旋转中心点Y轴坐标相对类型
     * @param pivotYValue       旋转中心点Y轴坐标
     * @param durationMillis    持续时间
     * @param animationListener 动画监听器
     * @return 一个旋转动画
     */
    public static RotateAnimation getRotateAnimation(float fromDegrees, float toDegrees, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue, long durationMillis, AnimationListener animationListener) {
        RotateAnimation rotateAnimation = new RotateAnimation(fromDegrees,
                toDegrees, pivotXType, pivotXValue, pivotYType, pivotYValue);
        rotateAnimation.setDuration(durationMillis);
        if (animationListener != null) {
            rotateAnimation.setAnimationListener(animationListener);
        }
        return rotateAnimation;
    }


    /**
     * 获取一个根据视图自身中心点旋转的动画
     *
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听器
     * @return 一个根据中心点旋转的动画
     */
    public static RotateAnimation getRotateAnimationByCenter(long durationMillis, AnimationListener animationListener) {
        return getRotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, durationMillis,
                animationListener);
    }


    /**
     * 获取一个根据中心点旋转的动画
     *
     * @param duration 动画持续时间
     * @return 一个根据中心点旋转的动画
     */
    public static RotateAnimation getRotateAnimationByCenter(long duration) {
        return getRotateAnimationByCenter(duration, null);
    }


    /**
     * 获取一个根据视图自身中心点旋转的动画
     *
     * @param animationListener 动画监听器
     * @return 一个根据中心点旋转的动画
     */
    public static RotateAnimation getRotateAnimationByCenter(AnimationListener animationListener) {
        return getRotateAnimationByCenter(DEFAULT_ANIMATION_DURATION,
                animationListener);
    }


    /**
     * 获取一个根据中心点旋转的动画
     *
     * @return 一个根据中心点旋转的动画，默认持续时间为DEFAULT_ANIMATION_DURATION
     */
    public static RotateAnimation getRotateAnimationByCenter() {
        return getRotateAnimationByCenter(DEFAULT_ANIMATION_DURATION, null);
    }


    /**
     * 获取一个透明度渐变动画
     *
     * @param fromAlpha         开始时的透明度
     * @param toAlpha           结束时的透明度都
     * @param durationMillis    持续时间
     * @param animationListener 动画监听器
     * @return 一个透明度渐变动画
     */
    public static AlphaAnimation getAlphaAnimation(float fromAlpha, float toAlpha, long durationMillis, AnimationListener animationListener) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);
        alphaAnimation.setDuration(durationMillis);
        if (animationListener != null) {
            alphaAnimation.setAnimationListener(animationListener);
        }
        return alphaAnimation;
    }


    /**
     * 获取一个移动渐变动画
     *
     * @param fromX             动画开始x轴位置
     * @param toX               动画结束x轴位置
     * @param fromY             动画开始y轴位置
     * @param toY               动画结束y轴位置
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听器
     * @return
     */
    public static TranslateAnimation getTranslateAnimation(float fromX, float toX, float fromY, float toY, long durationMillis, AnimationListener animationListener) {
        TranslateAnimation translateAnimation = new TranslateAnimation(fromX, toX, fromY, toY);
        translateAnimation.setDuration(durationMillis);
        if (animationListener != null) {
            translateAnimation.setAnimationListener(animationListener);
        }
        return translateAnimation;
    }


    /**
     * 获取一个移动渐变动画
     *
     * @param fromX          动画开始x轴位置
     * @param toX            动画结束x轴位置
     * @param fromY          动画开始y轴位置
     * @param toY            动画结束y轴位置
     * @param durationMillis 动画持续时间
     * @return
     */
    public static TranslateAnimation getTranslateAnimation(float fromX, float toX, float fromY, float toY, long durationMillis) {

        return getTranslateAnimation(fromX, toX, fromY, toY, durationMillis, null);
    }


    /**
     * 获取一个移动渐变动画,相对于父布局
     *
     * @param fromX             动画开始x轴位置
     * @param toX               动画结束x轴位置
     * @param fromY             动画开始y轴位置
     * @param toY               动画结束y轴位置
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听器
     * @return
     */
    public static TranslateAnimation getTranslateAnimationRelationParent(float fromX, float toX, float fromY, float toY, long durationMillis, AnimationListener animationListener) {
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, fromX, Animation.RELATIVE_TO_PARENT, toX,
                Animation.RELATIVE_TO_PARENT, fromY, Animation.RELATIVE_TO_PARENT, toY
        );
        // 从屏幕底部进入的动画
        translateAnimation.setDuration(durationMillis);
        if (animationListener != null) {
            translateAnimation.setAnimationListener(animationListener);
        }
        return translateAnimation;
    }

    /**
     * 获取一个从底部淡入的动画，以父布局为参考点
     *
     * @param durationMillis
     * @param animationListener
     * @return
     */
    public static TranslateAnimation getTranslateAnimationRelationParentButtomFadeIn(long durationMillis, AnimationListener animationListener) {
        return getTranslateAnimationRelationParent(0.0f, 0.0f, 1.0f, 0.0f, durationMillis, animationListener);
    }

    public static TranslateAnimation getTranslateAnimationRelationParentButtomFadeIn(long durationMillis) {
        return getTranslateAnimationRelationParent(0.0f, 0.0f, 1.0f, 0.0f, durationMillis, null);
    }

    /**
     * 获取一个从底部淡出的动画，以父布局为参考点
     *
     * @param durationMillis
     * @param animationListener
     * @return
     */
    public static TranslateAnimation getTranslateAnimationRelationParentButtomFadeOut(long durationMillis, AnimationListener animationListener) {
        return getTranslateAnimationRelationParent(0.0f, 0.0f, 0.0f, 1.0f, durationMillis, animationListener);
    }

    public static TranslateAnimation getTranslateAnimationRelationParentButtomFadeOut(long durationMillis) {
        return getTranslateAnimationRelationParent(0.0f, 0.0f, 0.0f, 1.0f, durationMillis, null);
    }

    /**
     * 获取一个从底部淡入的动画，以父布局为参考点
     *
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听
     * @param toY               动画结束后y轴的位置
     * @return
     */
    public static TranslateAnimation getTranslateAnimationRelationParentButtomFadeIn(float toY, long durationMillis, AnimationListener animationListener) {
        return getTranslateAnimationRelationParent(0.0f, 0.0f, 1.0f, toY, durationMillis, animationListener);
    }

    public static TranslateAnimation getTranslateAnimationRelationParentButtomFadeIn(float toY, long durationMillis) {
        return getTranslateAnimationRelationParent(0.0f, 0.0f, 1.0f, toY, durationMillis, null);
    }

    /**
     * 获取一个从底部淡出的动画，以父布局为参考点
     *
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听
     * @param toY               动画结束后y轴的位置
     * @return
     */
    public static TranslateAnimation getTranslateAnimationRelationParentButtomFadeOut(float toY, long durationMillis, AnimationListener animationListener) {
        return getTranslateAnimationRelationParent(0.0f, 0.0f, 0.0f, toY, durationMillis, animationListener);
    }

    public static TranslateAnimation getTranslateAnimationRelationParentButtomFadeOut(float toY, long durationMillis) {
        return getTranslateAnimationRelationParent(0.0f, 0.0f, 0.0f, toY, durationMillis, null);
    }

    /**
     * 获取一个从左边淡入的动画，以父布局为参考点
     *
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听
     * @return
     */
    public static TranslateAnimation getTranslateAnimationRelationParentLeftFadeIn(long durationMillis, AnimationListener animationListener) {
        return getTranslateAnimationRelationParent(-1.0f, 0.0f, 0.0f, 0.0f, durationMillis, animationListener);
    }

    public static TranslateAnimation getTranslateAnimationRelationParentLeftFadeIn(long durationMillis) {
        return getTranslateAnimationRelationParent(-1.0f, 0.0f, 0.0f, 0.0f, durationMillis, null);
    }

    /**
     * 获取一个从左边淡出的动画，以父布局为参考点
     *
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听
     * @return
     */
    public static TranslateAnimation getTranslateAnimationRelationParentLeftFadeOut(long durationMillis, AnimationListener animationListener) {
        return getTranslateAnimationRelationParent(0.0f, -1.0f, 0.0f, 0.0f, durationMillis, animationListener);
    }

    public static TranslateAnimation getTranslateAnimationRelationParentLeftFadeOut(long durationMillis) {
        return getTranslateAnimationRelationParent(0.0f, -1.0f, 0.0f, 0.0f, durationMillis, null);
    }

    /**
     * 获取一个从左边淡入的动画，以父布局为参考点
     *
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听
     * @param toX               动画结束后x的位置
     * @return
     */

    public static TranslateAnimation getTranslateAnimationRelationParentLeftFadeIn(float toX, long durationMillis, AnimationListener animationListener) {
        return getTranslateAnimationRelationParent(-1.0f, toX, 0.0f, 0.0f, durationMillis, animationListener);
    }

    public static TranslateAnimation getTranslateAnimationRelationParentLeftFadeIn(float toX, long durationMillis) {
        return getTranslateAnimationRelationParent(-1.0f, toX, 0.0f, 0.0f, durationMillis, null);
    }

    /**
     * 获取一个从左边淡出的动画，以父布局为参考点
     *
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听
     * @param toX               动画结束后x的位置
     * @return
     */

    public static TranslateAnimation getTranslateAnimationRelationParentLeftFadeOut(float toX, long durationMillis, AnimationListener animationListener) {
        return getTranslateAnimationRelationParent(0.0f, -toX, 0.0f, 0.0f, durationMillis, animationListener);
    }

    public static TranslateAnimation getTranslateAnimationRelationParentLeftFadeOut(float toX, long durationMillis) {
        return getTranslateAnimationRelationParent(0.0f, -toX, 0.0f, 0.0f, durationMillis, null);
    }


    /**
     * 获取一个透明度渐变动画
     *
     * @param fromAlpha      开始时的透明度
     * @param toAlpha        结束时的透明度都
     * @param durationMillis 持续时间
     * @return 一个透明度渐变动画
     */
    public static AlphaAnimation getAlphaAnimation(float fromAlpha, float toAlpha, long durationMillis) {
        return getAlphaAnimation(fromAlpha, toAlpha, durationMillis, null);
    }


    /**
     * 获取一个透明度渐变动画
     *
     * @param fromAlpha         开始时的透明度
     * @param toAlpha           结束时的透明度都
     * @param animationListener 动画监听器
     * @return 一个透明度渐变动画，默认持续时间为DEFAULT_ANIMATION_DURATION
     */
    public static AlphaAnimation getAlphaAnimation(float fromAlpha, float toAlpha, AnimationListener animationListener) {
        return getAlphaAnimation(fromAlpha, toAlpha, DEFAULT_ANIMATION_DURATION,
                animationListener);
    }


    /**
     * 获取一个透明度渐变动画
     *
     * @param fromAlpha 开始时的透明度
     * @param toAlpha   结束时的透明度都
     * @return 一个透明度渐变动画，默认持续时间为DEFAULT_ANIMATION_DURATION
     */
    public static AlphaAnimation getAlphaAnimation(float fromAlpha, float toAlpha) {
        return getAlphaAnimation(fromAlpha, toAlpha, DEFAULT_ANIMATION_DURATION,
                null);
    }


    /**
     * 获取一个由完全显示变为不可见的透明度渐变动画
     *
     * @param durationMillis    持续时间
     * @param animationListener 动画监听器
     * @return 一个由完全显示变为不可见的透明度渐变动画
     */
    public static AlphaAnimation getHiddenAlphaAnimation(long durationMillis, AnimationListener animationListener) {
        return getAlphaAnimation(1.0f, 0.0f, durationMillis, animationListener);
    }


    /**
     * 获取一个由完全显示变为不可见的透明度渐变动画
     *
     * @param durationMillis 持续时间
     * @return 一个由完全显示变为不可见的透明度渐变动画
     */
    public static AlphaAnimation getHiddenAlphaAnimation(long durationMillis) {
        return getHiddenAlphaAnimation(durationMillis, null);
    }


    /**
     * 获取一个由完全显示变为不可见的透明度渐变动画
     *
     * @param animationListener 动画监听器
     * @return 一个由完全显示变为不可见的透明度渐变动画，默认持续时间为DEFAULT_ANIMATION_DURATION
     */
    public static AlphaAnimation getHiddenAlphaAnimation(AnimationListener animationListener) {
        return getHiddenAlphaAnimation(DEFAULT_ANIMATION_DURATION,
                animationListener);
    }


    /**
     * 获取一个由完全显示变为不可见的透明度渐变动画
     *
     * @return 一个由完全显示变为不可见的透明度渐变动画，默认持续时间为DEFAULT_ANIMATION_DURATION
     */
    public static AlphaAnimation getHiddenAlphaAnimation() {
        return getHiddenAlphaAnimation(DEFAULT_ANIMATION_DURATION, null);
    }


    /**
     * 获取一个由不可见变为完全显示的透明度渐变动画
     *
     * @param durationMillis    持续时间
     * @param animationListener 动画监听器
     * @return 一个由不可见变为完全显示的透明度渐变动画
     */
    public static AlphaAnimation getShowAlphaAnimation(long durationMillis, AnimationListener animationListener) {
        return getAlphaAnimation(0.0f, 1.0f, durationMillis, animationListener);
    }


    /**
     * 获取一个由不可见变为完全显示的透明度渐变动画
     *
     * @param durationMillis 持续时间
     * @return 一个由不可见变为完全显示的透明度渐变动画
     */
    public static AlphaAnimation getShowAlphaAnimation(long durationMillis) {
        return getAlphaAnimation(0.0f, 1.0f, durationMillis, null);
    }


    /**
     * 获取一个由不可见变为完全显示的透明度渐变动画
     *
     * @param animationListener 动画监听器
     * @return 一个由不可见变为完全显示的透明度渐变动画，默认持续时间为DEFAULT_ANIMATION_DURATION
     */
    public static AlphaAnimation getShowAlphaAnimation(AnimationListener animationListener) {
        return getAlphaAnimation(0.0f, 1.0f, DEFAULT_ANIMATION_DURATION,
                animationListener);
    }


    /**
     * 获取一个由不可见变为完全显示的透明度渐变动画
     *
     * @return 一个由不可见变为完全显示的透明度渐变动画，默认持续时间为DEFAULT_ANIMATION_DURATION
     */
    public static AlphaAnimation getShowAlphaAnimation() {
        return getAlphaAnimation(0.0f, 1.0f, DEFAULT_ANIMATION_DURATION, null);
    }


    /**
     * 获取一个缩小动画
     *
     * @param durationMillis    时间
     * @param animationListener 监听
     * @return 一个缩小动画
     */
    public static ScaleAnimation getLessenScaleAnimation(long durationMillis, AnimationListener animationListener) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f,
                0.0f, ScaleAnimation.RELATIVE_TO_SELF,
                ScaleAnimation.RELATIVE_TO_SELF);
        scaleAnimation.setDuration(durationMillis);
        scaleAnimation.setAnimationListener(animationListener);

        return scaleAnimation;
    }


    /**
     * 获取一个缩小动画
     *
     * @param durationMillis 时间
     * @return 一个缩小动画
     */
    public static ScaleAnimation getLessenScaleAnimation(long durationMillis) {
        return getLessenScaleAnimation(durationMillis, null);

    }


    /**
     * 获取一个缩小动画
     *
     * @param animationListener 监听
     * @return 返回一个缩小的动画
     */
    public static ScaleAnimation getLessenScaleAnimation(AnimationListener animationListener) {
        return getLessenScaleAnimation(DEFAULT_ANIMATION_DURATION,
                animationListener);

    }


    /**
     * 获取一个放大动画
     *
     * @param durationMillis    时间
     * @param animationListener 监听
     * @return 返回一个放大的效果
     */
    public static ScaleAnimation getAmplificationAnimation(long durationMillis, AnimationListener animationListener) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f,
                1.0f, ScaleAnimation.RELATIVE_TO_SELF,
                ScaleAnimation.RELATIVE_TO_SELF);
        scaleAnimation.setDuration(durationMillis);
        scaleAnimation.setAnimationListener(animationListener);
        return scaleAnimation;
    }

    /**
     *
     /**
     *    获取一个缩放动画
     * @param positionType 指定动画的中心位置
     * @param type 进场还是离场类型
     * @return
     * @return
     */
    public static ScaleAnimation getScaleAnimationByPosition(ScaleAnimationPositionType positionType, ApproachAndDepartureType type) {
        return getScaleAnimationByPosition(positionType,type,DEFAULT_ANIMATION_DURATION,null);
    }


    /**
     * 获取一个缩放动画
     * @param positionType 指定动画的中心位置
     * @param type 进场还是离场类型
     * @param durationMillis  持续时间
     * @return
     */
    public static ScaleAnimation getScaleAnimationByPosition(ScaleAnimationPositionType positionType, ApproachAndDepartureType type, long durationMillis) {
        return getScaleAnimationByPosition(positionType,type,durationMillis,null);
    }

    /**
     * 获取一个缩放动画
     * @param positionType  指定动画的中心位置
     * @param type  进场还是离场类型
     * @param durationMillis  持续时间
     * @param animationListener 动画监听
     * @return
     */

    public static ScaleAnimation getScaleAnimationByPosition(ScaleAnimationPositionType positionType, ApproachAndDepartureType type, long durationMillis, AnimationListener animationListener) {
        float fromX = -1f,toX = -1f,fromY = -1f,toY =-1f;
        switch (type) {
            case TYPE_APPROACH:
                fromX = 0.0f;
                toX = 1.0f;
                fromY = 0.0f;
                toY = 1.0f;
                break;
            case TYPE_DEPARTURE:
                fromX = 1.0f;
                toX = 0.0f;
                fromY = 1.0f;
                toY = 0.0f;
                break;
        }
        return getScaleAnimationByPosition(positionType,fromX,toX,fromY,toY,durationMillis,animationListener);
    }

    /**
     * 获取一个放大动画
     *
     * @param durationMillis    时间
     * @param animationListener 监听
     * @return 返回一个放大的效果
     */
    private static ScaleAnimation getScaleAnimationByPosition( ScaleAnimationPositionType positionType,float fromX, float toX, float fromY, float toY, long durationMillis, AnimationListener animationListener) {

        float pivotXValue = 0.0f;
        float pivotYValue = 0.0f;
        switch (positionType) {
            case TYPE_TOP:
                pivotXValue = 0.5f;
                pivotYValue = 0.0f;
                break;
            case TYPE_LEFT:
                pivotXValue = 0.0f;
                pivotYValue = 0.5f;
                break;
            case TYPE_RIGHT:
                pivotXValue = 1.0f;
                pivotYValue = 0.5f;
                break;
            case TYPE_BOTTOM:
                pivotXValue = 0.5f;
                pivotYValue = 1.0f;
                break;
            case TYPE_CENTER:
                pivotXValue = 0.5f;
                pivotYValue = 0.5f;
                break;
        }
        ScaleAnimation scaleAnimation = new ScaleAnimation(fromX, toX, fromY,
                toY, ScaleAnimation.RELATIVE_TO_SELF,
                pivotXValue, ScaleAnimation.RELATIVE_TO_SELF, pivotYValue);
        scaleAnimation.setDuration(durationMillis);
        scaleAnimation.setAnimationListener(animationListener);
        return scaleAnimation;
    }


    /**
     * 获取一个放大动画
     *
     * @param durationMillis 时间
     * @return 返回一个放大的效果
     */
    public static ScaleAnimation getAmplificationAnimation(long durationMillis) {
        return getAmplificationAnimation(durationMillis, null);

    }


    /**
     * 获取一个放大动画
     *
     * @param animationListener 监听
     * @return 返回一个放大的效果
     */
    public static ScaleAnimation getAmplificationAnimation(AnimationListener animationListener) {
        return getAmplificationAnimation(DEFAULT_ANIMATION_DURATION,
                animationListener);
    }

    /**
     * 得到一个动画集
     * @param animations
     * @return
     */
    public static AnimationSet getAnimationSet(Animation ...animations){
        AnimationSet animationSet = new AnimationSet(true);
        if (EmptyAndSizeUtils.isNotEmpty(animations)){
            for (Animation mAnimation:animations){
                animationSet.addAnimation(mAnimation);
            }
        }
        return animationSet;
    }

    /**
     * 播放帧动画
     * @param view
     * @param animationSet
     */
    public static void playAnimations(View view,AnimationSet animationSet){
        if (EmptyAndSizeUtils.isNotEmpty(view)){
            view.setAnimation(animationSet);
        }
    }

    /**
     * 播放帧动画集合
     * @param view
     * @param animation
     */
    public static void playAnimations(View view,Animation animation){
        if (EmptyAndSizeUtils.isNotEmpty(view)){
            view.setAnimation(animation);
        }
    }

    /**
     * 从底部淡出的动画
     * @param view
     */
    public static void playObjectAnimatorUp(View view,Animator.AnimatorListener listener){
        ObjectAnimatorType mAlpha =new ObjectAnimatorType(ObjectAnimatorConstant.ALPHA);

        ObjectAnimatorType mTranslation = new ObjectAnimatorType(ObjectAnimatorConstant.TRANSLATIONX);
        mTranslation.setAnimType(ObjectAnimatorConstant.TRANSLATIONY);
        mTranslation.setValue(ScreenUtils.getScreenHeight(view.getContext()) ,0);
        ObjectAnimator mAnimator = ObjectAnimatorUtils.getAnimator(view, mAlpha);
        ObjectAnimator mAnimator1 = ObjectAnimatorUtils.getAnimator(view, mTranslation);
       // ObjectAnimator mAnimator2 = ObjectAnimatorUtils.getAnimator(view, mRotation);
        ObjectAnimatorUtils.startAnimators(listener,mTranslation.getDurationMillis(),mAnimator,mAnimator1);
    }


    /**
     * 从底部淡出的动画
     * @param view
     */
    public static void playObjectAnimatorDown(View view,Animator.AnimatorListener listener){
        ObjectAnimatorType mAlpha =new ObjectAnimatorType(ObjectAnimatorConstant.ALPHA);
        mAlpha.setValue(1.0f,0.0f);

        ObjectAnimatorType mTranslation = new ObjectAnimatorType(ObjectAnimatorConstant.TRANSLATIONX);
        mTranslation.setAnimType(ObjectAnimatorConstant.TRANSLATIONY);
        mTranslation.setValue(0,ScreenUtils.getScreenHeight(view.getContext()));

        ObjectAnimatorType mScale = new ObjectAnimatorType(ObjectAnimatorConstant.SCALEX);
       // mScale.setAnimType(ObjectAnimatorConstant.TRANSLATIONY);
        mScale.setValue(1f,0.0f);

        ObjectAnimator mAnimator = ObjectAnimatorUtils.getAnimator(view,mAlpha);
        ObjectAnimator mAnimator1 = ObjectAnimatorUtils.getAnimator(view, mTranslation);
        ObjectAnimator mAnimator2 = ObjectAnimatorUtils.getAnimator(view, mScale);
        ObjectAnimatorUtils.startAnimators(listener,mScale.getDurationMillis(),mAnimator,mAnimator1,mAnimator2);
    }

    /**
     * 从中心淡入的动画
     * @param view
     */
    public static void playObjectAnimatorFadeInCenter(View view,Animator.AnimatorListener listener){
        ObjectAnimatorType mAlpha =new ObjectAnimatorType(ObjectAnimatorConstant.ALPHA);
        mAlpha.setValue(0.0f,1.0f);

        ObjectAnimatorType mScaleX = new ObjectAnimatorType(ObjectAnimatorConstant.SCALEX);
        ObjectAnimatorType mScaleY = new ObjectAnimatorType(ObjectAnimatorConstant.SCALEY);
        mScaleY.setAnimType(ObjectAnimatorConstant.SCALEY);
        //mTranslation.setValue(0,ScreenUtils.getScreenHeight(view.getContext()));*/

        ObjectAnimator mAnimator = ObjectAnimatorUtils.getAnimator(view,mAlpha);
        ObjectAnimator mAnimator1 = ObjectAnimatorUtils.getAnimator(view, mScaleX);
        ObjectAnimator mAnimator2 = ObjectAnimatorUtils.getAnimator(view, mScaleY);
        // ObjectAnimator mAnimator2 = ObjectAnimatorUtils.getAnimator(view, mRotation);
        ObjectAnimatorUtils.startAnimators(listener,mScaleX.getDurationMillis(), mAnimator,mAnimator1,mAnimator2);
    }

 /**
     * 从中心淡出的动画
     * @param view
     */
    public static void playObjectAnimatorFadeOutCenter(View view, Animator.AnimatorListener listener){
        ObjectAnimatorType mAlpha =new ObjectAnimatorType(ObjectAnimatorConstant.ALPHA);
        mAlpha.setValue(1.0f,0.0f);

        ObjectAnimatorType mScaleX = new ObjectAnimatorType(ObjectAnimatorConstant.SCALEX);
        mScaleX.setValue(1.0f,0.0f);
        ObjectAnimatorType mScaleY = new ObjectAnimatorType(ObjectAnimatorConstant.SCALEY);
        mScaleY.setAnimType(ObjectAnimatorConstant.SCALEY);
        mScaleY.setValue(1.0f,0.0f);
        //mTranslation.setValue(0,ScreenUtils.getScreenHeight(view.getContext()));*/

        ObjectAnimator mAnimator = ObjectAnimatorUtils.getAnimator(view,mAlpha);
        ObjectAnimator mAnimator1 = ObjectAnimatorUtils.getAnimator(view, mScaleX);
        ObjectAnimator mAnimator2 = ObjectAnimatorUtils.getAnimator(view, mScaleY);
        // ObjectAnimator mAnimator2 = ObjectAnimatorUtils.getAnimator(view, mRotation);
        ObjectAnimatorUtils.startAnimators(listener,mScaleX.getDurationMillis(),mAnimator,mAnimator1,mAnimator2);
    }


    /**
     * 从中左侧淡入的动画
     * @param view
     */
    public static void playObjectAnimatorFadeInLeft(View view,Animator.AnimatorListener listener,long durationMillis){
        ObjectAnimatorType mAlpha =new ObjectAnimatorType(ObjectAnimatorConstant.ALPHA);
        mAlpha.setValue(0.5f,1.0f);

        ObjectAnimatorType mTranslateX  = new ObjectAnimatorType(ObjectAnimatorConstant.TRANSLATIONX);
        mTranslateX.setValue(-ScreenUtils.getScreenWidth(view.getContext()),0);

        ObjectAnimatorType mScaleY = new ObjectAnimatorType(ObjectAnimatorConstant.SCALEY);
        mScaleY.setValue(0.0f,1.0f);
        ObjectAnimatorType mScaleX = new ObjectAnimatorType(ObjectAnimatorConstant.SCALEX);
        mScaleX.setValue(0.0f,1.0f);
        //mTranslation.setValue(0,ScreenUtils.getScreenHeight(view.getContext()));*/

        ObjectAnimator mAnimatorAlpha = ObjectAnimatorUtils.getAnimator(view,mAlpha);
        ObjectAnimator mAnimatorScaleX = ObjectAnimatorUtils.getAnimator(view, mScaleX);
        ObjectAnimator mAnimatorScaleY = ObjectAnimatorUtils.getAnimator(view, mScaleY);
        // ObjectAnimator mAnimator2 = ObjectAnimatorUtils.getAnimator(view, mRotation);
        ObjectAnimator mAnimatorTranslateX = ObjectAnimatorUtils.getAnimator(view, mTranslateX);


        ObjectAnimatorUtils.startAnimators(listener, durationMillis,mAnimatorAlpha,mAnimatorScaleY,/*mAnimatorTranslateX,*/mAnimatorScaleX);
    }

    /**
     * 从左测淡出的动画
     * @param view
     */
    public static void playObjectAnimatorFadeOutRight(View view, Animator.AnimatorListener listener){
        ObjectAnimatorType mAlpha =new ObjectAnimatorType(ObjectAnimatorConstant.ALPHA);
        mAlpha.setValue(1.0f,0.0f);

        ObjectAnimatorType mTranslate = new ObjectAnimatorType(ObjectAnimatorConstant.TRANSLATIONX);
        mTranslate.setValue(1.0f,ScreenUtils.getScreenWidth(view.getContext())*1f);

        ObjectAnimatorType mScaleY = new ObjectAnimatorType(ObjectAnimatorConstant.SCALEY);
        mScaleY.setAnimType(ObjectAnimatorConstant.SCALEY);
        mScaleY.setValue(1.0f,0.0f);

        ObjectAnimator mAnimatorAlpha = ObjectAnimatorUtils.getAnimator(view,mAlpha);
        ObjectAnimator mAnimatorTranslate = ObjectAnimatorUtils.getAnimator(view, mTranslate);
        ObjectAnimator mAnimatorScaleY = ObjectAnimatorUtils.getAnimator(view, mScaleY);
        ObjectAnimatorUtils.startAnimators(listener,mTranslate.getDurationMillis(),mAnimatorAlpha,mAnimatorTranslate/*,mAnimatorScaleY*/);
    }



}
