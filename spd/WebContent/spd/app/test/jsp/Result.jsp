<%@ page language="java" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="sumForm" name="sumForm" type="lopicost.spd.test.struts.form.SumForm" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Suma Resultado</title>
</head>
<body>
<html:form action="/sumAction" >
	La suma de  <html:text property="x"/>
	y   <html:text property="y"/>
	es:
	 <html:text property="sum"/>
	</html:form>
</body>
</html>