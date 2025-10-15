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
<title>Selección de laboratorio</title>
</head>

<bean:define id="formulari" name="BdConsejoForm" type="lopicost.spd.struts.form.BdConsejoForm" />

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
				
		
		function buscaLab(codiLab, nombreLab) {

	//		alert(<%=request.getParameter("CallBackID")%>);
	//		alert(<%=request.getParameter("CallBackNAME")%>);

			if(opener.document.forms[0].codiLab)
				opener.document.forms[0].elements['codiLab'].value=codiLab;
			if(opener.document.forms[0].filtroCodiLaboratorio)
				opener.document.forms[0].elements['filtroCodiLaboratorio'].value=codiLab;
			if(opener.document.forms[0].filtroCodiLaboratorio2)
				opener.document.forms[0].elements['filtroCodiLaboratorio2'].value=codiLab;
			if(opener.document.forms[0].nombreLaboratorio)
				opener.document.forms[0].elements['nombreLaboratorio'].value=nombreLab;
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
</head>
<body id="lookUpLabsBdConsejo" class="popup">
		<html:errors />
		<div id="caixa">
		
			
			<h2>Laboratorios</h2>
			<html:form action="/LookUpLabsBdConsejo.do">
			<html:hidden property="fieldName1"/>
			<html:hidden property="numpages"/>
			<html:hidden property="currpage"/>
			<html:hidden property="parameter" value="initLabs"/>
			

			<input type="hidden" name="CallBack" 		value="<%=request.getParameter("CallBack")%>">
			<input type="hidden" name="CallBackID" 		value="<%=request.getParameter("CallBackID")%>">
			<input type="hidden" name="CallBackNAME" 	value="<%=request.getParameter("CallBackNAME")%>">
			
			<div id="contingut">
			<div id="datos">
					<!-- contingut -->
		
						<div id="formdata">
						<fieldset>
							<div>
								<!-- cnConsejo -->
								<p><label for="codiLab" accesskey="n">codiLab</label>
								<html:text property="codiLab"/></p>
								<!-- MEDICAMENTO -->
								<p><label for="nombreLab" accesskey="e">nombreLab</label>
								<html:text property="nombreLab" /></p>
							</div>
					</div>
					<div class="botons">
								<p>
									<label class="ocult" for="nuevo" accesskey="e">filtrar</label>
									<html:button  styleClass="clsButtonStyle" property="button" onclick="javascript:updateList();">filtrar</html:button>					
								</p>
							</div>					
						</fieldset>

						<table class="graella">
						<thead>
									<th class="logo">logo</th>
									<th>Código</th>
									<th>Nombre Laboratorio</th>
						</thead>
							
							
						
									<logic:present name="formulari" property="listaLabsBdConsejo">
									<logic:iterate id="bdLabConsejo" name="formulari" property="listaLabsBdConsejo" type="lopicost.spd.model.BdConsejo" indexId="position">
									<tbody>
									<tr>
										<td class="logo">
										</td>
										<td>
											<a href="#" onclick="javascript:buscaLab('<bean:write name="bdLabConsejo" property="codigoLaboratorio"/>','<bean:write name="bdLabConsejo" property="nombreLaboratorio"/>');"><bean:write name="bdLabConsejo" property="codigoLaboratorio"/></a>
										</td>
										<td>
											<a href="#" onclick="javascript:buscaLab('<bean:write name="bdLabConsejo" property="codigoLaboratorio"/>','<bean:write name="bdLabConsejo" property="nombreLaboratorio"/>');"><bean:write name="bdLabConsejo" property="nombreLaboratorio"/></a>
										</td>
									</tr>	
									</logic:iterate>	
									</logic:present> 
									<logic:notPresent name="formulari" property="listaLabsBdConsejo">
									<tr>
										<td colspan="3" class="nota"><bean:message key="info.nolist"/></td>
									</tr>	
									</logic:notPresent>									
											
							
							</tbody>
							</table>	
					</div>			

	<!--  paginación  -->
	<div>
		<logic:greaterThan name="formulari" property="numpages" value="1">
			<p align="center">
				<logic:greaterThan name="formulari" property="currpage" value="0">
					<a href="javascript:pageDown();" ><<</a>
				</logic:greaterThan>
				&nbsp;<input type="text" name="newPage" value="<%= formulari.getCurrpage()+1 %>" size="1" maxlength="4" onkeypress="gotoPage(this.value,<%=formulari.getNumpages()%>);">/<%= formulari.getNumpages() %>&nbsp;
				<logic:lessThan name="formulari" property="currpage" value="<%= String.valueOf(formulari.getNumpages() -1)%>">
					<a href="javascript:pageUp();" >>></a>
				</logic:lessThan>
			</p>
		</logic:greaterThan>
	</div>
	<!--  paginación   -->
					
					
			</div>
			</div>
			</html:form>
		
			
</div>
</body>
</html>