spring��̨���ڹ���������Զ�ƴ��urlParent��currentUrlDecode����


jspҳ�����ʱ��
1���ڸ�ҳ����ת����ҳ���URL����Ҫ���ϲ����� urlParent=${currentUrlDecode}	
2����ҳ�淵�ذ�ť��js����ͳһʹ�ã� window.location.href = "${basePath}/"+"${urlParent}";
   ����basePathΪ${pageContext.request.contextPath}
����
   function  pageBack(){
	window.location.href = "${basePath}/"+"${urlParent}";
   }


���ò���
1����web-return-0.0.1-SNAPSHOT.jar���ص���Ŀ�
2���޸�web.xml����������filter���ã�
	 <filter>
		<filter-name>returnFilter</filter-name>
		<filter-class>com.bacel.returns.filter.ReturnFilter</filter-class>
		<init-param>
			<!-- �����ҳ��·���������currentUrlDecode��ʱ�������·�����Բ��ô���-->
			<param-name>excludedPages</param-name>
			<param-value>resources/,persons/login/,persons/register/</param-value>
		</init-param>
		<init-param>
			<!-- ���session����sessionStringֵ���ǿ���֧�ֻ���;�˴�֧����չ֧�ֽ�����¼����ܻ���-->
			<param-name>sessionString</param-name>
			<param-value></param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>returnFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping> 


лл��ң�������¹�˼���뼰ʱ����.

