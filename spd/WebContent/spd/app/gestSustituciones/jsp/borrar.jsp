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
			f.parameter.value='borrar';
			f.ACTIONTODO.value='CONFIRMADO_OK';
		//	f.oidGestSustituciones.value=oidGestSustituciones;
			
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
<bean:define id="bdConsejo" name="data" property="bdConsejo"  />
<bean:define id="bdConsejoBiblia" name="data" property="bdConsejoBiblia"/>

     <html:hidden property="oidDivisionResidenciaFiltro" />
     <html:hidden property="parameter" value="borrar"/>
     <html:hidden property="ACTIONTODO" value="CONFIRMAR"/>
     <html:hidden property="oidGestSustituciones" />
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
		<td><bean:write name="data" property="idDivisionResidencia"/></td>
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
		<td><bean:write name="data" property="nombreCorto"/></td>
	</tr>
	<tr>
		<td>Nombre bdConsejo</td>
		<td><bean:write name="bdConsejo" property="nombreConsejo"/></td>
	</tr>	
	<tr>
		<td>Forma farmaceutica</td>
		<td><bean:write name="bdConsejo" property="nombreFormaFarmaceutica"/></td>
	</tr>
	<tr>
		<td>Accion</td>
		<td><bean:write name="data" property="accion"/></td>
	</tr>
	<tr>
		<td>Comentario</td>
		<td><bean:write name="data" property="comentario"/></td>
	</tr>
	<tr>
		<td>Sustituible</td>
		<td>
			<%if (formulari.getSustitucion()!=null&&formulari.getSustitucion().getSustituible()!=null&&formulari.getSustitucion().getSustituible().equals("1")){%>SI<%}%>	
		   	<%if (formulari.getSustitucion()!=null&&formulari.getSustitucion().getSustituible()!=null&&formulari.getSustitucion().getSustituible().equals("0")){%>NO<%}%>	
		</td>
	</tr>
	<tr>
		<td>¿Existe en bdConsejo?</td>
		<td><bean:write name="data" property="existeBdConsejo"/></td>
	</tr>
	<tr>
		<td>CN (Vademecum)</td>         	
		<td><bean:write name="bdConsejoBiblia" property="cnConsejo" /></td>
	</tr>
	<tr>
		<td>Medicamento (Vademecum)</td>         	
        <td><bean:write name="bdConsejoBiblia" property="nombreMedicamento" /></td>
	</tr>
	<tr>
		<td> Conj Homog Vademecum (GtVmpp)</td>         	
        <td><bean:write name="bdConsejoBiblia" property="nomGtVmpp" /></td>
	</tr>
	<tr>
		<td>Laboratorio (Vademecum)</td>         	
            <td><bean:write name="bdConsejoBiblia" property="nombreLaboratorio" /></td>
	</tr>
	<tr>
		<td>Ponderación total (Vademecum)</td>         	
        <td><bean:write name="bdConsejoBiblia" property="nota" /></td>
	</tr>
	<tr>
 		<td>MensajesInfo</td>
		<td><%= formulari.getSustitucion().getMensajesInfo() %></td>
	</tr>
	<tr>
		<td>MensajesAlerta</td>         	
 		<td><%= formulari.getSustitucion().getMensajesAlerta() %></td>
    </tr>     
        
<%	
	String mensajesInfo =(formulari.getSustitucionXResi()!=null&&!formulari.getSustitucionXResi().equals("")?formulari.getSustitucion().getMensajesInfo():"");
	String mensajesAlertaResi =(formulari.getSustitucionXResi()!=null&&!formulari.getSustitucionXResi().equals("")?formulari.getSustitucion().getMensajesAlerta():"");
%>
	<tr>
 		<td>MensajesInfo</td>
		<td><%= mensajesInfo %></td>
	</tr>


	<tr>
		<td>MensajesAlerta</td>         	
 		<td><%= mensajesAlertaResi %></td>
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