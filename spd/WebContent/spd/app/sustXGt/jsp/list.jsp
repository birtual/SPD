
<%@ page language="java" %>
<%@ page import="java.util.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    
    
.nivel3  td {
    background-color: #84ec84; !important;/* Azul claro */
}
.nivel3-laboratorio  td {
    background-color: #84ec84; !important;/* Verde claro */
}
.nivel3-laboratorio-farmacia  td {
    background-color: #3cc63c; !important;/* Verde más intenso */
}

.separator {
    background-color: #f7fdfe; 
    height: 10px; /* Altura de la fila */
    border: none; /* Sin bordes */
}

    
    
  tr {
    border: none;
}

td {
    border: 0.1em solid black; !important;
}

.blanco {
    background-color: #f7fdfe;  !important;
    border: 0.1em solid #85f1d9; !important;
    
}

.hidden {
    display: none;
}
    </style>

    
    <script>

    
 // Ejecutar al cargar la página
    window.onload = function() {
        const toggleAll = document.getElementById("toggleAll");
        // Llamar a la función toggleAllChildren con el estado del checkbox
        toggleAllChildren(toggleAll.checked);
    };


    // Función para mostrar/ocultar el listado de robots y las columnas de farmacia
    function toggleFarmaciaVisibility(isChecked) {
        // Mostrar/ocultar el dropdown de robots
        const dropdown = document.getElementById('robotDropdown');
        dropdown.style.display = isChecked ? 'block' : 'none';

        // Mostrar/ocultar las columnas de farmacia
        const farmaciaColumns = document.querySelectorAll('#farmaciaColumn');
        farmaciaColumns.forEach(function(column) {
            column.style.display = isChecked ? 'table-cell' : 'none';
        });
    }
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

	 <html:hidden property="filtroCodGtVmp"/>
	 <html:hidden property="filtroCodGtVmpp"/>
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
		    onchange="toggleAllChildren(this.checked)" 
		    <%= request.getParameter("toggleAll") != null ? "checked" : "" %> 
		>
		<label for="toggleAll">Desplegar niveles</label>
	</div>
    <div>
        <!-- Checkbox -->
        <input 
            type="checkbox" 
            id="filtroVerTodoConsejo" 
            name="filtroVerTodoConsejo" 
            value="true"
            onchange="this.form.submit()" 
            <%= formulari.isFiltroVerTodoConsejo() ? "checked" : "" %>
        >
        <label for="filtroVerTodoConsejo">Mostrar todos los GTVM del Consejo</label>
    </div>

    <div>
        <input 
            type="checkbox" 
            id="filtroVerFarmacias" 
            name="filtroVerFarmacias" 
            onchange="toggleFarmaciaVisibility(this.checked)" 
            <%= "on".equals(request.getParameter("filtroVerFarmacias")) ? "checked" : "" %> 
        >
        <label for="filtroVerFarmacias">Mostrar farmacias</label>
    </div>
    <!-- Contenedor del desplegable de robots -->
	<div id="robotDropdown" style="display: <%= formulari.isFiltroVerFarmacias() ? "block" : "none" %>;">
	    <logic:notEmpty name="formulari" property="listaRobots">
	        <label for="filtroRobot">Selecciona:</label>
	        <html:select property="filtroRobot" value="<%= formulari.getFiltroRobot() %>" onchange="submit()"> 
	            <html:option value="">Todos</html:option>
	            <html:optionsCollection name="formulari" property="listaRobots" label="nombreRobot" value="idRobot" />
	        </html:select>
	    </logic:notEmpty>
	</div>

    
  

	<div>
		<a href="javascript:resetFilters();">Eliminar filtros</a>
	</div>
	</fieldset>
	<table border="1">
 		<thead id="cabecera" style="display: none;">
    	<tr>
 			<th width="40em"></th>
        	<th width="40em"></th>
        	<th width="500em"></th>
    		<th>Lab</th>
   			<th>Rentabilidad</th>
        	<th id="farmaciaColumn" style="display: <%= "on".equals(request.getParameter("filtroVerFarmacias")) ? "table-cell" : "none" %>;">Ponderacion</th>
        	<th id="farmaciaColumn" style="display: <%= "on".equals(request.getParameter("filtroVerFarmacias")) ? "table-cell" : "none" %>;">nota</th>
            <th id="farmaciaColumn" style="display: <%= "on".equals(request.getParameter("filtroVerFarmacias")) ? "table-cell" : "none" %>;">Farmacia</th>
            <th id="farmaciaColumn" style="display: <%= "on".equals(request.getParameter("filtroVerFarmacias")) ? "table-cell" : "none" %>;">dto farmacia</th>
			<th>cn6</th>
			<th>cn7</th>
			<th id="farmaciaColumn" style="display: <%= "on".equals(request.getParameter("filtroVerFarmacias")) ? "table-cell" : "none" %>;">sustituible</th>        
			<th id="farmaciaColumn" style="display: <%= "on".equals(request.getParameter("filtroVerFarmacias")) ? "table-cell" : "none" %>;">tolva</th>        
		</tr>
	    	</thead>
 
	<logic:notEmpty name="SustXGtForm" property="listaBeans">	
	<logic:iterate id="nivel1" name="SustXGtForm" property="listaBeans" type="lopicost.spd.model.Nivel1" indexId="position1">
		<tr id="nivel1-${position1}" class="nivel1" onclick="javascript:toggleChildren(${position1})" >
			<td style="width: 40em"><input type="button" onclick="javascript:nuevoDesdeNivel1('<bean:write name="nivel1" property="codGtVm" />', 'NUEVO');" value="añadir" />
			</td>
			<td style="width: 40em" align="left">1
			</td>
			<td style="width: 500em">
				<bean:write name="nivel1" property="nomGtVm" />
			</td>
			<td class="blanco" colspan="10"></td>
		</tr>
		
	 	<logic:notEmpty name="nivel1" property="listaNivel2">	
		<logic:iterate id="nivel2" name="nivel1" property="listaNivel2" type="lopicost.spd.model.Nivel2" indexId="position2">
			<tr id="nivel2-${position1}-${position2}" class="fila-nivel2" onclick="javascript:toggleChildren(${position2})" >
	    		<td style="width:40em"><input type="button" onclick="javascript:nuevoDesdeNivel2('<bean:write name="nivel2" property="codGtVmp" />', 'NUEVO');" value="añadir" />
	    		</td>
	    		<td style="width:40em" align="center">2&nbsp;</td>
	    		<td style="width:500em">
		    		&nbsp;&nbsp;&nbsp;<bean:write name="nivel2" property="nomGtVmp" />
		    	</td>
				<td class="blanco" colspan="10"></td>
			</tr>
	
		<logic:notEmpty name="nivel2" property="listaNivel3">
		<%-- Declarar una variable para almacenar el valor previo --%>
		<%
		    String ultimoNomGtVmpp = null;
		%>
    
		<logic:iterate id="nivel3" name="nivel2" property="listaNivel3" type="lopicost.spd.model.Nivel3" indexId="position3">
			    <tr id="nivel3-${position1}-${position2}-${position3}" class="${!empty nivel3.nomLaboratorio ? (!empty nivel3.nombreRobot ? 'nivel3-laboratorio-farmacia' : 'nivel3-laboratorio') : 'nivel3'}">

			        <td style="width:40em"><input type="button" onclick="javascript:nuevoDesdeNivel3('<bean:write name="nivel3" property="codGtVmpp" />', 'NUEVO');" value="añadir" />
	    			</td>
			       <td style="width:40em" align="right">3</td>
	    			<td style="width:500em">
			            <%-- Mostrar el valor solo si es diferente al último mostrado --%>
			            <%
			                if (ultimoNomGtVmpp == null || !ultimoNomGtVmpp.equals(nivel3.getNomGtVmpp())) {
			                    ultimoNomGtVmpp = nivel3.getNomGtVmpp(); // Actualizar el último valor mostrado
			            %>
			                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			                <bean:write name="nivel3" property="nomGtVmpp" />
			            <%
			                }
			            %>
			        </td>
			        
			        	<%-- No mostrar valor en caso que sea un registro informativo vacío de bdConsejo --%>
			            <%
			                if (nivel3.getCodLaboratorio() != null && !nivel3.getCodLaboratorio().equalsIgnoreCase("")) {
			                   
			            %>
			        		<td><bean:write name="nivel3" property="nomLaboratorio" /></td>
			                <td><bean:write name="nivel3" property="rentabilidad" /></td>
					        <td id="farmaciaColumn" style="display: <%= "on".equals(request.getParameter("filtroVerFarmacias")) ? "table-cell" : "none" %>;"><bean:write name="nivel3" property="ponderacion" /></td>
					        <td id="farmaciaColumn" style="display: <%= "on".equals(request.getParameter("filtroVerFarmacias")) ? "table-cell" : "none" %>;"><bean:write name="nivel3" property="nota" /></td>
					        <td id="farmaciaColumn" style="display: <%= "on".equals(request.getParameter("filtroVerFarmacias")) ? "table-cell" : "none" %>;"><bean:write name="nivel3" property="nombreRobot" /></td>
					        <td id="farmaciaColumn" style="display: <%= "on".equals(request.getParameter("filtroVerFarmacias")) ? "table-cell" : "none" %>;">dto farmacia</td>
					        <td><bean:write name="nivel3" property="cn6" /></td>
					        <td><bean:write name="nivel3" property="cn7" /></td>
					        <td id="farmaciaColumn" style="display: <%= "on".equals(request.getParameter("filtroVerFarmacias")) ? "table-cell" : "none" %>;"><bean:write name="nivel3" property="sustituible" /></td>        
					        <td id="farmaciaColumn" style="display: <%= "on".equals(request.getParameter("filtroVerFarmacias")) ? "table-cell" : "none" %>;"><bean:write name="nivel3" property="tolva" /></td>        
			            <%
			                } else {
			            %>
			        		<td style="background-color: #f7fdfe;" colspan="10"></td>
			        	<%
			                }
			            %>
			        
			        
			    </tr>
			</logic:iterate>
		</logic:notEmpty>
	</logic:iterate>
	</logic:notEmpty>
    
    <!-- Fila de separación -->
    <tr>
        <td class="separator" colspan="13"></td>
    </tr>

	
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