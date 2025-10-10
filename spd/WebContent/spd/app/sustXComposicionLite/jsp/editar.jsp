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

<bean:define id="formulari" name="SustXComposicionForm" type="lopicost.spd.struts.form.SustXComposicionForm" />

<script type="text/javascript">	
	
		function volver(oidSustXComposicion)
		{
			var f = document.SustXComposicionForm;
			f.parameter.value='list';
			f.ACTIONTODO.value='LIST';
			//f.oidSustXComposicion.value=oidSustXComposicion;
			f.codiLab='';
			//alert(oidDivisionResidenciaFiltro);
			f.submit();
		}	
		
		function grabar(oidSustXComposicion)
		{
			var f = document.SustXComposicionForm;
			f.parameter.value='editar';
			f.ACTIONTODO.value='EDITA_OK';
			f.oidSustXComposicion.value=oidSustXComposicion;
			f.submit();
		}	

		//función de carga del lookUp
		function doLookUpBdConsejo(filtroGtVmpp){				
			var loc = '/spd/LookUpBdConsejo.do?parameter=init'+ 						//url de llamanda				
				'&CallBackID=cn6'+			  			//Nombre del campo para el valor Id
				'&CallBackNAME=nombreMedicamento'+		   		//Nombre del campo para el valor descriptivo
				'&filtroGtVmpp='+filtroGtVmpp;	
				
			//Importante que se realice en ventana nueva!!!!
			window.open(loc, 'LookUpBdConsejo', 'dependent=yes,height=500,width=800,top=50,left=50,resizable=yes,scrollbars=yes' );
		}	
		
				
</script>




<body id="general">

	<center>
		<h2>Edición sustitución</h2>
		<html:form action="/SustXComposicionLite.do" method="post">	


<div id="contingut">
<bean:define id="data" name="SustXComposicionForm" property="sustXComposicion"/>

     <html:hidden property="parameter" value="editar"/>
     <html:hidden property="ACTIONTODO" value="EDITA"/>
     <html:hidden property="oidSustXComposicion" />
     <html:hidden property="fieldName1" /> 
     <html:hidden property="codGtVmpp" /> 
 
<table>


	<tr>
		<td>Robot</td><td><bean:write name="data" property="idRobot" /></td>
	</tr>
	<tr>
		<td>Nombre Conj Homog</td><td><bean:write name="data" property="nomGtVmpp" /></td>
	</tr>	
	<!--  tr>
		<td>Código Lab</td><td><bean:write name="data" property="codiLab"  /></td>
	</tr -->	
	<tr>
		<td>Nombre Lab Prioritario</td><td><bean:write name="data" property="nombreLab"  /></td>
	</tr>	
	<tr>
		<td>Rentabilidad</td><td><html:text name="data" property="rentabilidad" styleClass="rentabilidad" /></td>
	</tr>	
	<tr>
		<td>Ponderacion</td><td><html:text name="data" property="ponderacion" styleClass="ponderacion" /></td>
	</tr>	
	<tr>
		<td>Comentario</td><td><html:text name="data" property="comentarios" styleClass="comentarios" /></td>
	</tr>	
	<tr>
		<td>Fecha creación</td><td><bean:write name="data" property="fechaCreacion"/></td>
	</tr>	
	<tr>
		<td>última modificacion</td><td><bean:write name="data" property="ultimaModificacion"/></td>
	</tr>	
	<tr>
		<td>CN a pedir</td><td><html:text name="data" property="cn6"/><a href="#" onclick="javascript:doLookUpBdConsejo('<bean:write name="data" property="codGtVmpp" />');">Buscar CN</a></td>
	</tr>	
	<tr>
		<td>CN7 a pedir</td><td><bean:write name="data" property="cn7"/></a></td>
	</tr>	
	<tr>
		<td>Sustituible</td><td><html:text name="data" property="sustituible"/></td>
	</tr>	
		<tr>
		<td>última modificacion</td><td><html:text name="data" property="nombreMedicamento"/></td>
	</tr>	

		
		
</table>

	<tr>
		<td>	
		<p class="botons">
			<input type="button" onclick="javascript:volver('<bean:write name="formulari" property="oidSustXComposicion"/>')" value="Volver"/>
			<input type="button" onclick="javascript:grabar('<bean:write name="formulari" property="oidSustXComposicion"/>')" value="Confirmar"/>
		</p>	
		</td>	
	</tr>
		
	
	</center>

</div>	

	</html:form>

</body>
</html>