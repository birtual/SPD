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
<bean:define id="formulari" name="FarmaciasForm" type="lopicost.spd.struts.form.FarmaciasForm" />
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
		

		function buscaElemento(idFarmacia, nombreFarmacia) {

			if(opener.document.forms[0].idFarmacia) 
				opener.document.forms[0].idFarmacia.value=idFarmacia;
			if(opener.document.forms[0].nombreFarmacia) 
				opener.document.forms[0].nombreFarmacia.value=nombreFarmacia;
			
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
<body id="lookUpFarmacias" class="popup">
<html:errors />
	<div id="caixa">
		<h2>Medicamentos</h2>
		<html:form action="/Farmacias.do"  method="post" styleId="${formulari}">
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
		        <th>idFarmacia</th>
		        <th>Nombre Farmacia</th>
			</tr>
			<logic:iterate id="data" name="formulari" property="listaFarmacias" type="lopicost.spd.model.Farmacia" indexId="position">
			<tr>
		        <td>
		        	<a href="#" onclick="javascript:buscaElemento('<bean:write name="data" property="idFarmacia"/>', '<bean:write name="data" property="nombreFarmacia" />');">
		        	<bean:write name="data" property="idFarmacia" />
		        </td>
		        <td><bean:write name="data" property="nombreFarmacia" /></td>

	        </tr>
			</logic:iterate>
		 </table>	
		</fieldset>
	</div>		
	</html:form>		
	</div>		
	
		
		
		
			

</body>
</html>