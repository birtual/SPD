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
	<title><bean:message key="ImportData.info.title"/></title>
</head>


	<bean:define id="formulari" name="ImportIOSpdForm" type="lopicost.spd.iospd.importdata.struts.form.ImportDataForm" />

	<script type="text/javascript">	
		//document.write('<link rel="stylesheet" href=<%= "\"" +request.getContextPath() +"/spd/css/gestio.css\""%> media="screen" />');

		function erroresExcel() 
		{
			document.forms[0].operation.value="EXPORT_ERRORES";
			document.forms[0].submit();
		}
		function goGestion() 
		{
			document.location.href='/spd/FicheroResiCabeceraLite.do?parameter=list';
			return true;
		}

		function goInicio()
		{
			document.location.href='/spd/Enlaces.do?parameter=list&ACTIONTODO=INICIO';
		}

		function generateExcelExtra() 
		{
			if (document.forms[0].file.value!=""){
				document.forms[0].operation.value="GENERATE";
				document.forms[0].submit();
			}else alert('Falta añadir fichero');
		}
			
		function generateExcel() 
		{
			var fechaInicioSpd = document.forms[0].fechaInicioSpd.value;
			var fechaFinSpd = document.forms[0].fechaFinSpd.value;
			
			  // Parsear las fechas en formato dd/mm/yyyy
			  const partesFechaInicio = fechaInicioSpd.split('/');
			  const partesFechaFin = fechaFinSpd.split('/');

			  // Crear objetos de fecha
			  const fechaInicio = new Date(partesFechaInicio[2], partesFechaInicio[1] - 1, partesFechaInicio[0]);
			  const fechaFin = new Date(partesFechaFin[2], partesFechaFin[1] - 1, partesFechaFin[0]);

			  // Comparar las fechas
			  if (fechaFin < fechaInicio) {
			    alert('La fecha de fin es menor que la fecha de inicio');
			    return;
			  } 

			if (document.forms[0].idDivisionResidencia.value=="")
			{
				alert('Debe seleccionar residencia');
				return;
			}
			if (document.forms[0].file.value!=""){
				document.forms[0].operation.value="GENERATE";
				document.forms[0].submit();
			}else alert('Falta añadir fichero');
		}
		function refreshPage() 
		{
			document.forms[0].operation.value="THREADING";
			millis =  5000;
			setTimeout('document.forms[0].submit();',millis);
		}
		function refreshDesc() 
		{
			var f = document.ImportIOSpdForm;
			f.parameter.value='list';
			//f.operation.value="FILTER";
			f.action="/spd/Iospd/Iospd.do";
			f.submit();
		}
		function openFile(fitxer) 
		{
			document.forms[0].idThread.value="";
			document.forms[0].operation.value="FILTER";
			document.forms[0].submit();
			window.open(fitxer);
		}	
		
	</script>
</head>
<%
	String idThread = formulari.getIdThread();
	String mensajeEvolucion="";
	
	int processedRows = -1;
	int errorRows = -1;
	int okRows = -1;
	
	if (idThread!=null && idThread.length()>0)
	{
		lopicost.spd.iospd.importdata.process.ImportDataThread thread=(lopicost.spd.iospd.importdata.process.ImportDataThread)session.getAttribute(idThread);
		if(thread!=null)
		{
			processedRows = thread.getFilesProcessades();
			errorRows = thread.getErrors().size();
			okRows = processedRows-errorRows;
		}
			

		if (thread!=null && thread.checkStatus()<2)
		{
			%>
			<body onLoad="refreshPage();">
			<%
			//mensajeEvolucion=lopicost.spd.utils.TextManager.getMensaje("ImportData.info.reg")+" "+formulari.getFilesProcessades();
			mensajeEvolucion="Filas procesadas:  "+formulari.getFilesProcessades();
			//System.out.println("mensajeEvolucion : "+mensajeEvolucion);
		} 
		else if (thread!=null && thread.checkStatus()>=2)
		{
			session.removeAttribute(idThread);
			formulari.setIdThread(null);
			formulari.setOperation("FILTER");
			%>
			<body onLoad="if (opener && opener.list) opener.list()">
			<%
		}
		else
		{ 
			%>
			<body onLoad="if (opener && opener.list) opener.list()">	
			<%
		}
	}
	else
	{ 
		%>
		<body>	
		<%
	}
%>
	<html:form action="/Iospd/ImportFile.do" method="POST" enctype="multipart/form-data">
   		<html:hidden property="operation"/>
   		<html:hidden property="idThread"/>
     	<html:hidden property="parameter"/>
     	<html:hidden property="idUsuario"/>
     	<html:hidden property="cargaExtra"/>
     	<html:hidden property="idProceso"/>
     	<html:hidden property="idDivisionResidencia"/>
 
	<html:hidden property="ACTIONTODO" value="addInProcess"/>
 <fieldset>
	<div id="container">
		<strong>Residencia:</strong>
				<bean:write name="ImportIOSpdForm" property="idDivisionResidencia"/>
				<bean:write name="ImportIOSpdForm" property="idProcessIospd"/>
				<bean:define id="readers" name="ImportIOSpdForm" property="readers"/>
				<html:select property="fileType"  onchange="javascript:refreshDesc()">
					<html:options collection="readers" property="idreader" labelProperty="namereader"/>
				</html:select>
				</br></br>
				
				<div class="clsMenuRow">
					      	Fecha inicio proceso: <bean:write name="ImportIOSpdForm" property="fechaInicioSpd"/>
				</div>				
				</br>
				<div class="clsMenuRow">
					Fecha fin proceso:&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="ImportIOSpdForm" property="fechaFinSpd"/>
				</div>	

				<div class="clsMenuRow">
					      	idProceso: <bean:write name="ImportIOSpdForm" property="idProceso"/>
				</div>	
				
		<logic:notEqual name="ImportIOSpdForm" property="idProcessIospd" value="">
			<div class="clsMenuRow">
 				</br>Fichero  						
					<logic:notEmpty name="ImportIOSpdForm" property="descReader"><bean:write name="ImportIOSpdForm" property="descReader" filter="false"/></logic:notEmpty>
			  			<html:file name="ImportIOSpdForm" property="file"/>
						<br><br>
						
		<logic:notEqual name="ImportIOSpdForm" property="operation" value="THREADING">
		 <p class="botons">
			<input type="button" class=".azulCielo" value="Procesar" onclick="javascript:generateExcelExtra();" align="right">
		 </p>				
	
		</logic:notEqual>		

			</div>
			<div>
				<logic:equal name="ImportIOSpdForm" property="operation" value="THREADING">Cargando<b>  <%=mensajeEvolucion%></b></logic:equal>
				<logic:equal name="ImportIOSpdForm" property="end" value="FINALIZED_KO">Finalizado con ERRORES"/></logic:equal>
				<logic:equal name="ImportIOSpdForm" property="end" value="FINALIZED_OK">Finalizado OK"/></logic:equal>
				<logic:equal name="ImportIOSpdForm" property="end" value="FINALIZED_OKKO">Finalizado OKKO"/></logic:equal>
				<p>
					<% if (processedRows > -1) { %>
						Filas procesadas: <b><%=processedRows%></b><br/>
					<% } %>
					<% if (errorRows > -1) { %>
						Errores : <b><font color="red"><%=errorRows%></font></b><br/>
					<% } %>
					<% if (okRows > -1) { %>
						Correctas: <b><%=okRows%> </b><br/>
					<% } %>
				</p>
				<logic:notEmpty name="ImportIOSpdForm" property="errors">
					<%request.setAttribute("listaErrores", formulari.getErrors());  %>
						<font color="red"><ul>
							<u>Descripción avisos"/></u>
								<logic:iterate id="error" name="ImportIOSpdForm" property="errors" type="java.lang.String">
									<li><bean:write name="error"/></li>
								</logic:iterate>
								
								<!--  <input type="button" class="clsButtonStyle" value="Exportar Errores" onclick="javascript:erroresExcel();" align="right">  -->
								</ul></font>
							</logic:notEmpty>
			</div>
		</logic:notEqual>
	</div>
</fieldset>	
	</html:form>
			<!-- p><html:link page="/FicheroResiCabeceraLite.do?parameter=list">Gestión de ficheros de residencia</html:link></p> --> 
				<p class="botons">
				<!--	<input type="button" class="btn primary" value="Inicio" onclick="javascript:goInicio();" /> --> 
					<input type="button" class="azulCielo" value="Salir" onclick="javascript:salir();" />
				</p>
			
	<div>
	</div>		
</body>

</html>
