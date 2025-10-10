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
<title>Borrado de control especial en las producciones para un principio activo</title>
</head>

	<script>
		
		function borrarOk(idFarmacia, oidDivisionResidencia, codGtVm)
		{
			var f = document.ControlPrincipioActivoForm;
			f.idFarmacia.value = idFarmacia ;
			f.oidDivisionResidencia.value=oidDivisionResidencia;
			f.codGtVm.value=codGtVm;
		
			f.parameter.value='borrar';
			f.ACTIONTODO.value='CONFIRMADO_OK';
			f.submit();
		}	
		
		function volver()
		{
			var f = document.ControlPrincipioActivoForm;
			f.parameter.value='list';
			f.submit();
		}	

	</script>


<bean:define id="formulari" name="ControlPrincipioActivoForm" type="lopicost.spd.struts.form.ControlPrincipioActivoForm" />

<body id="general">
	<center>
		<h2>Borrado de control especial en las producciones para un principio activo</h2>
		<html:form action="/CtrlPrincActivos.do" >	
<div id="contingut">

     <html:hidden property="idFarmacia"/>
     <html:hidden property="oidDivisionResidencia"/>
     <html:hidden property="codGtVm"/>

     <html:hidden property="parameter" value="borrar"/>
     <html:hidden property="ACTIONTODO" value="CONFIRMAR"/>

 
	<h3>Confirmar borrado</h3>
	 	<logic:iterate id="data" name="formulari" property="listaControlPrincipioActivo" type="lopicost.spd.model.ControlPrincipioActivo" indexId="position">
			<table>
			<tr>
				<td>Farmacia</td>
				<td><bean:write name="data" property="farmacia.nombreFarmacia"/></td>
			</tr>
			<tr>
				<td>Residencia</td>
				<td><bean:write name="data" property="divisionResidencia.nombreDivisionResidencia"/></td>
			</tr>
			<tr>
				<td>Principio activo de control</td>
				<td><bean:write name="data" property="bdConsejo.nomGtVm"/></td>
			</tr>	
		</table>
	</logic:iterate>
	<tr>
		<td>	
			<p class="botons">
			<input type="button" onclick="javascript:volver()" value="Volver"/>
			<input type="button" onclick="javascript:borrarOk( '<bean:write name="data" property="farmacia.idFarmacia" />', '<bean:write name="data" property="divisionResidencia.oidDivisionResidencia" />', '<bean:write name="data" property="bdConsejo.codGtVm" />');"  value="Borrar"  />
			</p>	
		</td>	
	</tr>
		
	
	</center>

</div>	

	</html:form>

</body>
</html>