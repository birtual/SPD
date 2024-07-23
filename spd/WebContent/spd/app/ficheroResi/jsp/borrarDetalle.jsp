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
<title>Borrado de carga de fichero</title>
</head>
<bean:define id="formulari" name="FicheroResiForm" type="lopicost.spd.struts.form.FicheroResiForm" />


<script type="text/javascript">	
		document.write('<link rel="stylesheet" href=<%= "\"" +request.getContextPath() + "/spd/css/gestio.css\""%>  />');				 
		
		function volver()
		{
			var f = document.FicheroResiForm;
			f.parameter.value='list';
			f.ACTIONTODO.value='LIST';
			f.submit();
		}	
		
		function borrar(oidFicheroResiDetalle)
		{
		
			var f = document.FicheroResiForm;
			f.parameter.value='borrar';
			f.ACTIONTODO.value='CONFIRMADO_OK';
			f.oidFicheroResiDetalle.value=oidFicheroResiDetalle;
			f.submit();
		}	
				
</script>




<body id="general">
	
		<h2>Borrado de carga de fichero</h2>
		<html:form action="/FicheroResiDetalle.do" method="post">	



     <html:hidden property="parameter" value="borrar"/>
     <html:hidden property="ACTIONTODO" value="CONFIRMAR"/>
     <html:hidden property="filtroDivisionResidenciasCargadas" /> 
     <html:hidden property="oidFicheroResiCabecera" /> 
     <html:hidden property="oidFicheroResiDetalle" />
 
	<h3>Confirmar borrado</h3>
	
<table>
	   <tr>
	  <th>idProceso</th>
	  <th>Residencia</th>
	  <th>fechaHoraProceso</th>
	  <th>CIP</th>
	  <th>nombre paciente</th>
	  <th>Cn resi</th>
	  <th>medicamento resi</th>
	  <th>forma medicacion</th>
	  <th>inicio-fin tratamiento</th>
	  <th>inicio-fin producción</th>
	  <th>observaciones</th>
	  <th>variante</th>
	  <th>comentarios</th>
	  <th>SiPrecisa</th>
	  <th>via administración</th>
	  <th>spdCnFinal</th>
	  <th>Nombre en bolsa</th>
	  <th>forma Medicacion</th>
	  <th>acción bolsa</th>
	  <th>L</th>	  
	  <th>M</th>	  
	  <th>X</th>	  
	  <th>J</th>	  
	  <th>V</th>	  
	  <th>S</th>	  
	  <th>D</th>	  
	  <th>marcado automático</th>	  
				<th width="3px">01h</th>
				<th>02h</th>
				<th>03h</th>
				<th>04h</th>
				<th>05h</th>
				<th>06h</th>
				<th>07h</th>
				<th>08h</th>
				<th>09h</th>
				<th>10h</th>
				<th>11h</th>
				<th>12h</th>
				<th>13h</th>
				<th>14h</th>
				<th>15h</th>
				<th>16h</th>
				<th>17h</th>
				<th>18h</th>
				<th>19h</th>
				<th>20h</th>
				<th>21h</th>
				<th>22h</th>
				<th>23h</th>
				<th>24h</th>
				<th>Incidencia</th>
				<th>ResultLog</th>
     </tr>	
<logic:iterate id="data" name="FicheroResiForm" property="listaFicheroResiDetalleBean" type="lopicost.spd.struts.bean.FicheroResiBean" indexId="position"> 


       <tr>

   			<td><html:text name="data" property="idProceso" /></td>
			<td><html:text name="data" property="idDivisionResidencia" /></td>
			<td><html:text name="data" property="fechaHoraProceso" /></td>
			<td><html:text name="data" property="resiCIP" /></td>
			<td><html:text name="data" property="resiNombrePaciente" /></td>
			<td><html:text name="data" property="resiCn" /></td>
			<td><html:text name="data" property="resiMedicamento" /></td>
			<td><html:text name="data" property="resiFormaMedicacion" /></td>
			<td><html:text name="data" property="resiInicioTratamiento" /> - <html:text name="data" property="resiFinTratamiento" /></td>
			<td><html:text name="data" property="resiInicioTratamientoParaSPD" /> - <html:text name="data" property="resiFinTratamientoParaSPD" /></td>
			<td><html:text name="data" property="resiObservaciones" /></td>
			<td><html:text name="data" property="resiVariante" /></td>
			
			<td><html:text name="data" property="resiComentarios" /></td>
			<td><html:text name="data" property="resiSiPrecisa" /></td>

			<td><html:text name="data" property="resiViaAdministracion" /></td>
			<td><html:text name="data" property="spdCnFinal" /></td>
			<td><html:text name="data" property="spdNombreBolsa" /></td>
			<td><html:text name="data" property="spdFormaMedicacion" /></td>
			<td><html:text name="data" property="spdAccionBolsa" /></td>
			<td><html:text name="data" property="resiD1" /></td>
			<td><html:text name="data" property="resiD2" /></td>
			<td><html:text name="data" property="resiD3" /></td>
			<td><html:text name="data" property="resiD4" /></td>
			<td><html:text name="data" property="resiD5" /></td>
			<td><html:text name="data" property="resiD6" /></td>
			<td><html:text name="data" property="resiD7" /></td>
			<td><html:text name="data" property="resiDiasAutomaticos" styleClass="resiDiasAutomaticos" /></td>
			<td><html:text name="data" property="resiToma1" styleClass="resiToma" /></td>
			<td><html:text name="data" property="resiToma2" styleClass="resiToma" /></td>
			<td><html:text name="data" property="resiToma3" styleClass="resiToma" /></td>
			<td><html:text name="data" property="resiToma4" styleClass="resiToma" /></td>
			<td><html:text name="data" property="resiToma5" styleClass="resiToma" /></td>
			<td><html:text name="data" property="resiToma6" styleClass="resiToma" /></td>
			<td><html:text name="data" property="resiToma7" styleClass="resiToma" /></td>
			<td><html:text name="data" property="resiToma8" styleClass="resiToma" /></td>
			<td><html:text name="data" property="resiToma9" styleClass="resiToma" /></td>
			<td><html:text name="data" property="resiToma10" styleClass="resiToma" /></td>
			<td><html:text name="data" property="resiToma11" styleClass="resiToma" /></td>
			<td><html:text name="data" property="resiToma12" styleClass="resiToma" /></td>
			<td><html:text name="data" property="resiToma13" styleClass="resiToma" /></td>
			<td><html:text name="data" property="resiToma14" styleClass="resiToma" /></td>
			<td><html:text name="data" property="resiToma15" styleClass="resiToma" /></td>
			<td><html:text name="data" property="resiToma16" styleClass="resiToma" /></td>
			<td><html:text name="data" property="resiToma17" styleClass="resiToma" /></td>
			<td><html:text name="data" property="resiToma18" styleClass="resiToma" /></td>
			<td><html:text name="data" property="resiToma19" styleClass="resiToma" /></td>
			<td><html:text name="data" property="resiToma20" styleClass="resiToma" /></td>
			<td><html:text name="data" property="resiToma21" styleClass="resiToma" /></td>
			<td><html:text name="data" property="resiToma22" styleClass="resiToma" /></td>
			<td><html:text name="data" property="resiToma23" styleClass="resiToma" /></td>
			<td><html:text name="data" property="resiToma24" styleClass="resiToma" /></td>

	<td><bean:write name="data" property="incidencia" /></td>
	<td><bean:write name="data" property="resultLog" /></td>
	
 </tr>

		<tr>
			<td>	
				<p class="botons">
					<input type="button" onclick="javascript:volver()" value="Volver"/>
					<input type="button" onclick="javascript:borrar('<bean:write name="data" property="oidFicheroResiDetalle"/>')" value="Confirmar"/>
				</p>	
			</td>	
		</tr>
	 </logic:iterate>
		 
		</table>




	</html:form>

</body>
</html>