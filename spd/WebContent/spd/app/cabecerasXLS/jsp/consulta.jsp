<%@page import="lopicost.spd.struts.bean.CabecerasXLSBean"%>
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
<title></title>
<jsp:include page="/spd/jsp/global/head.jsp"/>

</HEAD>
<bean:define id="formulari" name="FicheroResiForm" type="lopicost.spd.struts.form.FicheroResiForm" />
   <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

<script type="text/javascript">	


	function volver()
	{
		var f = document.FicheroResiForm;
		if (window.opener) 
		{
			window.opener.location.reload();
		}
		
		window.close();
		f.submit();
	}	
		
	function crearNuevaToma()
	{
		var f = document.FicheroResiForm;
		f.parameter.value='edicionLista';
		f.submit();
	}	


</script>		

<body>
<html:form action="/CabecerasXLS.do" method="post">

     <html:hidden property="parameter" value="consulta"/>
     <html:hidden property="ACTIONTODO"/>
     <html:hidden property="oidDivisionResidencia" />	
     <html:hidden property="oidFicheroResiCabecera" />	


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
	

<h2>Consulta de las tomas </h2>

 <table class="detalle">
	 <tr>
 		<td class="primera"></td>
		<td class="primera">horaToma</td>
		<td class="primera">idToma</td>
		<td class="primera">nombreToma</td>
		<td class="segunda">posición en BBDD</td>
		<td class="segunda">posición en Vistas</td>
		<td class="segunda">tipo</td>
	</tr>
	   	
	<c:forEach items="${formulari.listaTomasCabecera}" var="toma"> 
	<tr>
		<td></td>
		<td><c:out value="${toma.horaToma}" ></c:out></td>
		<td><c:out value="${toma.idToma}" ></c:out></td>
		<td><c:out value="${toma.nombreToma}" ></c:out></td>
		<td align="center"><c:out value="${toma.posicionEnBBDD}" ></c:out></td>
		<td align="center"><c:out value="${toma.posicionEnVistas}" ></c:out></td>
		<td><c:out value="${toma.tipo}" ></c:out></td>
	</tr>	
	</c:forEach> 
 </table>

	<div class="">	
		<p class="botons">
			<input type="button" class="btn primary" onclick="javascript:volver()" value="Cerrar"/>
			<input type="button" class="btn primary"  onclick="javascript:crearNuevaToma()" value="Gestionar las tomas"/>
		</p>	
	</div>	
	</html:form>
</html:html>
