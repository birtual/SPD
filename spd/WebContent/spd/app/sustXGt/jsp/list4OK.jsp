
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
        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            border: 1px solid black;
           /* text-align: center;
            padding: 8px;*/
        }

        /* Las tres primeras columnas con ancho fijo */
        th:nth-child(1),
        td:nth-child(1),
        th:nth-child(2),
        td:nth-child(2),
        th:nth-child(3),
        td:nth-child(3) {
            width: 50px; /* Ancho suficiente para 3 caracteres */
        }

        /* La cuarta columna ocupa el 25% del ancho de la tabla */
        th:nth-child(4),
        td:nth-child(4) {
            width: 25%;
        }

        /* Las demás columnas tienen ancho automático */
        th:nth-child(n+5),
        td:nth-child(n+5) {
            width: auto;
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
		Lógica de filtrado:
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
		    onchange="toggleAllChildren(this.checked)" 
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
 			<th colspan="3"></th>
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
		<tr id="nivel1-${position1}" onclick="javascript:toggleChildren(${position1})" >
			<td><input type="button" onclick="javascript:nuevoDesdeNivel1('<bean:write name="nivel1" property="codGtVm" />', 'NUEVO');" value="añadir" />
			</td>
			<td align="left">1&nbsp;
			</td>
			<td>
				<bean:write name="nivel1" property="nomGtVm" />
			</td>
		</tr><!-- Nivel 1 -->
<logic:iterate id="nivel1" name="nivel1List">
    <div class="nivel1" onclick="toggleVisibility('nivel1-${nivel1.id}')">
        <bean:write name="nivel1" property="nomGtVm" />
    </div>

    <!-- Nivel 2 (oculto por defecto) -->
    <div id="nivel1-${nivel1.id}" class="hidden">
        <logic:iterate id="nivel2" name="nivel1" property="nivel2List">
            <div class="nivel2" onclick="toggleVisibility('nivel2-${nivel2.id}')">
                <bean:write name="nivel2" property="nombre" />
            </div>

            <!-- Nivel 3 (oculto por defecto) -->
            <div id="nivel2-${nivel2.id}" class="hidden">
                <logic:iterate id="nivel3" name="nivel2" property="nivel3List">
                    <div class="nivel3">
                        <bean:write name="nivel3" property="nombre" />
                    </div>
                </logic:iterate>
            </div>
        </logic:iterate>
    </div>
</logic:iterate>
		
	 	<logic:notEmpty name="nivel1" property="listaNivel2">	
		<logic:iterate id="nivel2" name="nivel1" property="listaNivel2" type="lopicost.spd.model.Nivel2" indexId="position2">
			<tr id="nivel2-${position1}-${position2}" onclick="javascript:toggleChildren(${position2})" >
	    		<td><input type="button" onclick="javascript:nuevoDesdeNivel2('<bean:write name="nivel2" property="codGtVmp" />', 'NUEVO');" value="gestionar" />
	    		</td>
	    		<td align="center">2&nbsp;</td>
	    		<td>
		    		<bean:write name="nivel2" property="nomGtVmp" />
		    	</td>
			</tr>
	
		<logic:notEmpty name="nivel2" property="listaNivel3">
		<%-- Declarar una variable para almacenar el valor previo --%>
		<%
		    String ultimoNomGtVmpp = null;
		%>
    
		<logic:iterate id="nivel3" name="nivel2" property="listaNivel3" type="lopicost.spd.model.Nivel3" indexId="position3">
			    <tr id="nivel3-${position1}-${position2}-${position3}" class="${!empty nivel3.nomLaboratorio ? (!empty nivel3.nombreRobot ? 'nivel3-laboratorio-farmacia' : 'nivel3-laboratorio') : 'nivel3'}">

			        <td><input type="button" onclick="javascript:nuevoDesdeNivel3('<bean:write name="nivel3" property="codGtVmpp" />', 'NUEVO');" value="modificar" />
	    			</td>
			        <td align="right">3</td>
	    			<td>
			            <%-- Mostrar el valor solo si es diferente al último mostrado --%>
			            <%
			                if (ultimoNomGtVmpp == null || !ultimoNomGtVmpp.equals(nivel3.getNomGtVmpp())) {
			                    ultimoNomGtVmpp = nivel3.getNomGtVmpp(); // Actualizar el último valor mostrado
			            %>
			                <bean:write name="nivel3" property="nomGtVmpp" />
			            <%
			                }
			            %>
			        </td>
			        <td><bean:write name="nivel3" property="nomLaboratorio" /></td>
			        <td><bean:write name="nivel3" property="rentabilidad" /></td>
			        <td><bean:write name="nivel3" property="ponderacion" /></td>
			        <td><bean:write name="nivel3" property="nota" /></td>
			        <td><bean:write name="nivel3" property="nombreRobot" /></td>
			        <td>dto farmacia</td>
			        <td><bean:write name="nivel3" property="cn6" /></td>
			        <td><bean:write name="nivel3" property="cn7" /></td>
			        <td><bean:write name="nivel3" property="sustituible" /></td>        
			        <td><bean:write name="nivel3" property="tolva" /></td>        
			    </tr>
			</logic:iterate>
		</logic:notEmpty>
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