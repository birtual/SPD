<%@ page language="java" %>
<%@ page import="java.util.*" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">


<head>
<jsp:include page="/spd/jsp/global/head.jsp"/>

<title>Índice</title>
</head>

<bean:define id="formulari" name="LoginForm" type="lopicost.spd.security.form.LoginForm" />
<%
request.getSession().setAttribute("idUsuario", formulari.getIdUsuario());

String usuario = formulari.getIdUsuario();
request.setAttribute("idUsuario", usuario);
out.print(usuario);
%>

<body id="general">
<p>&nbsp;</p>
<div>
<H3>LOGIN</H3>
<p><html:link page="/login.do?parameter=list">Login</html:link></p>  
<p>&nbsp;</p>
</div>
<div>
<H3>IOSPD</H3>
<p><html:link page="/Iospd/Iospd.do?parameter=list&operation=FILTER">Gestión de carga de ficheros resi</html:link></p> 
<p>&nbsp;</p>
</div>
<div>
<H3>GESTION DE SUSTITUCIONES</H3>
<p><html:link page="/SustXComposicion.do?parameter=list">Gestión de sustituciones por composición (Principio activo + dosis + forma --> Gtvmpp)  </html:link></p> 
<p><html:link page="/GestSustituciones.do?parameter=list">Gestión de sustituciones (cnResi/cnSust)</html:link></p>
<p><html:link page="/GestSustitucionesLite.do?parameter=list">Gestión de sustituciones LITE (cnResi/cnSust) </html:link></p>


<p>&nbsp;</p>
</div>
<div>


<H3>FICHEROS</H3>
<p><html:link page="/FicheroResiCabecera.do?parameter=list">Gestión de ficheros de residencia (FicheroResiCabecera)</html:link></p> 
<p><html:link page="/FicheroResiCabeceraLite.do?parameter=list">Gestión de ficheros de residencia (LITE)</html:link></p> 

<p>&nbsp;</p>
</div>
<div>

<H3>PENDIENTES</H3>
<p><html:link page="/Iospd/ImportGestion.do?parameter=list&operation=FILTER">Mantenimiento de tablas</html:link></p> 

<p><html:link page="/Pacientes.do?parameter=list">Pacientes</html:link></p>  
<p><html:link page="/Pacientes.do?parameter=listadoProceso">Pacientes Fichero</html:link></p>  
<p><html:link page="/Enlaces.do?parameter=list&ACTIONTODO=BIRT&free1=">BIRT Reports</html:link></p>

<p><html:link page="/Menu.do?parameter=list">Gestión del menú </html:link></p>


</div>
<!--
<p><html:link page="/user.do?parameter=add">Call Add Section</html:link></p> 
<p><html:link page="/user.do?parameter=edit">Call Edit Section</html:link></p> 
<p><html:link page="/user.do?parameter=search">Call Search Section</html:link></p> 
<p><html:link page="/user.do?parameter=save">Call Save Section</html:link></p> 
-->
