<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/taglibs-io.tld" prefix="io" %>

<HTML>
<HEAD>
	<jsp:include page="/spd/jsp/global/header.jsp"/>
	<jsp:include page="/spd/jsp/global/skin.jsp"/>			
</HEAD>
<BODY class="newbody">
<h3><bean:message key="ImportData.info.title"/></h3>
<bean:message key="ImportData.info.intro"/>
<font color="red">
	<ul>
		<logic:iterate id="error" name="ImportIOSpdForm" property="errors" type="java.lang.String">
			<li><bean:write name="error" filter="false"/></li>
		</logic:iterate>
	</ul>
</font>
</BODY>
</HTML>