<%@page import="java.awt.Window"%>
<%@page import="java.util.concurrent.TimeUnit"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dao.UserDAO" %>
<%
	request.setCharacterEncoding("utf-8");
 
	String uid = request.getParameter("id");
    UserDAO dao = new UserDAO();

    if (dao.exists(uid)) {
    	%>
 	   <script>
 	   alert("이미 가입된 ID입니다");
 	   </script>
 	   <%
    }
    else{
    	%>
    	<script>
    	alert("사용 가능한 ID입니다");
    	</script>
    	<%
    }
    
%>
