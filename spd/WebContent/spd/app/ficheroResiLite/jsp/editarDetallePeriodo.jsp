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
<title>Edición Fichero Resi Carga Robot</title>
</head>
<bean:define id="formulari" name="FicheroResiForm" type="lopicost.spd.struts.form.FicheroResiForm" />



<script type="text/javascript">	
	
		function volver()
		{
			var f = document.FicheroResiForm;
			f.parameter.value='list';
			f.ACTIONTODO.value='LIST';

			f.submit();
		}	
		
		function grabar()
		{
			var f = document.FicheroResiForm;
			/*
			if(f.idTipoAccion.value=='')
				alert('Falta indicar la acción');
			else if(f.formaFarmaceutica.value=='')
				alert('Falta indicar la forma');
			else
				*/{		
				f.parameter.value='editar';
				f.ACTIONTODO.value='EDITA_OK';
				//f.oidFicheroResiCabecera.value=oidFicheroResiCabecera;
				
				f.submit();
			}
			
		}	




		//función de carga del lookUp
		function doLookUpBdConsejo(){				
			var loc = '/spd/LookUpBdConsejo.do?parameter=init&'+ 						//url de llamanda				
				'CallBackID=cnOk&'+			  			//Nombre del campo para el valor Id
				'CallBackNAME=nombreConsejo';			   		//Nombre del campo para el valor descriptivo
		
			//Importante que se realice en ventana nueva!!!!
			window.open(loc, 'LookUpBdConsejo', 'dependent=yes,height=500,width=800,top=50,left=50,resizable=yes,scrollbars=yes' );
		}		
		
				
</script>




<body id="general">
	<!-- mostramos mensajes y errores, si existen -->

	<center>
		<h2>Edición Fichero Resi Carga Robot</h2>
		<html:form action="/FicheroResiDetalleLite.do" method="post">	


<div id="contingut">
     <html:hidden property="parameter" value="editarFrecuencia"/>
     <html:hidden property="ACTIONTODO" value="EDITA"/>
     <html:hidden property="fieldName1" /> 
     <html:hidden property="oidFicheroResiDetalle" />
 

<bean:define id="data" name="formulari" property="ficheroResiDetalleBean" type="lopicost.spd.struts.bean.FicheroResiBean" />
	 <!--logic:iterate id="data" name="FicheroResiForm" property="listaFicheroResiDetalleBean" type="lopicost.spd.struts.bean.FicheroResiBean" indexId="position"> -->

<table>
	   	<tr>
	   		<th>idProceso</th>				
	   			<td><bean:write name="data" property="idProceso" /></td>
	   		<th>Residencia</th>				
	   			<td><bean:write name="data" property="idDivisionResidencia" /></td>
	  		<th>fechaHoraProceso</th>		
	  			<td><bean:write name="data" property="fechaHoraProceso" /></td>
	  	</tr>
	  	<tr>
	  	 	<th>CIP</th>					
	  	 		<td><html:text name="data" property="resiCIP" /></td>				
	  	 	<th>nombre paciente</th><td><html:text name="data" property="resiNombrePaciente" /></td>
	  	 </tr>
	  	<tr>	<td></td></br>
	  	<tr>	<th>Cn resi</th>				<td><html:text name="data" property="resiCn" /></td>
	  			<th>medicamento resi</th>		<td><bean:write name="data" property="resiMedicamento" /></td>
	 			<th>inicio-fin tratamiento</th>		<td><html:text name="data" property="resiInicioTratamiento" />-<html:text name="data" property="resiFinTratamiento" /></td>
				<th>inicio-fin producción</th>		<td><html:text name="data" property="resiInicioTratamientoParaSPD" />-<html:text name="data" property="resiFinTratamientoParaSPD" /></td>
				<th>forma medicacion</th><td><bean:write name="data" property="resiFormaMedicacion" /></td></tr>
	  	<tr> 	<th>observaciones</th>			<td><bean:write name="data" property="resiObservaciones" /></td>
	 	 		<th>variante</th>				<td><bean:write name="data" property="resiVariante" /></td>
	 		 	<th>comentarios</th>			<td><bean:write name="data" property="resiComentarios" /></td>
	  		 	<th>SiPrecisa</th>				<td><html:checkbox name="data" property="resiSiPrecisa" value="X"/></td></tr>

	 
	  
	  <th>via administración</th><td><html:text name="data" property="resiViaAdministracion" /></td>
	
	   <table>
		<tr>
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
	<tr>
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
</table>
	<table>
	  <tr>
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
	  </tr>
	<tr>
	<tr>
			<td><html:text name="data" property="spdCnFinal" /></td>
			<td><html:text name="data" property="spdNombreBolsa" /></td>
			<td><html:text name="data" property="spdFormaMedicacion" /></td>
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
		
		<td><html:checkbox name="data" property="resiD1" value="X"/></td>
		<td><html:checkbox name="data" property="resiD2" value="X"/></td>
		<td><html:checkbox name="data" property="resiD3" value="X"/></td>
		<td><html:checkbox name="data" property="resiD4" value="X"/></td>
		<td><html:checkbox name="data" property="resiD5" value="X"/></td>
		<td><html:checkbox name="data" property="resiD6" value="X"/></td>
		<td><html:checkbox name="data" property="resiD7" value="X"/></td>
		<td><html:text name="data" property="resiDiasAutomaticos" styleClass="resiDiasAutomaticos" /></td>
	  </tr>	
	  </table>
			
	

 	<tr>
		<td>	
			<p class="botons">
			<input type="button" onclick="javascript:volver()" value="Volver"/>
			<!--<input type="button" onclick="javascript:grabar('<bean:write name="data" property="oidFicheroResiCabecera"/>')" value="Confirmar"/> -->
			<input type="button" onclick="javascript:grabar()" value="Confirmar"/>
		</p>	
		</td>	
	</tr>
	

</table>


	</center>

</div>	

	</html:form>

</body>
</html>