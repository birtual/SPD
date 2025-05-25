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
<title>Creación de aviso</title>
</head>


<bean:define id="formulari" name="AvisosForm" type="lopicost.spd.struts.form.AvisosForm" />

<head>
<script type="text/javascript">	
		
		function volver()
		{
			var f = document.AvisosForm;
			f.parameter.value='list';
			f.ACTIONTODO.value='ADMIN';
			f.submit();
		}	
		
		function nuevo()
		{
			var f = document.AvisosForm;
		
			if(f.texto.value=='')
				alert('Falta indicar el texto');
			else if(f.tipo.value=='')
				alert('Falta indicar el tipo de aviso ');
			else if(f.fechaInicio.value=='')
				alert('Falta indicar la fecha de inicio del aviso');
			else if(f.fechaFin.value=='')
				alert('Falta indicar la fecha fin del aviso ');
			else
			{
				//f.nombreCorto.value=f.nombreCorto.value;
				f.parameter.value='nuevo';
				f.ACTIONTODO.value='NUEVO_OK';

				f.submit();
			}
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

	



		 <html:hidden property="oidAviso" />
		 <html:hidden property="parameter" value="nuevo"/>
		 <html:hidden property="ACTIONTODO" value="NUEVO"/>

		<th class="segunda">Orden</th>
		<th class="segunda">Tipo</th>


		
		<table class="detalleEnlace">

		<tr>
			<th class="segunda">Texto</th><td><html:textarea property="texto"/></td>
		</tr>
		<tr>
			<th class="segunda">Fecha inicio</th><td><input type="text" id="fechaInicio" name="fechaInicio" value="${fechaInicio}" placeholder="Selecciona fecha"></td>
			
		</tr>
		<tr>
			<th class="segunda">Fecha fin</th><td><input type="text" id="fechaFin" name="fechaFin" value="${fechaFin}" placeholder="Selecciona fecha"></td>
		</tr>
		<tr>
			<th class="segunda">Activo</th>
			<td>
				<select name="activo" style="text-align: left;">>
				    <option value="SI" ${dat.activo}='SI' ? 'selected="selected"' : ''}>Sí</option>
				    <option value="No" ${dat.activo}='NO' ? 'selected="selected"' : ''}>No</option>
				</select>
			</td>
		</tr>
		<tr>
			<th class="segunda">Farmacia</th><td>
			<div>
			    <html:select property="idFarmacia">
			        <html:option value="">Todas</html:option>
			        <html:optionsCollection name="formulari" property="listaFarmacias" label="nombreFarmacia" value="idFarmacia" />
			    </html:select>
			</div>
			</td>
		</tr>

		<tr>
			<th class="segunda">Orden</th><td><html:text property="orden"  /></td>
		</tr>
		<tr>
			<th class="segunda">Tipo</th>
			<td>
				<select name="tipo">
				    <option value="" selected>Selección de tipo</option>
				    <option value="INFO" >Aviso informativo</option>
				    <option value="INCIDENCIA">Incidencia</option>
				    <option value="ALERTA" >Alerta</option>
				</select>

			</td>
		</tr>

		
			
		</table>

		<tr>
			<td>	
				<p class="botons">
				<input type="button" onclick="javascript:volver()" value="Volver"/>
				<input type="button" onclick="javascript:nuevo()" value="Nuevo"/>
				</p>	
			</td>	
		</tr>	
	</table>

	</div>	

	</html:form>

</body>
</html>