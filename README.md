spring后台会在过滤器里会自动拼接urlParent和currentUrlDecode参数


jsp页面编码时：
1，在父页面跳转打开子页面的URL，需要带上参数： urlParent=${currentUrlDecode}	
2，子页面返回按钮的js代码统一使用： window.location.href = "${basePath}/"+"${urlParent}";
   其中basePath为${pageContext.request.contextPath}
例如
   function  pageBack(){
	window.location.href = "${basePath}/"+"${urlParent}";
   }


配置步骤
1，将web-return-0.0.1-SNAPSHOT.jar加载到项目里。
2，修改web.xml，加入如下filter配置：
	 <filter>
		<filter-name>returnFilter</filter-name>
		<filter-class>com.bacel.returns.filter.ReturnFilter</filter-class>
		<init-param>
			<!-- 例外的页面路径，因计算currentUrlDecode耗时，例外的路径可以不用处理-->
			<param-name>excludedPages</param-name>
			<param-value>resources/,persons/login/,persons/register/</param-value>
		</init-param>
		<init-param>
			<!-- 如果session属性sessionString值，非空则支持回退;此处支持扩展支持仅仅登录后才能回退-->
			<param-name>sessionString</param-name>
			<param-value></param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>returnFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping> 


谢谢大家，如果有新构思，请及时分享.

