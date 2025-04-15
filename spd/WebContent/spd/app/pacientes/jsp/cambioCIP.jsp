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
<title>Modificación de CIP</title>
</head>

<bean:define id="formulari" name="PacientesForm" type="lopicost.spd.struts.form.PacientesForm" />
<script language="javaScript" src="/spd/spd/app/pacientes/js/pacientes.js"></script>


<body id="general">

<html:form action="/Pacientes.do" method="post">	
<html:errors/>

<div id="contingut">
<bean:define id="data" name="formulari" property="pacienteBean" />

    <html:hidden property="parameter" value="cambioCIP"/>
     <html:hidden property="ACTIONTODO" value="CAMBIOCIP"/>
	<html:hidden property="oidPaciente"/>
	   	<!-- mostramos mensajes y errores, si existen -->
		<logic:notEmpty name="formulari" property="errors">
			<font color="red"><ul>
				<u>Mensaje:</u>
					<logic:iterate id="error" name="formulari" property="errors" type="java.lang.String">
							<li><bean:write name="error"/></li>
					</logic:iterate>
				</ul></font>
		</logic:notEmpty>

	
  		<table class="detallePaciente">
		<tr >
			<td id="campo1" >Residencia</td><td><bean:write name="data" property="idDivisionResidencia" /></td>
		</tr>
		<tr>
			<td>Identificador</td><td class="oidPaciente"><bean:write name="data" property="oidPaciente"/></td>
		</tr>
		<tr>
			<td>CIP</td><td><html:text name="data" property="CIP"/></td>
		</tr>
		<tr>
			<td>Residente</td><td><bean:write name="data" property="nombre"/>  <bean:write name="data" property="apellido1"/>  <bean:write name="data" property="apellido2"/></td>
		</tr>
		<tr>
			<td>Mutua</td><td><bean:write name="data" property="mutua"/></td>
		</tr>
		<tr>
			<td>Nº identidad</td>
			<td><bean:write name="data" property="numIdentidad"/></td>
		</tr>
		<tr>
			<td>Nº Seg Social</td>
			<td><bean:write name="data" property="segSocial"/></td>
		</tr>
		<tr>
			<td>id en la residencia</td>
			<td><bean:write name="data" property="idPacienteResidencia"/></td>
		</tr>
		<tr>
			<td>Planta</br>Habitación</td><td><bean:write name="data" property="planta" /></br><bean:write name="data" property="habitacion" /></td>
		</tr>
		<tr>
			<td>Hacer spd</td>
			<td><bean:write name="data" property="spd" /></td>
		</tr>
		<tr>
			<td>Servir pañales</td>
			<td><bean:write name="data" property="bolquers" /></td>
		</tr>		
		<tr>
			<td>Fecha alta</td>
			<td><bean:write name="data" property="fechaAltaPaciente" /></td>
		</tr>
		<tr>
			<td>Comentarios</td>
			<td><textarea rows="4" cols="50" readonly><bean:write name="data" property="comentarios"/></textarea></td>
		</tr>
		<tr>
			<td>Actividad</td><td><bean:write name="data" property="activo"/></td>
		</tr>			
		<tr>
			<td>Estado</td><td><bean:write name="data" property="estatus"/></td>
		</tr>			
		<tr>
			<td>idFarmatic</td><td><bean:write name="data" property="idPharmacy"/></td>
		</tr>			
		<tr>
			<td>UPFarmacia</td><td><bean:write name="data" property="UPFarmacia"/></td>
		</tr>
		<tr>
			<td>	
				<p class="botons">
					<input type="button" onclick="javascript:cerrar()" value="Cerrar"/>
					<input type="button" onclick="javascript:goCambiarCipOK()" value="Cambiar CIP"/>
				</p>	
			</td>	
		</tr>	
	</table>
	</center>
</div>	
</html:form>

</body>
</html>