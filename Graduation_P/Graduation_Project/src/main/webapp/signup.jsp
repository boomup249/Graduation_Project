<%@page import="java.sql.Date"%>
<%@page import="java.util.concurrent.TimeUnit"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dao.UserDAO" %>
<%
	request.setCharacterEncoding("utf-8");
%>
	<jsp:useBean id="user" class="dao.UserBean">
		<jsp:setProperty name="user" property="*" />
	</jsp:useBean>
<%  
	String uid = request.getParameter("id");
	String upass = request.getParameter("ps"); 
	String cupass = request.getParameter("ps2");
    UserDAO dao = new UserDAO();

    if (dao.exists(user.getId())) {
    	%>
  	   <script>
  	   alert("이미 가입된 ID입니다");
  	   location.href = "signupForm.jsp";
  	   </script>
  	   <%
        return;
    }
    if(!upass.equals(cupass)){
    	%>
 	   <script>
 	   alert("비밀번호가 일치하지 않습니다.");
 	   location.href = "signupForm.jsp";
 	   </script>
 	   <%
 	   return;
    }
    
   if (dao.insert(user)) {
	   uid = (String) session.getAttribute("id");
	   upass = (String) session.getAttribute("id");
	   %>
	   <script>
	   alert("회원가입이 완료되었습니다");
	   location.href = "main.jsp";
	   </script>
	   <%
   }
   else {
	   %>
 	   <script>
 	   alert("회원 가입 중 오류가 발생했습니다");
 	   </script>
 	   <%
   }
   
%>
