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
<title>Edición sustitución</title>
</head>

<bean:define id="formulari" name="GestSustitucionesForm" type="lopicost.spd.struts.form.GestSustitucionesForm" />


<script type="text/javascript">	
	
		function volver()
		{
			var f = document.GestSustitucionesForm;
			f.parameter.value='list';
			f.ACTIONTODO.value='LIST';
		//	f.oidDivisionResidenciaFiltro.value=oidDivisionResidenciaFiltro;
			//alert(oidDivisionResidenciaFiltro);
			f.submit();
		}	
		
		function grabar(oidDivisionResidenciaFiltro)
		{
			var f = document.GestSustitucionesForm;
			//alert();
			if(f.oidDivisionResidencia.value=='0')
			{
				f.parameter.value='editar';			//en caso contrario se edita el GestSust
				f.ACTIONTODO.value='EDITA_OK';
			}
			else
			{
				f.parameter.value='nuevoSustXResi';	//si se añade una resi se crea un GestSustXResi
				f.ACTIONTODO.value='NUEVO_OK';
			}
				
			if(f.idTipoAccion.value=='')
				alert('Falta indicar la acción');
			else
			{		
				f.oidDivisionResidenciaFiltro.value=oidDivisionResidenciaFiltro;
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
		<h2>Edición sustitución</h2>
		<html:form action="/GestSustituciones.do" method="post">	


<div id="contingut">

<bean:define id="data" name="formulari" property="sustitucion" />

<bean:define id="bdConsejo" name="data" property="bdConsejo"  />

<bean:define id="bdConsejoBiblia" name="data" property="bdConsejoBiblia"/>


	

     <html:hidden property="oidDivisionResidenciaFiltro" />
     <html:hidden property="parameter" value="editar"/>
     <html:hidden property="ACTIONTODO" value="EDITA"/>
     <html:hidden property="filtroNombreCorto" />
     <html:hidden property="oidGestSustituciones" />
     <html:hidden property="fieldName1" /> 

	<!-- se pasan parámetros de los filtros del listado para la vuelta -->
     <html:hidden property="filtroNombreCorto" />
     <html:hidden property="filtroMedicamentoResi" />
     <html:hidden property="filtroNombreCortoOK" />
     <html:hidden property="filtroFormaFarmaceutica" />
     <html:hidden property="filtroAccion" />
     <html:hidden property="filtroExisteBdConsejo" />

<table>
	<tr>
		<td>Residencia</td>
		<td>
		    <bean:define id="listaDivisionResidencia" name="GestSustitucionesForm" property="listaDivisionResidencia" />
			   	<% String div2 = formulari.getSustitucion().getOidDivisionResidencia()+""; %>
			<html:select name="data"  property="oidDivisionResidencia" value="<%= div2 %>"  styleClass="nombreDivisionResidencia">
			<html:options collection="listaDivisionResidencia" property="oidDivisionResidencia" labelProperty="nombreDivisionResidencia" />
			<html:option value="0">Todas</html:option>
			</html:select>
			</td>
	</tr>
	<tr>
		<td>Código CN residencia</td>
		<td><html:text name="data" property="cnResi" styleClass="cnResi" />
		<html:text name="data"  property="medicamentoResi" styleClass="medicamentoResi" /></td>
	</tr>
	<tr>
		<td>Código CN OK</td>
		<td><html:text name="data" property="cnOk" styleClass="cnOk"/>
<logic:notEmpty name="data" property="bdConsejo">
			<html:text name="bdConsejo" property="nombreConsejo" readonly="true" styleClass="nombreConsejo"/>
</logic:notEmpty>
			<a href="#" onclick="javascript:doLookUpBdConsejo();">Buscar</a>
		</td>
	</tr>	
	<tr>
		<td>Nombre corto</td>
		<td><html:text name="data" property="nombreCorto" styleClass="nombreCortoOK" /></td>
	</tr>


		<tr>
		<td>Forma farmacéutica</td>
		<td>			
		<logic:notEmpty name="data" property="bdConsejo">
					<html:text name="bdConsejo" property="nombreFormaFarmaceutica" readonly="true"  styleClass="nombreFormaFarmaceutica"/>
		</logic:notEmpty>
		<logic:empty name="data" property="bdConsejo">
					No asignada en el Consejo
		</logic:empty>
		</td>
	</tr>
	

	<tr>
		<td>Accion</td>
		<td>		
			<bean:define id="tiposAccion" name="formulari" property="listaTiposAccion" />
		<html:select property="idTipoAccion" value="<%= formulari.getSustitucion().getAccion() %>">
			<html:options collection="tiposAccion" property="idTipoAccion" labelProperty="idTipoAccion" />
			<html:option value="">-------------</html:option>
		</html:select>
		</td>
	</tr>
	<tr>
		<td>Comentario</td>
		<td><html:text name="data" property="comentario"  styleClass="comentario"/></td>
	</tr>
	<tr>
		<td>CN (Vademecum)</td>         	
		<td><bean:write name="bdConsejoBiblia" property="cnConsejo" /></td>
	</tr>
	<tr>
		<td>Medicamento (Vademecum)</td>         	
        <td><bean:write name="bdConsejoBiblia" property="nombreMedicamento" /></td>
	</tr>
	<tr>
		<td> Conj Homog Vademecum (GtVmpp)</td>         	
        <td><bean:write name="bdConsejoBiblia" property="nomGtVmpp" /></td>
	</tr>
	<tr>
		<td>Laboratorio (Vademecum)</td>         	
            <td><bean:write name="bdConsejoBiblia" property="nombreLaboratorio" /></td>
	</tr>
	<tr>
		<td>Ponderación total (Vademecum)</td>         	
        <td><bean:write name="bdConsejoBiblia" property="nota" /></td>
	</tr>
	<tr>
		<td>Sustituible</td>
		<td>
			<select name="sustituible" >
		   	 	<option value="1"  <%if (formulari.getSustitucion()!=null&&formulari.getSustitucion().getSustituible()!=null&&formulari.getSustitucion().getSustituible().equals("1")){%>selected<%}%>	>SI</option>
		   	 	<option value="0"  <%if (formulari.getSustitucion()!=null&&formulari.getSustitucion().getSustituible()!=null&&formulari.getSustitucion().getSustituible().equals("0")){%>selected<%}%>	>NO</option>
			</select>
		</td>
	</tr>


<%	
	String mensajesInfo =(formulari.getSustitucionXResi()!=null&&!formulari.getSustitucionXResi().equals("")?formulari.getSustitucion().getMensajesInfo():"");
	String mensajesAlertaResi =(formulari.getSustitucionXResi()!=null&&!formulari.getSustitucionXResi().equals("")?formulari.getSustitucion().getMensajesAlerta():"");
%>
	<tr>
 		<td>MensajesInfo</td>
		<td><%= mensajesInfo %></td>
	</tr>


	<tr>
		<td>MensajesAlerta</td>         	
 		<td><%= mensajesAlertaResi %></td>
    </tr>   		
			
		<tr>
		<td>	
		<p class="botons">
			<input type="button" onclick="javascript:volver('<bean:write name="formulari" property="oidDivisionResidenciaFiltro"/>')" value="Volver"/>
			<input type="button" onclick="javascript:grabar('<bean:write name="formulari" property="oidDivisionResidenciaFiltro"/>')" value="Confirmar"/>
		</p>	
		</td>	
	</tr>	
		
</table>


		
	
	</center>

</div>	

	</html:form>

</body>
</html>