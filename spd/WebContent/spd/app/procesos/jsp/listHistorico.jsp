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

 
<title>




</title>
<jsp:include page="/spd/jsp/global/head.jsp"/>



</HEAD>
<bean:define id="formulari" name="ProcesosForm" type="lopicost.spd.struts.form.ProcesosForm" />

<script type="text/javascript">	



function borrar(oidProceso) {
    // Mostrar un cuadro de confirmaci�n
    var confirmacion = window.confirm("�Est�s seguro de que deseas borrar este proceso?");
    
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
	f.ACTIONTODO.value='EDITA';
	f.oidAviso.value= oidProceso;

	f.submit();
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
	</p>
	
	
<table style="width:50%">
	<tr>
		<th class="segunda">Fecha creación</th>
        <th>OID Proceso</th>
        <th>Nombre</th>
        <th>Lanzadera</th>
        <th>Descripción</th>
        <th>Estado</th>
        <th>Parámetros</th>
        <th>Tipo ejecución</th>
        <th>Veces</th>
        <th>Periodo</th>
        <th>Días semana</th>
        <th>Hora ejecución</th>
        <th>Max reintentos</th>
        <th>Max duración (s)</th>
        <th>Fecha desde</th>
        <th>Fecha hasta</th>
        <th>inicio ejecución</th>
        <th>fin ejecución</th>  
        <th>Último duración (s)</th>
        <th>Último resultado</th>
        <th>Último usuario ejecución</th>
        <th>Último mensaje</th>
        <th>Último tipo error</th>
        <th>Último código resultado</th>
        <th>Último error</th>
	</tr>
	<logic:iterate id="data" name="formulari" property="procesos" type="lopicost.spd.model.Proceso" indexId="position">
	<tr>
		<td><bean:write name="data" property="oidProceso" /></td>
        <td><bean:write name="data" property="fechaCreacion" /></td>
        <td><bean:write name="data" property="nombreProceso" /></td>
        <td><bean:write name="data" property="lanzadera" /></td>
        <td><bean:write name="data" property="descripcion" /></td>
        <td><bean:write name="data" property="estado" /></td>
        <td><bean:write name="data" property="parametros" /></td>
        <td><bean:write name="data" property="tipoEjecucion" /></td>
        <td><bean:write name="data" property="frecuenciaPeriodo" /></td>
        <td><bean:write name="data" property="tipoPeriodo" /></td>
        <td><bean:write name="data" property="diasSemana" /></td>
        <td><bean:write name="data" property="horaEjecucion" /></td>
        <td><bean:write name="data" property="maxReintentos" /></td>
        <td><bean:write name="data" property="maxDuracionSegundos" /></td>
        <td><bean:write name="data" property="fechaDesde" /></td>
        <td><bean:write name="data" property="fechaHasta" /></td>
        <td><bean:write name="data" property="fechaInicioEjecucion" /></td>
        <td><bean:write name="data" property="fechaFinEjecucion" /></td>
        <td><bean:write name="data" property="duracionSegundos" /></td>
        <td><bean:write name="data" property="resultado" /></td>
        <td><bean:write name="data" property="usuarioEjecucion" /></td>
        <td><bean:write name="data" property="mensaje" /></td>
        <td><bean:write name="data" property="tipoError" /></td>
        <td><bean:write name="data" property="codigoResultado" /></td>
        <td><bean:write name="data" property="error" /></td>
		
		
			<td>
				<p class="botons">
						<input type="button" onclick="javascript:editar('<bean:write name="data" property="oidProceso" />');"  value="Editar"  />
				</p>
				<p class="botons">
				<input type="button" onclick="javascript:borrar('<bean:write name="data" property="oidProceso" />');"  value="Borrar"  />
				</p>				
			</td>
        </tr>
	</tr>	
	</logic:iterate>


 </table>
 
</html:form>
</html:html>
