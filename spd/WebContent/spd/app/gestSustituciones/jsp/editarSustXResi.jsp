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

<title>Edición sustitución específica</title>

<script type="text/javascript">	
		
		function volver(oidDivisionResidenciaFiltro)
		{
			var f = document.GestSustitucionesForm;
			f.parameter.value='list';
			f.ACTIONTODO.value='LIST';
			f.oidDivisionResidenciaFiltro.value=oidDivisionResidenciaFiltro;
			//alert(oidDivisionResidenciaFiltro);
			f.submit();
		}	
		
		function grabar(oidDivisionResidenciaFiltro)
		{
			var f = document.GestSustitucionesForm;
			if(f.accionSustXResi.value=='')
				alert('Falta indicar la acción');
//			else if(f.formaFarmaceutica.value=='')
//				alert('Falta indicar la forma');
			else
			{		
				f.parameter.value='editarSustXResi';
				f.ACTIONTODO.value='EDITA_OK';
				f.oidDivisionResidenciaFiltro.value=oidDivisionResidenciaFiltro;
				f.submit();
			}
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
		<h2>Edición sustitución</h2>
		<html:form action="/GestSustituciones.do" method="post">	


<div id="contingut">
<bean:define id="formulari" name="GestSustitucionesForm" type="lopicost.spd.struts.form.GestSustitucionesForm" />

<bean:define id="data" name="formulari" property="sustitucion" />
		<logic:iterate id="listaSustitucionesXResi" name="data" property="listaSustitucionesXResi"  indexId="position">
			<bean:define id="dataXResi" name="listaSustitucionesXResi" />
		</logic:iterate>

<bean:define id="bdConsejoSustXResi" name="dataXResi" property="bdConsejoSustXResi"  />
			
<bean:define id="bdConsejoSustXResiBiblia" name="dataXResi" property="bdConsejoSustXResiBiblia"/>

     <html:hidden property="parameter" value="editar"/>
     <html:hidden property="oidDivisionResidenciaFiltro" />
     <html:hidden property="ACTIONTODO" value="EDITA"/>
     <html:hidden property="oidGestSustituciones" />
     <html:hidden property="oidGestSustitucionesXResi" />
     <html:hidden property="oidDivisionResidencia" />
     <html:hidden property="fieldName1" /> 
 

 
 	<!-- se pasan parámetros de los filtros del listado para la vuelta -->
     <html:hidden property="filtroNombreCorto" />
     <html:hidden property="filtroMedicamentoResi" />
     <html:hidden property="filtroNombreCortoOK" />
     <html:hidden property="filtroFormaFarmaceutica" />
     <html:hidden property="filtroAccion" />
     <html:hidden property="filtroExisteBdConsejo" />
 
 
<table>
	<tr>
		<td>Residencia</td>
			<td><bean:write name="dataXResi" property="idDivisionResidencia"/>
		
		</td>
		
		
	</tr>
	<tr>
		<td> Medicamento residencia</td>
		<td><bean:write name="data" property="cnResi"/>   -   <bean:write name="data" property="medicamentoResi"/>
		
		</td>
	</tr>
	<tr>
		<td>Código CN OK</td>
		<td><html:text name="dataXResi" property="cnOkSustXResi" styleClass="cnOkSustXResi"/> 
<logic:notEmpty name="dataXResi" property="bdConsejoSustXResi">
- <html:text name="bdConsejoSustXResi" property="nombreConsejo" styleClass="nombreConsejo"/> 

</logic:notEmpty>
			<a href="#" onclick="javascript:doLookUpBdConsejo();">Buscar</a>
		</td>
	</tr>	
	<tr>
		<td>Nombre corto</td>
		<td><html:text name="dataXResi" property="nombreCortoSustXResi" styleClass="nombreCortoSustXResi" /></td>
	</tr>

		<tr>
		<td>Forma farmacéutica</td>
		<td>			
		<logic:notEmpty name="dataXResi" property="bdConsejoSustXResi">
					<bean:write name="bdConsejoSustXResi" property="nombreFormaFarmaceutica"/>
		</logic:notEmpty>
		</td>
	</tr>
	

	<tr>
		<td>Accion</td>
		<td>		
			<bean:define id="accionSustXResi" name="formulari" property="listaTiposAccion" />
			<% String div3 = formulari.getSustitucion().getListaSustitucionesXResi().get(0).getAccionSustXResi()+ ""; %>
			<html:select property="accionSustXResi" value="<%= div3 %>">
			<html:options collection="accionSustXResi" property="idTipoAccion" labelProperty="idTipoAccion" />
			<html:option value="">-------------</html:option>
		</html:select>
		</td>
	</tr>
	<tr>
		<td>Comentario</td>
		<td><html:text name="dataXResi" property="comentarioSustXResi"  styleClass="comentarioSustXResi"/></td>
	</tr>
	<tr>
		<td>CN (Vademecum)</td>         	
		<td><bean:write name="bdConsejoSustXResiBiblia" property="cnConsejo" /></td>
	</tr>
	<tr>
		<td>Medicamento (Vademecum)</td>         	
        <td><bean:write name="bdConsejoSustXResiBiblia" property="nombreMedicamento" /></td>
	</tr>
	<tr>
		<td> Conj Homog Vademecum (GtVmpp)</td>         	
        <td><bean:write name="bdConsejoSustXResiBiblia" property="nomGtVmpp" /></td>
	</tr>
	<tr>
		<td>Laboratorio (Vademecum)</td>         	
            <td><bean:write name="bdConsejoSustXResiBiblia" property="nombreLaboratorio" /></td>
	</tr>
	<tr>
		<td>Ponderación total (Vademecum)</td>         	
        <td><bean:write name="bdConsejoSustXResiBiblia" property="nota" /></td>
	</tr>
	<tr>
		<td>Sustituible</td>
		<td>
			<select name="sustituibleXResi" >
		   	 	<option value="1"  <%if (formulari.getSustitucion()!=null
		   	 		&&formulari.getSustitucion().getListaSustitucionesXResi()!=null
		   	 		&&formulari.getSustitucion().getListaSustitucionesXResi().size()>0
		   	 		&&formulari.getSustitucion().getListaSustitucionesXResi().get(0).getSustituibleXResi()!=null
		   	 		&&formulari.getSustitucion().getListaSustitucionesXResi().get(0).getSustituibleXResi().equals("1")){
		   	 		%>selected<%}%>	>SI</option>
		   	 	<option value="0"  <%if (formulari.getSustitucion()!=null
		   	 		&&formulari.getSustitucion().getListaSustitucionesXResi()!=null
		   	 		&&formulari.getSustitucion().getListaSustitucionesXResi().size()>0
		   	 		&&formulari.getSustitucion().getListaSustitucionesXResi().get(0).getSustituibleXResi()!=null
		   	 		&&formulari.getSustitucion().getListaSustitucionesXResi().get(0).getSustituibleXResi().equals("0")){
		   	 		%>selected<%}%>	>NO</option>		   	 		
			</select>

		</td>
	</tr>+
<%	
String mensajesInfoSustXResi =(formulari.getSustitucionXResi()!=null&&!formulari.getSustitucionXResi().equals("")?formulari.getSustitucionXResi().getMensajesInfo():"");
	String mensajesAlertaSustXResi =(formulari.getSustitucionXResi()!=null&&!formulari.getSustitucionXResi().equals("")?formulari.getSustitucionXResi().getMensajesAlerta():"");
%>
	<tr>
 		<td>MensajesInfo</td>
		<td><%= mensajesInfoSustXResi %></td>
	</tr>


	<tr>
		<td>MensajesAlerta</td>         	
 		<td><%= mensajesAlertaSustXResi %></td>
    </tr>             
</table>

	<tr>
		<td>	
		<p class="botons">
			<input type="button" onclick="javascript:volver('<bean:write name="formulari" property="oidDivisionResidenciaFiltro"/>')" value="Volver"/>
			<input type="button" onclick="javascript:grabar('<bean:write name="formulari" property="oidDivisionResidenciaFiltro"/>')" value="Confirmar"/>
		</p>	
		</td>	
	</tr>
		
	
	</center>

</div>	

	</html:form>

</body>
</html>