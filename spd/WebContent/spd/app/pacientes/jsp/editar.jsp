<%@ include file="/spd/jsp/global/headFragmento.jspf" %>

<!DOCTYPE html>
<html:html>
<head>
<title><bean:message key="paciente.edicion.titulo"/></title>
</head>

<bean:define id="formulari" name="PacientesForm" type="lopicost.spd.struts.form.PacientesForm" />
<script language="javaScript" src="/spd/spd/app/pacientes/js/pacientes.js"></script>


<body id="general">

<html:form action="/Pacientes.do" method="post">	
<html:errors/>

<div id="contingut">
<bean:define id="data" name="formulari" property="pacienteBean" />

    <html:hidden property="parameter" value="editar"/>
    <html:hidden property="ACTIONTODO"/>
	<html:hidden property="oidDivisionResidencia"/>
	<html:hidden property="oidPaciente"/>
	<html:hidden property="filtroEstatusResidente"/>
	<html:hidden property="filtroEstadosResidente"/>
	<html:hidden property="filtroEstadosSPD"/>
	<html:hidden property="campoGoogle"/>
	
 
 	   	<!-- mostramos mensajes y errores, si existen -->
		<logic:notEmpty name="formulari" property="errors">
			<font color="red"><ul>
				<u><bean:message key="global.mensaje"/>:</u>
					<logic:iterate id="error" name="formulari" property="errors" type="java.lang.String">
							<li><bean:write name="error"/></li>
					</logic:iterate>
				</ul></font>
		</logic:notEmpty>
 			
		<div>
			<input type="checkbox" name="filtroVerDatosPersonales" ${formulari.filtroVerDatosPersonales ? 'checked' : ''}  onchange="reloadCheckbox('filtroVerDatosPersonales', 'editar')" />
				<bean:message key="global.mostrarDatos"/>
		</div>	
		
  		<table class="detallePaciente">
  		<tr >
			<td><bean:message key="paciente.edicion.residencia"/></td><td>
		
			<html:select property="idDivisionResidencia"  > 
				<html:option value=""><bean:message key="paciente.edicion.residencia"/></html:option>
	   			<html:optionsCollection name="formulari" property="listaDivisionResidencias" label="nombreDivisionResidencia" value="idDivisionResidencia" />
			</html:select>
			
			</td>
		
		</tr>
		<tr>
			<td><bean:message key="paciente.edicion.identificador"/></td><td class="oidPaciente"><bean:write name="data" property="oidPaciente"/></td>
		</tr>
		<tr>
			<c:choose>
			    <c:when test="${formulari.filtroVerDatosPersonales}">
					<td><bean:message key="paciente.edicion.cip"/><br>
					<input type="button" onclick="javascript:goCambiarCip()" value="cambiar CIP"/>
					</td><td><bean:write name="data" property="CIP"/> 
					</td>
			    </c:when>
			    <c:otherwise>
					<td><bean:message key="paciente.edicion.cip"/></td><td><bean:write name="data" property="CIPMask"/></td>
			    </c:otherwise>
			</c:choose>
		</tr>
		<tr>
			<c:choose>
			    <c:when test="${formulari.filtroVerDatosPersonales}">
					<td><bean:message key="paciente.edicion.residente"/></td><td><html:text name="data" property="nombre"/>  <html:text name="data" property="apellido1"/>  <html:text name="data" property="apellido2"/></td>
			    </c:when>
			    <c:otherwise>
					<td><bean:message key="paciente.edicion.residente"/></td><td><bean:write name="data" property="nombreMask"/>  <bean:write name="data" property="apellido1Mask"/>  <bean:write name="data" property="apellido2Mask"/></td>
			    </c:otherwise>
			</c:choose>
		</tr>
		

		<tr>
			<td><bean:message key="paciente.edicion.mutua"/></td>
			<td><select name="mutua" >
			   	 	<option value='S' ${data.mutua == 'S' ? 'selected' : ' '}><c:out value="SI" ></c:out></option>
			   	 	<option value='N' ${data.mutua == 'N' || data.mutua == null ? 'selected' : ' '}><c:out value="NO" ></c:out></option>  
				</select>
			</td>
		</tr>		
		<tr>
			<td><bean:message key="paciente.edicion.nIdentidad"/></td>
			<c:choose>
			    <c:when test="${formulari.filtroVerDatosPersonales}">
					<td><html:text name="data" property="numIdentidad"/></td>
			    </c:when>
			    <c:otherwise>
					<td><bean:write name="data" property="numIdentidadMask"/></td>
			    </c:otherwise>
			</c:choose>
		</tr>
		<tr>
			<td><bean:message key="paciente.edicion.nSegSocial"/></td>
			<c:choose>
			    <c:when test="${formulari.filtroVerDatosPersonales}">
					<td><html:text name="data" property="segSocial"/></td>
			    </c:when>
			    <c:otherwise>
					<td><bean:write name="data" property="segSocialMask"/></td>
			    </c:otherwise>
			</c:choose>
		</tr>
		<tr>
			<td><bean:message key="paciente.edicion.idPacienteResidencia"/></td>
			<td><html:text name="data" property="idPacienteResidencia"/></td>
		</tr>
		<tr>
			<td><bean:message key="paciente.planta"/></br><bean:message key="paciente.habitacion"/></td>
			<td><html:text name="data" property="planta" /></br><html:text name="data" property="habitacion" /></td>
		</tr>
		<tr>
			<td><bean:message key="paciente.edicion.hacerSpd"/></td>
			<td><select name="spd" >
			   	 	<option value='S' ${data.spd == 'S' ? 'selected' : ' '}><c:out value="SI" ></c:out></option>
			   	 	<option value='N' ${data.spd == 'N' ? 'selected' : ' '}><c:out value="NO" ></c:out></option>  
				</select>
			</td>
		</tr>
		<tr>
			<td><bean:message key="paciente.edicion.panyales"/></td>
			<td>
				<select name="bolquers" >
			   	 	<option value='S' ${data.bolquers == 'S' ? 'selected' : ' '}><c:out value="SI" ></c:out></option>
			   	 	<option value='N' ${data.bolquers == 'N' ? 'selected' : ' '}><c:out value="NO" ></c:out></option>  
				</select>
			</td>
		</tr>		
		<tr>
			<td><bean:message key="paciente.edicion.fechaAlta"/></td>
			<td><bean:write name="data" property="fechaAltaPaciente" /></td>
		</tr>
		<tr>
			<td><bean:message key="paciente.edicion.comentarios"/></td>
			<td><html:textarea name="data" property="comentarios" /></td>
		</tr>
		<tr>
			<td><bean:message key="paciente.edicion.estado"/></td>
			<td>
				<select name="estatus">
				    <option value=""></option>
				    <c:forEach items="${formulari.listaEstatusResidente}" var="bean">
				        <option value="${bean}" 
				            ${bean == data.estatus ? 'selected="selected"' : ''}>
				            <c:out value="${bean}" />
				        </option>
				    </c:forEach>
				</select>
				  (<bean:write name="data" property="activo"/>)</br>
			</td>
		</tr>
		<tr>
			<td><bean:message key="paciente.edicion.idFarmatic"/></td>
			<td><bean:write name="data" property="idPharmacy"/></td>
		</tr>
		<tr>
			<td><bean:message key="paciente.edicion.codigoUP"/></td>
			<td><bean:write name="data" property="UPFarmacia"/></td>
		</tr>				
		<tr>
			<td>	
				<p class="botons">
					<input type="button" onclick="javascript:volver()" value="Volver"/>
					<input type="button" onclick="javascript:goEditarOK()" value="Grabar"/>
				</p>	
			</td>	
		</tr>	
	</table>
</div>	
</html:form>

</body>
</html:html>