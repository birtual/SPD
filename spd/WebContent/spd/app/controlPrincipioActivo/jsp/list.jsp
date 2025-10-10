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
<title>Gestión carga ficheros Robot</title>
</head>

<bean:define id="formulari" name="ControlPrincipioActivoForm" type="lopicost.spd.struts.form.ControlPrincipioActivoForm" />
<script type="text/javascript">	


function goInicio()
{
	document.location.href='/spd/Enlaces.do?parameter=list&ACTIONTODO=INICIO';
}
function goNuevo()
{
	var f = document.ControlPrincipioActivoForm;
	f.parameter.value='nuevo';
	f.ACTIONTODO.value='NUEVO';
	f.submit();
}	
function borrar(idFarmacia, oidDivisionResidencia, codGtVm)
{
	var f = document.ControlPrincipioActivoForm;
	f.idFarmacia.value=idFarmacia;
	f.oidDivisionResidencia.value=oidDivisionResidencia;
	f.codGtVm.value=codGtVm;

	f.parameter.value='borrar';
	f.submit();
}	
</script>

		

<body id="general">

<html:form action="/CtrlPrincActivos.do" method="post">	
<html:errors/>

<div id="contingut">

     <html:hidden property="oidDivisionResidencia"/>
	 <html:hidden property="idFarmacia"/>
	 <html:hidden property="codGtVm"/>
    <html:hidden property="parameter" value="list"/>
    <html:hidden property="ACTIONTODO" value="list"/>
 	<fieldset>
	   	<!-- mostramos mensajes y errores, si existen -->
		<logic:notEmpty name="formulari" property="errors">
			<font color="red"><ul>
				<u>Mensaje:</u>
					<logic:iterate id="error" name="formulari" property="errors" type="java.lang.String">
							<li><bean:write name="error"/></li>
					</logic:iterate>
				</ul></font>
		</logic:notEmpty>
		
		<div>
			   <label for="listaDivisionResidencia" accesskey="e">Residencia</label>
			<logic:notEmpty name="formulari" property="listaDivisionResidencia">	
			  
		   	 <html:select property="oidDivisionResidenciaFiltro"   onchange="submit()"> 
		   	 	<html:option value="">Todas</html:option>
   					<html:optionsCollection name="formulari" property="listaDivisionResidencia" label="nombreDivisionResidencia" value="oidDivisionResidencia" />
				</html:select>
		     </logic:notEmpty>	
		</div>	
	</fieldset>
    <p class="botonBuscar">
			<input type="button" class="azulCielo" value="Salir" onclick="javascript:salir();" />
			<input type="button" onclick="javascript:goNuevo()" value="Asignar nuevo"/>
	</p> 

	<table border="1">
		<tr>
		 	<!-- th>Residencia</th> -->
		 	<th>Farmacia</th>
		 	<th>Residencia</th>
		 	<th>Principio activo de control extra</th>
			<th></th>
	   </tr>


 	<logic:iterate id="data" name="formulari" property="listaControlPrincipioActivo" type="lopicost.spd.model.ControlPrincipioActivo" indexId="position">
		<tr>
			<!--<td><bean:write name="data" property="idDivisionResidencia" /></td> -->
			<td><bean:write name="data" property="farmacia.nombreFarmacia" /></td>
			<td><bean:write name="data" property="divisionResidencia.nombreDivisionResidencia" /></td>
			<td><bean:write name="data" property="bdConsejo.nomGtVm" /></td>
			<td>
				<p class="botons">
					<input type="button" onclick="javascript:borrar( '<bean:write name="data" property="farmacia.idFarmacia" />', '<bean:write name="data" property="divisionResidencia.oidDivisionResidencia" />', '<bean:write name="data" property="bdConsejo.codGtVm" />');"  value="Borrar"  />
				</p>
			</td> 
		</tr>
    </logic:iterate>


    
</table>

		

		
	</div>	
	</html:form>

</body>
</html>