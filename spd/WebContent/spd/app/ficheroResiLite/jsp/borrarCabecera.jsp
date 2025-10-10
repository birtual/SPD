<%@ page language="java" %>
<%@ page import="java.util.*" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
<script language="javaScript" src="/spd/spd/app/ficheroResiLite/js/ficheroResiCabeceraLite.js"></script>

<head>
<jsp:include page="/spd/jsp/global/head.jsp"/>
<title>Borrado de carga de fichero</title>
</head>

<bean:define id="formulari" name="FicheroResiForm" type="lopicost.spd.struts.form.FicheroResiForm" />


<body id="general">
	<center>
		<h2>Borrado de carga de fichero</h2>
		<html:form action="/FicheroResiCabeceraLite.do" method="post">	

<div >

     <html:hidden property="parameter" value="borrar"/>
     <html:hidden property="ACTIONTODO" value="CONFIRMAR"/>
     <html:hidden property="filtroDivisionResidenciasCargadas" /> 
     <html:hidden property="oidFicheroResiCabecera" /> 
     <html:hidden property="oidDivisionResidencia" /> 
       
	<h3>Confirmar borrado</h3>
	
	<table>
	<logic:iterate id="data" name="FicheroResiForm" property="listaFicheroResiCabeceraBean" type="lopicost.spd.struts.bean.FicheroResiBean" indexId="position">
		<tr>
			<th>Residencia</th><td><bean:write name="data" property="idDivisionResidencia" /></td>
		</tr>
			
		<tr>
			<th>idProceso</th><td><bean:write name="data" property="idProceso" /></td>
		</tr>
		<tr>
			<th>fechaHoraProceso</th><td><bean:write name="data" property="fechaHoraProceso" /></td>
		</tr>
		<tr>
			<td>	
				<p class="botons">
					<input type="button" onclick="javascript:volver()" value="Volver"/>
					<input type="button" onclick="javascript:borrarOk('<bean:write name="data" property="oidFicheroResiCabecera"/>')" value="Confirmar"/>
				</p>	
			</td>	
		</tr>
	 </logic:iterate>
		 
		</table>




	</html:form>

</body>
</html>