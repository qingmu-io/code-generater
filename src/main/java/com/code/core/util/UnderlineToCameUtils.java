package com.code.core.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnderlineToCameUtils {
	public static final char UNDERLINE='_';
	public static String camelToUnderline(String param){
	    if (StringUtils.isBlank(param)){
	        return StringUtils.EMPTY;
	    }
	    int len=param.length();
	    StringBuilder sb=new StringBuilder(len);
	    for (int i = 0; i < len; i++) {
	        char c=param.charAt(i);
	        if (Character.isUpperCase(c)){
	            sb.append(UNDERLINE);
	            sb.append(Character.toLowerCase(c));
	        }else{
	            sb.append(c);
	        }
	    }
	    return sb.toString();
	}
	public static String underlineToCamel(String param){
	    if (param==null||"".equals(param.trim())){
	        return "";
	    }
	    int len=param.length();
	    StringBuilder sb=new StringBuilder(len);
	    for (int i = 0; i < len; i++) {
	        char c=param.charAt(i);
	        if (c==UNDERLINE){
	           if (++i<len){
	               sb.append(Character.toUpperCase(param.charAt(i)));
	           }
	        }else{
	            sb.append(c);
	        }
	    }
	    return sb.toString();
	}
	public static String underlineToCamel2(String param){
	    if (StringUtils.isBlank(param)){
	        return StringUtils.EMPTY;
	    }
	    StringBuilder sb=new StringBuilder(param);
	    Matcher mc= Pattern.compile("_").matcher(param);
	    int i=0;
	    while (mc.find()){
	        int position=mc.end()-(i++);
	        sb.replace(position-1,position+1,sb.substring(position,position+1).toUpperCase());
	    }
	    return sb.toString();
	}
}