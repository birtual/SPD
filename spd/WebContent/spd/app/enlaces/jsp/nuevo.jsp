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
<title>Creación de enlace</title>
</head>


<bean:define id="formulari" name="EnlacesForm" type="lopicost.spd.struts.form.EnlacesForm" />

	<script type="text/javascript">	
			function volver()
			{
				var f = document.EnlacesForm;
				f.parameter.value='admin';
				f.ACTIONTODO.value='ADMIN';
				f.submit();
			}	
			
			function nuevo()
			{
		
				var f = document.EnlacesForm;
			
				if(f.idEnlace.value=='')
					alert('Falta indicar el idEnlace');
				else if(f.nombreEnlace.value=='')
					alert('Falta indicar el nombre del enlace ');
				else if(f.descripcion.value=='')
					alert('Falta indicar la descripcion del enlace ');
				else if(f.idApartado.value=='')
					alert('Falta indicar el apartado del enlace ');
				else if(f.preEnlace.value=='')
					alert('Falta indicar el preEnlace del enlace ');
				else if(f.linkEnlace.value=='')
					alert('Falta indicar el linkEnlace del enlace ');
				else
				{
					//f.nombreCorto.value=f.nombreCorto.value;
					f.parameter.value='nuevo';
					f.ACTIONTODO.value='NUEVO_OK';

					f.submit();
				}
			}
	</script>




<body id="general">
<div id="contingut">
	<center>
		<h2>Creación de enlace</h2>
		<html:form action="/Enlaces.do" >	
		
		<bean:define id="data" name="formulari" property="enlace" />
		 <html:hidden property="parameter" value="nuevo"/>
		 <html:hidden property="ACTIONTODO" value="NUEVO"/>

   	<!-- mostramos mensajes y errores, si existen -->
	<logic:notEmpty name="formulari" property="errors">
		<ul>
		<font color="red">
			<u>Mensaje:</u>
				<logic:iterate id="error" name="formulari" property="errors" type="java.lang.String">
						<li><bean:write name="error"/></li>
				</logic:iterate>
		</font>
		</ul>
	</logic:notEmpty>
	
	

		<table class="tablaResi">
		<tr>
			<th class="segunda">idEnlace</th><th><html:text name="data" property="idEnlace" styleClass="inputLargo" /></th>
		</tr>

		<tr>
			<th class="segunda">Nombre enlace</th><th><html:text name="data" property="nombreEnlace" styleClass="inputLargo" /></th>
		</tr>
		<tr>
			<th class="segunda">Descripción</th><th><html:text name="data" property="descripcion"  styleClass="inputLargo" /></th>
		</tr>
		<tr>
			<th class="segunda">idapartado</th><th><html:text name="data" property="idApartado"  styleClass="inputLargo" /></th>
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
			<th class="segunda">activo</th>
			<td>
				<select name="activo" style="text-align: left;">>
				    <option value="true" ${data.activo ? 'selected="selected"' : ''}>Sí</option>
				    <option value="false" ${!data.activo ? 'selected="selected"' : ''}>No</option>
				</select>
			</td>
		</tr>
		<tr>
			<th class="segunda">abrir en nueva ventana</th>
			<td>
				<select name="nuevaVentana" style="text-align: left;">
				    <option value="true" ${data.nuevaVentana ? 'selected="selected"' : ''}>Sí</option>
				    <option value="false" ${!data.nuevaVentana ? 'selected="selected"' : ''}>No</option>
				</select>
			</td>
		</tr>
			
		</table>

	<tr>
		<td>	
		<p class="botons">
			<input type="button" onclick="javascript:volver()" value="Volver"/>
			<input type="button" onclick="javascript:nuevo()" value="Confirmar"/>
		</p>	
		</td>	
	</tr>
		
	
	</center>

</div>	

	</html:form>

</body>
</html>