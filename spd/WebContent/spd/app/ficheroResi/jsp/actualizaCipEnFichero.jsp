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
<title>Actualización CIP en fichero</title>
</head>


<bean:define id="formulari" name="FicheroResiForm" type="lopicost.spd.struts.form.FicheroResiForm" />


<script type="text/javascript">	
		function volver()
		{
			var f = document.FicheroResiForm;
			f.parameter.value='list';
			f.ACTIONTODO.value='LIST';
		//	f.oidDivisionResidenciaFiltro.value=oidDivisionResidenciaFiltro;
			f.oidFicheroResiCabecera.value=oidFicheroResiCabecera;
			//alert(oidDivisionResidenciaFiltro);
			f.submit();
		}	
		
		function grabar()
		{
			var f = document.FicheroResiForm;
			f.parameter.value='actualizaCipEnFichero';
			f.ACTIONTODO.value='EDITA_CIP_OK';
			f.submit();

			
		}	
				
</script>




<body id="general">

	<center>
		<h2>Edición Fichero Resi Carga Robot</h2>
		<html:form action="/FicheroResiDetalle.do" method="post">	


<div id="contingut">
     <html:hidden property="parameter" value="editar"/>
     <html:hidden property="ACTIONTODO" value="EDITA"/>
     <html:hidden property="fieldName1" /> 
     <html:hidden property="oidDivisionResidencia" /> 
     <html:hidden property="filtroProceso" />
     <html:hidden property="oidFicheroResiCabecera" /> 
     <html:hidden property="oidFicheroResiDetalle" />
     <html:hidden property="idProceso" /> 
 
<table>

<table>
	   <tr>
	  <th>idProceso</th>
	  <th>Residencia</th>
	  <th>fechaHoraProceso</th>
	  <th>CIP</th>
	  <th>nombre paciente</th>

     </tr>


<!--bean:define id="dataw" name="formulari" property="ficheroResiDetalleBean" type="lopicost.spd.struts.bean.FicheroResiBean" />-->
	 <logic:iterate id="data" name="FicheroResiForm" property="listaFicheroResiDetalleBean" type="lopicost.spd.struts.bean.FicheroResiBean" indexId="position"> 


       <tr>
  			<td><html:text name="data" property="idProceso" /></td>
			<td><html:text name="data" property="idDivisionResidencia" /></td>
			<td><html:text name="data" property="fechaHoraProceso" /></td>
			<td><input type="text" name="seleccionResiCIP"></td>
			<td><html:text name="data" property="resiNombrePaciente" /></td>

	
 </tr>
 	<tr>
		<td>	
			<p class="botons">
			<input type="button" onclick="javascript:volver()" value="Volver"/>
			<input type="button" onclick="javascript:grabar()" value="Confirmar"/>
		</p>	
		</td>	
	</tr>
	 </logic:iterate> 
</table>



</table>


		
	
	</center>

</div>	

	</html:form>

</body>
</html>