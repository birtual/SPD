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
	<jsp:include page="/spd/jsp/global/head.jsp"/>
	<title>Creación de Proceso</title>
</head>
<script>
<!-- Configuración de Flatpickr -->
  document.addEventListener("DOMContentLoaded", function () {
    flatpickr("#horaEjecucion", {
      enableTime: true,
      noCalendar: true,
      dateFormat: "H:i",
      time_24hr: true
    });
  });

	    document.addEventListener("DOMContentLoaded", function () {
	        flatpickr("#fechaDesde", {
	            dateFormat: "d/m/Y", // Formato de fecha
	            locale: "es",
	            allowInput: true // Permite escribir manualmente
	        });

	        flatpickr("#fechaHasta", {
	            dateFormat: "d/m/Y", // Formato de fecha
	            locale: "es",
	            allowInput: true // Permite escribir manualmente
	        });
	    });
	</script>

<bean:define id="formulari" name="ProcesosForm" type="lopicost.spd.struts.form.ProcesosForm" />


<script type="text/javascript">	
	
		function volver()
		{
			var f = document.ProcesosForm;
			f.parameter.value='list';
			f.ACTIONTODO.value='ADMIN';
			f.submit();
		}	
		
		function nuevo()
		{
			var f = document.ProcesosForm;
		
			if(f.nombreProceso.value=='')
				alert('Falta indicar el nombre del proceso');
			else if(f.lanzadera.value=='')
				alert('Falta indicar la lanzadera del proceso ');
			else if(f.descripcion.value=='')
				alert('Falta indicar algo de descripcion sobre el proceso ');
			else if(f.fechaDesde.value=='')
				alert('Falta indicar la fecha de activación del proceso');
			//else if(f.fechaDesde.value=='')
				//alert('Falta indicar la fecha de desactivación del Proceso ');
			else if(f.horaEjecucion.value=='')
				alert('Falta indicar la hora de ejecutó del proceso ');
			else
			{
				//f.nombreCorto.value=f.nombreCorto.value;
				f.parameter.value='nuevo';
				f.ACTIONTODO.value='NUEVO_OK';

				f.submit();
			}
		}
				
		</script>

<body id="general">


	<div id="contingut">
		<center>
		<h2>Nuevo proceso</h2>
		<html:form action="/Procesos.do" method="post">	

		 <html:hidden property="oidProceso" />
		 <html:hidden property="parameter" value="nuevo"/>
		 <html:hidden property="ACTIONTODO" value="NUEVO"/>

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

		
		<table class="detalle">

		<tr><th class="primera">Nombre del Proceso</th><td><html:text property="nombreProceso"/></td></tr>
		<tr><th class="primera">Lanzadera</th><td><html:text property="lanzadera"/></td></tr>
		<tr><th class="segunda">Descripción</th><td><html:textarea property="descripcion" cols="40" rows="3"/></td></tr>

		<tr>
			<th class="segunda">Activo</th>
			<td>
				<select name="activo" style="text-align: left;">>
				    <option value="SI" ${dat.activo}='SI' ? 'selected="selected"' : ''}>SÍ</option>
				    <option value="No" ${dat.activo}='NO' ? 'selected="selected"' : ''}>No</option>
				</select>
			</td>
		</tr>
		<tr><th class="segunda">Parámetros</th><td><html:textarea property="parametros" cols="40" rows="3"/></td></tr>
		<tr><th class="segunda">Tipo de ejecutó</th>
		<td>
			<select name="tipoEjecucion" style="text-align: left;">
			    <option value="AUTOMATICO" ${dat.tipoEjecucion}='AUTOMATICO' ? 'selected="selected"' : ''}>Automático</option>
			    <option value="MANUAL" ${dat.tipoEjecucion}='MANUAL' ? 'selected="selected"' : ''}>Manual</option>
			</select>
		</td>
		</tr>
		<!-- %= java.util.Arrays.toString(formulari.getDiasSemanaArray()) %> -->

		<tr><th class="segunda">Frecuencia</th>
		<td>
			<select name="frecuencia" style="text-align: left;">>
			    <option value="DIARIA" ${dat.frecuencia}='DIARIA' ? 'selected="selected"' : ''}>Diaria</option>
			    <option value="SEMANAL" ${dat.frecuencia}='SEMANAL' ? 'selected="selected"' : ''}>Semanal</option>
			    <option value="MENSUAL" ${dat.frecuencia}='MENSUAL' ? 'selected="selected"' : ''}>Mensual</option>
			</select>
		</td>
		
		</tr>
		<tr>
    <th class="segunda">Días de la Semana</th>
    <td>
		<html:multibox property="diasSemanaArray" value="Lunes"/> Lunes<br/>
		<html:multibox property="diasSemanaArray" value="Martes"/> Martes<br/>
		<html:multibox property="diasSemanaArray" value="Miercoles"/> miércoles<br/>
		<html:multibox property="diasSemanaArray" value="Jueves"/> Jueves<br/>
		<html:multibox property="diasSemanaArray" value="Viernes"/> Viernes<br/>
		<html:multibox property="diasSemanaArray" value="Sabado"/> Sábado<br/>
		<html:multibox property="diasSemanaArray" value="Domingo"/> Domingo<br/>
    </td>
</tr>


		<tr><th class="segunda">Hora de ejecutó</th>
			<td><input type="text" id="horaEjecucion" name="horaEjecucion" placeholder="HH:mm"></td>
			
		</tr>
		<tr><th class="segunda">máx. Reintentos</th>
			<td>
				<input type="number" name="maxReintentos" id="maxReintentos" value="<bean:write name='formulari' property='maxReintentos'/>" />
			</td>
		</tr>
		<tr><th class="segunda">máx. Duración (segundos)</th>
			<td>
				<input type="number" name="maxDuracionSegundos" id="maxDuracionSegundos" value="<bean:write name='formulari' property='maxDuracionSegundos'/>" />
			</td>
		</tr>

		<tr>
			<th class="segunda">Fecha desde</th><td><input type="text" id="fechaDesde" name="fechaDesde" value="${fechaDesde}" placeholder="Selecciona fecha de activación"></td>
		</tr>
		<tr>
			<th class="segunda">Fecha hasta</th><td><input type="text" id="fechaHasta" name="fechaHasta" value="${fechaHasta}" placeholder="Selecciona fecha de desactivación"></td>
		</tr>

		<tr>
			<th class="segunda">Tipo</th>
			<td>
				<select name="tipoEjecucion">
				    <option value="AUTOMATICO" >Automático</option>
				    <option value="MANUAL">Manual</option>
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