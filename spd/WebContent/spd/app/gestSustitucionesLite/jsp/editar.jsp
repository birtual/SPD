<%@ page language="java" %>
<%@ page import="java.util.*" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML  4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">

<head>
<jsp:include page="/spd/jsp/global/head.jsp"/>
<title>Edición sustitución lite</title>

</head>


<bean:define id="formulari" name="GestSustitucionesLiteForm" type="lopicost.spd.struts.form.GestSustitucionesLiteForm" />

<script language="javaScript" src="/spd/spd/app/gestSustitucionesLite/js/gestSustitucionesLite.js"></script>


<body>
		<center>
		<h2>Edición sustitución lite </h2>
		<html:form action="/GestSustitucionesLite.do" method="post">	


<div id="container">

<bean:define id="data" name="formulari" property="sustitucionLite" />
     <html:hidden property="oidDivisionResidenciaFiltro" />
     <html:hidden property="parameter" value="editar"/>
     <html:hidden property="ACTIONTODO" value="EDITA"/>
     <html:hidden property="filtroNombreCorto" />
    
     <html:hidden property="oidGestSustitucionesLite" />   
     <html:hidden property="fieldName1" /> 
     <html:hidden property="campoGoogle" />
     
	<!-- se pasan parámetros de los filtros del listado para la vuelta -->
     <html:hidden property="filtroNombreCorto" />
     <html:hidden property="filtroMedicamentoResi" />
     <html:hidden property="filtroNombreCortoOK" />
     <html:hidden property="filtroFormaFarmaceutica" />
     <html:hidden property="filtroAccion" />
 		<table>
		<tr>
			<td id="campo1">Residencia</td>
			<td><bean:write name="data" property="idDivisionResidencia" /></td>
		</tr>
		<tr>
			<td>Código CN residencia</td>
			<td><html:text name="data" property="resiCn" styleId="resiCn"/> - <html:text name="data"  property="resiMedicamento"  styleId="spdNombreMedicamento"/></td>
		</tr>
		<tr>
			<td>Código -  Nombre medicamento </td>
			<td><html:text name="data" property="spdCn" styleId="spdCn"/> - <html:text name="data" property="spdNombreMedicamento" styleId="spdNombreMedicamento" readonly="true"/>
				<a href="javascript:buscaConsejoPorCodGtVm('<bean:write name="data" property="codGtVm" />');">Buscar</a>
			</td>
		</tr>
		<tr>
			<td>Nombre Corto</td>
			<td><html:textarea name="data" property="spdNombreBolsa" styleId="spdNombreBolsa" /></td>
		</tr>
		<tr>
			<td>GTVMP</td>
			<td><bean:write name="data" property="nomGtVmp" /></td>
		</tr>
		<tr>
			<td>Forma farmacéutica</td>
			<td><html:text name="data" property="spdFormaMedicacion" /></td>
		</tr>
		<tr>
			<td>Acción</td>
			<td>		
				<bean:define id="tiposAccion" name="formulari" property="listaTiposAccion" />
			<html:select property="idTipoAccion" value="<%= formulari.getSustitucionLite().getSpdAccionBolsa() %>">
				<html:options collection="tiposAccion" property="idTipoAccion" labelProperty="idTipoAccion" />
				<html:option value="">-------------</html:option>
			</html:select>
			</td>
		</tr>
		<tr>
			<td>Excepciones</td>
			<td><html:textarea name="data" property="excepciones"  styleId="excepciones"/></td>
		</tr>
		<tr>
			<td>Aux1</td>
			<td><html:textarea name="data" property="aux1"  styleId="aux1"/></td>
		</tr>
		<tr>
			<td>	
				<p class="botons">
					<input type="button" onclick="javascript:volver('<bean:write name="formulari" property="oidDivisionResidenciaFiltro"/>')" value="Volver"/>
					<input type="button" onclick="javascript:grabar()" value="Confirmar"/>
				</p>
			</td>	
		</tr>	
	</table>
</div>	
</html:form>
</center>

</body>
</html>