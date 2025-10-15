<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%> 

<%@ page import="javax.servlet.http.HttpSession" %>


<!DOCTYPE html>
<html lang="es">
<head>
	<link rel="stylesheet"  href="spd/css/login.css">
	<meta http-equiv="Content-Type" pageEncoding="text/html; charset=UTF-8" %>
	<TITLE>Formulario acceso </TITLE>
</head>	

<body>
	<div class="form-body"> 
		<!--img src="spd/img/logo.jpg" alt="user-login"-->
		<!-- mostramos mensajes y errores, si existen -->

<%

// Recupera el valor del idlogin proporcionado por el usuario
String error = (String)session.getAttribute("error"); 
	if(error!=null && !error.isEmpty())
	{
		%>
		<p class="text"><font color="red">
			<%=error%>
		</font>
		</p>
		<%
	}
%>


	
	
		<p class="text">Formulario de acceso</p>
		<html:form action="/login">
			<input type="text" name="idUsuario" placeholder="Nombre de usuario">
			<input type="password" name="password" placeholder="Contraseña">
			<button>Iniciar sesión</button>	
			
			
			<html:hidden  property="parameter" value="list"/>
			
		</html:form>
	</div>	
<%

// Recupera el valor del idlogin proporcionado por el usuario
String idUsuario = request.getParameter("idUsuario"); 

// Almacenar el idlogin en la sesión
session.setAttribute("idUsuario", idUsuario);

%>
	
	
	<body>
</html>
