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
	<title>Mantenimiento de residencias</title>
</head>

<bean:define id="formulari" name="DivResidenciasForm" type="lopicost.spd.struts.form.DivResidenciasForm" />
<script language="javaScript" src="/spd/spd/app/divResidencias/js/divResidencias.js"></script>

<body id="general">

	<html:form action="/DivResidencias.do" method="post">	
	<html:errors/>

	<div>
	    <html:hidden property="parameter" value="list"/>
	    <html:hidden property="ACTIONTODO" value="list"/>
		<% String numPages = formulari.getNumpages()+""; %>
	    <% String currpage = formulari.getCurrpage()+""; %>
	    <html:hidden property="numpages" value="<%= numPages %>"/>
		<html:hidden property="currpage" value="<%= currpage %>"/>
		<html:hidden property="oidDivisionResidencia"/>
		
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
			<% String var = formulari.getOidDivisionResidencia()+""; %>

			<div>
				<label for="campoGoogle" accesskey="e">Texto a buscar </label>
			   	<html:text name="formulari" property="campoGoogle" />
			</div>

			<div> 
				<p class="botonBuscar">
					<input type="button" onclick="javascript:buscar()"  value="Buscar"  />  
					<input type="button" onclick="javascript:goNuevoCIP();"  value="Nuevo"  />
					<input type="button" class="azulCielo" value="Salir" onclick="javascript:salir();" />
					<!-- input type="button" onclick="javascript:nuevo()"  value="Nuevo"  /-->  
				</p> 
			</div> 
			
		<logic:notEmpty  name="formulari" property="listaDivisionResidencia">
			<table  id="listaDivisionResidencia" align="center" border="1">
				<tr>
					<th>idDivisionResidencia</th>
				 	<th>nombreDivisionResidencia</th>
					<th>Farmacia</th>
					<th>idLayout</th>
					<th>nombreBolsa</th>
					<th>aplicativo</th>
					<th>idProcessIospd</th> 		
					<th>tipoCLIfarmatic</th>
					<th>locationId</th>
					<th>Servir ""Si Precisa"</th>
					<th>Acciones</th>
			   </tr>
		
		
		 	<logic:iterate id="data" name="formulari" property="listaDivisionResidencia" type="lopicost.spd.model.DivisionResidencia" indexId="position">
				<tr>
					<td><bean:write name="data" property="idDivisionResidencia" /></td>
					<td><bean:write name="data" property="nombreDivisionResidencia" /></td>
					<td><bean:write name="data" property="idFarmacia" /></td>
					<td><bean:write name="data" property="idLayout" /></td>
					<td><bean:write name="data" property="nombreBolsa" /></td>
					<td>aplicativo</td>
					<td><bean:write name="data" property="idProcessIospd" /></td>
					<td><bean:write name="data" property="tipoCLIfarmatic" /></td>
					<td><bean:write name="data" property="locationId" /></td>
					<td>Servir ""Si Precisa"</td>
					<td>
						<p class="botons">
							<input type="button" onclick="javascript:goDetalle('<bean:write name="data" property="oidDivisionResidencia" />');"  value="Detalle"  />
							<input type="button" onclick="javascript:goEditar('<bean:write name="data" property="oidDivisionResidencia" />');"  value="Editar"  />
						</p>
					</td>
				</tr>
		    </logic:iterate>
		</table>

	</logic:notEmpty>

	<logic:empty  name="formulari" property="listaDivisionResidencia">
		<div>No existen registros a mostrar</div>
	</logic:empty>
	
		</fieldset>
			<!--  paginación  -->
	<div>
		<logic:greaterThan name="formulari" property="numpages" value="1">
			<p align="center">
				<logic:greaterThan name="formulari" property="currpage" value="0">
					<a href="javascript:pageDown();" ><<</a>
				</logic:greaterThan>
				&nbsp;<input type="text" name="newPage" value="<%= formulari.getCurrpage()+1 %>" size="1" maxlength="4" onkeypress="gotoPage(this.value,<%=formulari.getNumpages()%>);">/<%= formulari.getNumpages() %>&nbsp;
				<logic:lessThan name="formulari" property="currpage" value="<%= String.valueOf(formulari.getNumpages() -1)%>">
					<a href="javascript:pageUp();" >>></a>
				</logic:lessThan>
			</p>
		</logic:greaterThan>
	</div>
	<!--  paginación   -->
	</div>		
			

	
	</html:form>

</body>
</html>