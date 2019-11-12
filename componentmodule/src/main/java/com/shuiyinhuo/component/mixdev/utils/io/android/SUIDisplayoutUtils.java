package com.pdftron.webviewer.io_utils.android;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

/**
 * =====================================
 *
 * @ Author: szhh
 * @ Date : 2017/6/8.
 * @ Description：控件状态显示协助类
 * =====================================
 */
public class SUIDisplayoutUtils {
    public static void  showOrHiddenView(View view,boolean isVisiable){
        view.setVisibility(isVisiable?View.VISIBLE:View.GONE);
    }

    public static void  showOrHiddenViewByHasPosition(View view,boolean isVisiable){
        view.setVisibility(isVisiable?View.VISIBLE:View.INVISIBLE);
    }

    public static void setViewText(TextView text, String content, String ...color){
        text.setText((content!=null&&content.trim().length()!=0)?content:"");
        if (color!=null&&color.length!=0){
            String s = color[0].contains("#") ? color[0] : ("#" + color[0]);
            String news=s.replace("#","");
            text.setTextColor(Color.parseColor("#"+news));
        }
    }

    public static void setViewText(TextView text, String content, Typeface typeface, String ...color){
        text.setText((content!=null&&content.trim().length()!=0)?content:"");
        if (typeface!=null){
            text.setTypeface(typeface);
        }
        if (color!=null&&color.length!=0){
            String s = color[0].contains("#") ? color[0] : ("#" + color[0]);
            String news=s.replace("#","");
            text.setTextColor(Color.parseColor("#"+news));
        }
    }

    public static void setViewOnClick(View v,View.OnClickListener clickListener){
        v.setClickable(clickListener!=null);
        if (clickListener!=null){
            v.setOnClickListener(clickListener);
        }
    }

    public static TextView setTextColor(TextView tv,String color){
        if (tv==null||color==null){
            return tv;
        }
        String s = color.contains("#") ? color : ("#" + color);
        String news=s.replace("#","");
        tv.setTextColor(Color.parseColor("#"+news));
        return tv;
    }

    public static TextView setTextColor(TextView tv,int id){
        if (tv==null){
            return tv;
        }
        tv.setTextColor(id);
        return tv;
    }

    public static TextView setViewText(TextView view,Spanned htmlFormate){
        view.setText(htmlFormate);
        return view;
    }

    public static View setViewBackgroundColor(View view,String color){
        String s = color.contains("#") ? color : ("#" + color);
        String news=s.replace("#","");
        view.setBackgroundColor(Color.parseColor("#"+news));
        return view;
    }
}
