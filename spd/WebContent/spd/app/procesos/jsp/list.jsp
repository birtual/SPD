<%@ page language="java" %>
<%@ page import="java.util.*" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	


<%@ page session="true" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html:html>
<HEAD>
	<meta http-equiv="refresh" content="10; URL=Procesos.do?parameter=list">
	<title>Listado de Procesos</title>
	<jsp:include page="/spd/jsp/global/head.jsp"/>
</HEAD>
<bean:define id="formulari" name="ProcesosForm" type="lopicost.spd.struts.form.ProcesosForm" />

<script type="text/javascript">	

function lanzar(oidProceso) {
    // Mostrar un cuadro de confirmación
    var confirmacion = window.confirm("¿Estás seguro de que deseas lanzar este proceso?");
    
    // Si el usuario hace clic en "Aceptar" (true)
    if (confirmacion) {
        var f = document.ProcesosForm;
        f.parameter.value = 'lanzar';
        f.ACTIONTODO.value = 'LANZAR';
        f.oidProceso.value = oidProceso;
        f.submit(); // Enviar el formulario
    } else {
        // Si el usuario hace clic en "Cancelar", no hacer nada
        return false;
    }
}



function borrar(oidProceso) {
    // Mostrar un cuadro de confirmación
    var confirmacion = window.confirm("¿Estás seguro de que deseas borrar este proceso?");
    
    // Si el usuario hace clic en "Aceptar" (true)
    if (confirmacion) {
        var f = document.ProcesosForm;
        f.parameter.value = 'borrar';
        f.ACTIONTODO.value = 'CONFIRMAR';
        f.oidProceso.value = oidProceso;
        f.submit(); // Enviar el formulario
    } else {
        // Si el usuario hace clic en "Cancelar", no hacer nada
        return false;
    }
}

function editar(oidProceso)
{
	var f = document.ProcesosForm;
	f.parameter.value='editar';
	f.ACTIONTODO.value='EDITAR';
	f.oidProceso.value= oidProceso;
	f.submit();
}



function ejecuciones(oidProceso)
{
	var url = '/spd/Procesos.do?parameter=listadoEjecuciones&oidProceso=' + oidProceso;
	window.open(url, 'historico', 'dependent=yes,height=500,width=800,top=50,left=0,resizable=yes,scrollbars=yes' );
}

function goDetalle(oidProceso)
{
	 var url = '/spd/Procesos.do?parameter=detalle&oidProceso=' + oidProceso;
	 window.open(url, 'detalleProcesos', 'dependent=yes,height=500,width=800,top=50,left=0,resizable=yes,scrollbars=yes' );
}

function buscar()	
{
	var f = document.ProcesosForm;
	f.parameter.value='admin';
	f.submit();	
}

function nuevo()
{
	var f = document.ProcesosForm;
	f.parameter.value='nuevo';
	f.ACTIONTODO.value='NUEVO';
	f.submit();
}	


function goAdminMenu()
{
	
	var f = document.ProcesosForm;
	document.location.href='http://'+window.location.host+'<%=request.getContextPath()%>/Procesos.do?parameter=list';
	return true;
}



function getContextPath() {
	return "<c:out value="${pageContext.request.contextPath}" />";
	}
	


</script>
<body>
<html:form action="/Procesos.do" method="post">
 <html:hidden property="idUsuario" />
 <html:hidden property="parameter" value="list"/>
 <html:hidden property="ACTIONTODO"/>
 <html:hidden property="oidProceso" /> 
 
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


    <p class="botonBuscar">
	
		<input type="button" onclick="javascript:nuevo();"  value="Nuevo"  />  
		<input type="button" class="azulCielo" value="Salir" onclick="javascript:salir();" />
		<input type="button" value="Bloqueos Horarios" onclick="window.open('ProcesosBloqueosHorarios.do?parameter=list');" />
		
		
	</p>
	

<table style="width:80%">
	<tr>
		<!-- th class="segunda">Fecha creación</th> -->
        <th>Orden</th>
        <th>Lanzadera</th>
        <th>Nombre</th>
        <!-- <th>Usuario creación</th> -->
        <!-- <th>Versión</th> -->
        <!-- <th>Descripción</th> -->
        <th>Parámetros</th>
        <!--<th>Tipo ejecutó</th> -->
        <!--<th>Cada</th> -->
        <!--<th>Periodo</th> -->
        <th>Frecuencia</th>
        <th>Hora ejecutó</th>
        <th>Días semana</th>
        <th>Días concretos (opcional)</th>
       <!--  <th>Max reintentos</th> -->
       <!--  <th>Max duración (s)</th> -->
       <!--  <th>Fecha desde</th> -->
       <!--  <th>Fecha hasta</th> -->
        <th>Activo</th>
        <td>Última ejecutó</td>
        <td>Resultado</td>
        <td>Fecha inicio</td>
        <td>Fecha fin</td>
        <td>Mensaje</td>
       

	</tr>
	<c:set var="ultimoApartado" value="" />
	
	<logic:iterate id="data" name="formulari" property="procesos" type="lopicost.spd.model.Proceso" indexId="position">
	<c:if test="${ultimoApartado != (data.apartado != null ? data.apartado : '')}">
		<!-- Fila de separación por cambio de apartado -->
		<tr>
			<td colspan="100" style="background-color:#e0e0e0; font-weight:bold; padding:8px;">
				<c:out value="${data.apartado}" />
			</td>
		</tr>
		<!-- Actualizar valor del último apartado mostrado -->
		<c:set var="ultimoApartado" value="${data.apartado}" />
	</c:if>
	
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

  <tr>


        <td><bean:write name="data" property="orden" /></td>
        <!-- td><bean:write name="data" property="fechaCreacion" /></td> -->
        <td class="${clasePrioridad}"><bean:write name="data" property="lanzadera" /></td>
        <td><bean:write name="data" property="nombreProceso" /></td>
		<!-- <td><bean:write name="data" property="usuarioCreacion" /></td> -->
		<!-- <td><bean:write name="data" property="version" /></td> -->
        <!-- <td><bean:write name="data" property="descripcion" /></td> -->
        <td><bean:write name="data" property="parametros" /></td>
        <!--<td><bean:write name="data" property="tipoEjecucion" /></td> -->
        <td>Cada <bean:write name="data" property="frecuenciaPeriodo" /> - <bean:write name="data" property="tipoPeriodo" /></td>
        <td><bean:write name="data" property="horaEjecucion" /></td>
        <!--<td><bean:write name="data" property="diasSemana" /></td> -->
        <td>
	        <c:choose>
			  <c:when test="${data.diasSemana == '1,2,3,4,5,6,7'}">Todos</c:when>				  
			  <c:otherwise><bean:write name="data" property="diasSemana"/></c:otherwise>
			</c:choose>
        </td>
        
        <td><bean:write name="data" property="diasMes" /></td>
        <!-- <td><bean:write name="data" property="maxReintentos" /></td> -->
        <!-- <td><bean:write name="data" property="maxDuracionSegundos" /></td> -->
       <!--  <td><bean:write name="data" property="fechaDesde" /></td> -->
       <!--  <td><bean:write name="data" property="fechaHasta" /></td> -->
        <td><bean:write name="data" property="activo" /></td>
	<logic:notEmpty name="data" property="ultimaEjecucion">	
	<bean:define id="ejec" name="data" property="ultimaEjecucion" />
		<td><bean:write name="ejec" property="estado" /></td>
        <!--td><bean:write name="ejec" property="resultado" /></td>  -->
        
                <c:choose>
				  <c:when test="${ejec.resultado == 'ERROR'}">
				    <td align="center" style="background-color: red; color: white;">
				      <bean:write name="ejec" property="resultado"/>
				    </td>
				  </c:when>
				  <c:when test="${ejec.resultado == 'BLOQUEADO'}">
				    <td align="center" style="background-color: orange; color: whiblackte;">
				      <bean:write name="ejec" property="resultado"/>
				    </td>
				  </c:when>
				  <c:when test="${ejec.resultado == 'CANCELADO'}">
				    <td align="center" style="background-color: orange; color: white;">
				      <bean:write name="ejec" property="resultado"/>
				    </td>
				  </c:when>				  
				  <c:otherwise>
				    <td align="center" style="background-color: green; color: white;">
				      <bean:write name="ejec" property="resultado"/>
				    </td>
				  </c:otherwise>
				</c:choose>

	            
        
        
        <td><bean:write name="ejec" property="fechaInicioEjecucion" /></td>
        <td><bean:write name="ejec" property="fechaFinEjecucion" /></td>
        <td><bean:write name="ejec" property="mensaje" /></td>
	</logic:notEmpty>
	<logic:empty name="data" property="ultimaEjecucion">	
	<td>-</td>
	<td>-</td>
	<td>-</td>
	<td>-</td>
	<td>-</td>
	</logic:empty>
	<td>
		<p class="botons">
			<input type="button" onclick="javascript:goDetalle('<bean:write name="data" property="oidProceso" />');"  value="Detalle"  />
			<input type="button" onclick="javascript:editar('<bean:write name="data" property="oidProceso" />');"  value="Editar"  />
			<input type="button" onclick="javascript:lanzar('<bean:write name="data" property="oidProceso" />');"  value="Lanzar proceso"  />
			<input type="button" onclick="javascript:ejecuciones('<bean:write name="data" property="oidProceso" />');"  value="Ver histórico"  />
		</p>
	</td>
	</tr>	
	</logic:iterate>


 </table>
 
</html:form>
</html:html>
