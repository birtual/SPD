<%@page import="java.text.DecimalFormat"%>
<%@ page language="java" %>
<%@ page import="java.util.*" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page session="true" %>
<%@page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html:html>
<HEAD>


<title>


</title>
<jsp:include page="/spd/jsp/global/head.jsp"/>



</HEAD>
<bean:define id="formulari" name="GenericForm" type="lopicost.spd.struts.form.GenericForm" />


<script type="text/javascript">	
function abre(loc)
{
	alert(loc);
	window.open(loc, 'new', 'dependent=yes,height=500,width=800,top=50,left=50,resizable=yes,scrollbars=yes' );

}

function abreVentana(idDivisionResidencia, tipo)
{
	var loc = '/spd/ExtRe.do?parameter=sinProcesar&ACTIONTODO='+tipo+'&idDivisionResidencia='+ idDivisionResidencia ;				//url de llamanda				
	window.open(loc, 'Consulta', 'dependent=yes,height=600,width=800,top=50,left=50,resizable=yes,scrollbars=yes' );

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
		<th>Residencia</th>
		<th>Residentes activos (no mutua)</th>
		<!-- th class="verde">Cips procesados (trat)</th -->
		<th class="verde">Tratamientos <br>  (recogidos / No recogidos)</th>
		<th class="verde">última fecha recogida</th>
		<!-- th class="naranja">Cips procesados (pend)</th -->
		<th nowrap class="naranja">Ventana activa<br>  (recogidos / No recogidos)</th>
		<th class="naranja">última fecha recogida</th>					
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
					//out.println("| (int)data.getCipsProcesadosTrat(): " + data.getCipsProcesadosTrat());
					//out.println("| data.getCipsProcesadosTratNo(): " + data.getCipsProcesadosTratNo());
					//out.println("| tratSinError: " + tratSinError);
					//out.println("| tratSinError = (int)data.getCipsProcesadosTrat()-(int)data.getCipsProcesadosTratNo(): " + tratSinError);

					tratConError = data.getCipsActivos()-data.getCipsProcesadosTrat()+data.getCipsProcesadosTratNo();
					//out.println("| (int)data.getCipsActivos(): " + (int)data.getCipsActivos());
					//out.println("| (int)data.getCipsProcesadosTrat(): " + (int)data.getCipsProcesadosTrat());
					//out.println("| (int)data.getCipsProcesadosTratNo(): " + (int)data.getCipsProcesadosTratNo());
					//out.println("| tratConError: " + tratConError);
					//out.println("| tratConError = (int)data.getCipsActivos()-(int)data.getCipsProcesadosTrat()+(int)data.getCipsProcesadosTratNo(): " + tratConError);

					pendSinError = (int)data.getCipsProcesadosRecPend()-(int)data.getCipsProcesadosRecPendNo();
					pendConError = (int)data.getCipsActivos()-(int)data.getCipsProcesadosRecPend()+(int)data.getCipsProcesadosRecPendNo();
					//out.println("| pendSinError: " + pendSinError);
					//out.println("| pendConError: " + pendConError);
					
					//float  operacion1 = (float)data.getCipsProcesadosTrat()/(float)data.getCipsActivos();
					//float  operacion2 = (float)data.getCipsProcesadosRecPend()/(float)data.getCipsActivos();
			        // Formatear el resultado como un porcentaje con dos decimales
			        //DecimalFormat df = new DecimalFormat("#.##%");
			        //porcenPend = df.format(operacion1);
			        //porcenTrat = " ("+df.format(operacion1)+")";
					//porcenPend = " ("+df.format(operacion2)+")";
				}catch(Exception e){} 
				%>
				<!-- td style="width: 15%; text-align: center;"><bean:write name="data" property="cipsProcesadosTrat" />  %= porcenTrat %></td -->	
				<td style="width: 15%; text-align: center;"> <%= tratSinError %> / <a href="javascript:abreVentana('<bean:write name="data" property="idDivisionResidencia" />', 'TRATAMIENTOS')"><%= tratConError %></a></td>	
				<td style="width: 10%; text-align: center;"><bean:write name="data" property="fechaUltimoProcesoTrat" /></td>
				<!-- td style="width: 15%; text-align: center;"><bean:write name="data" property="cipsProcesadosRecPend"/>  %= porcenPend %></td -->
				<td nowrap style="width: 15%; text-align: center;"> <%= pendSinError %> / <a href="javascript:abreVentana('<bean:write name="data" property="idDivisionResidencia" />', 'PENDIENTES')"><%= pendConError %></a></td>	
				<td style="width: 10%; text-align: center;"><bean:write name="data" property="fechaUltimoProcesoRecPend" /></td>					
			
			</tr>
			</logic:iterate>
		</table>
	</div>	<div>
		<!-- input type="button" class="btn primary" value="Inicio" onclick="javascript:goInicio();" /> -->
		<input type="button" class="azulCielo" value="Salir" onclick="javascript:salir();" />
		
	</div>
</html:form>
</html:html>
