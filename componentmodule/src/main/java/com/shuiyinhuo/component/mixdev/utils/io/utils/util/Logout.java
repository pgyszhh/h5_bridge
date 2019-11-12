package com.shuiyinhuo.component.mixdev.utils.io.utils.util;


import android.util.Log;

public class Logout {
	private static final String TAT="---------------->";
	
	public static void out(String content){
		System.out.println(TAT+content);
	}
	public static void e(String tag,String msg){
		Log.e(tag,msg);
	}
}
