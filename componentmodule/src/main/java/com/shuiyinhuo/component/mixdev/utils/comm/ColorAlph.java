package com.shuiyinhuo.component.mixdev.utils.comm;

import java.util.HashMap;

/**
 * =====================================
 *
 * @ Author: ZhiHeng Su
 * @ Date : on 2019/9/6 0006
 * @ Description：颜色透明度解析
 * =====================================
 */
public class ColorAlph {
    private HashMap<Integer,String > colorsAlph= new HashMap();
    public ColorAlph(){
        colorsAlph.clear();
        colorsAlph.put(100,"00");
        colorsAlph.put(95,"0D");
        colorsAlph.put(90,"1A");
        colorsAlph.put(85 ,"26");
        colorsAlph.put(80,"33");
        colorsAlph.put(75,"40");
        colorsAlph.put(70,"4D");
        colorsAlph.put(65 ,"59");
        colorsAlph.put(60 ,"66");
        colorsAlph.put(55 ,"73");
        colorsAlph.put(50,"80");
        colorsAlph.put(45 ,"8C");
        colorsAlph.put(40,"99");
        colorsAlph.put(35,"A6");
        colorsAlph.put(30 ,"B3");
        colorsAlph.put(25,"BF");
        colorsAlph.put(20,"CC");
        colorsAlph.put(15,"D9");
        colorsAlph.put(10,"E6");
        colorsAlph.put(5,"F2");
        colorsAlph.put(0,"FF");
    }

    public String getAlph(int level){
        int key=50;
        if (level<=0){
           key=0;
        }else if (level>0 &&level <=5){
            key=5;
        }else if (level>5 &&level <=10){
            key=10;
        }else if (level>10 &&level <=15){
            key=15;
        }else if (level>15 &&level <=20){
            key=20;
        }else if (level>20 &&level <=25){
            key=25;
        }else if (level>25 &&level <=30){
            key=30;
        }else if (level>30 &&level <=35){
            key=35;
        }else if (level>35 &&level <=40){
            key=40;
        }else if (level>40 &&level <=45){
            key=45;
        }else if (level>45 &&level <=50){
            key=50;
        }else if (level>5 &&level <=55){
            key=55;
        }else if (level>55 &&level <=60){
            key=60;
        }else if (level>60 &&level <=65){
            key=65;
        }else if (level>65 &&level <=70){
            key=70;
        }else if (level>70 &&level <=75){
            key=75;
        }else if (level>75 &&level <=80){
            key=80;
        }else if (level>80 &&level <=85){
            key=85;
        }else if (level>85 &&level <=90){
            key=90;
        }else if (level>90 &&level <=95){
            key=95;
        }else if (level>95){
            key=100;
        }
        return colorsAlph.get(key);
    }

}
