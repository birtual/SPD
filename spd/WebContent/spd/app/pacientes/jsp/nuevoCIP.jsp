<%@ page language="java" %>
<%@ page import="java.util.*" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">

<head>
<jsp:include page="/spd/jsp/global/head.jsp"/>
<title>Creación de usuario SPD</title>
</head>

<bean:define id="formulari" name="PacientesForm" type="lopicost.spd.struts.form.PacientesForm" />
<script language="javaScript" src="/spd/spd/app/pacientes/js/pacientes.js"></script>


<body id="general">

<html:form action="/Pacientes.do" method="post">	
<html:errors/>

<div id="contingut">
<bean:define id="data" name="formulari" property="pacienteBean" />

    <html:hidden property="parameter" value="nuevo"/>
    <html:hidden property="ACTIONTODO" value="NUEVO_CIP"/>
	   	<!-- mostramos mensajes y errores, si existen -->
		<logic:notEmpty name="formulari" property="errors">
			<font color="red"><ul>
				<u>Mensaje:</u>
					<logic:iterate id="error" name="formulari" property="errors" type="java.lang.String">
							<li><bean:write name="error"/></li>
					</logic:iterate>
				</ul></font>
		</logic:notEmpty>
		
		
  		<div>
			<p>CIP</p><p><html:text name="data" property="CIP"/></p>
			 <html:errors property="CIP" />
		</div>
  		<div>
			<p class="botons">
				<input type="button" onclick="javascript:volver()" value="Volver"/>
				<input type="button" onclick="javascript:goNuevo()" value="Crear nuevo"/>
			</p>	
	</div>
	

		     
	
</div>	
</html:form>

</body>
</html>