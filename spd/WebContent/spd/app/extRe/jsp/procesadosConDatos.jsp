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
<title>Listado residentes sin procesar</title>
</head>
<script type="text/javascript">	
	function cerrar()
	{
		window.close();
	}	
</script>

<bean:define id="formulari" name="GenericForm" type="lopicost.spd.struts.form.GenericForm" />
<body id="general">

<html:form action="/ExtRe.do" method="post">	
<html:errors/>

<div id="contingut">
    <html:hidden property="parameter" value="procesadosConDatos"/>
    <html:hidden property="ACTIONTODO" value="list"/>
	   	
	   	<!-- mostramos mensajes y errores, si existen -->
		<logic:notEmpty name="formulari" property="errors">
			<font color="red"><ul>
				<u>Mensaje:</u>
					<logic:iterate id="error" name="formulari" property="errors" type="java.lang.String">
							<li><bean:write name="error"/></li>
					</logic:iterate>
				</ul></font>
		</logic:notEmpty>

	<table border="1">
		<tr>
		<th>CIP</th>
		<th>Apellidos, nombre</th>
	 	<th>Seg Social</th>
		<th>CN</th>
		<th>Medicamento</th>
		<th>Fecha proceso</th>
	   </tr>


 	<logic:iterate id="data" name="formulari" property="listaBdPacientes" type="lopicost.spd.struts.bean.PacienteBean" indexId="position">
	<tr class="<bean:write name="data" property="claseId" />">
		<td class="naranja"><bean:write name="data" property="CIP" /></td>
		<td class="naranja"><bean:write name="data" property="apellidosNombre" /></td>
		<td class="naranja"><bean:write name="data" property="segSocial" /></td>
		<td class="naranja" COLSPAN="2"></td>
		<td class="naranja"><bean:write name="data" property="fechaHoraProceso" /></td>
	</tr>
 	<logic:iterate id="medic" name="data" property="listaTratamientos" type="lopicost.spd.struts.bean.TratamientoRctBean" indexId="position2">
	<tr>
		<td COLSPAN="3"></td>
		<td><bean:write name="medic" property="recetaCn" /></td>
		<td><bean:write name="medic" property="recetaMedicamento" /></td>
		

	</tr>
    </logic:iterate>	
    </logic:iterate>


    
</table>
    <p class="botonBuscar">
			<input type="button" onclick="javascript:cerrar()" value="cerrar"/>
	</p> 
		

		
	</div>	
	</html:form>

</body>
</html>