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
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script language="javaScript" src="/spd/spd/app/sustXGt/js/sustXGt.js"></script>
 	<jsp:include page="/spd/jsp/global/head.jsp"/>
	<title>Base para realizar sustituciones - Listado</title>
</head>

<bean:define id="formulari" name="SustXGtForm" type="lopicost.spd.struts.form.SustXGtForm" />
	<style>
       .fila-padre {
    	cursor: pointer;
    	font-weight: bold;
		}
	.hijo {
    	background-color: #f9f9f9;
	}
	</style>

<body id="general">

<html:form action="/SustXGt.do" method="post"  >	
<html:errors/>

<div id="contingut">
     <html:hidden property="parameter" value="list"/>
     <html:hidden property="ACTIONTODO" value="list"/>
     <html:hidden property="oidSustXComposicion" />
	 <html:hidden property="verSoloGestionados" />
	
     	<% String numPages = formulari.getNumpages()+""; %>
     	<% String currpage = formulari.getCurrpage()+""; %>
     <html:hidden property="numpages" value="<%= numPages %>"/>
	 <html:hidden property="currpage" value="<%= currpage %>"/>
	
   	<!-- mostramos mensajes y errores, si existen -->
	<logic:notEmpty name="SustXGtForm" property="errors">
		<font color="red"><ul>
			<u>Mensaje:</u>
				<logic:iterate id="error" name="formulari" property="errors" type="java.lang.String">
						<li><bean:write name="error"/></li>
				</logic:iterate>
			</ul></font>
	</logic:notEmpty>

	<fieldset>
	<table>
		<tr>
		<th>
		<div>
			Robot
			<logic:notEmpty name="formulari" property="listaRobots">	
		   	 <html:select property="filtroRobot"  value="<%= formulari.getFiltroRobot() %>" onchange="submit()"> 
		   	 	<html:option value="">Todos</html:option>
   					<html:optionsCollection name="formulari" property="listaRobots" label="nombreRobot" value="idRobot" />
				</html:select>
		     </logic:notEmpty>	
		</div>
		<div>Texto a buscar 
			<html:text property="campoGoogle" alt="Texto a buscar" title="Texto a buscar"  onkeypress="handleEnter(event)" />
		</div>

<div>Grupo VM (Principio activo)
    <input 
        type="text" 
        id="filtroNomGtVm" 
        name="filtroNomGtVm" 
        onkeypress="handleEnter(event)" 
        value="<%= request.getParameter("filtroNomGtVm") != null ? request.getParameter("filtroNomGtVm") : "" %>" 
        placeholder="Escribe 3 caracteres para buscar" 
        class="select_corto"
    />
</div>
<input 
    type="hidden" 
    id="filtroCodGtVm" 
    name="filtroCodGtVm" 
    value="<%= request.getParameter("filtroCodGtVm") != null ? request.getParameter("filtroCodGtVm") : "" %>"
/>




		</th>
	   	</tr>
	</table>

	<div>
	    <input 
	        type="checkbox" 
	        id="toggleAll" 
	        name="toggleAll" 
	        onchange="toggleAllChildren(this)" 
	        <%= request.getParameter("toggleAll") != null ? "checked" : "" %> 
	    >
	    <label for="toggleAll">Mostrar/Ocultar todos</label>
	</div>
	<div>
	
		<logic:equal name="formulari" property="verSoloGestionados" value="false">
			<input type="checkbox" name="filtroCheckedVerGestionados"   onclick="checkValue()" />
		</logic:equal>
		<logic:equal name="formulari" property="verSoloGestionados" value="true">
			<input type="checkbox" name="filtroCheckedVerGestionados" checked onclick="checkValue()" />
		</logic:equal>
		<label for="toggleAll">Mostrar los gestionados</label>
	</div>

	</fieldset>
	<table border="1">
 		<thead id="cabecera" style="display: none;">
    	<tr>
 			<th>GTVMPP</th>
        	<th>Lab</th>
        	<th>Ponderacion</th>
            <th>Farmacia</th>
            <th>dto farmacia</th>
			<th>cn6</th>
			<th>cn7</th>
			<th>sustituible</th>        
			<th>tolva</th>        
		</tr>
	    	</thead>
 
	 	<logic:notEmpty name="SustXGtForm" property="listaBeans">	
		<logic:iterate id="padre" name="SustXGtForm" property="listaBeans" type="lopicost.spd.model.SustXGtvmp" indexId="position">
			<tr id="padre-${position}" class="fila-padre" onclick="javascript:toggleChildren(${position})" >
	    		<td colspan="9" style="background-color: #f0f0f0;"><bean:write name="padre" property="nomGtvmp" /><html:hidden name="padre" property="codGtvmp"/></td>
	    		<!--
	    		<td><bean:write name="padre" property="nomGtvmp" /><html:hidden name="padre" property="codGtvmp"/></td>
	    		<td><bean:write name="padre" property="nomLaboratorio" /></td>
	    		<td><bean:write name="padre" property="ponderacion" /></td>
	    		<td>Farmacia</td>
			    <td>dto farmacia</td>
			    <td>cn6</td>
			    <td>cn7</td>
			    <td>sustituible</td>        
			    <td>tolva</td>        
			     -->
			    <td>
			        <p class="botons">
			        <logic:notEqual name="padre" property="oidSustXComposicion" value="0">    
			            <input type="button" onclick="javascript:nuevo('<bean:write name="padre" property="oidSustXComposicion" />');" value="añadir" />
			        </logic:notEqual>
			        </p>
			    </td>
			</tr>
	
		<logic:notEmpty name="padre" property="hijos">    
			<logic:iterate id="hijo" name="padre" property="hijos" type="lopicost.spd.model.SustXGtvmpp" indexId="position2">
			<tr id="hijo-${position}-${position2}" class="hijo hijo-${position}" style="display: none;">
				<td style="text-align: right; background-color: orange;">
					<bean:write name="hijo" property="nomGtvmpp" />
				</td>
				<td><bean:write name="hijo" property="nomLaboratorio" /></td>
				<td><bean:write name="hijo" property="ponderacion" /></td>
				<td><bean:write name="hijo" property="nombreRobot" /></td>
				<td>dto farmacia</td>
				<td><bean:write name="hijo" property="cn6" /></td>
				<td><bean:write name="hijo" property="cn7" /></td>
				<td><bean:write name="hijo" property="sustituible" /></td>        
				<td><bean:write name="hijo" property="tolva" /></td>        
				<td></td>
			</tr>
		</logic:iterate>
		</logic:notEmpty>
	</logic:iterate>
	</logic:notEmpty>
	</table>


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
		
		
		
	</fieldset>
	</html:form>
</div>	
</body>
</html>