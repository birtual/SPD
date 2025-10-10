<%@ page language="java" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="frm" name="sumForm" type="lopicost.spd.test.struts.form.SumForm" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Struts beginner example application</title>
</head>
<body>
	<center>
		<h2>Calcular la suma de dos números</h2>

		
		<html:form action="/sumAction?parameter=confirma" method="post">
		   <html:text property="x"></html:text>
		   <html:text property="y"></html:text>
		   <html:submit>Confirmar</html:submit>
		   <html:cancel>Cancelar</html:cancel>
		</html:form>

<p><html:link page="/user.do?parameter=add">Call Add Section</html:link></p> 
<p><html:link page="/user.do?parameter=edit">Call Edit Section</html:link></p> 
<p><html:link page="/user.do?parameter=search">Call Search Section</html:link></p> 
<p><html:link page="/user.do?parameter=save">Call Save Section</html:link></p> 

	</center>
</body>
</html>