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
<title>Creación de sustitución</title>
</head>

<bean:define id="formulari" name="GestSustitucionesForm" type="lopicost.spd.struts.form.GestSustitucionesForm" />

<script type="text/javascript">	
		function volver()
		{
			var f = document.GestSustitucionesForm;
			f.parameter.value='list';
			f.ACTIONTODO.value='LIST';
			//alert(oidDivisionResidenciaFiltro);
			f.submit();
		}	
		
		function nuevo()
		{
			var f = document.GestSustitucionesForm;
			if(f.idTipoAccion.value=='')
				alert('Falta indicar la acción');
			else if(f.formaFarmaceutica.value=='')
				alert('Falta indicar la forma');
			else if(f.cnOkSustXResi.value=='')
				alert('Falta indicar Código CN OK');
			else if(f.nombreCortoOK.value=='')
				alert('Falta indicar el nombre corto ');
			else if((f.cn_resi.value=='')&&(f.medicamento.value==''))
				alert('Falta indicar el Código CN residencia o el nombre del medicamento');
			else
			{
				f.parameter.value='nuevo';
				f.ACTIONTODO.value='NUEVO_OK';
				f.submit();
			}
				
			//alert(f.nombreCorto.value);
		}
		


		
		//función de carga del lookUp
		function doLookUpBdConsejo(){				
			var loc = '/spd/LookUpBdConsejo.do?parameter=init&'+ 						//url de llamanda				
				'CallBackID=cnOkSustXResi&'+			  			//Nombre del campo para el valor Id
				'CallBackNAME=nombreConsejo';			   		//Nombre del campo para el valor descriptivo
		
			//Importante que se realice en ventana nueva!!!!
			window.open(loc, 'LookUpBdConsejo', 'dependent=yes,height=500,width=800,top=50,left=50,resizable=yes,scrollbars=yes' );
		}	
	
			
</script>





<body id="general">
	<!-- mostramos mensajes y errores, si existen -->

	<center>
		<h2>Creación de sustitución</h2>
		<html:form action="/GestSustituciones.do" >	


<div id="contingut">
<bean:define id="data" name="GestSustitucionesForm" property="sustitucion"/>

     <html:hidden property="filtroNombreCorto" />
     <html:hidden property="parameter" value="nuevo"/>
     <html:hidden property="oidDivisionResidenciaFiltro" />
     <html:hidden property="ACTIONTODO" value="NUEVO"/>
     <html:hidden property="fieldName1" /> 
<table>
	<tr>
		<td>Residencia</td>
		<td>
			<bean:define id="listaDivisionResidencia" name="GestSustitucionesForm" property="listaDivisionResidencia" />
			<html:select name="data"  property="oidDivisionResidencia" value="0"  styleClass="nombreDivisionResidencia">
			<html:options collection="listaDivisionResidencia" property="oidDivisionResidencia" labelProperty="nombreDivisionResidencia" />
			<html:option value="0">Todas</html:option>
			</html:select>
			</td>
	</tr>
	<tr>
		<td>Código CN residencia</td>
		<td>
			<html:text property="cn_resi"  styleClass="cn_resi" />
			<html:text property="medicamento"  styleClass="medicamentoResi" />
		</td>
	</tr>
	<tr>
		<td>Código CN OK</td>
		<td><html:text property="cnOkSustXResi" readonly="true" styleClass="cnOkSustXResi"/>
		<!-- a href="#" onclick="javascript:window.open('/DispatchActionExample/bdConsejoLookUp.do?parameter=init&fieldName1=cnOkSustXResi','','width=920,height=700,toolbar=no,scrollbars=yes' );">...</a> -->
		
			<html:text property="nombreConsejo" readonly="true" styleClass="nombreConsejo"/>
			<a href="#" onclick="javascript:doLookUpBdConsejo();">Buscar</a>
		</td>
		
		
	</tr>
	<tr>
		<td>Nombre corto</td>
		<td id='nombreCorto'>
			<logic:notEmpty name="GestSustitucionesForm" property="listaNombreCortoOK">	
		   	 <html:select property="nombreCortoOK"  value="" styleClass="nombreCortoOK" >
		   	 	<html:option value="">-Seleccionar-</html:option>
   					<html:optionsCollection name="GestSustitucionesForm" property="listaNombreCortoOK" label="nombreCorto" value="nombreCorto" />
				</html:select>
		     </logic:notEmpty>	
		</td>
		
	
	</tr>
	<tr>
		<td>Forma</td>
		<td>			
			<bean:define id="formasFarmaceuticas" name="formulari" property="formasFarmaceuticas" />
			<html:select property="formaFarmaceutica" value="">
			<html:options collection="formasFarmaceuticas" property="nombreFormaFarmaceutica" labelProperty="nombreFormaFarmaceutica" />
			<html:option value="">-Seleccionar-</html:option>
			</html:select>
		</td>
	</tr>

	<tr>
		<td>Accion</td>
		<td>		
			<bean:define id="tiposAccion" name="formulari" property="listaTiposAccion" />
		<html:select property="idTipoAccion" value="">
			<html:options collection="tiposAccion" property="idTipoAccion" labelProperty="idTipoAccion" />
			<html:option value="">-Seleccionar-</html:option>
		</html:select>
		</td>
	</tr>
	<tr>
		<td>Comentario</td>
		<td><html:text property="comentario" styleClass="comentario"/></td>
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