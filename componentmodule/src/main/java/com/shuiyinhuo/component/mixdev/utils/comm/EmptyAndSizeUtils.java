package com.shuiyinhuo.component.mixdev.utils.comm;

import java.util.ArrayList;

/**
 * =====================================
 *
 * @ Author: szhh
 * @ Date : on 2018/12/7 0007
 * @ Description：
 * =====================================
 */
public class EmptyAndSizeUtils {
    public static boolean isEmpty(Object o){
        if (null!=o){
            if (o instanceof String ){
                String temp= (String) o;
                temp=temp.trim().toLowerCase();
             return temp.equals("")||temp.length()==0;
            }else if (o instanceof ArrayList){
                ArrayList arrayList= (ArrayList) o;
                return arrayList.size()==0;
            }else if (o instanceof String[]){
                String [] newStr= (String[]) o;
                return newStr.length==0;
            }else if (o instanceof Boolean[]){
                Boolean[] booleans= (Boolean[]) o;
                return booleans.length==0;
            }else if (o instanceof Integer[]){
                Integer[] integers= (Integer[]) o;
                return integers.length==0;
            }else if (o instanceof char[]){
                char[] chars= (char[]) o;
                return chars.length==0;
            }else if (o instanceof Character[]){
                Character[] characters= (Character[]) o;
                return characters.length==0;
            }else if (o instanceof int[]){
                int[] ints= (int[]) o;
                return ints.length==0;
            }else if (o instanceof Long[]){
                Long[] longs= (Long[]) o;
                return longs.length==0;
            }else if (o instanceof Double[]){
                Double[] doubles= (Double[]) o;
                return doubles.length==0;
            }else if (o instanceof double[]){
                double[] doubles= (double[]) o;
                return doubles.length==0;
            }else if (o instanceof long[]){
                long[] longs= (long[]) o;
                return longs.length==0;
            }else if (o instanceof boolean[]){
                boolean[] booleans= (boolean[]) o;
                return booleans.length==0;
            }else if (o instanceof CharSequence[]){
                CharSequence[] sequences= (CharSequence[]) o;
                return sequences.length==0;
            }else if (o instanceof CharSequence){
                CharSequence sequences= (CharSequence) o;
                String string = sequences.toString().trim();
                return null==string||string.length()==0||string.equals("");
            }
            return false;
        }
        return true;
    }
    public static boolean isNotEmpty(Object o){
        return !isEmpty(o);
    }
}
