<%@ page import = "java.util.*"%>
<%@ page import = "lopicost.spd.model.*"%>
<%@ page import = "lopicost.spd.utils.SPDConstants" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/taglibs-io.tld" prefix="io" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
		"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
		

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
	<bean:define id="formulari" name="ImportIOSpdForm" type="lopicost.spd.iospd.importdata.struts.form.ImportDataForm" />

	
<jsp:include page="../common/jsp/cabecera.jsp"></jsp:include>
<head>
	<title><bean:message key="ImportData.info.title"/></title>
	
	<script type="text/javascript">	
		document.write('<link rel="stylesheet" href=<%= "\"" +request.getContextPath() +"/spd/css/gestio.css\""%> media="screen" />');

		function goIndex()
		{
			var f = document.ImportIOSpdForm;
			document.location.href='http://'+window.location.host+'<%=request.getContextPath()%>/';
			return true;
		}	
		
		
		function generateExcel() 
		{
			if (document.forms[0].file.value!=""){
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

		processedRows = thread.getFilesProcessades();
		errorRows = thread.getErrors().size();
		okRows = processedRows-errorRows;

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
			<strong>Residencia:</strong>
			<logic:notEmpty property="lstDivisionResidencias" name="ImportIOSpdForm">
		   	 	<html:select property="idDivisionResidencia"   onchange="javascript:refreshDesc()" >
		   	 	<html:option value="">Seleccionar</html:option>
   					<html:optionsCollection name="ImportIOSpdForm" property="lstDivisionResidencias" label="nombreDivisionResidencia" value="idDivisionResidencia" />
				</html:select>
				
				<logic:notEqual name="ImportIOSpdForm" property="idProcessIospd" value="">
					<html:text name="ImportIOSpdForm" property="idProcessIospd" />
					<html:text name="ImportIOSpdForm" property="fechasSpd"  />
				</logic:notEqual>

			</logic:notEmpty>
				
					
				<logic:notEqual name="ImportIOSpdForm" property="idProcessIospd" value="">
					<div>
		  				<div class="clsMenuRow">
		  				</br>
		  				Seleccionar fichero 						
							<logic:notEmpty name="ImportIOSpdForm" property="descReader"><bean:write name="ImportIOSpdForm" property="descReader" filter="false"/></logic:notEmpty>
		  					
			  				<html:file name="ImportIOSpdForm" property="file"/><br><br>
							<input type="button" class="clsButtonStyle" value="Procesar" onclick="javascript:generateExcel();" align="right">
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
					<input type="button" onclick="javascript:goIndex()" value="Volver"/>
					<p><html:link page="/FicheroResiDetalle.do?parameter=list">Gestión de ficheros de residencia (gestFicheroResiBolsa)</html:link></p> 
					
	
</body>

</html>
