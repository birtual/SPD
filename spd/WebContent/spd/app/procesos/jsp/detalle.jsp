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
	<title>Detalle del proceso</title>
</head>




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
				
</script>

<body id="general">


	<div id="contingut">
		<h2>Nuevo proceso</h2>
		<html:form action="/Procesos.do" method="post">	

		 <html:hidden property="oidProceso" />
		 <html:hidden property="parameter" value="detalle"/>

		
		<table class="detalle">
			<tr><th class="primera">Prioridad *</th>
			<c:set var="clasePrioridad" value="" />
			  <c:choose>
			    <c:when test="${data.prioridad == '1'}">
			      <c:set var="clasePrioridad" value="prio-alta" />
			    </c:when>
			    <c:when test="${data.prioridad == '2'}">
			      <c:set var="clasePrioridad" value="prio-media" />
			    </c:when>
			    <c:when test="${data.prioridad == '3'}">
			      <c:set var="clasePrioridad" value="prio-baja" />
			    </c:when>
			  </c:choose>
        <td class="${clasePrioridad}">
		  <c:choose>
		    <c:when test="${data.prioridad == 1}">Alta</c:when>
		    <c:when test="${data.prioridad == 2}">Media</c:when>
		    <c:when test="${data.prioridad == 3}">Baja</c:when>
		    <c:otherwise>Desconocida</c:otherwise>
		  </c:choose>
		</td>
			</tr>			
			<tr><th class="primera">Nombre del Proceso</th><td><bean:write name="data"  property="nombreProceso"/></td></tr>
			<tr><th class="primera">Lanzadera (versión <bean:write name="data"  property="version"/>)</th><td><bean:write name="data"  property="lanzadera"/></td></tr>
			<tr><th class="segunda">Descripción</th><td><bean:write name="data"  property="descripcion"/></td></tr>
			<tr><th class="primera">Apartado</th><td><bean:write name="data"  property="apartado"/></td></tr>
			<tr><th class="segunda">Activo</th><td><bean:write name="data"  property="activo"/></td></tr>
			<tr><th class="segunda">Parámetros</th><td><bean:write name="data" property="parametros"/></td></tr>
			<tr><th class="segunda">Periodo</th><td>
				Cada
				<bean:write  name="data" property="frecuenciaPeriodo" />
				<bean:write  name="data" property="tipoPeriodo" />
				</td>
			</tr>
			<tr><th class="segunda">Días de la Semana</th><td><bean:write  name="data" property="diasSemana" /></td></tr>
			<tr><th class="segunda">Días concretos</th><td><bean:write  name="data" property="diasMes" /></td></tr>
	 		<tr><th class="segunda">Hora de Ejecución</th><td><bean:write  name="data" property="horaEjecucion" /></td></tr>
			<tr><th class="segunda">Máx. Reintentos</th><td><bean:write  name="data" property="maxReintentos" /></td></tr>
			<tr><th class="segunda">Máx. Duración (segundos)</th><td><bean:write  name="data" property="maxDuracionSegundos" /></td></tr>
			<tr><th class="segunda">Fecha desde</th><td><bean:write  name="data" property="fechaDesde" /></td></tr>
			<tr><th class="segunda">Fecha hasta</th><td><bean:write  name="data" property="fechaHasta" /></td></tr>
			<tr><th class="segunda">Tipo de Ejecución</th><td><bean:write  name="data" property="tipoEjecucion" /></td></tr>
			<tr><th class="segunda">Orden</th><td><bean:write  name="data" property="orden" /></td></tr>
			<c:if test="${not empty data.nombreOriginal}">
				<tr><td colspan=2 align="center">(Nombre original: <bean:write name="data"  property="nombreOriginal"/>)</td></tr>
			</c:if>


	
			
		</table>

	
				<p class="botons">
				<input type="button" onclick="javascript:window.close()" value="Cerrar"/>
				</p>	


	</html:form>
	</center>	
	</div>	


</body>
</html>