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
<title>Borrado de sustitución</title>

</head>
<bean:define id="formulari" name="GestSustitucionesForm" type="lopicost.spd.struts.form.GestSustitucionesForm" />

<script type="text/javascript">	
		
		function volver()
		{
			var f = document.GestSustitucionesForm;
			f.parameter.value='list';
			f.ACTIONTODO.value='LIST';
			//f.oidDivisionResidenciaFiltro.value=oidDivisionResidenciaFiltro;
			//alert(oidDivisionResidenciaFiltro);
			f.submit();
		}	
		
		function borrar()
		{
			var f = document.GestSustitucionesForm;
			f.parameter.value='borrarSustXResi';
			f.ACTIONTODO.value='CONFIRMADO_OK';
		//	f.oidDivisionResidenciaFiltro.value=oidDivisionResidenciaFiltro;
			
			//alert(f.nombreCorto.value);
			f.submit();
		}	
				
</script>




<body id="general">
	<center>
		<h2>Borrado de sustitución</h2>
		<html:form action="/GestSustituciones.do" method="post">	

<div id="contingut">
<bean:define id="data" name="GestSustitucionesForm" property="sustitucion"/>
		<logic:iterate id="listaSustitucionesXResi" name="data" property="listaSustitucionesXResi"  indexId="position">
			<bean:define id="dataXResi" name="listaSustitucionesXResi" />
		</logic:iterate>

<bean:define id="bdConsejoSustXResi" name="dataXResi" property="bdConsejoSustXResi"  />
			
<bean:define id="bdConsejoSustXResiBiblia" name="dataXResi" property="bdConsejoSustXResiBiblia"/>

     <html:hidden property="parameter" value="borrarSustXResi"/>
     <html:hidden property="ACTIONTODO" value="CONFIRMAR"/>
     <!-- html:hidden property="oidDivisionResidenciaFiltro" />  -->
     <!-- html:hidden property="oidDivisionResidencia" /> -->
     <html:hidden property="oidGestSustituciones" />
     <html:hidden property="oidGestSustitucionesXResi" />
     <html:hidden property="fieldName1" /> 
 
 	<!-- se pasan parámetros de los filtros del listado para la vuelta -->
     <html:hidden property="filtroNombreCorto" />
     <html:hidden property="filtroMedicamentoResi" />
     <html:hidden property="filtroNombreCortoOK" />
     <html:hidden property="filtroFormaFarmaceutica" />
     <html:hidden property="filtroAccion" />
     <html:hidden property="filtroExisteBdConsejo" />
 
 
	<h3>Confirmar borrado</h3>
	
	<table>
	<tr>
		<td>Residencia</td>
		<td><bean:write name="dataXResi" property="idDivisionResidencia"/></td>
	</tr>
	<tr>
		<td>Código residencia</td>
		<td><bean:write name="data" property="cnResi"/></td>
	</tr>
	<tr>
		<td>Medicamento residencia</td>
		<td><bean:write name="data" property="medicamentoResi"/></td>
	</tr>
	<tr>
		<td>Código OK</td>
		<td><bean:write name="data" property="cnOk"/></td>
	</tr>
	<tr>
		<td>Nombre corto</td>
		<td><bean:write name="dataXResi" property="nombreCortoSustXResi"/></td>
	</tr>
	<tr>
		<td>Nombre bdConsejo</td>
		<td><bean:write name="bdConsejoSustXResi" property="nombreConsejo"/></td>
	</tr>	
	<tr>
		<td>Forma farmaceutica</td>
		<td><bean:write name="bdConsejoSustXResi" property="nombreFormaFarmaceutica"/></td>
	</tr>
	<tr>
		<td>Accion</td>
		<td><bean:write name="dataXResi" property="accionSustXResi"/></td>
	</tr>
	<tr>
		<td>Comentario</td>
		<td><bean:write name="dataXResi" property="comentarioSustXResi"/></td>
	</tr>
	<tr>
		<td>Sustituible</td>
		<td>

		   	 	<%if (formulari.getSustitucion()!=null
		   	 		&&formulari.getSustitucion().getListaSustitucionesXResi()!=null
		   	 		&&formulari.getSustitucion().getListaSustitucionesXResi().size()>0
		   	 		&&formulari.getSustitucion().getListaSustitucionesXResi().get(0).getSustituibleXResi()!=null
		   	 		&&formulari.getSustitucion().getListaSustitucionesXResi().get(0).getSustituibleXResi().equals("1")){
		   	 		%>SI<%}%>	
		   	 	<%if (formulari.getSustitucion()!=null
		   	 		&&formulari.getSustitucion().getListaSustitucionesXResi()!=null
		   	 		&&formulari.getSustitucion().getListaSustitucionesXResi().size()>0
		   	 		&&formulari.getSustitucion().getListaSustitucionesXResi().get(0).getSustituibleXResi()!=null
		   	 		&&formulari.getSustitucion().getListaSustitucionesXResi().get(0).getSustituibleXResi().equals("0")){
		   	 		%>NO<%}%>	
		</td>
	</tr>
	<tr>
		<td>¿Existe en bdConsejo?</td>
		<td><bean:write name="dataXResi" property="existeBdConsejoSustXResi"/></td>
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
			<input type="button" onclick="javascript:volver()" value="Volver"/>
			<input type="button" onclick="javascript:borrar()" value="Confirmar"/>
		</p>	
		</td>	
	</tr>
		
	
	</center>

</div>	

	</html:form>

</body>
</html>