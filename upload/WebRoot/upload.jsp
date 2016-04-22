<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>文件上传</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
 <!-- 图片上传预览和文件格式限制-->
 <script> 
   function PreviewImage(imgFile) 
   { 
    var pattern = /(\.*.jpg$)|(\.*.png$)|(\.*.jpeg$)|(\.*.gif$)|(\.*.bmp$)/;      
    if(!pattern.test(imgFile.value)) 
    { 
     alert("系统仅支持jpg/jpeg/png/gif/bmp格式的照片！");  
     imgFile.focus(); 
    } 
    else 
    { 
     var path; 
     if(document.all)//IE 
     { 
      imgFile.select(); 
      path = document.selection.createRange().text; 
      document.getElementById("imgPreview").innerHTML=""; 
      document.getElementById("imgPreview").style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled='true',sizingMethod='scale',src=\"" + path + "\")";//使用滤镜效果 
     } 
     else//FF 
     { 
      path = URL.createObjectURL(imgFile.files[0]);
      document.getElementById("imgPreview").innerHTML = "<img src='"+path+"' style='width:150px; height:200px;'/>"; 
     } 
    } 
   } 
  </script> 
  </head>
  
  <body>
    
	<form action="UploadHandleServlert" method="post" enctype="multipart/form-data">
	 欢迎您，${username}<br/>
	上传用户：<input type="text" name="BT"><br/>
	上传图片：<input type="file" name="file1" onchange='PreviewImage(this)'>
	<div id="imgPreview"></div><br/>
	<input type="submit" value="提交">
	</form>
  </body>
</html>
