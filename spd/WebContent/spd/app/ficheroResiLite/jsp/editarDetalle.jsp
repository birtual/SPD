<%@ page language="java" %>
<%@ page import="java.util.*" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">

<head>
<jsp:include page="/spd/jsp/global/head.jsp"/>
<title>Edición Fichero Resi Carga Robot</title>
</head>
<bean:define id="formulari" name="FicheroResiForm" type="lopicost.spd.struts.form.FicheroResiForm" />
	<script language="javaScript" src="/spd/spd/app/ficheroResiLite/js/ficheroResiLite.js"></script>


<link rel="stylesheet" href="/spd/spd/css/ficheroResiLite/base.css" media="screen" />

<body id="general">
	<!-- mostramos mensajes y errores, si existen -->

		<h2>Edición Fichero Resi Carga Robot</h2>
		<html:form action="/FicheroResiDetalleLite.do" method="post">	




<div id="contingut">
    <html:hidden property="parameter" value="editar"/>
    <html:hidden property="ACTIONTODO" />
    <html:hidden property="fieldName1" /> 
    <html:hidden property="filtroDivisionResidenciasCargadas" /> 
    <html:hidden property="idDivisionResidencia" /> 
    <html:hidden property="oidDivisionResidencia" /> 
    <html:hidden property="filtroProceso" />
    <html:hidden property="oidFicheroResiCabecera" /> 
    <html:hidden property="oidFicheroResiDetalle" />
    <html:hidden property="idProceso" /> 
    <html:hidden property="campoOrder" />
    <html:hidden property="excluirNoPintar" />
    <html:hidden property="seleccionResiCIP" /> 			
	<html:hidden property="seleccionResiApellidosNombre" />
	<html:hidden property="seleccionResiNombrePaciente" />
    <html:hidden property="seleccionResiCn" />
    <html:hidden property="seleccionEstado" /> 
    <html:hidden property="seleccionResiMedicamento" />
    <html:hidden property="seleccionResiFormaMedicacion" />
    <html:hidden property="seleccionResiObservaciones" />
    <html:hidden property="seleccionResiComentarios" />
    <html:hidden property="seleccionResiSiPrecisa" />
    <html:hidden property="seleccionResiPeriodo" />
    <html:hidden property="seleccionResiViaAdministracion" />
    <html:hidden property="seleccionSpdCnFinal" />
    <html:hidden property="seleccionSpdNombreBolsa" />
    <html:hidden property="seleccionSpdNombreBolsa" />
    <html:hidden property="seleccionResiTipoMedicacion" />
    <html:hidden property="seleccionSpdAccionBolsa" />
    <html:hidden property="seleccionIncidencia" />
    <html:hidden property="seleccionConfirmar" />
    <html:hidden property="seleccionSecuenciaGuide" />   
    <html:hidden property="idProcessIospd" />
    <html:hidden property="mode" />         
    <html:hidden property="filtroNumComprimidos" />  
    <html:hidden property="filtroRegistroAnterior" />  
    <html:hidden property="filtroRegistroRobot" />  
    <html:hidden property="filtroValidacionDatos" />  
    <html:hidden property="filtroPrincipioActivo" />  
    <html:hidden property="filtroNoSustituible" />  
    <html:hidden property="filtroDiferentesGtvmp" />  


    

<bean:define id="data" name="formulari" property="ficheroResiDetalleBean" type="lopicost.spd.struts.bean.FicheroResiBean" />
					 <!--logic:iterate id="data" name="FicheroResiForm" property="listaFicheroResiDetalleBean" type="lopicost.spd.struts.bean.FicheroResiBean" indexId="position"> -->
				
					<div>idProceso: <bean:write name="data" property="idProceso" /></div>
					<div>Residencia: <bean:write name="data" property="idDivisionResidencia" /></div>
					<div>Fecha proceso:  <bean:write name="data" property="fechaHoraProceso" /></div>


 	<!-- mostramos mensajes y errores. si existen -->
	<logic:notEmpty name="formulari" property="errors">
		<font color="red"><ul>
			<u>Mensaje:</u>
				<logic:iterate id="error" name="formulari" property="errors" type="java.lang.String">
						<li><bean:write name="error"/></li>
				</logic:iterate>
			</ul></font>
	</logic:notEmpty>

<fieldset>
		<div><bean:write name="data" property="resiNombrePaciente" /> <bean:write name="data" property="resiApellido1" /> <bean:write name="data" property="resiApellido2" /> - <html:text name="data" property="resiCIP" /></div>
		
		<logic:greaterThan name="formulari" property="oidPaciente" value="0">
			 <div >identificador: <b><bean:write name="formulari" property="oidPaciente" /></b></div>
		</logic:greaterThan>
		<logic:lessEqual name="formulari" property="oidPaciente" value="0">
			 <div ><b>No existe en mantenimiento de residentes</b></div>
		</logic:lessEqual>
	<fieldset>
	<table class="tablaResi">
		<tr>	
		 	<th>CN Resi</th>
			<th>Medicamento resi</th>
			<th>Inicio-Fin tratamiento</th>
		  	<th>Observaciones</th>
			<th>Variante</th>
		 	<th>Comentarios</th>
		 	<th>TipoMedicacion</th>
		  	<th>Vía administración</th>	
		</tr>	
		 <tr>	
		 	<td><html:text name="data" property="resiCn" /></td>
			<td nowrap><html:text name="data" property="resiMedicamento" /></td>
			<td nowrap><html:text name="data" property="resiInicioTratamiento" /> - <html:text name="data" property="resiFinTratamiento" /></td>
		  	<td><html:textarea name="data" property="resiObservaciones" /></td>
			<td><html:textarea name="data" property="resiVariante" /></td>
		 	<td><html:textarea name="data" property="resiComentarios" /></td>
		 	<td><html:text name="data" property="resiTipoMedicacion" /></td>
		  	<td><html:text name="data" property="resiViaAdministracion" /></td>
		</tr>			
	</table>	

		
	<table class="tablaResi">
		<tr>	
			<th>marcado automático</th>	
			<th>L</th>	  
			<th>M</th>	  
			<th>X</th>	  
			<th>J</th>	  
			<th>V</th>	  
			<th>S</th>	  
			<th>D</th>
			<logic:notEmpty name="FicheroResiForm" property="listaTomasCabecera">	
				<logic:iterate id="dose" name="FicheroResiForm" property="listaTomasCabecera" type= "lopicost.spd.helium.model.Dose" >
					<th class="<bean:write  name="dose" property="tipo" /> pautasDia"><bean:write  name="dose" property="name" /></th>
				</logic:iterate>
			</logic:notEmpty>	
	
		</tr>			
		<tr>			
			<td><bean:write name="data" property="resiDiasAutomaticos" /></td>
			<td class="pautasDia"><html:checkbox name="data" property="resiD1" value="X"/></td>
			<td class="pautasDia"><html:checkbox name="data" property="resiD2" value="X"/></td>
			<td class="pautasDia"><html:checkbox name="data" property="resiD3" value="X"/></td>
			<td class="pautasDia"><html:checkbox name="data" property="resiD4" value="X"/></td>
			<td class="pautasDia"><html:checkbox name="data" property="resiD5" value="X"/></td>
			<td class="pautasDia"><html:checkbox name="data" property="resiD6" value="X"/></td>
			<td class="pautasDia"><html:checkbox name="data" property="resiD7" value="X"/></td>
			
			<bean:define id="campos" name="FicheroResiForm" property="camposPantallaBean" />
			<%
			for (int i = 1; i <= 24; i++) {
				String visibleProperty = "visibleToma" + i;
				String resiProperty = "resiToma" + i;
			%>
		<logic:equal property="<%= visibleProperty %>" name="campos" value="true">
			<td class="pautasDia"><html:text name="data" property="<%= resiProperty %>" styleClass="resiToma" /></td>
		</logic:equal>
					    
			<%
				}
			%>		
		</tr>		
	</table>
</fieldset>
<fieldset>
	<table class="tablaResi">
		<tr>	
	 		<th>CN Final</th>
		  	<th>Nombre en bolsa</th>
	  		<th>Inicio fin (formato dd/mm/yyyy)</th>
 		  	<th>Acción bolsa</th>
		  	<th>SiPrecisa</th>
 			<th>Forma Medicacion</th>
			<th>Periodo</th>					
			<th>Días Mes Concretos</th>
			<th>SecuenciaGuide</th>
		</tr>	
		<tr>	
			<td><html:text name="data" property="spdCnFinal" /></td>
			<td><html:textarea name="data" property="spdNombreBolsa" /></td>
			<td nowrap><html:text name="data" property="resiInicioTratamientoParaSPD" /> - <html:text name="data" property="resiFinTratamientoParaSPD" /></td>
			<td>
				</br>
				<logic:notEmpty name="FicheroResiForm" property="listaSpdAccionBolsa">	
				<html:select property="spdAccionBolsa"  value="${data.spdAccionBolsa}" > 
	   			<c:forEach items="${FicheroResiForm.listaSpdAccionBolsa}" var="bean"> 
		       		 <option value='${bean}' ${data.spdAccionBolsa == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
	    		</c:forEach>                     
				</html:select>
			     </logic:notEmpty>	
			</td>

		  	<td class="pautasDia"><html:checkbox name="data" property="resiSiPrecisa" value="X" disabled="true" /></td>
			<td><bean:write name="data" property="spdFormaMedicacion" /></td>
			<td>
	  			<logic:notEmpty name="FicheroResiForm" property="listaResiPeriodo">	
				<html:select property="resiPeriodo"  value="${data.resiPeriodo}" > 
	   					<c:forEach items="${FicheroResiForm.listaResiPeriodo}" var="bean"> 
			       		 <option value='${bean}' ${data.resiPeriodo == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
	   				</c:forEach>                     
				</html:select>
		     </logic:notEmpty>	
			</td>
			<td><html:text name="data" property="diasMesConcretos" /></td>
			<td><html:text name="data" property="secuenciaGuide" /></td>			
		  </tr>				
	</table>	
	

		<table class="tablaResi">
			<tr>
	 			<th>mensajes Alerta</th>
				<th>mensajes Info interna</th>
				<th>mensajes para la Residencia</th>
			</tr>

			<tr>
				<td><bean:write name="data" property="mensajesAlerta" /></td>
				<td><html:textarea name="data" property="mensajesInfo"/></td>
				<td><html:textarea name="data" property="mensajesResidencia"/></td>
		  </tr>	
	  </table>
</fieldset>
	



	  
	  		<ul>
			<li>tipoEnvioHelium:<bean:write name="data" property="tipoEnvioHelium" /></li><br>
			<li>Incidencia:<bean:write name="data" property="incidencia" /></li>
			<li>Texto original cargado:<span  class="textoAzul"><bean:write name="data" property="detalleRow" /></li>
	  		</ul>
			
	<center></center>

</fieldset>
	  
	<div>
		<p class="botons">
				<input type="button" class="btn primary" onclick="javascript:volver()" value="Volver"/>
				<!--<input type="button" onclick="javascript:grabar('<bean:write name="data" property="oidFicheroResiCabecera"/>')" value="Confirmar"/> -->
				<input type="button" class="btn primary" onclick="javascript:grabar()" value="Grabar"/>
				
				
			<c:choose> 
   					 <c:when test="${data.porcentajeCIPS > 90}">
        				<input type="button" class="btn primary"  onclick="javascript:crearNuevaToma()" value="Gestionar las tomas"/>
        				(Esta producción es del <c:out value="${data.porcentajeCIPS}%" /> de los cips de la residencia)
    				</c:when>
    			<c:otherwise>
    				(No es posible modificar o añadir tomas porque esta producción solo contiene el <c:out value="${data.porcentajeCIPS}%" /> de los cips de la residencia)
			    </c:otherwise>
			</c:choose>



				
				

		</p>	
	</div>
	
		</div>	


	</html:form>
</body>
</html>