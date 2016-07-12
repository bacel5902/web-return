package com.bacel.returns.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ReturnUtil {
	public final static String URLPARENT ="urlParent";
	public final static String CURRENTURLDECODE="currentUrlDecode";
//	public final static String USER = "user";
	public final static String HEADER_ACCEPT ="Accept";
	  /**
	   * 加密url数据
	   * @param data
	   * @return
	   */
	  public static String encodeURL(String data){
			try {
				return	URLEncoder.encode(Base64.encodeBase64URLSafeString(data.getBytes()),"utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				return null;
			}
		}
	  /**
	   * 解密URL数据
	   * @param data
	   * @return
	   */
	  public static String decodeURL(String data){
		  try {
				return	new String(Base64.decodeBase64(java.net.URLDecoder.decode(data,"utf-8")));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				return null;
			}
	  }
	  
		/**
		 * 解析请求参数
		 * 
		 * @param request
		 * @return  相对url路径带参数
		 * @throws UnsupportedEncodingException 
		 */
		public static String getRequestUrl(HttpServletRequest request) throws UnsupportedEncodingException {
			StringBuffer param = new StringBuffer();			 
			String header = request.getServletPath()+"?";
			if(header.indexOf("/")==0){
				header = header.substring(1);
			}			 		
			Map<String, String[]> map = request.getParameterMap();  
			         Set<Entry<String, String[]>> set = map.entrySet();  
			        Iterator<Entry<String, String[]>> it = set.iterator();  
			         while (it.hasNext()) {  
			             Entry<String, String[]> entry = it.next(); 	
			             if(!URLPARENT.equals(entry.getKey())){//剔除掉重复的urlParent
				            for (String i : entry.getValue()) { 
				            		param.append(entry.getKey());
									param.append("=");
									param.append(i);
									param.append("&");
				            }  
			             }
		        } 
			String body = param.toString();			 
			if(body.length()>0){
				body = body.substring(0,body.length()-1);
			}
			return header+body;
		}
}
