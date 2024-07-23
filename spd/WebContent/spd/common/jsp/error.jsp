<%@ page language="java" %>
<%@ page isErrorPage="true" %>
<%@ page contentType="text/html"%>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/taglibs-io.tld" prefix="io" %>

<%@page import="java.util.*" %>
<%@page import="java.lang.Exception" %>
<%@page import="java.lang.Throwable" %>
<%@page import="lopicost.config.logger.Logger" %>

<%@page import="java.io.StringWriter" %>
<%@page import="java.io.PrintWriter" %>


<%
  // ESTE JSP SACA EL MENSAJE DE ERROR DE DOS POSIBLES SITIOS:
  //  sitio 1: la "exception" que viene de un JSP donde hubo error y que tiene este JSP como JSP de error en su cabecera
  //  sitio 2: el atributo "exception" de la request (el servlet que redireccione a este JSP debe hacer req.setAttribute("exc",e); 
  String stackTrace = "--";
  // La excepcion se produjo al procesar un JSP?
  if (exception != null && exception instanceof java.lang.Throwable) 
  {
    StringWriter sout = new StringWriter();
    PrintWriter pout = new PrintWriter(sout);
    exception.printStackTrace(pout);
    stackTrace = sout.toString();
  }
  // La excepcion se produjo en un SERVLET?
  if ( request.getAttribute("excepcion") != null )
  {
    Throwable svltExc = null;
    try { svltExc = (Throwable) request.getAttribute("excepcion"); }
    catch ( Throwable t ) {;};
    if ( svltExc != null )
    {
      StringWriter sout = new StringWriter();
      PrintWriter pout = new PrintWriter(sout);
      svltExc.printStackTrace(pout);
      stackTrace = sout.toString();
    }
  }  
%>

<html><!-- #BeginTemplate "/Templates/base.dwt" -->
<head>
	<title><bean:message key="global.error"/></title>
<script>	
	document.write('<link rel="stylesheet" href=<%= "\"" +request.getContextPath() +"/spd/css/spd.css\""%> media="screen" />');
</script>
	
</head>


<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<div id="caixa">
	<!-- cantonades superiors blaves -->
	<div id="fonslt"></div>
	<div id="fonsrt"></div>
	<!-- fi cantonades superiors blaves-->

	<!-- titol de pagina -->
	<h2><bean:message key="global.error.page"/></h2>
	<!-- fi titol de pagina -->

   	<div id="contingut">
		<div id="caixa2">
			<!-- cantonades superiors blanques -->
			<div id="fonslt2"></div>
			<div id="fonsrt2"></div>
			<!-- fi cantonades superiors blanques -->

			<div id="datos">
			<!-- contingut -->

				<fieldset>

				<div>
				
				<html:errors/>
<% 
	
	Exception e 			= (Exception) request.getAttribute("exception");
	String typeOfEception 	= "";	// "Error" ó "Aviso"

	if (e == null) { %>
					<br><bean:message key="errorpage.title1"/>
					<br><bean:message key="errorpage.notfound"/>

<% 	} else if (e.getMessage() != null ){ 
%>	
					<br><bean:message key="errorpage.title1"/> : <%=e.getMessage() %>
<% 	} %>

					<p>					
					<center>
						<!-- history.back() -->
						<!--input type="button" value="Volver" onClick="history.go(-1)"-->
						<!--input type="button" value="Volver" onClick="history.back()"-->
					</center>
					</p>
				</div>
			<!-- fi contingut -->			
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

    <!--
    <%= stackTrace %>
    -->

</body>
<!-- #EndTemplate --></html>
