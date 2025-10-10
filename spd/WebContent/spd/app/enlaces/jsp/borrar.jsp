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
<title>Borrado de enlace</title>
</head>

<bean:define id="formulari" name="EnlacesForm" type="lopicost.spd.struts.form.EnlacesForm" />


<script type="text/javascript">	
		function volver()
		{
			var f = document.EnlacesForm;
			f.parameter.value='list';
			f.ACTIONTODO.value='LIST';
			f.submit();
		}	
		
		function borrar()
		{
			var f = document.EnlacesForm;
			f.parameter.value='borrar';
			f.ACTIONTODO.value='CONFIRMADO_OK';
			f.submit();
		}	
				
</script>




<body id="general">
	<center>
		<h2>Borrado de enlace</h2>
		<html:form action="/Enlaces.do" method="post">	

<div id="contingut">
<bean:define id="data" name="formulari" property="enlace"/>

     <html:hidden property="idEnlace"/>
     <html:hidden property="parameter" value="borrar"/>
     <html:hidden property="ACTIONTODO" value="CONFIRMAR"/>
 	<h3>Confirmar borrado</h3>
	<table>
		<tr>
			<th class="segunda">idEnlace</th>
			<th class="segunda">Nombre enlace</th>
			<th class="segunda">Descripción</th>
			<th class="segunda">idapartado</th>
			<th class="segunda">preEnlace</th>
			<th class="segunda">link</th>
			<th class="segunda">Parámetros</th>
			<th class="segunda">activo</th>
		</tr>
		<th><bean:write name="data" property="idEnlace" /></th>
		<th><bean:write name="data" property="nombreEnlace" /></th>
		<th><bean:write name="data" property="descripcion" /></th>
		<th><bean:write name="data" property="idApartado" /></th>
		<th><bean:write name="data" property="preEnlace" /></th>
		<th><bean:write name="data" property="linkEnlace" /></th>
		<th><bean:write name="data" property="paramsEnlace" /></th>
		<th><bean:write name="data" property="activo" /></th>
	</table>
       

	
	
	
  
</table>

	<tr>
		<td>	
			<p class="botons">
			<input type="button" onclick="javascript:volver()" value="Volver"/>
			<input type="button" onclick="javascript:borrar()" value="Confirmar"/>
		</p>	
		</td>	
	</tr>
		
	
	</center>

</div>	

	</html:form>

</body>
</html>