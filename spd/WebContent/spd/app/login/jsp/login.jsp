<%@page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%> 

<%@ page import="javax.servlet.http.HttpSession" %>


<!DOCTYPE html>
<html lang="es">
<head>
	<!-- link rel="stylesheet"  href="spd/css/login.css"> -->
	<link rel="stylesheet"  href="spd/css/login.css"> 
	<meta http-equiv="Content-Type" pageEncoding="text/html; charset=ISO-8859-1" %>
	<TITLE>Formulario acceso </TITLE>
</head>	

<body>
  <div class="form-body"> 
    <!--img src="spd/img/logo.jpg" alt="user-login"-->

    <p class="text">Formulario de acceso</p>
    <html:form action="/login">
      <input type="text" name="idUsuario" placeholder="Nombre de usuario">
      <input type="password" name="password" placeholder="Contrase�a">
      <button>Iniciar sesi�n</button>	
      
      <html:hidden  property="parameter" value="list"/>
    </html:form>
  </div>	

  <%-- Verifica si el formulario se ha enviado y almacena el nombre de usuario en la sesi�n --%>
  <%
  if ("POST".equalsIgnoreCase(request.getMethod())) {
    String idUsuario = request.getParameter("idUsuario");  
    session.setAttribute("idUsuario", idUsuario);
  }
  %>
</body>
</html>
