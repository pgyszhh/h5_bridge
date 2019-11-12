package com.shuiyinhuo.component.mixdev.utils.io.utils;

import java.util.List;
import java.util.Map;

public class Utils {
	public static boolean isEmpty(Object obj){
		String temp=null;
		if(obj==null){
			return true;
		}
		
		if(obj instanceof String){
			String tag=(String) obj;
			temp=tag+"";
		}
		
		if(obj instanceof Boolean){
			Boolean tag=(Boolean) obj;
			temp=tag+"";
		}
		
		if(obj instanceof Integer){
			Integer tag=(Integer) obj;
			temp=tag+"";
		}
		
		if(obj instanceof Map<?, ?>){
			Map<?, ?> tag=(Map<?, ?>) obj;
			return tag.size()==0;
		}
		
		if(obj instanceof Double){
			Double tag=(Double) obj;
			temp=tag+"";
		}
		
		if(obj instanceof Float){
			Float tag=(Float) obj;
			temp=tag+"";
			
		}
		
		if(obj instanceof List<?>){
			List<?> tag=(List<?>) obj;
			return tag.size()==0;
		}
		
		/*if(obj instanceof String){
			
		}*/
		
				
		return (temp!=null)?lenIsZero(temp):obj==null;
	}
	
	
	private static boolean lenIsZero(String str){
		return str.trim().length()==0;
	}
}
