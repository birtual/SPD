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
<!-- Flatpickr CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">

<!-- Flatpickr JavaScript -->

<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
<script src="https://cdn.jsdelivr.net/npm/flatpickr/dist/l10n/es.js"></script>



<jsp:include page="/spd/jsp/global/head.jsp"/>
<title>Edición aviso</title>

</head>


<bean:define id="formulari" name="AvisosForm" type="lopicost.spd.struts.form.AvisosForm" />

<head>
<script type="text/javascript">	
		
		function volver()
		{
			var f = document.AvisosForm;
			f.parameter.value='list';
			f.submit();
		}	
		
		function grabar(oidAviso)
		{
			
			var f = document.AvisosForm;

			f.parameter.value='editar';		
			f.ACTIONTODO.value='EDITA_OK';
			f.oidAviso.value=oidAviso;
			f.submit();
		}	

				
</script>
		<!-- Configuración de Flatpickr -->
		<script>
		    document.addEventListener("DOMContentLoaded", function () {
		        flatpickr("#fechaInicio", {
		            dateFormat: "d/m/Y", // Formato de fecha
		            locale: "es",
		            allowInput: true // Permite escribir manualmente
		        });

		        flatpickr("#fechaFin", {
		            dateFormat: "d/m/Y", // Formato de fecha
		            locale: "es",
		            allowInput: true // Permite escribir manualmente
		        });
		    });
		</script>



<body id="general">
	<div id="contingut">
		<center>
		<h2>Edición aviso</h2>
		<html:form action="/Avisos.do" method="post">	

		<bean:define id="data" name="formulari" property="aviso"/>



		 <html:hidden property="oidAviso" />
		 <html:hidden property="parameter" value="editar"/>
		 <html:hidden property="ACTIONTODO" value="EDITA"/>

		<th class="segunda">Orden</th>
		<th class="segunda">Tipo</th>


		
		<table class="detalleEnlace">
		<tr>
			<th class="segunda">Fecha creación</th><td><bean:write name="data" property="fechaInsert" /></td>
		</tr>
		<tr>
			<th class="segunda">Texto</th><td><html:textarea name="data" property="texto"/></td>
		</tr>
		<tr>
			<th class="segunda">Fecha inicio</th><td><input type="text" id="fechaInicio" name="fechaInicio" value="${data.fechaInicio}" placeholder="Selecciona fecha"></td>
			
		</tr>
		<tr>
			<th class="segunda">Fecha fin</th><td><input type="text" id="fechaFin" name="fechaFin" value="${data.fechaFin}" placeholder="Selecciona fecha"></td>
		</tr>
		<tr>
			<th class="segunda">Activo</th>
			<td>
			    <select name="activo" style="text-align: left;">
			        <option value="SI" <c:if test="${data.activo == 'SI'}">selected="selected"</c:if>>Sí</option>
			        <option value="NO" <c:if test="${data.activo == 'NO'}">selected="selected"</c:if>>No</option>
			    </select>
			</td>
		</tr>
		<tr>
			<th class="segunda">Farmacia</th><td>
			<div>
			    <html:select name="data"  property="idFarmacia">
			        <html:option value="">Todas</html:option>
			        <html:optionsCollection name="formulari" property="listaFarmacias" label="nombreFarmacia" value="idFarmacia" />
			    </html:select>
			</div>
			</td>
		</tr>
		<tr>
			<th class="segunda">Creador</th><td><bean:write name="data" property="usuarioCreador" /></td>
		</tr>
		<tr>
			<th class="segunda">Orden</th><td><html:text name="data" property="orden"  /></td>
		</tr>
		<tr>
			<th class="segunda">Tipo</th>
			<td>
				<select name="tipo">
				    <option value="INFO" <c:if test="${data.tipo == 'AVISO'}">selected</c:if>>Aviso informativo</option>
				    <option value="INCIDENCIA" <c:if test="${data.tipo == 'INCIDENCIA'}">selected</c:if>>Incidencia</option>
				    <option value="ALERTA" <c:if test="${data.tipo == 'ALERTA'}">selected</c:if>>Alerta</option>
				</select>

			</td>
		</tr>

		
			
		</table>

		<tr>
			<td>	
				<p class="botons">
				<input type="button" onclick="javascript:volver('<bean:write name="formulari" property="oidAviso"/>')" value="Volver"/>
				<input type="button" onclick="javascript:grabar('<bean:write name="formulari" property="oidAviso"/>')" value="Confirmar"/>
				</p>	
			</td>	
		</tr>	
	</table>

	</div>	

	</html:form>

</body>
</html>