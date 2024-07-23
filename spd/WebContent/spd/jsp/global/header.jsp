<%@ page language="java" %>

	<jsp:include page="skin.jsp"/>
	<script>
	 document.write('<link rel="stylesheet" href=<%= "\"" +request.getContextPath() + "/admin/'+getSkin()+'/dbforms.css\""%>  />');				 
	 </script>

	