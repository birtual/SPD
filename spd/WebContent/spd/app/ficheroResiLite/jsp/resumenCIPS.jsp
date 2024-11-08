<%@ page language="java" %>
<%@ page import="java.util.*" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
<script language="javaScript" src="/spd/spd/app/ficheroResiLite/js/ficheroResiCabeceraLite.js"></script>

<head>
<jsp:include page="/spd/jsp/global/head.jsp"/>
<title>Diferencias de CIPS </title>
</head>

<bean:define id="formulari" name="FicheroResiForm" type="lopicost.spd.struts.form.FicheroResiForm" />


<body id="general">
	<center>
		<h2>Diferencias de CIPS entre fichero producción y el mantenimiento de residentes </h2>
		<html:form action="/FicheroResiCabeceraLite.do" method="post">	

	<bean:define id="data" name="formulari" property="ficheroResiDetalleBean" />


     <html:hidden property="parameter" value="resumenCIPS"/>
 
	
	<table>

		<tr>
			<td><h5>
				<c:choose>
	    		<c:when test="${empty data.resumenCIPS}">
	        		<p>Los CIP del fichero de producción coinciden 100% con los datos de residentes activos en el mantenimiento </p>
	    		</c:when>
	    		<c:otherwise>
	        		<c:out value="${data.resumenCIPS}" escapeXml="false" />
	    		</c:otherwise>
				</c:choose>
			</h5></td>
			



		</tr>
		<tr>
			<td>	
				<p class="botons">
					<input type="button" onclick="javascript:salir()" value="Volver"/>
				</p>	
			</td>	
		</tr>

		 
		</table>




	</html:form>

</body>
</html>