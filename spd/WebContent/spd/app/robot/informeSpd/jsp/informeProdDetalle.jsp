<%@ page language="java" %>
<%@ page import="java.util.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html:html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <title>Informe global producción</title>
  <jsp:include page="/spd/jsp/global/head.jsp"/>
</head>
<body>
<bean:define id="formulari" name="InformeProdSpdForm" type="lopicost.spd.struts.form.InformeProdSpdForm" />
<bean:define id="cab" name="formulari"  property="cabecera" type="lopicost.spd.struts.bean.FicheroResiBean" />


<html:form action="/InformeProdSpd.do" method="post">

<fieldset>

   <a href="ExportarInformeProdSpd.do?oidFicheroResiCabecera=<bean:write name="cab" property="oidFicheroResiCabecera" />" >Exportar a PDF</a>
</fieldset>			
			
	<input type="hidden" name="oidFicheroResiCabecera" value="<bean:write name='InformeProdSpdForm' property='oidFicheroResiCabecera' />" />
    <logic:iterate id="data" name="formulari" property="producciones" type="lopicost.spd.robot.bean.rd.ProduccionPaciente">
	<bean:define id="dias" name="data" property="diasProduccion" />
    <bean:define id="pac" name="data" property="paciente" />
	
		<table border="1" style="width:80%">
            <thead>
   			CIP:<bean:write name="pac" property="CIP" />
   			<br/>
   			Código numérico interno:<bean:write name="data" property="orderNumber" />
			<br/><br/>
                <tr>
                    <th>CN</th>
	                    <c:forEach var="dia" items="${data.diasSPD}">
	                        <th><c:out value="${dia.fechaToma}" /></th>
	                    </c:forEach>
                   <th>Unidades utilizadas</th>
                </tr>
            </thead>
	</table>

    </logic:iterate>
    
</html:form>
</body>
</html:html>
