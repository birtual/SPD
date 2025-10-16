<%@ page language="java" %>
<%@ page import="java.util.*" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page session="true" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html:html>
<HEAD>
<title></title>
<jsp:include page="/spd/jsp/global/head.jsp"/>

</HEAD>
<bean:define id="formulari" name="GenericForm" type="lopicost.spd.struts.form.GenericForm" />


<script type="text/javascript">	


</script>
<body>
<html:form action="/ControlDataImport.do" method="post">
	<html:hidden property="parameter" value="list"/>
 
	
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
	
<h2> </h2>
<div id="contingut">


 <h3></h3>
		
		<div></div>

		<logic:present name="formulari" property="listaBeans">
		<logic:iterate id="data" name="formulari" property="listaBeans" type="lopicost.spd.struts.bean.ControlDataImportBean" indexId="position">

	<table style="width:50%">
		<br><br>
		<tr>
			<h4>  <bean:write name="data" property="nombreTabla" /></h4>
		</tr>
		<tr>
			<th>Días desde última fecha</th>
			<logic:notEqual property="idFarmacia" name="data" value="NO">
				<th>Farmacia</th>
			</logic:notEqual>
			<logic:notEqual property="idDivisionResidencia" name="data" value="NO">
				<th>Residencia</th>
			</logic:notEqual>
			<logic:notEqual property="numeroCIPs" name="data" value="-1">
				<th>número CIPs</th>
			</logic:notEqual>					
			<logic:notEqual property="ultimaFechaEnOrigen" name="data" value="NO">
				<th>última fecha en Farmatic</th>
			</logic:notEqual>					
			<logic:notEqual property="ultimaFechaRecogida" name="data" value="NO">
				<th>última fecha recogida</th>
			</logic:notEqual>					
			<logic:notEqual property="cuantos" name="data" value="-1">
				<th>Total dispensadas</th>
			</logic:notEqual>					
		</tr>
			
		<logic:present name="data" property="detalleBeans">
		<logic:iterate id="detalle" name="data" property="detalleBeans" type="lopicost.spd.struts.bean.ControlDataImportBean" indexId="position2">
		<tr>
			<td  class="<bean:write name="detalle" property="alerta" />" style="width: 15%; text-align: center;"><bean:write name="detalle" property="diasDesdeUltimaFecha" /></td>	

			<logic:notEqual property="idFarmacia" name="data" value="NO">
				<td  text-align: left;"><bean:write name="detalle" property="nombreFarmacia" /> </td>
			</logic:notEqual>

			<logic:notEqual property="idDivisionResidencia" name="data" value="NO">
				<td  text-align: left;"><bean:write name="detalle" property="nombreDivisionResidencia" /></td>
			</logic:notEqual>	

			<logic:notEqual property="numeroCIPs" name="data" value="-1">
				<td  text-align: right;"><bean:write name="detalle" property="numeroCIPs" /></td>
			</logic:notEqual>			

			<logic:notEqual property="ultimaFechaEnOrigen" name="data" value="NO">
				<td text-align: left;"><bean:write name="detalle" property="ultimaFechaEnOrigen" /></td>
			</logic:notEqual>							

			<logic:notEqual property="ultimaFechaRecogida" name="data" value="NO">
				<td text-align: left;"><bean:write name="detalle" property="ultimaFechaRecogida" /></td>
			</logic:notEqual>							

			<logic:notEqual property="cuantos" name="data" value="-1">
				<td text-align: left;"><bean:write name="detalle" property="cuantos" /></td>
			</logic:notEqual>					

		</tr>
		</logic:iterate>
		</logic:present>

 	</table>
				</logic:iterate>
				</logic:present>

	<div>
		<input type="button" class="azulCielo" value="Salir" onclick="javascript:salir();" />
	</div>
 </div>
</html:form>
</html:html>
