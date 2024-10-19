<%@page import="lopicost.spd.utils.SPDConstants"%>
<%@page import="java.text.DecimalFormat"%>
<%@ page language="java" %>
<%@ page import="java.util.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">

<head>
	<jsp:include page="/spd/jsp/global/head.jsp"/>
	<title>Producciones por CIP</title>
</head>

<bean:define id="formulari" name="PacientesForm" type="lopicost.spd.struts.form.PacientesForm" />
<script language="javaScript" src="/spd/spd/app/pacientes/js/pacientes.js"></script>


<body id="general">

	<html:form action="/Pacientes.do" method="post">	
	<html:errors/>
	<html:hidden property="parameter" value="detalleTratamientoSPD"/>
	<html:hidden property="oidPaciente" />

	<div id="contingut">
		<div><h3><p>Producciones CIP</p></h3></div>
		<div><p><bean:write name="formulari" property="CIP"/>-<bean:write name="formulari" property="nombreApellidos"/></p></div>
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
		<logic:notEmpty  name="formulari" property="listaProcesosCargados">
			<div>
				<label for="campoGoogle" accesskey="e">Producciones</label>
					<html:select property="idProceso"  onchange="submit()"> 
		   			<html:optionsCollection name="formulari" property="listaProcesosCargados" label="idEstado" value="idProceso" />
				</html:select>
			</div>	
		</logic:notEmpty>
	
	
	<fieldset>

	<logic:notEmpty  name="formulari" property="listaBeans">
	<table class="blueTable" border="1">
		<bean:define id="campos" name="PacientesForm" property="camposPantallaBean" />
	 	<tr>
			<th class="infoSelect"> Mensajes (Info /<span class="textoRojo">alerta</span> /<span class="textoAzul">resi</span> )</th> 
			<th>Periodo</th>	
			<th>spdCnFinal</th>
			<th >Nombre en bolsa</th>
			<th>Forma medicacion</th>
			<th>Acci�n bolsa</th> 	
			<th>Si Precisa</th>	 
			<th>Identificador residente</th>	
			<th>Nombre </th>	
			<th>CIP</th>	
			<th>CN</th>	
			<th>Medicamento resi</th>	
			<th>Variante</th>	
			<th>Observaciones</th>	
			<th>Comentarios</th>
	        <th > Tipo medicaci�n</th>
			<th >inicio-fin tratamiento
			<th>L</th><th>M</th><th>X</th><th>J</th><th>V</th><th>S</th><th>D</th>	  
			<logic:notEmpty name="PacientesForm" property="listaTomasCabecera">	
			 	<logic:iterate id="dose" name="PacientesForm" property="listaTomasCabecera" type= "lopicost.spd.helium.model.Dose" >
			 		<th><bean:write  name="dose" property="name" /></th>
			 	</logic:iterate>
			</logic:notEmpty>	 	
			<th>Estado linea</th>
			<th> Editado</th>    
	     </tr>

	<!--  Inicio del bucle para mostrar las filas -->
	<logic:iterate id="data" name="PacientesForm" property="listaBeans" type="lopicost.spd.struts.bean.FicheroResiBean" indexId="position">
		<tr>
			<td title="<c:out value='${data.mensajesAlerta}' /> <c:out value='${data.mensajesInfo}' /> <c:out value='${data.mensajesResidencia}' />"><bean:write name="data" property="mensajesInfo" /> </br>
				<span class="textoRojo"><bean:write name="data" property="mensajesAlerta" /></span>
				<span class="textoAzul"><bean:write name="data" property="mensajesResidencia" /></span>
			</td>
			<td><bean:write name="data" property="resiPeriodo" />    <bean:write name="data" property="diasMesConcretos" /> 	<bean:write name="data" property="secuenciaGuide" /></td>
			<td><bean:write name="data" property="spdCnFinal" /></td>
			<td><bean:write name="data" property="spdNombreBolsa" /></td>
			<td><bean:write name="data" property="spdFormaMedicacion" /></td>
			<td><bean:write name="data" property="spdAccionBolsa" /></td>
			<td><html:checkbox disabled="true" name="data" property="resiSiPrecisa" value="X" /></td> 
			<td class="oidPaciente" align="center"><bean:write name="PacientesForm" property="oidPaciente" /></td>
			<td><bean:write name="data" property="resiApellidosNombre" /></td>
		    <td><bean:write name="data" property="resiCIP" /></td>
			<td><bean:write name="data" property="resiCn" /></td>
			<td><bean:write name="data" property="resiMedicamento" /></td>
			<td><bean:write name="data" property="resiVariante" /></td>
			
			<td>
			<c:choose>
				<c:when test="${fn:contains(data.mensajesInfo, '(INTERNO FARMACIA)')}">
				  <span class="textoRojo"><bean:write name="data" property="resiObservaciones" />  </span>
		     	</c:when>
		        <c:otherwise>
		        	<bean:write name="data" property="resiObservaciones" />
		     	</c:otherwise>
		    </c:choose>
		    </td>
		  
			<td><bean:write name="data" property="resiComentarios" /></td>
			<td><bean:write name="data" property="resiTipoMedicacion" />
			<td nowrap><bean:write name="data" property="resiInicioTratamiento" /> - <bean:write name="data" property="resiFinTratamiento" />
			<c:choose>
			  <c:when test="${not empty data.resiInicioTratamientoParaSPD and (data.resiInicioTratamientoParaSPD ne data.resiInicioTratamiento or data.resiFinTratamientoParaSPD ne data.resiFinTratamiento)}">
			       <br><I> SPD (  <c:out value="${data.resiInicioTratamientoParaSPD}" /> - 	        <c:out value="${data.resiFinTratamientoParaSPD}" />) </I>
			    </c:when>
			</c:choose>
			</td>
			<td><html:checkbox disabled="true" name="data" property="resiD1" value="X" /></td>
			<td><html:checkbox disabled="true" name="data" property="resiD2" value="X" /></td>
			<td><html:checkbox disabled="true" name="data" property="resiD3" value="X"/></td>
			<td><html:checkbox disabled="true" name="data" property="resiD4" value="X"/></td>
			<td><html:checkbox disabled="true" name="data" property="resiD5" value="X"/></td>
			<td><html:checkbox disabled="true" name="data" property="resiD6" value="X"/></td>
			<td><html:checkbox disabled="true" name="data" property="resiD7" value="X"/></td>
		
		
			<logic:equal property="visibleToma1" name="campos" value="true"><td class="toma"><bean:write name="data" property="resiToma1" /></td></logic:equal>
			<logic:equal property="visibleToma2" name="campos" value="true"><td class="toma"><bean:write name="data" property="resiToma2" /></td></logic:equal>
			<logic:equal property="visibleToma3" name="campos" value="true"><td class="toma"><bean:write name="data" property="resiToma3" /></td></logic:equal>
			<logic:equal property="visibleToma4" name="campos" value="true"><td class="toma"><bean:write name="data" property="resiToma4" /></td></logic:equal>
			<logic:equal property="visibleToma5" name="campos" value="true"><td class="toma"><bean:write name="data" property="resiToma5" /></td></logic:equal>
			<logic:equal property="visibleToma6" name="campos" value="true"><td class="toma"><bean:write name="data" property="resiToma6" /></td></logic:equal>
			<logic:equal property="visibleToma7" name="campos" value="true"><td class="toma"><bean:write name="data" property="resiToma7" /></td></logic:equal>
			<logic:equal property="visibleToma8" name="campos" value="true"><td class="toma"><bean:write name="data" property="resiToma8" /></td></logic:equal>
			<logic:equal property="visibleToma9" name="campos" value="true"><td class="toma"><bean:write name="data" property="resiToma9" /></td></logic:equal>
			<logic:equal property="visibleToma10" name="campos" value="true"><td class="toma"><bean:write name="data" property="resiToma10" /></td></logic:equal>
			<logic:equal property="visibleToma11" name="campos" value="true"><td class="toma"><bean:write name="data" property="resiToma11" /></td></logic:equal>
			<logic:equal property="visibleToma12" name="campos" value="true"><td class="toma"><bean:write name="data" property="resiToma12" /></td></logic:equal>
			<logic:equal property="visibleToma13" name="campos" value="true"><td class="toma"><bean:write name="data" property="resiToma13" /></td></logic:equal>
			<logic:equal property="visibleToma14" name="campos" value="true"><td class="toma"><bean:write name="data" property="resiToma14" /></td></logic:equal>
			<logic:equal property="visibleToma15" name="campos" value="true"><td class="toma"><bean:write name="data" property="resiToma15" /></td></logic:equal>
			<logic:equal property="visibleToma16" name="campos" value="true"><td class="toma"><bean:write name="data" property="resiToma16" /></td></logic:equal>
			<logic:equal property="visibleToma17" name="campos" value="true"><td class="toma"><bean:write name="data" property="resiToma17" /></td></logic:equal>
			<logic:equal property="visibleToma18" name="campos" value="true"><td class="toma"><bean:write name="data" property="resiToma18" /></td></logic:equal>
			<logic:equal property="visibleToma19" name="campos" value="true"><td class="toma"><bean:write name="data" property="resiToma19" /></td></logic:equal>
			<logic:equal property="visibleToma20" name="campos" value="true"><td class="toma"><bean:write name="data" property="resiToma20" /></td></logic:equal>
			<logic:equal property="visibleToma21" name="campos" value="true"><td class="toma"><bean:write name="data" property="resiToma21" /></td></logic:equal>
			<logic:equal property="visibleToma22" name="campos" value="true"><td class="toma"><bean:write name="data" property="resiToma22" /></td></logic:equal>
			<logic:equal property="visibleToma23" name="campos" value="true"><td class="toma"><bean:write name="data" property="resiToma23" /></td></logic:equal>
			<logic:equal property="visibleToma24" name="campos" value="true"><td class="toma"><bean:write name="data" property="resiToma24" /></td></logic:equal>
		
			<td><bean:write name="data" property="idEstado" /></td>
			<td><bean:write name="data" property="editado" /></td>
		</tr>
		</logic:iterate>
	</table>
	</logic:notEmpty>
	<logic:empty name="formulari" property="listaBeans">
		No existen registros a mostrar
	</logic:empty>
		
		</fieldset>
		<p class="botons">
			<input type="button" onclick="javascript:cerrar()" value="Cerrar"/>
		</p>	
	</div>		
</html:form>
</body>
</html>