package com.laky.edu.util;

/**
 * @author WYB
 */

import org.springframework.util.StringUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class StringUtil {
   public static ScriptEngine js = new ScriptEngineManager().getEngineByName("JavaScript");
    
	public static String encodePassword(String password, String algorithm) {
		byte[] unencodedPassword = password.getBytes();
		MessageDigest md;
		try {
			md = MessageDigest.getInstance(algorithm);
		} catch (Exception e) {
			return password;
		}
		md.reset();
		// call the update method one or more times
		// (useful when you don't know the size of your data, eg. stream)
		md.update(unencodedPassword);
		// now calculate the hash
		byte[] encodedPassword = md.digest();
		StringBuffer buf = new StringBuffer();
		for (byte anEncodedPassword : encodedPassword) {
			if ((anEncodedPassword & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString(anEncodedPassword & 0xff, 16));
		}
		return buf.toString();
	}



	/**
	 * Decode a string using Base64 encoding.
	 *
	 * @return String
	 */
	/*public static String decodeString(String str) {
		Base64 dec = new Base64();
		try {
			return String.valueOf(dec.decode(str));
		} catch (DecoderException de) {
			throw new RuntimeException(de.getMessage(), de.getCause());
		}
	}*/


	public static String toFirstUpper(String str) {
		return Character.toUpperCase(str.charAt(0)) + str.substring(1);
	}

	public static String[] split(String str, char c) {
		int pos = str.indexOf(c);
		if (pos < 0) {
			return new String[]{str};
		}
		List<String> strList = new ArrayList<String>();
		if (pos == 0) {
			strList.add("");
		} else {
			strList.add(str.substring(0, pos));
		}
		while (pos >= 0) {
			int end = str.indexOf(c, pos + 1);
			if (end < 0) {
				end = str.length();
			}
			if (end - pos == 1) {
				strList.add("");
			} else {
				strList.add(str.substring(pos + 1, end));
			}
			pos = str.indexOf(c, pos + 1);
		}
		return strList.toArray(new String[strList.size()]);
	}
    public static String random(int randStrLength, int type)
    {
        String randStr = "";
        if(type == 1)
            randStr = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if(type == 2)
            randStr = "0123456789";
        if(type == 3)
            randStr = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if(type == 4)
            randStr = "0123456789abcdefghijklmnopqrstuvwxyz";
        StringBuffer generateRandStr = new StringBuffer();
        Random rand = new Random();
        for(int i = 0; i < randStrLength; i++)
        {
            int randNum = rand.nextInt(randStr.length());
            generateRandStr.append(randStr.substring(randNum, randNum + 1));
        }

        return generateRandStr.toString();
    }
    public static boolean isNumeric(String str){
      for (int i = str.length();--i>=0;){
       if (!Character.isDigit(str.charAt(i))){
        return false;
       }
      }
      return true;
     }

    /**
     * 符号环绕
     * 将例如string=a,b,c regex=，symbol=‘  返回形式为'a','b','c'
     * @param string  要换换的字符串
     * @param regex   分隔符
     * @param symbol  需要环绕的符号
     * @return
     */
    public static String surroundSymbol(String string ,String regex,String symbol){
        String[] array = string.split(regex);
        StringBuilder stringBuilder = new StringBuilder();
        for(String s:array){
            stringBuilder.append(symbol).append(s).append(symbol).append(regex);
        }
        if(stringBuilder.indexOf(",")!=-1)
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
        return stringBuilder.toString();
    }

    /**
     * 将inputStream转为String
     * @param inputStream
     * @return
     */
    public static String inputStream2String(InputStream inputStream){
        StringBuilder stringBuilder = new StringBuilder();

        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        String line = "";
        try {
            while ((line = in.readLine()) != null){
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return stringBuilder.toString();
    }


	/**
	 * 替换四个字节的字符 '\xF0\x9F\x98\x84\xF0\x9F）的解决方案 😁
	 * @author ChenGuiYong
	 * @data 2015年8月11日 上午10:31:50
	 * @param content
	 * @return
	 */
	public static String removeFourChar(String content) {
		byte[] conbyte = content.getBytes();
		for (int i = 0; i < conbyte.length; i++) {
			if ((conbyte[i] & 0xF8) == 0xF0) {
				for (int j = 0; j < 4; j++) {
					conbyte[i+j]=0x30;
				}
				i += 3;
			}
		}
		content = new String(conbyte);
		return content.replaceAll("0000", "");
	}

	public static boolean isEmpty(Object obj){
		if (obj==null){
			return true;
		}else if (obj instanceof String){
			return StringUtils.isEmpty(String.valueOf(obj));
		}else if (obj instanceof Collection){
			return ((Collection)obj).size()==0;
		}else {
			return obj==null;
		}
	}

	/**
	 * 计算 表达式
	 * 'a'=='a'&&1==2
	 * 'abc'.indexOf('d')!=-1"
	 * 'a'=='a'&&(1==2 || 'abc'.indexOf('d')!=-1)
	 * @param param
	 * @return
	 */
	public static Object runExpression(String param){
		try {
			return js.eval(param);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args){

		System.out.println("{name:'${params}'}".startsWith("{||["));
	}

}
