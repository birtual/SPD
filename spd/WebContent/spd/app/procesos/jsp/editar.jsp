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
	<title>Edición del proceso</title>
</head>

<script>
function actualizarColorPrioridad(select) {
  select.classList.remove('opcion-alta', 'opcion-media', 'opcion-baja');

  switch (select.value) {
    case '1':
      select.classList.add('opcion-alta');
      break;
    case '2':
      select.classList.add('opcion-media');
      break;
    case '3':
      select.classList.add('opcion-baja');
      break;
  }
}

// Aplicar color inicial al cargar
window.addEventListener('DOMContentLoaded', function() {
  const sel = document.getElementById('prioridad');
  if (sel) actualizarColorPrioridad(sel);
});

<!-- Configuración de Flatpickr -->
window.onload = function() {
	document.getElementById("fechaDesde").placeholder = "Selecciona fecha de activación";
    document.getElementById("horaEjecucion").setAttribute("placeholder", "HH:mm");
};


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
<bean:define id="data" name="formulari" property="proceso"/>

<script type="text/javascript">	
	
		function volver()
		{
			var f = document.ProcesosForm;
			f.parameter.value='list';
			f.ACTIONTODO.value='ADMIN';
			f.submit();
		}	
		
		function grabar()
		{
			var f = document.ProcesosForm;
			/*if(f.nombreProceso.value=='')
				alert('Falta indicar el nombre del proceso');
			else if(f.descripcion.value=='')
				alert('Falta indicar algo de descripcion sobre el proceso ');
			else if(f.fechaDesde.value=='')
				alert('Falta indicar la fecha de activación del proceso');
			else if(f.horaEjecucion.value=='')
				alert('Falta indicar la hora de ejecución del proceso ');
			else if(f.maxDuracionSegundos.value=='')
				alert('Falta indicar el tope de duración posible ');
			else */
			if (f.activo.value == 'DESCARTADO') {
			    if (confirm('En caso de descartar el proceso no aparecerá en el listado. ¿Está seguro?')) {
			        f.parameter.value = 'editar';
			        f.ACTIONTODO.value = 'EDITA_OK';
			        f.submit();
			    } else {
			        // Cancelado por el usuario, no hacer nada
			    }
			} else {
			    f.parameter.value = 'editar';
			    f.ACTIONTODO.value = 'EDITA_OK';
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
			<u>Mensaje:</u>		
			<font color="red">
			
				<logic:iterate id="error" name="formulari" property="errors" type="java.lang.String">
						<li><bean:write name="error"/></li>
				</logic:iterate>
		</font>
		</ul>
	</logic:notEmpty>

		
		<table class="detalle">* campos obligatorios
			
		<tr><th class="primera">Prioridad *</th>
		<td>
			<select id="prioridad" name="prioridad" style="width: 30em;"  onchange="actualizarColorPrioridad(this)">
			  <option value="1" <c:if test="${data.prioridad == 1}">selected</c:if>>Alta</option>
			  <option value="2" <c:if test="${data.prioridad == 2}">selected</c:if>>Media</option>
			  <option value="3" <c:if test="${data.prioridad == 3}">selected</c:if>>Baja</option>
			</select>
		</td>
		</tr>
		<tr><th class="primera">Nombre del Proceso  *</th><td><html:text name="data" style="width: 30em;"  property="nombreProceso"/></td></tr>
		<tr><th class="primera">Lanzadera  * (versión <bean:write name="data"  property="version"/>)</th><td><html:text name="data"  style="width: 30em;"  property="lanzadera"/></td></tr>
		<tr><th class="segunda">Descripción  *</th><td><html:textarea name="data"  property="descripcion" cols="40" rows="3"/></td></tr>
		<tr><th class="primera">Apartado</th><td><html:text name="data" style="width: 20em;"  property="apartado"/></td></tr>

		<tr>
			<th class="segunda">Activo</th>
				<td>
      			 <html:select  name="data" property="activo" style="text-align: left;">>
		            <html:option value="ACTIVO">ACTIVO</html:option>
		            <html:option value="INACTIVO">INACTIVO</html:option>
		            <html:option value="DESCARTADO">DESCARTADO</html:option>
        		</html:select>
			</td>
		</tr>
		<tr><th class="segunda">Parámetros <br>(separados por # o  |)</th><td><html:textarea name="data" property="parametros" cols="40" rows="3"/></td></tr>
		<!-- %= java.util.Arrays.toString(formulari.getDiasSemanaArray()) %> -->

		<tr><th class="segunda">Periodo  *</th>
		<td>
			Cada
			<input type="number" style="width: 3em;"  name="frecuenciaPeriodo" id="frecuenciaPeriodo" value="<bean:write name='data' property='frecuenciaPeriodo'/>" />
			<html:select  name="data" property="tipoPeriodo" style="text-align: left;">>
				<html:option value="MINUTOS">Minutos</html:option>
				<html:option value="HORAS">Horas</html:option>
				<html:option value="DIAS">Días</html:option>
	            <html:option value="SEMANAS">Semanas</html:option>
	            <html:option value="MESES">Meses</html:option>
			</html:select>
		</td>
		
		</tr>
		<tr>
		    <th class="segunda">Días de la Semana  *</th>
		    <td>
				<html:multibox property="diasSemanaArray" name="data" value="1"/>Lunes<br/>
				<html:multibox property="diasSemanaArray" name="data" value="2"/>Martes<br/>
				<html:multibox property="diasSemanaArray" name="data" value="3"/>Miércoles<br/>
				<html:multibox property="diasSemanaArray" name="data" value="4"/>Jueves<br/>
				<html:multibox property="diasSemanaArray" name="data" value="5"/>Viernes<br/>
				<html:multibox property="diasSemanaArray" name="data" value="6"/>Sábado<br/>
				<html:multibox property="diasSemanaArray" name="data" value="7"/>Domingo<br/>
		    </td>
		</tr>
		<tr>
		    <th class="segunda">Días concretos </th>
    		<td><html:text name="data"  property="diasMes"/><br><small>(usar coma para separar días o guión para intervalos)</small></td>
		</tr>


		<tr><th class="segunda">Hora de Ejecución  *</th>
			<td>
				<html:text property="horaEjecucion" name="data" styleId="horaEjecucion" style="width: 80px;" />
			</td>
		</tr>
		<tr><th class="segunda">Máx. Reintentos  *</th>
			<td>
				<input type="number" name="maxReintentos" style="width: 2em;"  id="maxReintentos" value="<bean:write name='data' property='maxReintentos'/>" />
			</td>
		</tr>
		<tr><th class="segunda">Máx. Duración (segundos)  *</th>
			<td>
				<input type="number" name="maxDuracionSegundos" style="width: 4em;"   id="maxDuracionSegundos" value="<bean:write name='data' property='maxDuracionSegundos'/>" />
			</td>
		</tr>

		<tr>
			<th class="segunda">Fecha desde  *</th>
			<td>
				<input type="text" id="fechaDesde" name="fechaDesde" style="width: 8em;" value="<bean:write name='data' property='fechaDesde'/>" placeholder="Selecciona fecha de activación" class="flatpickr">
						</td>
		</tr>
		<tr>
			<th class="segunda">Fecha hasta</th>
			<td>
				<input type="text" id="fechaHasta" name="fechaHasta" style="width: 8em;" value="<bean:write name='data' property='fechaHasta'/>" placeholder="Selecciona fecha de desactivación" class="flatpickr">
			</td>
		</tr>


		<tr><th class="segunda">Tipo de Ejecución</th>
			<td>
				 <html:select  name="data" property="tipoEjecucion" style="text-align: left;">>
			        <html:option value="AUTOMATICO">Automático</html:option>
			    	<html:option value="MANUAL">Manual</html:option>
	        	</html:select>
			</td>
		</tr>
		<tr><th class="segunda">Orden</th>
			<td>
				<input type="number" name="orden" style="width: 4em;" id="orden" value="<bean:write name='data' property='orden'/>" />
			</td>
		</tr>
		<tr><th class="primera">Nombre original</th><td><html:text style="width: 30em;"  name="data"  property="nombreOriginal"/></td></tr>
		<tr>
			<td>	
				<p class="botons">
				<input type="button" onclick="javascript:volver()" value="Volver"/>
				<input type="button" onclick="javascript:grabar()" value="Grabar"/>
				</p>	
			</td>	
		</tr>	
	</table>

	</html:form>
	</center>
	</div>	



</body>
</html>