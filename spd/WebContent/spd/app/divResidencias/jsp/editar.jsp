<%@ page language="java" %>
<%@ page import="java.util.*" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">

<head>
<jsp:include page="/spd/jsp/global/head.jsp"/>
<title>Edición de residencia</title>
</head>

<bean:define id="formulari" name="DivResidenciasForm" type="lopicost.spd.struts.form.DivResidenciasForm" />
<script language="javaScript" src="/spd/spd/app/divResidencias/js/divResidencias.js"></script>


<body id="general">

<html:form action="/DivResidencias.do" method="post">	
<html:errors/>

<div id="contingut">
<bean:define id="data" name="formulari" property="divisionResidencia" />

    <html:hidden property="parameter" value="editar"/>
	<html:hidden property="oidDivisionResidencia"/>

		
  		<table class="editar">

  		<tr >
			<td>Residencia</td><td>
		
			<html:select property="idDivisionResidencia"  > 
				<html:option value="">Residencia</html:option>
	   			<html:optionsCollection name="formulari" property="listaDivisionResidencias" label="nombreDivisionResidencia" value="idDivisionResidencia" />
			</html:select>
			
			</td>
		
		</tr>
		
					
					
		<tr>
			<td>	
				<p class="botons">
					<input type="button" onclick="javascript:cerrar()" value="Cerrar"/>
				</p>	
			</td>	
		</tr>	
	</table>
	</center>
</div>	
</html:form>

</body>
</html>