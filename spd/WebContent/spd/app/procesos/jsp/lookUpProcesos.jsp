<%@ page language="java" %>
<%@ page import="java.util.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">


   <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<jsp:include page="/spd/jsp/global/head.jsp"/>
<head>
<title>Selección de proceso</title>
   
</head>
<bean:define id="formulari" name="ProcesosForm" type="lopicost.spd.struts.form.ProcesosForm" />
<%
String fieldName1="";
if (request.getParameter("fieldName1")!=null)
	fieldName1 = (String) request.getParameter("fieldName1");
%>


	<script type="text/javascript">	
		function updateList()
		{
			document.forms[0].submit();
		}
		

		function buscaElemento(oidProceso, lanzadera) {

			if(opener.document.forms[0].oidProceso) 
				opener.document.forms[0].oidProceso.value=oidProceso;
			if(opener.document.forms[0].lanzadera) 
				opener.document.forms[0].lanzadera.value=lanzadera;
			
<%
		if(request.getParameter("CallBack")!=null && !request.getParameter("CallBack").equals("")&& !request.getParameter("CallBack").equals("null"))
		{
%>	

				opener.<%=request.getParameter("CallBack")%>(id, name);
<%
		}
%>
			window.close();
		}		
		
	</script>
<body id="lookUpProcesos" class="popup">
<html:errors />
	<div id="caixa">
		<h2>Medicamentos</h2>
		<html:form action="/Procesos.do"  method="post" styleId="${formulari}">
		<html:hidden property="fieldName1"/>
		<html:hidden property="parameter" value="lookUp"/>
		<input type="hidden" name="CallBack" 		value="<%=request.getParameter("CallBack")%>">
		<input type="hidden" name="CallBackID" 		value="<%=request.getParameter("CallBackID")%>">
		<input type="hidden" name="CallBackNAME" 	value="<%=request.getParameter("CallBackNAME")%>">
        
	<div id="contingut">
		<fieldset>
		<div class="botons">
			<p><input type="button" onclick="window.close()" value="Volver"/></p>
		</div>					

		<table style="width:80%">
			<tr>
				<th class="segunda">Fecha creación</th>
		        <th>Lanzadera</th>
		        <th>Nombre</th>
		        <th>Usuario creación</th>
		        <th>Versión</th>
		        <th>Descripci�n</th>
		        <th>Parámetros</th>
		        <th>Tipo ejecución</th>
		        <th>Cada</th>
		        <th>Periodo</th>
		        <th>Días semana</th>
		        <th>Días concretos (opcional)</th>
		        <th>Hora ejecución</th>
		        <th>Max reintentos</th>
		        <th>Max duración (s)</th>
		        <th>Fecha desde</th>
		        <th>Fecha hasta</th>
		        <th>Activo</th>
			</tr>
			<logic:iterate id="data" name="formulari" property="procesos" type="lopicost.spd.model.Proceso" indexId="position">
			<tr>
		        <td><bean:write name="data" property="fechaCreacion" /></td>
		        <td>
		        	<a href="#" onclick="javascript:buscaElemento('<bean:write name="data" property="oidProceso"/>','<bean:write name="data" property="lanzadera"/>');">
		        	<bean:write name="data" property="lanzadera" />
		        </td>
		        <td><bean:write name="data" property="nombreProceso" /></td>
				<td><bean:write name="data" property="usuarioCreacion" /></td>
				<td><bean:write name="data" property="version" /></td>
		        <td><bean:write name="data" property="descripcion" /></td>
		        <td><bean:write name="data" property="parametros" /></td>
		        <td><bean:write name="data" property="tipoEjecucion" /></td>
		        <td><bean:write name="data" property="frecuenciaPeriodo" /></td>
		        <td><bean:write name="data" property="tipoPeriodo" /></td>
		        <td><bean:write name="data" property="diasSemana" /></td>
		        <td><bean:write name="data" property="diasMes" /></td>
		        <td><bean:write name="data" property="horaEjecucion" /></td>
		        <td><bean:write name="data" property="maxReintentos" /></td>
		        <td><bean:write name="data" property="maxDuracionSegundos" /></td>
		        <td><bean:write name="data" property="fechaDesde" /></td>
		        <td><bean:write name="data" property="fechaHasta" /></td>
		        <td><bean:write name="data" property="activo" /></td>
				<td>

				</td>
	        </tr>
			</logic:iterate>
		 </table>	
		</fieldset>
	</div>		
	</html:form>		
	</div>		
	
		
		
		
			

</body>
</html>