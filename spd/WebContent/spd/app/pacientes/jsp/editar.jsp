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
<title>Edición de usuario SPD</title>
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
	
 
 
 	
  		<table class="detallePaciente">
  		<tr >
			<td>Residencia</td><td>
		
			<html:select property="idDivisionResidencia"  > 
				<html:option value="">Residencia</html:option>
	   			<html:optionsCollection name="formulari" property="listaDivisionResidencias" label="nombreDivisionResidencia" value="idDivisionResidencia" />
			</html:select>
			
			</td>
		
		</tr>
		<tr>
			<td>Identificador</td><td class="oidPaciente"><bean:write name="data" property="oidPaciente"/></td>
		</tr>

		<tr>
			<td>CIP</td><td><bean:write name="data" property="CIP"/></td>
		</tr>
		<tr>
			<td>Residente</td><td><html:text name="data" property="nombre"/></br>
			<html:text name="data" property="apellido1"/></br>
			<html:text name="data" property="apellido2"/></td>
		</tr>
		<tr>
			<td>Mutua</td>
			<td><select name="mutua" >
			   	 	<option value='S' ${data.mutua == 'S' ? 'selected' : ' '}><c:out value="SI" ></c:out></option>
			   	 	<option value='N' ${data.mutua == 'N' || data.mutua == null ? 'selected' : ' '}><c:out value="NO" ></c:out></option>  
				</select>
			</td>
		</tr>		
		<tr>
			<td>Nº identidad</br>
			Nº Seg Social</br>
			id en la residencia
			</td>
			<td><html:text name="data" property="nIdentidad"/></br>
			<bean:write name="data" property="segSocial"/></br>
			<html:text name="data" property="idPacienteResidencia"/></td>
		</tr>
		<tr>
			<td>Planta</br>Habitación</td>
			<td><html:text name="data" property="planta" /></br><html:text name="data" property="habitacion" /></td>
		</tr>
		<tr>
			<td>Hacer spd</td>
			<td><select name="spd" >
			   	 	<option value='S' ${data.spd == 'S' ? 'selected' : ' '}><c:out value="SI" ></c:out></option>
			   	 	<option value='N' ${data.spd == 'N' ? 'selected' : ' '}><c:out value="NO" ></c:out></option>  
				</select>
			</td>
		</tr>
		<tr>
			<td>Servir pañales</td>
			<td>
				<select name="bolquers" >
			   	 	<option value='S' ${data.bolquers == 'S' ? 'selected' : ' '}><c:out value="SI" ></c:out></option>
			   	 	<option value='N' ${data.bolquers == 'N' ? 'selected' : ' '}><c:out value="NO" ></c:out></option>  
				</select>
			</td>
		</tr>		
		<tr>
			<td>Fecha alta</td>
			<td><bean:write name="data" property="fechaAltaPaciente" /></td>
		</tr>
		<tr>
			<td>Comentarios</td>
			<td><html:textarea name="data" property="comentarios" /></td>
		</tr>
		<tr>
			<td>Estado</td>
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
			<td>idFarmatic</td>
			<td><bean:write name="data" property="idPharmacy"/></td>
		</tr>
		<tr>
			<td>Código UP Farmacia</td>
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
</html>