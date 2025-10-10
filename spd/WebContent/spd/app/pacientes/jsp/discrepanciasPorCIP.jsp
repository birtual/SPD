<%@page import="java.text.DecimalFormat"%>
<%@ page language="java" %>
<%@ page import="java.util.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">

<head>
	<jsp:include page="/spd/jsp/global/head.jsp"/>
	<title><bean:message key="paciente.edicion.discrepancias.titulo"/> </title>
</head>

<bean:define id="formulari" name="PacientesForm" type="lopicost.spd.struts.form.PacientesForm" />
<bean:define id="pac" name="formulari" property="pacienteBean"  type="lopicost.spd.struts.bean.PacienteBean" />


<script language="javaScript" src="/spd/spd/app/pacientes/js/pacientes.js"></script>


<body id="general">
<html:form action="/Pacientes.do" method="post">	
<html:errors/>

<div id="contingut">
   	<html:hidden property="parameter" />
   	<html:hidden property="oidPaciente" />  
   	<!--html:hidden property="filtroVerDatosPersonales" /-->  
	<div>
		<input type="checkbox" name="filtroVerDatosPersonales" ${formulari.filtroVerDatosPersonales ? 'checked' : ''}  onchange="reloadCheckbox('filtroVerDatosPersonales', 'detalleDiscrepancias')" />
			Mostrar datos 
	</div>	
	<c:choose>
		    <c:when test="${formulari.filtroVerDatosPersonales}">
				<div><p><bean:write name="pac" property="CIP" />-<bean:write name="pac" property="nombreApellidos" /></p></div>
		    </c:when>
		    <c:otherwise>
				<div><p><bean:write name="pac" property="CIPMask" />-<bean:write name="pac" property="nombreApellidosMask" /></p></div>
		    </c:otherwise>
	</c:choose>
	
	<div><h3><p><bean:message key="paciente.edicion.discrepancias.nota14dias"/></p></h3></div>
	<div>
		<p>		
		<logic:greaterThan name="formulari" property="oidPaciente" value="0">
			 <div >Identificador: <b><bean:write name="formulari" property="oidPaciente" /></b></div>
		</logic:greaterThan>
		<logic:lessEqual name="formulari" property="oidPaciente" value="0">
			 <div ><b><bean:message key="paciente.edicion.sinAlta"/></b></div>
		</logic:lessEqual>
		</p>
	</div>
 	<fieldset>
	
	<logic:notEmpty  name="formulari" property="listaBeans">
		<table id="listaPacientesBean" align="center" border="1">
			<tr>
				<th class="amarilloClaro"></th>
				<th class="amarilloClaro"></th>
				<th class="amarilloClaro"><bean:message key="paciente.edicion.discrepancias.residencia"/></th>
				<th class="amarilloClaro"></th>
				<th class="amarilloClaro"></th>
				<th class="azulClaro"></th>
				<th class="azulClaro"><bean:message key="paciente.edicion.discrepancias.comparacion"/></th>
				<th class="azulClaro"></th>
			 	<th class="verdeClaro"></th>
			 	<th class="verdeClaro"></th>
			 	<th class="verdeClaro"><bean:message key="paciente.edicion.discrepancias.farmaticPrevision"/></th>
			 	<th class="verdeClaro"></th>
			 	<th class="verdeClaro"></th>
			 	<th class="verdeClaro"></th>
			</tr>
			<tr>
			 	<th class="amarilloClaro">Cn Resi</th>
				<th class="amarilloClaro"><bean:message key="paciente.edicion.discrepancias.medicamentoResi"/></th>
			 	<th class="amarilloClaro"><bean:message key="paciente.edicion.discrepancias.cnSust"/></th>
				<th class="amarilloClaro"><bean:message key="paciente.edicion.discrepancias.nombreBolsa"/></th>
				<th class="amarilloClaro"><bean:message key="paciente.edicion.accionBolsa"/></th>
			 	<th class="azulClaro"><bean:message key="paciente.edicion.discrepancias.spdPrevision"/></th>
			 	<th class="azulClaro"><bean:message key="paciente.edicion.discrepancias.nombreGtvmp"/></th>
			 	<th class="azulClaro"><bean:message key="paciente.edicion.discrepancias.farmaticPrevision"/></th>
			 	<th class="verdeClaro"><bean:message key="paciente.edicion.discrepancias.cn"/>Cn</th>
			 	<th class="verdeClaro"><bean:message key="paciente.edicion.discrepancias.medicamento"/></th>
			 	<th class="verdeClaro"><bean:message key="paciente.edicion.discrepancias.cantidad"/></th>
			 	<th class="verdeClaro"><bean:message key="paciente.edicion.discrepancias.pauta"/></th>
			 	<th class="verdeClaro"><bean:message key="paciente.edicion.discrepancias.caducidad"/></th>
			 	<th class="verdeClaro"><bean:message key="paciente.edicion.discrepancias.disponibles"/></th>
		   </tr>
	
	 	<logic:iterate id="data" name="formulari" property="listaBeans" type="lopicost.spd.struts.bean.DiscrepanciaBean" indexId="position">
		<logic:equal  name="data" property="cuadraPrevision" value="true">
			<tr>
		</logic:equal>			
		<logic:notEqual  name="data" property="cuadraPrevision" value="true">
			<tr class="alertaSustXResi">
		</logic:notEqual>			
				<td><bean:write name="data" property="resiCn" /></td>
				<td><bean:write name="data" property="resiMedicamento" /></td>
				<td><bean:write name="data" property="spdCnSust" /></td>
				<td><bean:write name="data" property="spdNombreBolsa" /></td>
				<td><bean:write name="data" property="spdAccionBolsa" /></td>
				<td align="right">
					<logic:notEqual  name="data" property="resiMedicamento" value="">
<%
		DecimalFormat df = new DecimalFormat("0.##");
		String spdPrevision = df.format(data.getSpdPrevision());
		out.println(spdPrevision);
%>					
					
						<!-- bean:write name="data" property="spdPrevision" /> -->
					</logic:notEqual>
				</td>
				<td><bean:write name="data" property="nomGtVmp" /></td>
				<td align="right">
					<logic:notEqual  name="data" property="recetaCn" value="">
<%
		DecimalFormat df2 = new DecimalFormat("0.##");
		String recetaPrevision = df2.format(data.getRecetaPrevision());
		out.println(recetaPrevision);
%>						
						<!--bean:write name="data" property="recetaPrevision" />-->
					</logic:notEqual>
				</td>
				<td><bean:write name="data" property="recetaCn" /></td>
				<td><bean:write name="data" property="recetaMedicamento" /></td>
				<td><bean:write name="data" property="recetaCantidad" /></td>
				<td><bean:write name="data" property="recetaPauta" /></td>
				<td><bean:write name="data" property="recetaCaducidad" /></td>
				<td align="right"><bean:write name="data" property="totalRecetasDisponibles" /></td>
			</tr>
	    </logic:iterate>
	</table>

	</logic:notEmpty>
	<logic:empty  name="formulari" property="listaBeans">
		<bean:message key="paciente.edicion.sinRegistros"/>
	</logic:empty>
		
	</fieldset>
	<p class="botons">
		<input type="button" onclick="javascript:cerrar()" value="Cerrar"/>
	</p>	
	</div>		
</html:form>
</body>
</html>