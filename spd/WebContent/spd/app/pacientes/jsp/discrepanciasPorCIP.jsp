<%@page import="java.text.DecimalFormat"%>
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

<title>Discrepancias por CIP </title>
</head>



<bean:define id="formulari" name="PacientesForm" type="lopicost.spd.struts.form.PacientesForm" />
<script language="javaScript" src="/spd/spd/app/pacientes/js/pacientes.js"></script>


<body id="general">

<html:form action="/Pacientes.do" method="post">	
<html:errors/>


<div id="contingut">

	<div><h3><p>Discrepancias por CIP (cálculos realizados sobre 14 días)</p></h3></div>
	<div><p><bean:write name="formulari" property="CIP" />-<bean:write name="formulari" property="nombreApellidos" /></p></div>
	<div>
		<p>		
		<logic:greaterThan name="formulari" property="oidPaciente" value="0">
			 <div >identificador: <b><bean:write name="formulari" property="oidPaciente" /></b></div>
		</logic:greaterThan>
		<logic:lessEqual name="formulari" property="oidPaciente" value="0">
			 <div ><b>No existe en mantenimiento de residentes</b></div>
		</logic:lessEqual>
		</p>
	</div>
 	<fieldset>

		<table id="listaPacientesBean" align="center" border="1">
			<tr>
				<th class="amarilloClaro"></th>
				<th class="amarilloClaro"></th>
				<th class="amarilloClaro">Residencia</th>
				<th class="amarilloClaro"></th>
				<th class="amarilloClaro"></th>
				<th class="azulClaro"></th>
				<th class="azulClaro">Comparación</th>
				<th class="azulClaro"></th>
			 	<th class="verdeClaro"></th>
			 	<th class="verdeClaro"></th>
			 	<th class="verdeClaro">Farmatic</th>
			 	<th class="verdeClaro"></th>
			 	<th class="verdeClaro"></th>
			 	<th class="verdeClaro"></th>
			</tr>
			<tr>
			 	<th class="amarilloClaro">Cn Resi</th>
				<th class="amarilloClaro">Medicamento resi</th>
			 	<th class="amarilloClaro">Cn Sust</th>
				<th class="amarilloClaro">Nombre bolsa</th>
				<th class="amarilloClaro">Acción bolsa</th>
			 	<th class="azulClaro">SPD Previsión</th>
			 	<th class="azulClaro">Nombre GtVmp</th>
			 	<th class="azulClaro">Farmatic Previsión</th>
			 	<th class="verdeClaro">Cn</th>
			 	<th class="verdeClaro">Medicamento</th>
			 	<th class="verdeClaro">Cantidad</th>
			 	<th class="verdeClaro">Pauta</th>
			 	<th class="verdeClaro">Caducidad</th>
			 	<th class="verdeClaro">Disponibles</th>
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
				<p class="botons">
					<input type="button" onclick="javascript:cerrar()" value="Cerrar"/>
				</p>	


	</fieldset>
	</div>		
	
	</html:form>

</body>
</html>