<%@page import="lopicost.spd.model.Enlace"%>
<%@ page language="java" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.concurrent.atomic.AtomicInteger" %>

<%@ page session="true" %>
<%@page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html:html>
	<HEAD>
		<title>Inicio</title>
		<jsp:include page="/spd/jsp/global/head.jsp"/>
	</HEAD>

<bean:define id="formulari" name="EnlacesForm" type="lopicost.spd.struts.form.EnlacesForm" />
<script type="text/javascript">	
	function abre(loc)
	{
		//alert(loc);
		window.open(loc, 'new', 'dependent=yes,height=500,width=800,top=50,left=50,resizable=yes,scrollbars=yes' );
	}

	function goInicio()
	{
		document.location.href='/spd/Enlaces.do?parameter=list&ACTIONTODO=INICIO';
	}
</script>
<body>

<html:form action="/Enlaces.do" method="post">
 <html:hidden property="idUsuario" />
	
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

	<logic:iterate id="lista" name="formulari" property="listaEnlaces">
	<%
		// Obtén la lista de enlaces desde el atributo 'lista' en el objeto 'request'
		List<Enlace> listaEnlaces = (List<Enlace>) request.getAttribute("lista");
		// Calcula el tamaño de la lista
		int totalIteraciones = (listaEnlaces != null) ? listaEnlaces.size() : 0;
		// Inicializa un contador
		int contador = 0;
	%>
	
	
		<logic:iterate id="enlace" name="lista" type="lopicost.spd.model.Enlace" >
	<% 
	String target="";
	if(enlace.isNuevaVentana())
		target="_blank";

		contador++; 
		if(contador==1)
		{ 
	%>
		<div id="${enlace.idApartado}">	
		    <span class="titulo">${enlace.idApartado}</span>
		    <ul>
		    	
	<% 
		} 
	%>
				<li><a href="${enlace.preEnlace}${enlace.linkEnlace}${enlace.paramsEnlace}" target="<%=target%>">${enlace.nombreEnlace}</a></li>
	   		</logic:iterate>
			</ul>
		</div>
	</logic:iterate>
 	<div>
		<!--  input type="button" class="btn primary" value="Inicio" onclick="javascript:goInicio();" /> -->
		<input type="button" class="azulCielo" value="Salir" onclick="javascript:salir();" />
	</div>
   	

 
</html:form>
</html:html>
