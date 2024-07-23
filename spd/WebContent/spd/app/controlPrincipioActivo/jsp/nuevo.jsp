<%@ page language="java" %>
<%@ page import="java.util.*" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">

<head>
<jsp:include page="/spd/jsp/global/head.jsp"/>
<title>Creación de control especial en las producciones para un principio activo</title>
</head>


<bean:define id="formulari" name="ControlPrincipioActivoForm" type="lopicost.spd.struts.form.ControlPrincipioActivoForm" />


	<script type="text/javascript">	
			function volver()
			{
				var f = document.ControlPrincipioActivoForm;
				f.parameter.value='list';
				f.submit();
			}	
			
			function nuevo()
			{
		
				var f = document.ControlPrincipioActivoForm;
			
				{
					//f.nombreCorto.value=f.nombreCorto.value;
					f.parameter.value='nuevo';
					f.ACTIONTODO.value='NUEVO_OK';

					f.submit();
				}
			}
	</script>

<body id="general">
<div id="contingut">
	<center>
		<h2>Creación de control especial en las producciones para un principio activo</h2>
		<html:form action="/CtrlPrincActivos.do" >	
		
		 <html:hidden property="parameter" value="nuevo"/>
		 <html:hidden property="ACTIONTODO" value="NUEVO"/>

   	<!-- mostramos mensajes y errores, si existen -->
	<logic:notEmpty name="formulari" property="errors">
		<ul>
		<font color="red">
			<u>Mensaje:</u>
				<logic:iterate id="error" name="formulari" property="errors" type="java.lang.String">
						<li><bean:write name="error"/></li>
				</logic:iterate>
		</font>
		</ul>
	</logic:notEmpty>
	
	

		<table class="tablaResi">

		<tr>
			<th class="segunda"></th>
			<td><logic:notEmpty name="formulari" property="listaFarmacias">	
			  
		   	 <html:select property="idFarmacia"> 
		   	 	<html:option value="">Selección</html:option>
   					<html:optionsCollection name="formulari" property="listaFarmacias" label="nombreFarmacia" value="idFarmacia" />

				</html:select>
		     </logic:notEmpty>	
			</td>
		</tr>	
	
			
		<tr>
			<th class="segunda"></th>
			<td><logic:notEmpty name="formulari" property="listaDivisionResidencia">	
			  
		   	 <html:select property="oidDivisionResidencia" > 
		   	 	<html:option value="">Selección</html:option>
   					<html:optionsCollection name="formulari" property="listaDivisionResidencia" label="nombreDivisionResidencia" value="oidDivisionResidencia" />
   					
				</html:select>
		     </logic:notEmpty>	
			</td>
		</tr>

		<tr>
			<th class="segunda"></th>
			<td><logic:notEmpty name="formulari" property="listaGtVm">	
			  
		   	 <html:select property="codGtVm"> 
		   	 	<html:option value="">Todas</html:option>
   					<html:optionsCollection name="formulari" property="listaGtVm" label="nomGtVm" value="codGtVm" />
   					
				</html:select>

				
		     </logic:notEmpty>	
			</td>
		</tr>	
	
		</table>

	<tr>
		<td>	
		<p class="botons">
			<input type="button" onclick="javascript:volver()" value="Volver"/>
			<input type="button" onclick="javascript:nuevo()" value="Confirmar"/>
		</p>	
		</td>	
	</tr>
		
	
	</center>

</div>	

	</html:form>

</body>
</html>