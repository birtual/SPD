<%@page import="java.text.DecimalFormat"%>
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
	<title>Listado resumen</title>
	<jsp:include page="/spd/jsp/global/head.jsp"/>
</HEAD>

<bean:define id="formulari" name="GenericForm" type="lopicost.spd.struts.form.GenericForm" />
<script type="text/javascript">	
	function abre(loc)
	{
		alert(loc);
		window.open(loc, 'new', 'dependent=yes,height=500,width=800,top=50,left=50,resizable=yes,scrollbars=yes' );
	
	}
	
	function procesadosConDatos(idDivisionResidencia, tipo)
	{
		goNew("procesadosConDatos", idDivisionResidencia, tipo, 600, 800 );
	}	

	function procesadosSinDatos(idDivisionResidencia, tipo)
	{
		goNew("procesadosSinDatos", idDivisionResidencia, tipo, 600, 800 );
	}	

	function noProcesados(idDivisionResidencia, tipo)
	{
		goNew("noProcesados", idDivisionResidencia, tipo, 600, 800 );
	}	

	
	function goNew(parameter, idDivisionResidencia, tipo,  target, width, height ) {
	    var form = document.createElement("form");
	    form.method = "post";
	    form.action = "/spd/ExtRe.do";
	    form.target = target;
	
	    var filtroCheckbox = document.querySelector("input[name='filtroVerDatosPersonales']");
	    var filtroValor = (filtroCheckbox && filtroCheckbox.checked) ? "true" : "false";
	
	    [
	     ["parameter", parameter], 
	     ["idDivisionResidencia", idDivisionResidencia],
	     ["ACTIONTODO", tipo]
	    ]
	    .forEach(function(item) {
	        var input = document.createElement("input");
	        input.type = "hidden";
	        input.name = item[0];
	        input.value = item[1];
	        form.appendChild(input);
	    });
	    if(width=='') width='650';if(height=='') height='700';
	    document.body.appendChild(form);
	    window.open("", target, "dependent=yes,width="+width+",height="+height+",top=50,left=0,resizable=yes,scrollbars=yes");
	    form.submit();
	    document.body.removeChild(form);
	}
	
	
	
	function goInicio()
	{
		document.location.href='/spd/Enlaces.do?parameter=list&ACTIONTODO=INICIO';
	}
</script>

<body>

<html:form action="/ExtRe.do" method="post">
	<html:hidden property="parameter" value="preparaProceso"/>
	<html:hidden property="ACTIONTODO" value="EDITA"/>
	
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
	
	<h2> </h2>

<div id="conMargenSuperior">
<h3>Actualización de datos</h3>
	</br>
 	<table style="width:70%">
		<thead>
		<tr>
			<td COLSPAN="2"></td>
			<td class="verde" COLSPAN="4" align="CENTER">Tratamientos</td>
			<td class="naranja" COLSPAN="4" align="CENTER">Recetas</td>
		</tr>
		<tr>
			<th>Residencia</th>
			<th>Residentes activos (no mutua)</th>
			<th class="verde">CON tratamientos</th>
			<th class="verde">SIN tratamientos</th>
			<th class="verde">NO Recogidos</th>
			<th class="verde">última fecha recogida</th>
			<th class="naranja">CON ventana activa</th>
			<th class="naranja">SIN ventana activa</th>
			<th class="naranja">NO Recogidos</th>
			<th class="naranja">última fecha recogida</th>					
		</tr>
		</thead>
		<logic:iterate id="data" name="formulari" property="listaBeans" type="lopicost.spd.struts.bean.ExtReBean" indexId="position">
			<tr>
				<td style="width: 25%; text-align: left;"><bean:write name="data" property="nombreDivisionResidencia" /></td>
				<td style="width: 10%; text-align: center;"><bean:write name="data" property="cipsActivos" /></td>
				<%
				 String porcenTrat = ""; // Inicializar port como un String vacío
				 String porcenPend = ""; // Inicializar port como un String vacío
				 int tratSinError = 0;
				 int tratConError = 0;
				 int pendSinError = 0;
				 int pendConError = 0;
				try 
				{
					tratSinError = data.getCipsProcesadosTrat()-data.getCipsProcesadosTratNo();
					tratConError = data.getCipsActivos()-data.getCipsProcesadosTrat()+data.getCipsProcesadosTratNo();

					pendSinError = (int)data.getCipsProcesadosRecPend()-(int)data.getCipsProcesadosRecPendNo();
					pendConError = (int)data.getCipsActivos()-(int)data.getCipsProcesadosRecPend()+(int)data.getCipsProcesadosRecPendNo();

				}catch(Exception e){} 
				%>
				<td style="text-align: center;"><a href="javascript:procesadosConDatos('<bean:write name="data" property="idDivisionResidencia" />', 'TRATAMIENTOS')"><bean:write name="data" property="cipsProcesadosTrat" /></a></td>	
				<td style="text-align: center;"><a href="javascript:procesadosSinDatos('<bean:write name="data" property="idDivisionResidencia" />', 'TRATAMIENTOS')"><bean:write name="data" property="cipsProcesadosTratNo" /></a></td>	
				<td style="text-align: center;"><a href="javascript:noProcesados('<bean:write name="data" property="idDivisionResidencia" />', 'TRATAMIENTOS')"><bean:write name="data" property="cipsProcesadosTratError" /></a></td>	
				<td style="text-align: center;"><bean:write name="data" property="fechaUltimoProcesoTrat" /></td>

				<td style="text-align: center;"><a href="javascript:procesadosConDatos('<bean:write name="data" property="idDivisionResidencia" />', 'PENDIENTES')"><bean:write name="data" property="cipsProcesadosRecPend" /></a></td>	
				<td style="text-align: center;"><a href="javascript:procesadosSinDatos('<bean:write name="data" property="idDivisionResidencia" />', 'PENDIENTES')"><bean:write name="data" property="cipsProcesadosRecPendNo" /></a></td>	
				<td style="text-align: center;"><a href="javascript:noProcesados('<bean:write name="data" property="idDivisionResidencia" />', 'PENDIENTES')"><bean:write name="data" property="cipsProcesadosRecPendError" /></a></td>	
				<td style="text-align: center;"><bean:write name="data" property="fechaUltimoProcesoRecPend" /></td>					
			
			</tr>
			</logic:iterate>
		</table>
	</div>	<div>
		<!-- input type="button" class="btn primary" value="Inicio" onclick="javascript:goInicio();" /> -->
		<input type="button" class="azulCielo" value="Salir" onclick="javascript:salir();" />
		
	</div>
</html:form>
</html:html>
