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
	<title>Creación de sustitución Lite</title>
</head>



<bean:define id="formulari" name="GestSustitucionesLiteForm" type="lopicost.spd.struts.form.GestSustitucionesLiteForm" />

	<script language="javaScript" src="/spd/spd/app/gestSustitucionesLite/js/gestSustitucionesLite.js"></script>
<script type="text/javascript">	


		//funci�n de carga del lookUp
		function doLookUpBdConsejo(){				
			var loc = '/spd/LookUpBdConsejo.do?parameter=init'+ 						//url de llamanda
				'&resiCn='+document.GestSustitucionesLiteForm.resiCn.value+'&mode=showGtvmpCn'+	
				'&CallBackID=spdCn'+			  			//Nombre del campo para el valor Id
				'&CallBackNAME=spdNombreBolsa';			   		//Nombre del campo para el valor descriptivo
		
			//Importante que se realice en ventana nueva!!!!
			window.open(loc, 'LookUpBdConsejo', 'dependent=yes,height=500,width=800,top=50,left=50,resizable=yes,scrollbars=yes' );
		}	
	
		function copiar(spdCn, spdNombreBolsa,  spdNombreMedicamento, spdAccionBolsa)
		{
			var f = document.GestSustitucionesLiteForm;
			f.spdCn.value=spdCn;
			f.spdNombreBolsa.value=spdNombreBolsa;
			f.spdNombreMedicamento.value=spdNombreMedicamento;
			f.idTipoAccion.value=spdAccionBolsa;
		}	
			
</script>




<body id="general">

	<center>
		<h2>Creación de sustitución Lite</h2>
		<html:form action="/GestSustitucionesLite.do" >	


<div id="container">
<bean:define id="data" name="GestSustitucionesLiteForm" property="sustitucionLite"/>

     <html:hidden property="filtroNombreCorto" />
     <html:hidden property="parameter" value="nuevo"/>
     <html:hidden property="idDivisionResidencia" />
     <html:hidden property="ACTIONTODO" value="NUEVO"/>
     <html:hidden property="fieldName1" />
     <html:hidden property="campoGoogle" />

      
<table>

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
	<tr>
		<td>Residencia</td>
		<td><logic:notEmpty name="formulari" property="listaDivisionResidencia">	
			  
		   	 <html:select property="oidDivisionResidenciaFiltro"  onchange="submit()"> 
		   	 	<html:option value="">Selección</html:option>
   					<html:optionsCollection name="formulari" property="listaDivisionResidencia" label="nombreDivisionResidencia" value="oidDivisionResidencia" />
				</html:select>
		     </logic:notEmpty>	
			</td>
	<tr>
		<td>Código CN residencia</td>
		<td><html:text name="data" property="resiCn" styleId="resiCn" value="<%=formulari.getResiCn() %>"/> - <html:text name="data"  property="resiMedicamento"  styleId="spdNombreMedicamento" value="<%=formulari.getResiMedicamento() %>"/></td>
	</tr>
	<tr>
		<td>Código -  Nombre medicamento </td>
				<td><html:text name="data" property="spdCn" styleId="spdCn"/> - <html:text name="data" property="spdNombreMedicamento" styleId="spdNombreMedicamento" readonly="true"/>
		<a href="#" onclick="javascript:doLookUpBdConsejo();">Buscar</a>
		</td>
	</tr>	
	<tr>
		<td>Nombre corto</td>
		<td><html:text name="data" property="spdNombreBolsa" styleClass="spdNombreBolsa" /></td>
	</tr>

	<tr>
		<td>Accion</td>
		<td>		
			<bean:define id="tiposAccion" name="formulari" property="listaTiposAccion" />
		<html:select property="idTipoAccion" value="<%= formulari.getSustitucionLite().getSpdAccionBolsa() %>">
			<html:options collection="tiposAccion" property="idTipoAccion" labelProperty="idTipoAccion" />
			<html:option value="">-------------</html:option>
		</html:select>
		</td>
	</tr>

	

	<tr>
		<td>Excepciones</td>
		<td><html:text name="data" property="excepciones"  styleClass="excepciones"/></td>
	</tr>
	<tr>
		<td>Aux1</td>
		<td><html:text name="data" property="aux1"  styleClass="aux1"/></td>
	</tr>
	<tr>
		<td>Aux2</td>
		<td><html:text name="data" property="aux2"  styleClass="aux2"/></td>
		
	</tr>	
			
		

</table>



		<p class="botons">
			<input type="button" onclick="window.close()" value="Volver"/>
			<input type="button" onclick="javascript:grabar()" value="Confirmar"/>
		</p>	


  	<logic:notEmpty name="GestSustitucionesLiteForm" property="listaSustituciones">	

	
	
	<table border="1">
	Otras sustituciones similares
    <tr>
		<th>Residencia</th>
		<th>Resi Cn</th>
		<th>Resi Medicamento </th>
		<th>Spd Cn</th>
		<th>Spd NombreBolsa</th>
		<th>Spd FormaMedicacion</th>
		<th>Spd AccionBolsa</th>
		<th>Excepciones</th>
        <th>Aux1</th>
        <th>Lab</th>
		<th></th>
	</tr>	<logic:iterate id="data2" name="formulari" property="listaSustituciones"  type="lopicost.spd.model.GestSustitucionesLite"  indexId="position">
       <tr>
            <td><bean:write name="data2" property="idDivisionResidencia" /></td>
            <td><bean:write name="data2" property="resiCn" /></td>
            <td><bean:write name="data2" property="resiMedicamento" /></td>
 			<td><bean:write name="data2" property="spdCn" /></td>
            <td><bean:write name="data2" property="spdNombreBolsa" /></td>
            <td><bean:write name="data2" property="spdFormaMedicacion" /></td>
            <td><bean:write name="data2" property="spdAccionBolsa" /></td>
            <td><bean:write name="data2" property="excepciones" /></td>
            <td><bean:write name="data2" property="aux1" /></td>
            <td><bean:write name="data2" property="nomLABO" /></td>
			<td>
				<!-- p class="botons">
					<input type="button" onclick="javascript:editar('<bean:write name="data2" property="oidDivisionResidencia" />', '<bean:write name="data2" property="oidGestSustitucionesLite" />');"  value="Editar"  />
					<input type="button" onclick="javascript:borrar( '<bean:write name="data2" property="oidGestSustitucionesLite" />');"  value="Borrar"  />
					<input type="button" onclick="javascript:duplicar('<bean:write name="data2" property="oidGestSustitucionesLite" />');"  value="Duplicar"  />
				</p-->
				
				<p class="botons">
					<input type="button" onclick="javascript:copiar('<bean:write name="data2" property="spdCn" />', '<bean:write name="data2" property="spdNombreBolsa" />', '<bean:write name="data2" property="spdNombreMedicamento" />',  '<bean:write name="data2" property="spdAccionBolsa" />');"  value="Copiar"  />
				</p>
				
			</td>
        </tr>
      </logic:iterate>
   	</logic:notEmpty>
	</table>	

		
	
	</center>

</div>	

	</html:form>

</body>
</html>