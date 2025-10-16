<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/spd/jsp/global/headFragmento.jspf" %>

<!DOCTYPE html>
<html:html>

</HEAD>
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
		/*
			if(f.nombreProceso.value=='')
				alert('Falta indicar el nombre del proceso');
			else if(f.descripcion.value=='')
				alert('Falta indicar algo de descripcion sobre el proceso ');
			else if(f.fechaDesde.value=='')
				alert('Falta indicar la fecha de activación del proceso');
			//else if(f.fechaDesde.value=='')
				//alert('Falta indicar la fecha de desactivación del Proceso ');
			else if(f.horaEjecucion.value=='')
				alert('Falta indicar la hora de ejecutó del proceso ');
			else */
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
			* campos obligatorios

		<tr><th class="segunda">Prioridad *</th>
			<td>
				<select id="prioridad" name="prioridad" style="width: 30em;"  onchange="actualizarColorPrioridad(this)">
				  <option value="1" <c:if test="${formulari.prioridad == 1}">selected</c:if>>Alta</option>
				  <option value="2" <c:if test="${formulari.prioridad == 2}">selected</c:if>>Media</option>
				  <option value="3" <c:if test="${formulari.prioridad == 3}">selected</c:if>>Baja</option>
				</select>
			</td>
		</tr>
		<tr><th class="primera">Nombre del Proceso *</th><td><html:text property="nombreProceso"/></td></tr>
		<tr><th class="primera">Nombre original</th><td><html:text property="nombreOriginal"/></td></tr>
		<tr><th class="primera">Lanzadera *</th><td><html:text property="lanzadera"/></td></tr>
		<tr><th class="segunda">Descripción *</th><td><html:textarea property="descripcion" cols="40" rows="3"/></td></tr>
		<tr><th class="segunda">Apartado</th><td><html:textarea property="apartado" cols="40" rows="3"/></td></tr>

		<tr>
			<th class="segunda">Activo</th>
			<td>
      			 <html:select property="activo" style="text-align: left;">>
		            <html:option value="ACTIVO">ACTIVO</html:option>
		            <html:option value="INACTIVO">INACTIVO</html:option>
        		</html:select>
			</td>
		</tr>
		<tr><th class="segunda">Parámetros</th><td><html:textarea property="parametros" cols="40" rows="3"/></td></tr>
		<!-- %= java.util.Arrays.toString(formulari.getDiasSemanaArray()) %> -->

		<tr><th class="segunda">Periodo  *</th>
		<td>
			Cada
			<input type="number" style="width: 7em;"  name="frecuenciaPeriodo" id="frecuenciaPeriodo" value="<bean:write name='formulari' property='frecuenciaPeriodo'/>" />
			<html:select property="tipoPeriodo" name="formulari" style="text-align: left;">
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
			<html:multibox property="diasSemanaArray" value="1"/> Lunes<br/>
			<html:multibox property="diasSemanaArray" value="2"/> Martes<br/>
			<html:multibox property="diasSemanaArray" value="3"/> Miércoles<br/>
			<html:multibox property="diasSemanaArray" value="4"/> Jueves<br/>
			<html:multibox property="diasSemanaArray" value="5"/> Viernes<br/>
			<html:multibox property="diasSemanaArray" value="6"/> Sábado<br/>
			<html:multibox property="diasSemanaArray" value="7"/> Domingo<br/>
	    </td>
	</tr>
	<tr>
        <th class="segunda">Días del mes</th>
        <td><html:text property="diasMes"/><br><small>(usar coma para separar días o guión para intervalos)</small></td>
	</tr>


		<tr><th class="segunda">Hora de ejecutó *</th>
			<td><input type="text" id="horaEjecucion" name="horaEjecucion" value="${formulari.horaEjecucion} placeholder="HH:mm" style="width: 5em;"></td>
			
		</tr>
		<tr><th class="segunda">Máx. Reintentos *</th>
			<td>
				<input type="number" name="maxReintentos" id="maxReintentos" style="width: 3em;" value="<bean:write name='formulari' property='maxReintentos'/>" />
			</td>
		</tr>
		<tr><th class="segunda">Máx. Duración (segundos) *</th>
			<td>
				<input type="number" name="maxDuracionSegundos" id="maxDuracionSegundos" style="width: 5em;" value="<bean:write name='formulari' property='maxDuracionSegundos'/>" />
			</td>
		</tr>

		<tr>
			<th class="segunda">Fecha desde *</th><td><input type="text" id="fechaDesde" name="fechaDesde" value="${formulari.fechaDesde}" placeholder="Selecciona fecha de activación"></td>
		</tr>
		<tr>
			<th class="segunda">Fecha hasta</th><td><input type="text" id="fechaHasta" name="fechaHasta" value="${formulari.fechaHasta}" placeholder="Selecciona fecha de desactivación"></td>
		</tr>
		<tr><th class="segunda">Tipo de ejecutó</th>
		<td>
			<select name="tipoEjecucion" style="text-align: left;">
			    <option value="AUTOMATICO" ${formulari.tipoEjecucion}='AUTOMATICO' ? 'selected="selected"' : ''}>Automático</option>
			    <option value="MANUAL" ${formulari.tipoEjecucion}='MANUAL' ? 'selected="selected"' : ''}>Manual</option>
			</select>
		</td>
		</tr>
		<tr><th class="segunda">Orden</th>
			<td>
				<input type="number" name="orden" id="orden" style="width: 3em;" value="<bean:write name='formulari' property='orden'/>" />
			</td>
		</tr>


		<tr>
			<td>	
				<p class="botons">
				<input type="button" onclick="javascript:volver()" value="Volver"/>
				<input type="button" onclick="javascript:nuevo()" value="Nuevo"/>
				</p>	
			</td>	
		</tr>	
	</table>

	</html:form>
	</div>	


</body>
</html:html>