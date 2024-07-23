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
<title>Borrado de sustitución X Conjunto Homogéneo/LAB</title>

</head>


<bean:define id="formulari" name="SustXComposicionForm" type="lopicost.spd.struts.form.SustXComposicionForm" />

<script type="text/javascript">	
		
		function volver(oidSustXComposicion)
		{
			var f = document.SustXComposicionForm;
			f.parameter.value='list';
			f.ACTIONTODO.value='LIST';
			f.oidSustXComposicion.value=oidSustXComposicion;
			//alert(oidDivisionResidenciaFiltro);
			f.submit();
		}	
		
			
		function borrar(oidSustXComposicion)
		{
			var f = document.SustXComposicionForm;
			f.parameter.value='borrar';
			f.ACTIONTODO.value='CONFIRMADO_OK';
			f.oidSustXComposicion.value=oidSustXComposicion;
			f.submit();
		}	
				
</script>



<body id="general">
	<center>
		<h2>Borrado de sustitución X Conjunto Homogéneo</h2>
		<html:form action="/SustXComposicion.do" method="post">	

<div id="contingut">
<bean:define id="data" name="SustXComposicionForm" property="sustXComposicion"/>

     <html:hidden property="parameter" value="editar"/>
     <html:hidden property="ACTIONTODO" value="EDITA"/>
     <html:hidden property="oidSustXComposicion" />
     <html:hidden property="fieldName1" /> 
 
<table>

	<h3>Confirmar borrado</h3>
	<tr>
		<td>Código Conj Homog (GTVMPP)</td>
		<td><bean:write name="data" property="codGtVmpp"/></td>
	</tr>
	<tr>
		<td>Nombre Conj Homog</td>
		<td><bean:write name="data" property="nomGtVmpp"/></td>
	</tr>	
	<tr>
		<td>Rentabilidad</td>
		<td><bean:write name="data" property="rentabilidad"/></td>
	</tr>	
	<tr>
		<td>Ponderacion</td>
		<td><bean:write name="data" property="ponderacion"/></td>
	</tr>	
	<tr>
		<td>Código Lab</td>
		<td><bean:write name="data" property="codiLab"/></td>
	</tr>	
	<tr>
		<td>Nombre Lab</td>
		<td><bean:write name="data" property="nombreLab"/></td>
		
	</tr>	
	<tr>
		<td>Fecha creación</td>
		<td><bean:write name="data" property="fechaCreacion"/></td>
	</tr>	
	<tr>
		<td>última modificacion</td>
		<td><bean:write name="data" property="ultimaModificacion"/></td>
	</tr>	
	<tr>
		<td>Comentario</td>
		<td><bean:write name="data" property="comentarios"/></td>
	</tr>	

</table>

	<tr>
		<td>	
			<p class="botons">
			<input type="button" onclick="javascript:volver('<bean:write name="formulari" property="oidSustXComposicion"/>')" value="Volver"/>
			<input type="button" onclick="javascript:borrar('<bean:write name="formulari" property="oidSustXComposicion"/>')" value="Confirmar"/>
		</p>	
		</td>	
	</tr>
		
	
	</center>

</div>	

	</html:form>

</body>
</html>