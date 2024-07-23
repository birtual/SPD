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
<title>Edición enlace</title>

</head>


<bean:define id="formulari" name="EnlacesForm" type="lopicost.spd.struts.form.EnlacesForm" />

<head>
<script type="text/javascript">	
		
		function volver()
		{
			var f = document.EnlacesForm;
			f.parameter.value='admin';
			f.ACTIONTODO.value='ADMIN';
			f.submit();
		}	
		
		function grabar(idEnlace)
		{
			var f = document.EnlacesForm;

			f.parameter.value='editar';		
			f.ACTIONTODO.value='EDITA_OK';
			f.idEnlace.value=idEnlace;
			f.submit();
		}	

				
</script>





<body id="general">
	<div id="contingut">
		<center>
		<h2>Edición sustitución lite </h2>
		<html:form action="/Enlaces.do" method="post">	

		<bean:define id="data" name="formulari" property="enlace"/>



		 <html:hidden property="idEnlace" />
		 <html:hidden property="parameter" value="editar"/>
		 <html:hidden property="ACTIONTODO" value="EDITA"/>





		<table class="tablaResi">
		<tr>
			<th class="segunda">idEnlace</th><th><html:text name="data" property="idEnlace" styleClass="inputLargo" /></th>
		</tr>
		<tr>
			<th class="segunda">Enlace</th><th><html:text name="data" property="aliasEnlace" styleClass="inputLargo" /></th>
		</tr>
		<tr>
			<th class="segunda">Nombre enlace</th><th><html:text name="data" property="nombreEnlace" styleClass="inputLargo" /></th>
		</tr>
		<tr>
			<th class="segunda">Descripción</th><th><html:text name="data" property="descripcion"  styleClass="inputLargo" /></th>
		</tr>
		<tr>
			<th class="segunda">idapartado</th><th><bean:write name="data" property="idApartado"  /></th>
		</tr>
		<tr>
			<th class="segunda">preEnlace</th><th><html:text name="data" property="preEnlace" styleClass="inputLargo" /></th>
		</tr>
		<tr>
			<th class="segunda">link</th><th><html:text name="data" property="linkEnlace" styleClass="inputLargo" /></th>
		</tr>
		<tr>
			<th class="segunda">Parámetros</th><th><html:text name="data" property="paramsEnlace" styleClass="inputLargo" /></th>
		</tr>
		<tr>
			<th class="segunda">activo</th><th><html:text name="data" property="activo" styleClass="inputLargo" /></th>
		</tr>
		<tr>
			<th class="segunda">abrir en nueva ventana</th><th><html:text name="data" property="nuevaVentana" styleClass="inputLargo" /></th>
		</tr>
			
		</table>

		<tr>
			<td>	
				<p class="botons">
				<input type="button" onclick="javascript:volver('<bean:write name="formulari" property="idEnlace"/>')" value="Volver"/>
				<input type="button" onclick="javascript:grabar('<bean:write name="formulari" property="idEnlace"/>')" value="Confirmar"/>
				</p>	
			</td>	
		</tr>	
	</table>

	</div>	

	</html:form>

</body>
</html>