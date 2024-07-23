<%@ page language="java" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<bean:define id="frm" name="sumForm" type="lopicost.spd.test.struts.form.SumForm" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Struts2 beginner example application</title>
</head>
<body>
	<center>
		<h2>¿Seguro que quieres sumarlos?</h2>
		<html:form action="/sumAction" method="post" >
		   <html:hidden property="x"></html:hidden>
		   <input type="hidden" name="parameter" value="success2"/>
		   <html:hidden property="y"></html:hidden>
		   <html:submit>Confirmar</html:submit>
		   <html:cancel>Cancelar</html:cancel>
		</html:form>

	</center>
</body>
</html>