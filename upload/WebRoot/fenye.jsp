<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'fenye.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <form action="PageServlet" method="post" id="myform">
        <table border="1">
            <c:forEach items="${pageBean.arrayObjs }" var="obj">
                <tr>
                    <td>${obj }</td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="3">
                
                    跳转到：<input id="currentPageNum" type="text" name="currentPageNum" value="${pageBean.currentPageNum }" />
                    每页记录数：<input type="text" name="pageCount" value="${pageBean.pageCount }" />
                    <br />
                    <input type="submit" value="跳转" />
                    共有${pageBean.totalPage }
                    <c:if test="${pageBean.previous }">
                        <a onclick="page(${pageBean.currentPageNum}-1);">上一页</a>
                    </c:if>
                    <c:if test="${pageBean.next }">
                        <a onclick="page(${pageBean.currentPageNum}+1);">下一页</a>
                    </c:if>
                    <a onclick="page(${pageBean.totalPage});">最后一页</a>
                </td>
            </tr>
        </table>
    </form>
  </body>
</html>
