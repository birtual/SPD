<%@ page import = "java.util.*"%>
<%@ page import = "lopicost.spd.model.*"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/taglibs-io.tld" prefix="io" %>
<bean:define id="ExportIOSpdForm" type="lopicost.spd.iospd.exportdata.struts.form.ExportDataForm" name="ExportIOSpdForm"/>

<logic:notEmpty name="ExportIOSpdForm" property="datos">
<logic:iterate id="data" name="ExportIOSpdForm" property="datos" type="lopicost.spd.model.SustXComposicion" indexId="position">
		<bean:write name="dato" filter="false"/>
		<bean:write name="dato" property="nomGtVmpp"/>
	</logic:iterate>
</logic:notEmpty>