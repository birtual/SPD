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
<bean:define id="formulari" name="EnlacesForm" type="lopicost.spd.struts.form.EnlacesForm" />

<script type="text/javascript">	



function borrar(idEnlace)
{
	var f = document.EnlacesForm;
	f.parameter.value='borrar';
	f.ACTIONTODO.value='CONFIRMAR';
	f.idEnlace.value= idEnlace;
	f.submit();
}


function editar(idEnlace)
{
	
	var f = document.EnlacesForm;
	f.parameter.value='editar';
	f.ACTIONTODO.value='EDITA';
	f.idEnlace.value= idEnlace;

	f.submit();
}

function buscar()	
{
	var f = document.EnlacesForm;
	f.parameter.value='admin';
	f.submit();	
}

function nuevo()
{
	var f = document.EnlacesForm;
	f.parameter.value='nuevo';
	f.ACTIONTODO.value='NUEVO';
	f.submit();
}	

function duplicar(idEnlace)
{
	
	var f = document.EnlacesForm;
	f.parameter.value='nuevo';
	f.ACTIONTODO.value='COPIA';
	f.idEnlace.value= idEnlace;

	f.submit();
}

function goAdminMenu()
{
	
	var f = document.EnlacesForm;
	document.location.href='http://'+window.location.host+'<%=request.getContextPath()%>/Menu.do?parameter=list';
	return true;
}



function getContextPath() {
	return "<c:out value="${pageContext.request.contextPath}" />";
	}
	


</script>
<body>
<html:form action="/Enlaces.do" method="post">
 <html:hidden property="idUsuario" />
 <html:hidden property="parameter" value="admin"/>
 <html:hidden property="ACTIONTODO"/>
 <html:hidden property="idEnlace" /> 
 
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
 	<fieldset>
			<div>Texto a buscar 
			<% String campoGoogle = formulari.getCampoGoogle(); if(campoGoogle==null || campoGoogle.equals("")) campoGoogle= formulari.getCampoGoogle();%>

		   	<input type="text" name="campoGoogle" value="<%=campoGoogle %>" title="Texto a buscar" alt="Texto a buscar">
		    <html:hidden property="campoGoogle" />
		   	
		</div>
</fieldset>
 


    <p class="botonBuscar">
	
		<!-- input type="button" class="btn primary" value="Inicio" onclick="javascript:goInicio();" /> -->
		<input type="button" class="azulCielo" value="Salir" onclick="javascript:salir();" />
		<input type="button" onclick="javascript:buscar();"  value="Buscar"  />  
		<input type="button" onclick="javascript:nuevo();"  value="Nuevo"  />  
		<input type="button" onclick="javascript:goAdminMenu();" value="Ir a gestión de  menú"/>
	</p>
	
	
<table >
	<tr>
		<th class="segunda">idapartado</th>
		<th class="segunda">idEnlace</th>
		<th class="segunda">Enlace</th>
		<th class="segunda">Nombre enlace</th>
		<th class="segunda">Descripción</th>
		<!-- th class="segunda">preEnlace</th> -->
		<th class="segunda">link</th>
		<!-- th class="segunda">Parámetros</th> -->
		<th class="segunda">activo</th>
		<th class="segunda">nueva ventana</th>
	</tr>
	<logic:iterate id="data" name="formulari" property="listaEnlaces" type="lopicost.spd.model.Enlace" indexId="position">
	<tr>
		<td><bean:write name="data" property="idApartado" /></td>
		<td><bean:write name="data" property="idEnlace" /></td>
		<td><bean:write name="data" property="aliasEnlace" /></td>
		<td><bean:write name="data" property="nombreEnlace" /></td>
		<td><bean:write name="data" property="descripcion" /></td>
		<!-- th><bean:write name="data" property="preEnlace" /></th> -->
		<td><bean:write name="data" property="linkEnlace" /></td>
		<!-- th><bean:write name="data" property="paramsEnlace" /></th> -->
		<td><bean:write name="data" property="activo" /></td>
		<td><bean:write name="data" property="nuevaVentana" /></td>
			<td>
				<p class="botons">
						<input type="button" onclick="javascript:editar('<bean:write name="data" property="idEnlace" />');"  value="Editar"  />
				</p>
				<p class="botons">
				<input type="button" onclick="javascript:borrar('<bean:write name="data" property="idEnlace" />');"  value="Borrar"  />
				</p>				
				<p class="botons">
				<input type="button" onclick="javascript:duplicar('<bean:write name="data" property="idEnlace" />');"  value="Duplicar"  />
				</p>
				
			</td>
        </tr>
	</tr>	
	</logic:iterate>


 </table>
 
</html:form>
</html:html>
