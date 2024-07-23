<%@ page import = "java.util.*"%>
<%@ page import = "lopicost.spd.model.*"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/taglibs-io.tld" prefix="io" %>
<bean:define id="SustXComposicionForm" type="lopicost.spd.struts.form.SustXComposicionForm" name="SustXComposicionForm"/>

<logic:notEmpty name="SustXComposicionForm" property="listaSustXComposicion">
<logic:iterate id="data" name="SustXComposicionForm" property="listaSustXComposicion" type="lopicost.spd.model.SustXComposicion" indexId="position">
		
		<bean:write name="data" property="nomGtVmpp"/>
	</logic:iterate>
</logic:notEmpty>