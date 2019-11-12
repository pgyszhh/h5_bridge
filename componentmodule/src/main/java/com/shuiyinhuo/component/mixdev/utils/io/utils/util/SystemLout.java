package com.shuiyinhuo.component.mixdev.utils.io.utils.util;

import com.shuiyinhuo.component.mixdev.cnf.comm.StaticConfig;

public class SystemLout {
	private static final String TAT="---------------->";
	
	public static void out(String content){
		if (StaticConfig.isShowLogin) {
			System.out.println(TAT + content);
		}
	}
}
