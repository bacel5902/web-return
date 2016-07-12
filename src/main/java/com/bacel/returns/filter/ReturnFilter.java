package com.bacel.returns.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bacel.returns.util.ReturnUtil;
/**
 * 过滤器实现返回事件
 * @author cy
 *
 */
public class ReturnFilter extends HttpServlet implements Filter{

	private static final long serialVersionUID = -355105405910803550L;
	private String excludedPages;
	private String sessionString;
	private String[] excludedPageArray;  
	 /** 
	 * @see Filter#destroy() 
	 */  
	 public void destroy() {  
	 return;  
	 }  

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		boolean isExcludedPage = false;  
		 for (String page : excludedPageArray) {//判断是否在过滤url之外  
		 if(((HttpServletRequest) request).getServletPath().contains(page)){  
		    isExcludedPage = true;  
		 break;  
		 }  
		}  
		if (isExcludedPage) {//在过滤url之外  
			filterChain.doFilter(request, response);  
			} else {			
			String accept = request.getHeader(ReturnUtil.HEADER_ACCEPT);
			//sessionString非空使用  登录后才能使用
			if(accept!=null && accept.contains("text/html") && ( sessionString == null || request.getSession().getAttribute(sessionString)!=null) && !request.getServletPath().equals("/")){
				
				String urlParent = request.getParameter(ReturnUtil.URLPARENT);//请求的数据里面是encode的
				if(urlParent!=null && urlParent.length()>0 && !urlParent.equalsIgnoreCase("null")){
					//2级页面  返回按钮使用
					String urlParent2 = ReturnUtil.decodeURL(urlParent);
					request.setAttribute(ReturnUtil.URLPARENT,urlParent2);//返回给第2级菜单的urlParent是明文的
//					System.out.println(urlParent+"|"+urlParent2);
					//末级叶子页面   无需返回currentUrlDecode
					request.setAttribute(ReturnUtil.CURRENTURLDECODE,ReturnUtil.encodeURL(ReturnUtil.getRequestUrl(request)+"&"+ReturnUtil.URLPARENT+"="+urlParent));
				}
				else{
					//1级页面
					request.setAttribute(ReturnUtil.CURRENTURLDECODE,ReturnUtil.encodeURL(ReturnUtil.getRequestUrl(request)));//返回给第一级菜单的urlParent是密文的，
				}
			}
			
//			System.out.println(response.getContentType());
			filterChain.doFilter(request, response);
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		 excludedPages = filterConfig.getInitParameter("excludedPages");  
		 if (excludedPages!=null && excludedPages.length()>0) {  
		 excludedPageArray = excludedPages.split(",");  
		 }  
		 sessionString = filterConfig.getInitParameter("sessionString");
		 return; 		
	}
	

	
	  
	  

		
//		public static void main(String[] args) throws IOException {
//			String data = "aHR0cDovL2xvY2FsaG9zdDo4MDgwL2UtaG9tZS1wbGF0Zm9ybS13ZWIvcGVyc29ucy9vcGVy";			
//			 String x= Base64.encodeBase64URLSafeString(data.getBytes());			 
//			 String x2= new sun.misc.BASE64Encoder().encodeBuffer(data.getBytes());
//			 String x3 = com.eshore.crypto.Base64.encode(data.getBytes());
//			
//			System.out.println(x);
//			System.out.println(x2);
//			System.out.println(x3);
////			System.out.println(new String(new sun.misc.BASE64Decoder().decodeBuffer(java.net.URLDecoder.decode(x2,"utf-8"))));
//		}

}
