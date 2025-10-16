<%@ page language="java" %>
<%@ page import="java.util.*" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<%@ page session="true" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html:html>
<HEAD>


<title>Reports BIRT. Lopicost




</title>
<jsp:include page="/spd/jsp/global/head.jsp"/>



</HEAD>
<bean:define id="formulari" name="ReportingForm" type="lopicost.spd.struts.form.ReportingForm" />

<body>
<html:form action="/Reporting.do" method="post">

<h2>Reports Lopicost </h2>
 <table class="CSS_Table_Example" >
 <tr>
		<td class="primera">número</td>
		<td class="primera">Tipo</td>
		<td class="segunda">Nombre report</td>
   </tr>
   
   
   	<logic:iterate id="data" name="formulari" property="listaReports" type="lopicost.spd.model.Enlace" indexId="position">
	   <logic:present name="data" property="usuario">
			<tr>	<td><bean:write name="data" property="orden" /></td>
			<td><a href="<bean:write name="data" property="preEnlace" /><bean:write name="data" property="linkEnlace" /><bean:write name="data" property="paramsEnlace" />" target=_blank>
			<bean:write name="data" property="nombreEnlace" /></a></td>
			<td><bean:write name="data" property="descripcion" /></td>
			</tr>	
		</logic:present>	
		<logic:notPresent name="data" property="usuario">
		Usuario no válido
		</logic:notPresent>
	</logic:iterate>

	


		<a href="index_farm1.html" target=_blank>.</a>
		<a href="index_frm_rct2.html" target=_blank>.</a>
 </table>
 
</html:form>
</html:html>
