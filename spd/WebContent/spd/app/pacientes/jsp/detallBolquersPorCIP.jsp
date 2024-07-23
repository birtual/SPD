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

<title>Pañales por CIP</title>
</head>



<bean:define id="formulari" name="PacientesForm" type="lopicost.spd.struts.form.PacientesForm" />
<script language="javaScript" src="/spd/spd/app/pacientes/js/pacientes.js"></script>




<body id="general">

<html:form action="/Pacientes.do" method="post">	
<html:errors/>

<div id="contingut">
 	
 	<fieldset>

		<table  id="listaPacientesBean" align="center" border="1">
			<tr>
			 	<th>Fecha proceso</th>
			 	<th>CIP</th>
				<th>Nombre</th>
				<th>servir pañales</th>
			 	<th>CN</th>
			 	<th>Nombre pañal</th>
			 	<th>LAB</th>
			 	<th>PVP</th>
			 	<th>Grupo terapéutico</th>
			 	<th>Presentación</th>
			 	<th>Fin tratamiento</th>
			 	<th>Cantidad</th>
			 	<th>Pauta</th>
		   </tr>
	
	 	<logic:iterate id="data" name="formulari" property="listaBeans" type="lopicost.spd.struts.bean.BolquersDetalleBean" indexId="position">
			<tr>
				<td><bean:write name="data" property="fechaProceso" /></td>
				<td><bean:write name="data" property="CIP" /></td>
				<td><bean:write name="data" property="apellidosNombre" /></td>
				<td><bean:write name="data" property="servirBolquers" /></td>
				<td><bean:write name="data" property="cn" /></td>
				<td><bean:write name="data" property="medicamento" /></td>
				<td><bean:write name="data" property="lab" /></td>
				<td><bean:write name="data" property="pvp" /></td>
				<td><bean:write name="data" property="grupoTerapeutico" /></td>
				<td><bean:write name="data" property="presentacion" /></td>
				<td><bean:write name="data" property="finTratamiento" /></td>
				<td><bean:write name="data" property="cantidad" /></td>
				<td><bean:write name="data" property="pauta" /></td>
			</tr>
	    </logic:iterate>
	    	<tr>
			<td>	
				<p class="botons">
					<input type="button" onclick="javascript:cerrar()" value="Cerrar"/>
				</p>	
			</td>	
		</tr>	
		</table>
	</fieldset>
	</div>		
	
	</html:form>

</body>
</html>