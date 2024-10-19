<%@ page language="java" %>
<%@ page import="java.util.*" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">

<head>
<jsp:include page="/spd/jsp/global/head.jsp"/>
<title>Listado residentes sin procesar</title>
</head>
<script type="text/javascript">	
function cerrar()
{
	
	window.close();
	
}	
</script>


<bean:define id="formulari" name="GenericForm" type="lopicost.spd.struts.form.GenericForm" />


<body id="general">

<html:form action="/ExtRe.do" method="post">	
<html:errors/>


<div id="contingut">
    <html:hidden property="parameter" value="sinProcesar"/>
    <html:hidden property="ACTIONTODO" value="list"/>
	<% String numPages = formulari.getNumpages()+""; %>
    <% String currpage = formulari.getCurrpage()+""; %>
    <html:hidden property="numpages" value="<%= numPages %>"/>
	<html:hidden property="currpage" value="<%= currpage %>"/>
	
	   	<!-- mostramos mensajes y errores, si existen -->
		<logic:notEmpty name="formulari" property="errors">
			<font color="red"><ul>
				<u>Mensaje:</u>
					<logic:iterate id="error" name="formulari" property="errors" type="java.lang.String">
							<li><bean:write name="error"/></li>
					</logic:iterate>
				</ul></font>
		</logic:notEmpty>

	<table border="1">
		<tr>
		<th>CIP</th>
	 	<th>Seg Social</th>
		<th>Apellidos, nombre</th>
		<th>Estado</th>
		<th>Mensaje</th>
	   </tr>


 	<logic:iterate id="data" name="formulari" property="listaBdPacientes" type="lopicost.spd.struts.bean.PacienteBean" indexId="position">
		
	<tr class="<bean:write name="data" property="claseId" />">
		<td><bean:write name="data" property="CIP" /></td>
		<td><bean:write name="data" property="segSocial" /></td>
		<td><bean:write name="data" property="apellidosNombre" /></td>
		<td><bean:write name="data" property="activo" /></td>
		<td><bean:write name="data" property="mensajeTratamiento" /></td>
		


	</tr>
	
	
    </logic:iterate>


    
</table>
    <p class="botonBuscar">
			<input type="button" onclick="javascript:cerrar()" value="cerrar"/>
	</p> 
		
		
	<!--  paginación  -->
	<div>
		<logic:greaterThan name="formulari" property="numpages" value="1">
			<p align="center">
				<logic:greaterThan name="formulari" property="currpage" value="0">
					<a href="javascript:pageDown();" ><<</a>
				</logic:greaterThan>
				&nbsp;<input type="text" name="newPage" value="<%= formulari.getCurrpage()+1 %>" size="1" maxlength="4" onkeypress="gotoPage(this.value,<%=formulari.getNumpages()%>);">/<%= formulari.getNumpages() %>&nbsp;
				<logic:lessThan name="formulari" property="currpage" value="<%= String.valueOf(formulari.getNumpages() -1)%>">
					<a href="javascript:pageUp();" >>></a>
				</logic:lessThan>
			</p>
		</logic:greaterThan>
	</div>
	<!--  paginación   -->
		
	</div>	
	</html:form>

</body>
</html>