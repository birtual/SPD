<%@ include file="/spd/jsp/global/headFragmento.jspf" %>

<!DOCTYPE html>
<html:html>
<head>
<title><bean:message key="paciente.edicion.titulocip"/></title>
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
				<u><bean:message key="global.mensaje"/>Mensaje:</u>
					<logic:iterate id="error" name="formulari" property="errors" type="java.lang.String">
							<li><bean:write name="error"/></li>
					</logic:iterate>
				</ul></font>
		</logic:notEmpty>

	
  		<table class="detallePaciente">
		<tr >
			<td id="campo1" ><bean:message key="paciente.edicion.residencia"/></td><td><bean:write name="data" property="idDivisionResidencia" /></td>
		</tr>
		<tr>
			<td><bean:message key="paciente.edicion.identificador"/></td><td class="oidPaciente"><bean:write name="data" property="oidPaciente"/></td>
		</tr>
		<tr>
			<td><bean:message key="paciente.edicion.cip"/></td><td><html:text name="data" property="CIP"/></td>
		</tr>
		<tr>
			<td><bean:message key="paciente.edicion.residente"/></td><td><bean:write name="data" property="nombre"/>  <bean:write name="data" property="apellido1"/>  <bean:write name="data" property="apellido2"/></td>
		</tr>
		<tr>
			<td><bean:message key="paciente.edicion.mutua"/></td><td><bean:write name="data" property="mutua"/></td>
		</tr>
		<tr>
			<td><bean:message key="paciente.edicion.nIdentidad"/></td>
			<td><bean:write name="data" property="numIdentidad"/></td>
		</tr>
		<tr>
			<td><bean:message key="paciente.edicion.nSegSocial"/></td>
			<td><bean:write name="data" property="segSocial"/></td>
		</tr>
		<tr>
			<td><bean:message key="paciente.edicion.idPacienteResidencia"/></td>
			<td><bean:write name="data" property="idPacienteResidencia"/></td>
		</tr>
		<tr>
			<td><bean:message key="paciente.edicion.planta"/></br><bean:message key="paciente.edicion.habitacion"/></td><td><bean:write name="data" property="planta" /></br><bean:write name="data" property="habitacion" /></td>
		</tr>
		<tr>
			<td><bean:message key="paciente.edicion.hacerSpd"/></td>
			<td><bean:write name="data" property="spd" /></td>
		</tr>
		<tr>
			<td><bean:message key="paciente.edicion.panyales"/></td>
			<td><bean:write name="data" property="bolquers" /></td>
		</tr>		
		<tr>
			<td><bean:message key="paciente.edicion.fechaAlta"/></td>
			<td><bean:write name="data" property="fechaAltaPaciente" /></td>
		</tr>
		<tr>
			<td><bean:message key="paciente.edicion.comentarios"/></td>
			<td><textarea rows="4" cols="50" readonly><bean:write name="data" property="comentarios"/></textarea></td>
		</tr>
		<tr>
			<td><bean:message key="paciente.edicion.actividad"/></td><td><bean:write name="data" property="activo"/></td>
		</tr>			
		<tr>
			<td><bean:message key="paciente.edicion.estado"/></td><td><bean:write name="data" property="estatus"/></td>
		</tr>			
		<tr>
			<td><bean:message key="paciente.edicion.idFarmatic"/></td><td><bean:write name="data" property="idPharmacy"/></td>
		</tr>			
		<tr>
			<td><bean:message key="paciente.edicion.codigoUP"/></td><td><bean:write name="data" property="UPFarmacia"/></td>
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
</html:html>