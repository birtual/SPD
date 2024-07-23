<%@page import="lopicost.spd.utils.SPDConstants"%>
<%@ page language="java" %>
<%@ page import="java.util.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">

<script language="javaScript" src="/spd/spd/app/ficheroResiLite/js/ficheroResiCabeceraLite.js"></script>

<head>
<jsp:include page="/spd/jsp/global/head.jsp"/>
<title>Gestión carga ficheros Robot (lite)</title>
</head>
<script>
    // Este script manejará la respuesta del servidor y actualizará o redireccionará según sea necesario
    function handleServerResponse(responseText) {
        // Puedes mostrar una alerta, actualizar la página o redirigir según tus necesidades
        alert(responseText);
        // window.location.reload();  // Para recargar la página
        // window.location.href = '/tuApp/list';  // Para redirigir a un mapeo específico
    }

	
	
</script>
<bean:define id="formulari" name="FicheroResiForm" type="lopicost.spd.struts.form.FicheroResiForm" />

<body>

<html:form action="/FicheroResiCabeceraLite.do" method="post">	

<div id="container">
    <html:hidden property="oidDivisionResidencia" />
    <html:hidden property="idDivisionResidencia" />
    <html:hidden property="idUsuario" />
    <html:hidden property="parameter" value="list"/>
    <html:hidden property="ACTIONTODO" value="list"/>
    
    
    <html:hidden property="oidFicheroResiCabecera" />
	<% String numPages = formulari.getNumpages()+""; %>
    <% String currpage = formulari.getCurrpage()+""; %>
    <html:hidden property="numpages" value="<%= numPages %>"/>
	<html:hidden property="currpage" value="<%= currpage %>"/>
	<input type="hidden" name="idProceso" />
	<input type="hidden" name="filtroProceso" />
	<input type="hidden" name="filtroEstado" />
 	
 	<fieldset>
	<!-- mostramos mensajes y errores, si existen -->
	<logic:notEmpty name="formulari" property="errors">
		<font color="red">
		<ul>
			<u>Mensaje:</u>
				<logic:iterate id="error" name="formulari" property="errors" type="java.lang.String">
					<li><bean:write name="error"/></li>
				</logic:iterate>
		</ul>
		</font>
		</logic:notEmpty>
		<div>	
			<label for="Name">Residencia</label>	
			<html:select property="oidDivisionResidenciaFiltro"  value="<%= String.valueOf(formulari.getOidDivisionResidenciaFiltro()) %>" onchange="submitResi()"> 
				<html:option value="">Residencia</html:option>
	   			<html:optionsCollection name="formulari" property="listaDivisionResidenciasCargadas" label="nombreDivisionResidencia" value="oidDivisionResidencia" />
			</html:select>
		</div>	
		<!--div>	
			<label for="apellido" accesskey="e">Proceso</label>	
		   	<html:select property="filtroProceso"  value="<%= formulari.getFiltroProceso() %>" onchange="submitProceso()"> 
   				<html:option value="">Todos</html:option>
   					<c:forEach items="${formulari.listaProcesosCargados}" var="bean"> 
			       		 <option value='${bean.idProceso}' ${formulari.filtroProceso == bean.idProceso ? 'selected' : ' '}><c:out value="${bean.idDivisionResidencia} - ${bean.idProceso}" ></c:out></option>   
    				</c:forEach> 
   				</html:select>
		</div>
		<div>
			<html:select property="filtroProceso" multiple="multiple" size="5" value="<%= formulari.getFiltroProceso() %>" onchange="submitProceso()"> 
				<html:option value="">Todos</html:option>
				<c:forEach items="${formulari.listaProcesosCargados}" var="bean"> 
					<option value='${bean.idProceso}' ${fn:contains(formulari.filtroProceso, bean.idProceso) ? 'selected' : ''}>
						<c:out value="${bean.idDivisionResidencia} - ${bean.idProceso}" />
					</option>   
				</c:forEach> 
			</html:select>
</div>
	   	<div>	
			<label for="Estado">Estado</label>	
			<html:select property="filtroEstado"  value="<%= formulari.getFiltroEstado() %>" onchange="submit()"> 
	   			<html:option value="">Todos</html:option>	
					<c:forEach items="${formulari.listaEstadosCabecera}" var="bean"> 
			    		 <option value='${bean.idEstado}' ${formulari.filtroEstado == bean.idEstado ? 'selected' : ' '}><c:out value="${bean.idEstado}" ></c:out></option>   
    				</c:forEach> 
    		</html:select>		
		</div-->

	   	<div>	
		    <p class="botons">
				<!-- input type="button" class="azulCielo" onclick="javascript:goInicio();" value="Inicio" /> -->
				<input type="button" class="azulCielo" value="Salir" onclick="javascript:salir();" />
				<input type="button" class="azulCielo" onclick="javascript:listado();"  value="Buscar"  />  
				<input type="button" class="azulCielo" onclick="javascript:go('/spd/Iospd/Iospd.do?parameter=list&operation=FILTER')"  value="Carga de ficheros resi" />  
				<input type="button" class="azulCielo" onclick="javascript:goHistorico()"  value="Historico de producciones"  />  
			</p> 
		</div>

		<table class="blueTable" border="1">
			<tr>
			 	<!-- th>Residencia</th> -->
				<th>Id Proceso</th>
				<th>Fecha Creación</th>
				<th>Filas totales</th>
				<th>nº Errores</th>
				<th>Cips Fichero</th>
				<th>Cips Resi</th>
				<th>Creador carga</th>
				<th>pendiente validar</th>
				<th>Previsión actualizada</th>
				<!-- <th>Nota1</th>-->
				<!-- <th>Nota2</th>-->
				<!-- <th>Nota3</th>-->
				<th></th>
				<th></th>
				
		   </tr>
 	    	<tbody>
		 	<logic:iterate id="data" name="formulari" property="listaFicheroResiCabeceraBean" type="lopicost.spd.struts.bean.FicheroResiBean" indexId="position">
				<tr>
					<!-- td><bean:write name="data" property="idDivisionResidencia" /></td> -->
					<td class="a150"><a href="javascript:goDetalle('<bean:write name="data" property="oidDivisionResidencia" />', '<bean:write name="data" property="idProceso" />', '<bean:write name="data" property="oidFicheroResiCabecera" />');"><bean:write name="data" property="idProceso" /></a> <br></td>
					<td class="derecha a40"><bean:write name="data" property="fechaHoraProceso" /></td>
					<td class="derecha"><bean:write name="data" property="filasTotales" /></td>
					<!-- td class="derecha"><a href="javascript:abrirVentanaErrores('<bean:write name="data" property="oidFicheroResiCabecera" />', '<bean:write name="data" property="errores" />');"><bean:write name="data" property="numErrores" /></a></td> -->
					<td class="derecha"><a href="javascript:abrirVentanaErrores('<bean:write name="data" property="oidFicheroResiCabecera" />', '<bean:write name="data" property="idProceso" />');"><bean:write name="data" property="numErrores" /></a></td> 
					<td class="derecha"><bean:write name="data" property="cipsTotalesProceso" /></td>
					<td class="derecha"><bean:write name="data" property="cipsActivosSPD" /></td>
					<td><bean:write name="data" property="usuarioCreacion" /></td>
					<td class="derecha"><bean:write name="data" property="numeroValidacionesPendientes" /></td>

					
					<td><bean:write name="data" property="fechaCalculoPrevision" /></td>
					<!-- <td class="a250"><bean:write name="data" property="free1" /></td> -->
					<!-- <td class="a250"><bean:write name="data" property="free2" /></td> -->
					<!-- td class="a250"><bean:write name="data" property="free3" /></td> -->
			 		<td class="a150">
						<p class="botons">
						<logic:equal property="idEstado" name="data" value="<%= SPDConstants.SPD_PROCESO_2_PENDIENTE_VALIDAR %>">
							<input type="button" class=verde onclick="javascript:editar('<bean:write name="data" property="oidFicheroResiCabecera" />');"  value="Editar"  />
							<input type="button" class="rojo" onclick="javascript:borrar('<bean:write name="data" property="oidFicheroResiCabecera" />');"  value="Borrar"  />
							<input type="button" class="azul" onclick="javascript:exportExcel('<bean:write name="data" property="idDivisionResidencia" />', '<bean:write name="data" property="idProceso" />')" value="Exportar "  />
						</logic:equal>
						<logic:equal property="idEstado" name="data" value="<%= SPDConstants.SPD_PROCESO_3_VALIDADO %>">
							<input type="button" class=verde onclick="javascript:editar('<bean:write name="data" property="oidFicheroResiCabecera" />');"  value="Editar"  />
							<input type="button" class="rojo" onclick="javascript:borrar('<bean:write name="data" property="oidFicheroResiCabecera" />');"  value="Borrar"  />
							<input type="button" class="azul" onclick="javascript:exportExcel('<bean:write name="data" property="idDivisionResidencia" />', '<bean:write name="data" property="idProceso" />')" value="Exportar "  />
							<input type="button" class="marron" value="Ficheros Helium" onclick="javascript:generarFicherosHelium('<bean:write name="data" property="oidFicheroResiCabecera" />')" />
						<logic:equal property="idUsuario" name="formulari" value="admin">
							<input type="button" class="negro" value="Ficheros DM y RX" onclick="javascript:generarFicherosRobot('<bean:write name="data" property="oidFicheroResiCabecera" />')" />
						</logic:equal>
						</logic:equal>
						<logic:equal property="idEstado" name="data" value="<%= SPDConstants.SPD_PROCESO_3_POR_VALIDACION_MASIVA %>">
							<input type="button" class=verde onclick="javascript:editar('<bean:write name="data" property="oidFicheroResiCabecera" />');"  value="Editar"  />
							<input type="button" class="rojo" onclick="javascript:borrar('<bean:write name="data" property="oidFicheroResiCabecera" />');"  value="Borrar"  />
							<input type="button" class="azul" onclick="javascript:exportExcel('<bean:write name="data" property="idDivisionResidencia" />', '<bean:write name="data" property="idProceso" />')" value="Exportar "  />
							<input type="button"  class="marron" value="Ficheros Helium" onclick="javascript:generarFicherosHelium('<bean:write name="data" property="oidFicheroResiCabecera" />')" />
						<logic:equal property="idUsuario" name="formulari" value="admin">
							<input type="button" class="negro" value="Ficheros DM y RX" onclick="javascript:generarFicherosRobot('<bean:write name="data" property="oidFicheroResiCabecera" />')" />
						</logic:equal>
						</logic:equal>
						<logic:equal property="idEstado" name="data" value="<%= SPDConstants.SPD_PROCESO_4_CARGA_ERROR %>">
							<input type="button" class="rojo" onclick="javascript:borrar('<bean:write name="data" property="oidFicheroResiCabecera" />');"  value="Borrar"  />
						</logic:equal>



						
						</p>
					</td>
				<td><bean:write name="data" property="idEstado" />
				</td>
				    <logic:empty name="data" property="fechaCalculoPrevision">
				    
					<td class="a150"><a href="javascript:addTratamientos('<bean:write name="data" property="idDivisionResidencia" />', '<bean:write name="data" property="idProceso" />');">Añadir tratamientos</a>
				</td>
					</logic:empty>
								</tr>
	    	</logic:iterate>
	     	</tbody>  
		</table>
	</fieldset>

	<!--  paginación  -->
	<div>
		<logic:greaterThan name="formulari" property="numpages" value="1">
		<table border="0" width="100%">
			<tr>
				<td align="center">
				<logic:greaterThan name="formulari" property="currpage" value="0">
					<a href="javascript:atras();" ><<</a>
				</logic:greaterThan>
				&nbsp;<input type="text" name="newPage" value="<%= formulari.getCurrpage()+1 %>" size="1" maxlength="4" onkeypress="irAPagina(this.value,<%=formulari.getNumpages()%>);">/<%= formulari.getNumpages() %>&nbsp;
				<logic:lessThan name="formulari" property="currpage" value="<%= String.valueOf(formulari.getNumpages() -1)%>">
					<a href="javascript:avance();" >>></a>
				</logic:lessThan>
				</td>
			</tr>
		</table>
		</logic:greaterThan>
	</div>
	<!--  paginación   -->
</div>	
</html:form>
</body>
</html>