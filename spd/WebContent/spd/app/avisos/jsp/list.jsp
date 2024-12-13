<%@ page language="java" %>
<%@ page import="java.util.*" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	


<%@ page session="true" %>
<%@page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html:html>
<HEAD>

 
<title>




</title>
<jsp:include page="/spd/jsp/global/head.jsp"/>



</HEAD>
<bean:define id="formulari" name="AvisosForm" type="lopicost.spd.struts.form.AvisosForm" />

<script type="text/javascript">	



function borrar(oidAviso)
{
	var f = document.AvisosForm;
	f.parameter.value='borrar';
	f.ACTIONTODO.value='CONFIRMAR';
	f.oidAviso.value= oidAviso;
	f.submit();
}


function editar(oidAviso)
{
	
	var f = document.AvisosForm;
	f.parameter.value='editar';
	f.ACTIONTODO.value='EDITA';
	f.oidAviso.value= oidAviso;

	f.submit();
}

function buscar()	
{
	var f = document.AvisosForm;
	f.parameter.value='admin';
	f.submit();	
}

function nuevo()
{
	var f = document.AvisosForm;
	f.parameter.value='nuevo';
	f.ACTIONTODO.value='NUEVO';
	f.submit();
}	


function goAdminMenu()
{
	
	var f = document.AvisosForm;
	document.location.href='http://'+window.location.host+'<%=request.getContextPath()%>/Avisos.do?parameter=list';
	return true;
}



function getContextPath() {
	return "<c:out value="${pageContext.request.contextPath}" />";
	}
	


</script>
<body>
<html:form action="/Avisos.do" method="post">
 <html:hidden property="idUsuario" />
 <html:hidden property="parameter" value="admin"/>
 <html:hidden property="ACTIONTODO"/>
 <html:hidden property="oidAviso" /> 
 
    	<!-- mostramos mensajes y errores, si existen -->
	<logic:notEmpty name="formulari" property="errors">
		<ul>
		<font color="red">
			<u>Mensaje:</u>
				<logic:iterate id="error" name="formulari" property="errors" type="java.lang.String">
						<li><bean:write name="error"/></li>
				</logic:iterate>
		</font>
		</ul>
	</logic:notEmpty>


    <p class="botonBuscar">
	
		<input type="button" onclick="javascript:nuevo();"  value="Nuevo"  />  
		<input type="button" class="azulCielo" value="Salir" onclick="javascript:salir();" />
	</p>
	
	
<table >
	<tr>
		<th class="segunda">Fecha creación</th>
		<th class="segunda">Texto</th>
		<th class="segunda">Fecha inicio</th>
		<th class="segunda">Fecha fin</th>
		<th class="segunda">Activo</th>
		<th class="segunda">Farmacia</th>
		<th class="segunda">Creador</th>
		<th class="segunda">Orden</th>
		<th class="segunda">Tipo</th>
	</tr>
	<logic:iterate id="data" name="formulari" property="avisos" type="lopicost.spd.model.Aviso" indexId="position">
	<tr>
		<td><bean:write name="data" property="fechaInsert" /></td>
		<td><bean:write name="data" property="aviso" /></td>
		<td><bean:write name="data" property="fechaInicio" /></td>
		<td><bean:write name="data" property="fechaFin" /></td>
		<td><bean:write name="data" property="activo" /></td>
		<td><bean:write name="data" property="idFarmacia" /></td>
		<td><bean:write name="data" property="usuarioCreador" /></td>
		<td><bean:write name="data" property="orden" /></td>
		<td><bean:write name="data" property="tipo" /></td>
		
		
			<td>
				<p class="botons">
						<input type="button" onclick="javascript:editar('<bean:write name="data" property="oidAviso" />');"  value="Editar"  />
				</p>
				<p class="botons">
				<input type="button" onclick="javascript:borrar('<bean:write name="data" property="oidAviso" />');"  value="Borrar"  />
				</p>				
			</td>
        </tr>
	</tr>	
	</logic:iterate>


 </table>
 
</html:form>
</html:html>
