package com.shuiyinhuo.component.mixdev.jinbean.em;

import com.shuiyinhuo.component.mixdev.utils.comm.EmptyAndSizeUtils;
import com.shuiyinhuo.component.mixdev.utils.comm.ScreenUtils;
import com.shuiyinhuo.component.mixdev.utils.pkg.domain.anim.ObjectAnimatorConstant;

import java.util.ArrayList;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/9 0009
 * @ Description：属性动画属性绑定
 * =====================================
 */
public class ObjectAnimatorType {
   /* TRANSLATION(ObjectAnimatorConstant.TRANSLATIONX,  0f, 400f),
    ROTATION(ObjectAnimatorConstant.ROTATION,  0.0f, 360f),
    SCALE(ObjectAnimatorConstant.SCALEX, 0f, 1f),
    ALPHA(ObjectAnimatorConstant.ALPHA,0f, 1f);*/

    private long durationMillis = 400;
    private ObjectAnimatorConstant animType = null;
    private ArrayList<Float> Value = new ArrayList<>();


    public ObjectAnimatorType(ObjectAnimatorConstant animType) {
        this.durationMillis = animType.getDurationMillis();
        this.animType = animType;
        this.Value.add(0.0f);
        this.Value.add(1.0f);
    }

    public long getDurationMillis() {
        return durationMillis;
    }

    public void setDurationMillis(long durationMillis) {
        this.durationMillis = durationMillis;
    }

    public String getAnimType() {
        return animType.getProper();
    }

    public void setAnimType(ObjectAnimatorConstant animType) {
        this.animType = animType;
    }

    public float[] getValue() {
        return sortValues(Value);
    }

    public void setValue(ArrayList<Float> value) {
        Value = value;
    }

    public void setValue(Float ...value) {
        Value.clear();
        if (EmptyAndSizeUtils.isNotEmpty(value)&&value.length!=0){
            for (float v:value){
                Value.add(v);
            }
        }

    }

    public void setValue(int ...value) {
        Value.clear();
        if (EmptyAndSizeUtils.isNotEmpty(value)&&value.length!=0){
            for (float v:value){
                Value.add(v);
            }
        }

    }

    private float[] sortValues(ArrayList<Float> values){
        float[] val=new float[values.size()];
        for (int i=0;i<values.size();i++){
            val[i]=values.get(i);
        }
        return val;
    }

}
