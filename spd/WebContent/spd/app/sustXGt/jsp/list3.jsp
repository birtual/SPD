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
	<style>

.fila-padre  td {
    	cursor: pointer;
    	font-weight: bold !important;
		}
.hijo  td {
    background-color: #94cce6; !important;/* Azul claro */
}
.hijo-laboratorio  td {
    background-color: #84ec84; !important;/* Verde claro */
}
.hijo-laboratorio-farmacia  td {
    background-color: #3cc63c; !important;/* Verde más intenso */
}
 table .gtvmp-col {
    width: 25%; /* Ajusta al tamaño deseado */
}  
    </style>
    
    <script>

    
 // Ejecutar al cargar la página
    window.onload = function() {
        const toggleAll = document.getElementById("toggleAll");
        // Llamar a la función toggleAllChildren con el estado del checkbox
        toggleAllChildren(toggleAll.checked);
    };

	
	</script>
</head>

<bean:define id="formulari" name="SustXGtForm" type="lopicost.spd.struts.form.SustXGtForm" />


<body id="general" >

<html:form action="/SustXGt.do" method="post"  >	
<html:errors/>

<div id="contingut">
     <html:hidden property="parameter" value="list"/>
     <html:hidden property="ACTIONTODO" value="list"/>
     <html:hidden property="oidSustXComposicion" />
	 <html:hidden property="verSoloGestionados" />
	 <html:hidden property="filtroCodGtVmp"/>

	
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
		lógica de filtrado:
    <html:select property="filtroLogico" value="<%= formulari.getFiltroLogico() %>" onchange="submit()"> 
        <html:option value="AND">Y</html:option> <!-- AND para todas las condiciones -->
        <html:option value="OR">O</html:option> <!-- OR para cualquiera -->
    </html:select></div>
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
</div><div>
  
</div>
<div>
<input 
    type="hidden" 
    id="filtroCodGtVm" 
    name="filtroCodGtVm" 
    value="<%= request.getParameter("filtroCodGtVm") != null ? request.getParameter("filtroCodGtVm") : "" %>"
/>

</div>


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
 			<th class="gtvmp-col">GTVMPP</th>
        	<th>Lab</th>
        	<th>Rentabilidad</th>
        	<th>Ponderacion</th>
        	<th>nota</th>
            <th>Farmacia</th>
            <th>dto farmacia</th>
			<th>cn6</th>
			<th>cn7</th>
			<th>sustituible</th>        
			<th>tolva</th>        
		</tr>
	    	</thead>
 
	<logic:notEmpty name="SustXGtForm" property="listaBeans">	
	<logic:iterate id="nivel1" name="SustXGtForm" property="listaBeans" type="lopicost.spd.model.Nivel1" indexId="position1">

	 	<logic:notEmpty name="nivel1" property="listaNivel2">	
		<logic:iterate id="nivel2" name="nivel1" property="listaNivel2" type="lopicost.spd.model.Nivel2" indexId="position2">
			<tr id="nivel2-${position}" class="fila-padre" onclick="javascript:toggleChildren(${position})" >
	    		<td colspan="9" style="background-color: #f0f0f0;"><bean:write name="padre" property="nomGtVmp" /></td>
	    		<!--
	    		<td><bean:write name="padre" property="nomGtVmp" /><html:hidden name="padre" property="codGtVmp"/></td>
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
			            <input type="button" onclick="javascript:nuevoGtVmp('<bean:write name="padre" property="codGtVmp" />', 'NUEVO');" value="añadir" />
			        </p>
			    </td>
			</tr>
	
<logic:notEmpty name="padre" property="hijos">
<%-- Declarar una variable para almacenar el valor previo --%>
<%
    String ultimoNomGtVmpp = null;
%>
    
<logic:iterate id="hijo" name="padre" property="hijos" type="lopicost.spd.model.SustXGtvmpp" indexId="position2">
    <tr id="hijo-${position}-${position2}" class="${!empty hijo.nomLaboratorio ? (!empty hijo.nombreRobot ? 'hijo-laboratorio-farmacia' : 'hijo-laboratorio') : 'hijo'}">
        <td class="gtvmp-col" style="text-align: right;">
            <%-- Mostrar el valor solo si es diferente al último mostrado --%>
            <%
                if (ultimoNomGtVmpp == null || !ultimoNomGtVmpp.equals(hijo.getNomGtVmpp())) {
                    ultimoNomGtVmpp = hijo.getNomGtVmpp(); // Actualizar el último valor mostrado
            %>
                    <bean:write name="hijo" property="nomGtVmpp" />
            <%
                }
            %>
        </td>
        <td><bean:write name="hijo" property="nomLaboratorio" /></td>
        <td><bean:write name="hijo" property="rentabilidad" /></td>
        <td><bean:write name="hijo" property="ponderacion" /></td>
        <td><bean:write name="hijo" property="nota" /></td>
        <td><bean:write name="hijo" property="nombreRobot" /></td>
        <td>dto farmacia</td>
        <td><bean:write name="hijo" property="cn6" /></td>
        <td><bean:write name="hijo" property="cn7" /></td>
        <td><bean:write name="hijo" property="sustituible" /></td>        
        <td><bean:write name="hijo" property="tolva" /></td>        
    </tr>
</logic:iterate>
</logic:notEmpty>
	</logic:iterate>
	</logic:notEmpty>
	
	
	
		</logic:notEmpty>
		</logic:iterate>


	</table>


		
		
	</fieldset>
	</html:form>
</div>	
</body>
</html>