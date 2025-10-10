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

		function goIndex()
		{
			var f = document.ImportIOSpdForm;
			document.location.href='http://'+window.location.host+'<%=request.getContextPath()%>/';
			return true;
		}	

		function generateExcel(idprocess) 
		{
		//	var msg = '';
		//	var formularios = document.forms;
		//	for (var i=0; i<formularios.length;i++){
		//	                for (var j=0; j<formularios[i].elements.length; j++){
		//
		//	                               msg = msg + '\n\nElemento '+j+ ' del formulario '+(i+1)+ ' tiene id: '+ formularios[i].elements[j].id;
		//	                               msg = msg + ' y name: ' + formularios[i].elements[j].name;
		//	                               msg = msg + ' y value: ' + formularios[i].elements[j].value;
		//	                }
		//	}


			if( (idprocess=="importSustXComposicion" ) && ( document.forms[0].idRobot==null ||  document.forms[0].idRobot.value==''))
			{
				alert('Debe seleccionar robot');
				return;
			}
			if((idprocess=="ImportDatosResiAegerusSPDPrevision" ) && ( document.forms[0].idDivisionResidencia==null ||  document.forms[0].idDivisionResidencia.value==''))
			{
				alert('Debe seleccionar residencia');
				return;
			}

			if (document.forms[0].file.value!=""){
		
				
				//if(document.getElementById("idRobot") !== null&&document.forms[0].idRobot.value=='') {alert('Falta seleccionar robot') ; } 
				//if(document.getElementById("idDivisionResidencia") !== null&&document.forms[0].idDivisionResidencia.value==''){ alert('Falta seleccionar Residencia') ; } 
			
				
				document.forms[0].operation.value="GENERATE";
				document.forms[0].submit();
			}
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
			f.operation.value="FILTER";
			f.action="/spd/Iospd/ImportGestion.do";
			f.submit();
		}
		function getURL(proceso, type) 
		{
			var directory='http://'+window.location.host+'<%=request.getContextPath()%>'+'/tmp/iospd/plantillas/';
			var urlFichero = directory + proceso + type;
			
			var f = document.SustXComposicionForm;
			var loc = '/spd/Iospd/SustXConjExportData.do?parameter=specificPerform';			   	
	
		//Importante que se realice en ventana nueva!!!!
		window.open(loc, 'SustXComposicionExportData', 'dependent=yes,height=500,width=800,top=50,left=50,resizable=yes,scrollbars=yes' );
		
		
			return urlFichero;
		}	
		
		function openFile(fitxer, type) 
		{
			var f = document.ImportIOSpdForm;
			f.filenameOut.value=fitxer;
			f.fileType.value=type;
			f.parameter.value="descargaFichero";
			f.submit();

		}	

		
	</script>
</head>
<%
	String idThread = formulari.getIdThread();
	String idDivisionResidencia = formulari.getIdDivisionResidencia();
	String mensajeEvolucion="";
	
	int processedRows = -1;
	int errorRows = -1;
	int okRows = -1;
	
	out.println("idThread  " + idThread);
	

	
	if (idThread!=null && idThread.length()>0)
	{
		lopicost.spd.iospd.importdata.process.ImportDataThread thread=(lopicost.spd.iospd.importdata.process.ImportDataThread)session.getAttribute(idThread);

		
		if (thread!=null)
		{
			
			processedRows = thread.getFilesProcessades();
			errorRows = thread.getErrors().size();
			okRows = processedRows-errorRows;
	
			out.println("checkStatus  " + thread.checkStatus());
			out.println("idDivisionResidencia  " + idDivisionResidencia);
			
			if (thread.checkStatus()<2)
			{
				%>
				<body onLoad="refreshPage();">
				
				<%
				mensajeEvolucion=lopicost.spd.utils.TextManager.getMensaje("ImportData.info.reg")+" "+formulari.getFilesProcessades();
				//System.out.println("mensajeEvolucion : "+mensajeEvolucion);
			} 
			else if (thread.checkStatus()>=2)
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
	}
	else
	{ 
		%>
		<body>	
		<%
	}
%>
	<html:form action="/Iospd/ImportGestionFile.do" method="POST" enctype="multipart/form-data">
   		<html:hidden property="operation"/>
   		<html:hidden property="idThread"/>
     	<html:hidden property="parameter"/>
     	<html:hidden property="filenameOut"/>
     	<html:hidden property="exportType"/>
    	<html:hidden property="idUsuario"/>
 
   		
      <html:hidden property="ACTIONTODO" value="list"/>

		<div id="caixa">
			<!-- cantonades superiors blaves -->
			<div id="fonslt"></div>
			<div id="fonsrt"></div>
			<!-- fi cantonades superiors blaves-->
			<div id="contingut">
			<div id="caixa2">
			<!-- cantonades superiors blanques -->
			<div id="fonslt2"></div>
			<div id="fonsrt2"></div>
			<!-- fi cantonades superiors blanques -->
			<div class="clsMainMenuHeader">
			<!-- contingut -->
			<strong>Mantenimiento:</strong>
			<logic:notEmpty property="process" name="ImportIOSpdForm">
		   	 	<html:select property="idProcessIospd"   onchange="javascript:refreshDesc()" >
		   	 	<html:option value="">Seleccionar</html:option>
   					<html:optionsCollection name="ImportIOSpdForm" property="process" label="description" value="idprocess" />
				</html:select>
					</logic:notEmpty>
				
				<html:text name="ImportIOSpdForm" property="idProcessIospd" />
				<bean:define id="readers" name="ImportIOSpdForm" property="readers"/>
				<html:select property="fileType"  onchange="javascript:refreshDesc()">
					<html:options collection="readers" property="idreader" labelProperty="namereader"/>
				</html:select>

			
							
			<logic:equal property="existePlantilla" name="ImportIOSpdForm" value= "true"> 
								<a href="javascript:openFile('<%=formulari.getIdProcessIospd()%>', '<bean:write name="ImportIOSpdForm" property="fileType" />');"  > Descargar plantilla</a> 
			</logic:equal>
		  				
		<%
		if(formulari.getIdProcessIospd()!=null && formulari.getIdProcessIospd().equals("importSustitucionesLite"))
		{
			%>		
			
		<strong>Residencia:</strong>
		<logic:notEmpty property="lstDivisionResidencias" name="ImportIOSpdForm">
	   	 	<html:select property="idDivisionResidencia"   onchange="javascript:refreshDesc()" >
	   	 	<html:option value="">Todas</html:option>
					<html:optionsCollection name="ImportIOSpdForm" property="lstDivisionResidencias" label="nombreDivisionResidencia" value="idDivisionResidencia" />
			</html:select>
		</logic:notEmpty>
		<%
		}

		if(formulari.getIdProcessIospd()!=null && formulari.getIdProcessIospd().equals("importSustXComposicion"))
		{
		
			
			%>		
		<strong>Robot:</strong>
		<logic:notEmpty property="listaRobots" name="ImportIOSpdForm">
	   	 	<html:select property="idRobot"   onchange="javascript:refreshDesc()" >
	   	 	<html:option value="">Seleccionar</html:option>
					<html:optionsCollection name="ImportIOSpdForm" property="listaRobots" label="nombreRobot" value="idRobot" />
			</html:select>
		</logic:notEmpty>
		<%
		}
		if(formulari.getIdProcessIospd()!=null && formulari.getIdProcessIospd().equals("ImportDatosResiAegerusSPDPrevision"))
		{
			%>		
			<html:hidden property="idProcessIospd"/>
			<logic:notEmpty property="lstDivisionResidencias" name="ImportIOSpdForm">	
		   	 	<html:select property="idDivisionResidencia"   onchange="javascript:refreshDesc()" >
		   	 	<html:option value="">Seleccionar</html:option>
   					<html:optionsCollection name="ImportIOSpdForm" property="lstDivisionResidencias" label="nombreDivisionResidencia" value="idDivisionResidencia" />
				</html:select>
			</logic:notEmpty>
						
						
			<logic:notEqual name="ImportIOSpdForm" property="idProcessIospd" value="">
				</br></br>
				<div class="clsMenuRow">
			      	Fecha inicio proceso:
    			  	
    				  <input type="text" name="fechaInicioSpd" value="<%= formulari.getFechaInicioSpd() %>" class="clsColorMenuRow" readonly>
	    			  <A HREF="#"  onclick="calDateFormat='DD/MM/yyyy';setDateField(forms[0].fechaInicioSpd); 
	    			  top.newWin = window.open('../spd/common/dbformslib/jscal/calendar.html','cal','WIDTH=270, HEIGHT=280,location=NO,scrolling=NO, toolbar=NO, menubar=NO')">
	    			  <IMG SRC="../spd/common/dbformslib/jscal/calendar.gif"  BORDER=0  alt='Seleccionar fecha inicio'></a>
	      				&nbsp;<A HREF="#"  onclick="JavaScript:document.forms[0].fechaInicioSpd.value='';">
	      				<IMG SRC="../spd/common/dbformslib/jscal/nocalendar.gif"  BORDER=0  alt='borrar fecha'></a>
      			</div>				
				</br>
				<div class="clsMenuRow">
	      			Fecha fin proceso:&nbsp;&nbsp;&nbsp;&nbsp;
      				
      					<input type="text" name="fechaFinSpd" value="<%= formulari.getFechaFinSpd() %>" class="clsColorMenuRow" readonly>
	      				<A HREF="#"  onclick="calDateFormat='DD/MM/yyyy';setDateField(forms[0].fechaFinSpd); 
	      				top.newWin = window.open('../spd/common/dbformslib/jscal/calendar.html','cal','WIDTH=270, HEIGHT=280')">
	      				<IMG SRC="../spd/common/dbformslib/jscal/calendar.gif"  BORDER=0  alt='Seleccionar fecha fin'></a>
	      				&nbsp;<A HREF="#"  onclick="JavaScript:document.forms[0].fechaFinSpd.value='';"><IMG SRC="../spd/common/dbformslib/jscal/nocalendar.gif"  BORDER=0  alt='borrar fecha'></a>
										
				</div>	
				</logic:notEqual>
		<%
		}
		%>
			

					
			<logic:notEqual name="ImportIOSpdForm" property="idProcessIospd" value="">
					<div>
		  				<div class="clsMenuRow">
		  				

		  				
		  				
		  				</br>
		  				Seleccionar fichero 						
							<logic:notEmpty name="ImportIOSpdForm" property="descReader"><bean:write name="ImportIOSpdForm" property="descReader" filter="false"/></logic:notEmpty>
		  					
			  				<html:file name="ImportIOSpdForm" property="file"/><br><br>
							<input type="button" class="clsButtonStyle" value="Procesar" onclick="javascript:generateExcel('<%=formulari.getIdProcessIospd()%>');" align="right">
						</div>
						<div>
							<logic:equal name="ImportIOSpdForm" property="operation" value="THREADING">Cargando<b><%=mensajeEvolucion%></b></logic:equal>
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
								<font color="red"><ul>
								<u>Descripción errores"/></u>
								<logic:iterate id="error" name="ImportIOSpdForm" property="errors" type="java.lang.String">
									<li><bean:write name="error"/></li>
								</logic:iterate>
								</ul></font>
							</logic:notEmpty>
						</div>
					</div>
				</logic:notEqual>


			</div>
			<!-- cantonades inferiors blanques -->
			<div id="fonslb2"></div>
			<div id="fonsrb2"></div>
			<!-- fi cantonades inferiors blanques -->
		   	</div>
			</div>
			<!-- cantonades inferiors blaves -->
			<div id="fonslb"></div>
			<div id="fonsrb"></div>
			<!-- fi cantonades inferiors blaves -->
		</div>
	</html:form>
					<!-- input type="button" onclick="javascript:goIndex()" value="Volver"/> -->
					<input type="button" class="azulCielo" value="Salir" onclick="javascript:salir();" />
					
	
</body>

</html>
