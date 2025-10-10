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
<title>Borrado de sustitución Lite</title>
</head>

<bean:define id="formulari" name="GestSustitucionesLiteForm" type="lopicost.spd.struts.form.GestSustitucionesLiteForm" />
<script language="javaScript" src="/spd/spd/app/gestSustitucionesLite/js/gestSustitucionesLite.js"></script>

<body id="general">
	<center>
		<h2>Borrado de sustitución Lite</h2>
		<html:form action="/GestSustitucionesLite.do" method="post">	

<div id="contingut">
<bean:define id="data" name="GestSustitucionesLiteForm" property="sustitucionLite"/>

     <html:hidden property="oidDivisionResidenciaFiltro" />
     <html:hidden property="parameter" value="borrar"/>
     <html:hidden property="ACTIONTODO" value="CONFIRMAR"/>
     <html:hidden property="oidGestSustitucionesLite" />
	<!-- se pasan parámetros de los filtros del listado para la vuelta -->
     <html:hidden property="filtroNombreCorto" />
     <html:hidden property="filtroMedicamentoResi" />
     <html:hidden property="filtroNombreCortoOK" />
     <html:hidden property="filtroFormaFarmaceutica" />
     <html:hidden property="filtroAccion" />
       <html:hidden property="campoGoogle" />
 
	<h3>Confirmar borrado</h3>
	
	<table>
	<tr>
		<td>Residencia</td>
		<td><bean:write name="data" property="idDivisionResidencia"/></td>
	</tr>
	<tr>
		<td>Código residencia</td>
		<td><bean:write name="data" property="resiCn"/></td>
	</tr>
	<tr>
		<td>Medicamento residencia</td>
		<td><bean:write name="data" property="resiMedicamento"/></td>
	</tr>
	<tr>
		<td>Código OK</td>
		<td><bean:write name="data" property="spdCn"/></td>
	</tr>
	<tr>
		<td>Nombre corto</td>
		<td><bean:write name="data" property="spdNombreBolsa"/></td>
	</tr>

	<tr>
		<td>Forma farmaceutica</td>
		<td><bean:write name="data" property="spdFormaMedicacion"/></td>
	</tr>
	<tr>
		<td>Acci´pon</td>
		<td><bean:write name="data" property="spdAccionBolsa"/></td>
	</tr>
	<tr>
		<td>Excepción</td>
		<td><bean:write name="data" property="excepciones"/></td>
	</tr>

	
	<tr>
 		<td>aux1</td>
		<td><bean:write name="data" property="aux1"/></td>
	</tr>
	<tr>
		<td>aux2</td>
		<td><bean:write name="data" property="aux2"/></td>
    </tr>     
  
</table>

	<tr>
		<td>	
			<p class="botons">
			<input type="button" onclick="javascript:volver()" value="Volver"/>
			<input type="button" onclick="javascript:borrarOk( '<bean:write name="data" property="oidGestSustitucionesLite" />')" value="Confirmar"/>
		</p>	
		</td>	
	</tr>
		
	
	</center>

</div>	

	</html:form>

</body>
</html>