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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<jsp:include page="/spd/jsp/global/head.jsp"/>	
	<title>Listado sustituciones Robot</title>
</head>



	<bean:define id="formulari" name="GestSustitucionesLiteForm" type="lopicost.spd.struts.form.GestSustitucionesLiteForm" />
	<script language="javaScript" src="/spd/spd/app/gestSustitucionesLite/js/gestSustitucionesLite.js"></script>
<body id="general">
	<html:form action="/GestSustitucionesLite.do" method="post">	
	<html:errors/>

<div id="container">
     <html:hidden property="parameter" value="list"/>
     <html:hidden property="ACTIONTODO" value="list"/>
     <html:hidden property="accion" />
     <html:hidden property="idDivisionResidencia" />
     <!-- html:hidden property="filtroMedicamentoResi" /-->
     <html:hidden property="oidGestSustitucionesLite" />    
   	<% String numPages = formulari.getNumpages()+""; %>
   	<% String currpage = formulari.getCurrpage()+""; %>
     <html:hidden property="numpages" value="<%= numPages %>"/>
	 <html:hidden property="currpage" value="<%= currpage %>"/>
     <html:hidden property="resiCn" />
     <html:hidden property="resiMedicamento" />
	
   	<!-- mostramos mensajes y errores, si existen -->


	<logic:notEmpty name="GestSustitucionesLiteForm" property="errors">
		<ul>
		<font color="red">
			<u>Mensaje:</u>
				<logic:iterate id="error" name="GestSustitucionesLiteForm" property="errors" type="java.lang.String">
						<li><bean:write name="error"/></li>
				</logic:iterate>
		</font>
		</ul>
	</logic:notEmpty>
	<fieldset>
		<div>
			<label for="campoGoogle" accesskey="e">Texto a buscar </label>
			 <!--  < String campoGoogle = formulari.getCampoGoogle(); if(campoGoogle==null || campoGoogle.equals("")) campoGoogle= formulari.getFiltroCn();%> --> 
			<!-- % String campoGoogle = formulari.getResiCn();%> -->
		   	<!-- input type="text" name="campoGoogle" value="<=campoGoogle %>" title="Texto a buscar" alt="Texto a buscar" -->
		   	<html:text name="formulari" property="campoGoogle" />
		   	
		    <!-- html:hidden property="filtroCn" /> -->
		</div>
		<div>
			   <label for="listaDivisionResidencia" accesskey="e">Residencia</label>
			<logic:notEmpty name="GestSustitucionesLiteForm" property="listaDivisionResidencia">	
			  
		   	 <html:select property="oidDivisionResidenciaFiltro"   onchange="submit()"> 
		   	 	<html:option value="">Todas</html:option>
   					<html:optionsCollection name="GestSustitucionesLiteForm" property="listaDivisionResidencia" label="nombreDivisionResidencia" value="oidDivisionResidencia" />
				</html:select>
		     </logic:notEmpty>	
		</div>	
		<logic:notEmpty name="GestSustitucionesLiteForm" property="listaGtVm">	
		<div>
		     <label for="listaGtVm" accesskey="e">Grupo VM  (Princ.Activo)</label>
					<html:select property="filtroGtVm"  value="<%= formulari.getFiltroGtVm() %>" onchange="submit()"> 
			   	 		<html:option value="">Todos</html:option>
	  					<html:optionsCollection name="formulari" property="listaGtVm" label="nomGtVm" value="codGtVm" />
				</html:select>
		</div>	
	    </logic:notEmpty>	
		<logic:notEmpty name="GestSustitucionesLiteForm" property="listaGtVmp">	
		<div>
		     <label for="listaGtVmp" accesskey="e">Grupo VMP (Princ.Activo, dosis y forma)</label>
					<html:select property="filtroGtVmp"  value="<%= formulari.getFiltroGtVmp() %>" onchange="submit()" styleClass="select_corto"> 
			   	 		<html:option value="">Todos</html:option>
	  					<html:optionsCollection name="formulari" property="listaGtVmp" label="nomGtVmp" value="codGtVmp" />
				</html:select>
			</div>	
	    </logic:notEmpty>	
				
		<div>	<label for="resiCn" accesskey="e">CN a sustituir:</label>
		  <font color="red"><bean:write name="formulari" property="resiCn" /></font>  (<bean:write name="formulari" property="idDivisionResidencia" />)
		</div> 
		 <div>	<label for="resiMedicamento" accesskey="e">Descripción:</label>
		   			 <font color="red"><bean:write name="formulari"  property="resiMedicamento" />
     			</font>
     	</div>
    <p class="botonBuscar">
		<input type="button" onclick="javascript:buscar()"	 	 value="Buscar" />  
		<input type="button" onclick="javascript:nuevo()" 		 value="Nuevo"  />  
		<input type="button" onclick="javascript:salir()" 		 value="Salir"  />  
	</p>
	

	<table border="1">
    <tr>
		<th>Residencia</th>
		<th>Resi Cn</th>
		<th>Resi Medicamento </th>
		<th>Spd Cn</th>
		<th>Spd NombreBolsa</th>
		<th>Spd FormaMedicacion</th>
		<th>Spd AccionBolsa</th>
		<th>Excepciones</th>
        <th>Aux1</th>
		<th></th>
	</tr>

  	<logic:notEmpty name="GestSustitucionesLiteForm" property="listaSustituciones">	
	<logic:iterate id="data" name="formulari" property="listaSustituciones"  type="lopicost.spd.model.GestSustitucionesLite"  indexId="position">
       <tr>
            <td><bean:write name="data" property="idDivisionResidencia" /></td>
            <td><bean:write name="data" property="resiCn" /></td>
            <td><bean:write name="data" property="resiMedicamento" /></td>
 			<td><bean:write name="data" property="spdCn" /></td>
            <td><bean:write name="data" property="spdNombreBolsa" /></td>
            <td><bean:write name="data" property="spdFormaMedicacion" /></td>
            <td><bean:write name="data" property="spdAccionBolsa" /></td>
            <td><bean:write name="data" property="excepciones" /></td>
            <td><bean:write name="data" property="aux1" /></td>
			<td>
				<p class="botons">
					<input type="button" onclick="javascript:editar('<bean:write name="data" property="oidDivisionResidencia" />', '<bean:write name="data" property="oidGestSustitucionesLite" />');"  value="Editar"  />
				</p>
				<p class="botons">
					<input type="button" onclick="javascript:borrar( '<bean:write name="data" property="oidGestSustitucionesLite" />');"  value="Borrar"  />
				</p>
				<p class="botons">
					<input type="button" onclick="javascript:duplicar('<bean:write name="data" property="oidGestSustitucionesLite" />');"  value="Duplicar"  />
				</p>
			</td>
        </tr>
      </logic:iterate>
   	</logic:notEmpty>
	</table>	
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