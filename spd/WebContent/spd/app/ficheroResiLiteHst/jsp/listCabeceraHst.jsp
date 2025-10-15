<%@page import="lopicost.spd.utils.SPDConstants"%>
<%@ page language="java" %>
<%@ page import="java.util.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
<script language="javaScript" src="/spd/spd/app/ficheroResiLiteHst/js/ficheroResiCabeceraLiteHst.js"></script>

<head>
<jsp:include page="/spd/jsp/global/head.jsp"/>
<title>HISTORICO de producciones cargadas por ficheros</title>
</head>
<script>
    // Este script manejará la respuesta del servidor y actualizará o redireccionará según sea necesario
    function handleServerResponse(responseText) {
        // Puedes mostrar una alerta, actualizar la página o redirigir según tus necesidades
        alert(responseText);
        // window.location.reload();  // Para recargar la página
        // window.location.href = '/tuApp/list';  // Para redirigir a un mapeo específico
    }
</script>
<bean:define id="formulari" name="FicheroResiForm" type="lopicost.spd.struts.form.FicheroResiForm" />

<body>

<html:form action="/FicheroResiCabeceraLiteHst.do" method="post">	

<div id="container">
    <html:hidden property="oidDivisionResidencia" />
    <html:hidden property="idDivisionResidencia" />
    <html:hidden property="idUsuario" />
    <html:hidden property="parameter" value="list"/>
    <html:hidden property="ACTIONTODO" value="list"/>
    
    
    <html:hidden property="oidFicheroResiCabecera" />
	<% String numPages = formulari.getNumpages()+""; %>
    <% String currpage = formulari.getCurrpage()+""; %>
    <html:hidden property="numpages" value="<%= numPages %>"/>
	<html:hidden property="currpage" value="<%= currpage %>"/>
	<input type="hidden" name="idProceso" />
	<input type="hidden" name="filtroProceso" />
	<input type="hidden" name="filtroEstado" />
 	
 	<fieldset>
	<!-- mostramos mensajes y errores, si existen -->
	<logic:notEmpty name="formulari" property="errors">
		<font color="red">
		<ul>
			<u>Mensaje:</u>
				<logic:iterate id="error" name="formulari" property="errors" type="java.lang.String">
					<li><bean:write name="error"/></li>
				</logic:iterate>
		</ul>
		</font>
		</logic:notEmpty>
		<div>	
			<label for="Name">Residencia</label>	

			<html:select property="oidDivisionResidenciaFiltro"  value="<%= String.valueOf(formulari.getOidDivisionResidenciaFiltro()) %>" onchange="submitResi()"> 
				<html:option value="">Residencia</html:option>
	   			<html:optionsCollection name="formulari" property="listaDivisionResidenciasCargadas" label="nombreDivisionResidencia" value="oidDivisionResidencia" />
			</html:select>			
		</div>	

	   	<div>	
		    <p class="botons">
				<input type="button" class="azulCielo" onclick="javascript:goInicio();" value="Inicio" />
				<input type="button" class="azulCielo" onclick="javascript:listado();"  value="Buscar"  />  
				<input type="button" class="azulCielo" onclick="javascript:verActuales();"  value="Ir a las actuales" />
			</p> 
		</div>

		<table class="blueTable" border="1">
			<tr>
			 	<!-- th>Residencia</th> -->
				<th>Id Proceso</th>
				<th>Fecha Creación</th>
				<th>Filas totales</th>
				<th>nº Errores</th>
				<th>Cips Fichero</th>
				<th>Cips Resi</th>
				<th>Creador carga</th>
				<th>pendiente validar</th>
				<th>Previsión actualizada</th>
				<!-- <th>Nota1</th>-->
				<!-- <th>Nota2</th>-->
				<!-- <th>Nota3</th>-->
				
		   </tr>
 	    	<tbody>
		
		 	<logic:iterate id="data" name="formulari" property="listaFicheroResiCabeceraBean" type="lopicost.spd.struts.bean.FicheroResiBean" indexId="position">
				<tr>
					<td class="a150"><a href="javascript:goDetalle('<bean:write name="data" property="oidDivisionResidencia" />', '<bean:write name="data" property="idProceso" />', '<bean:write name="data" property="oidFicheroResiCabecera" />');"><bean:write name="data" property="idProceso" /></a> <br></td>
					<td class="derecha a40"><bean:write name="data" property="fechaHoraProceso" /></td>
					<td class="derecha"><bean:write name="data" property="filasTotales" /></td>
					<td class="derecha"><a href="javascript:abrirVentanaErrores('<bean:write name="data" property="oidFicheroResiCabecera" />', '<bean:write name="data" property="errores" />');"><bean:write name="data" property="numErrores" /></a></td>
					<td class="derecha"><bean:write name="data" property="cipsTotalesProceso" /></td>
					<td class="derecha"><bean:write name="data" property="cipsActivosSPD" /></td>
					<td><bean:write name="data" property="usuarioCreacion" /></td>
					<td class="derecha"><bean:write name="data" property="numeroValidacionesPendientes" /></td>
					<td><bean:write name="data" property="fechaCalculoPrevision" /></td>

				</tr>
	    	</logic:iterate>
	     	</tbody>  
		</table>
	</fieldset>

	<!--  paginación  -->
	<div>
		<logic:greaterThan name="formulari" property="numpages" value="1">
		<p align="center">
			<logic:greaterThan name="formulari" property="currpage" value="0">
				<a href="javascript:atras();" ><<</a>
			</logic:greaterThan>
				&nbsp;<input type="text" name="newPage" value="<%= formulari.getCurrpage()+1 %>" size="1" maxlength="4" onkeypress="irAPagina(this.value,<%=formulari.getNumpages()%>);">/<%= formulari.getNumpages() %>&nbsp;
			<logic:lessThan name="formulari" property="currpage" value="<%= String.valueOf(formulari.getNumpages() -1)%>">
				<a href="javascript:avance();" >>></a>
			</logic:lessThan>
		</p>
		</logic:greaterThan>
	</div>
	<!--  paginación   -->
</div>	
</html:form>
</body>
</html>