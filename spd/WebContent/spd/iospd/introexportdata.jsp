<%@ page import = "java.util.*"%>
<%@ page import = "lopicost.spd.model.*"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/taglibs-io.tld" prefix="io" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
	<bean:define id="ImportIOSgaForm" type="lopicost.spd.iospd.importdata.struts.form.ImportDataForm" name="ImportIOSpdForm"/>
	<jsp:include page="/spd/jsp/global/skin.jsp"/>
<head>
	<title><bean:message key="ImportData.info.title"/></title>
	<bean:define id="ExportIOSpdForm" type="lopicost.spd.iospd.exportdata.struts.form.ExportDataForm" name="ExportIOSpdForm"/>
</head>
<body>
	<html:form action="/Iospd/ImportFile.do" method="POST" enctype="multipart/form-data">
		</html:form>