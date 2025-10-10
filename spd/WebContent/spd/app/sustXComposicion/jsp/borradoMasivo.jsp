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
<title>Base para desasignar sustituciones - Listado</title>
</head>

<bean:define id="formulari" name="SustXComposicionForm" type="lopicost.spd.struts.form.SustXComposicionForm" />

<script type="text/javascript">	

		function goIndex()
		{
			var f = document.GestSustitucionesForm;
			document.location.href='http://'+window.location.host+'<%=request.getContextPath()%>/';
			return true;
		}	
			
		function volver(oidSustXComposicion)
		{
			var f = document.SustXComposicionForm;
			f.parameter.value='list';
			f.ACTIONTODO.value='LIST';
			//alert(oidDivisionResidenciaFiltro);
			f.submit();
		}	
		
		function grabar()
		{
			var f = document.SustXComposicionForm;
			f.parameter.value='borradoMasivo';
			f.ACTIONTODO.value='CONFIRMADO_OK';
			f.submit();
		}				
	</script>

<body id="general">

<html:form action="/SustXComposicion.do" method="post">	
<html:errors/>


<div id="contingut">
     <html:hidden property="parameter" value="borradoMasivo"/>
     <html:hidden property="ACTIONTODO" value="BORRADO_MASIVO"/>
     <html:hidden property="filtroCodiLaboratorio"/>
     <html:hidden property="filtroGtVm"/>
     <html:hidden property="filtroGtVmp"/>
     <html:hidden property="oidSustXComposicion"/>
     <html:hidden property="filtroTextoABuscar"/>
     
     
     
     
     
 	
   	<!-- mostramos mensajes y errores, si existen -->
	<logic:notEmpty name="SustXComposicionForm" property="errors">
		<font color="red"><ul>
			<u>Mensaje:</u>
				<logic:iterate id="error" name="SustXComposicionForm" property="errors" type="java.lang.String">
						<li><bean:write name="error"/></li>
				</logic:iterate>
			</ul></font>
	</logic:notEmpty>

<tr>
	<th>
	Dessignar la Rentabilidad Y Ponderacion del laboratorio <html:text name="SustXComposicionForm" property="nombreLab" styleClass="nombreLab" />
	de los siguientes conjuntos homogéneos 
	</th>
</tr>

<table border="1">



    <tr>
    <!--    <th>fecha</th> --> 
		<th>Grupo VM (Principio activo)</th>
		<th>Grupo VMPP (Princ.Activo, dosis, forma y número unidades) - (Conjunto Homogéneo)</th>
        <th>Rentabilidad</th>
        <th>Ponderacion</th>
        <th>Código Lab Asignado</th>
        <th>Nombre Lab Asignado</th>
        <th>Fecha creación</th>
        <th>última modificacion</th>
        <th>Comentario</th>
		<th></th>
		<th></th>
     </tr>
     
 
 	<logic:notEmpty name="SustXComposicionForm" property="listaSustXComposicion">	

		<logic:iterate id="data" name="SustXComposicionForm" property="listaSustXComposicion" type="lopicost.spd.model.SustXComposicion" indexId="position">
		
        <tr>
          <!--  td %=data.getFecha()%></td-->
            <td><bean:write name="data" property="nomGtVm" /><html:hidden name="data" property="codGtVm"/></td>
            <td><bean:write name="data" property="nomGtVmpp" /><html:hidden name="data" property="codGtVmpp"/></td>
            <td><bean:write name="data" property="rentabilidad" /></td>
            <td><bean:write name="data" property="ponderacion" /></td>
            <td><bean:write name="data" property="codiLab" /></td>
            <td><bean:write name="data" property="nombreLab" /></td>
            <td><bean:write name="data" property="fechaCreacion" /></td>
            <td><bean:write name="data" property="ultimaModificacion" /></td>
            <td><bean:write name="data" property="comentarios" /></td>

        </tr>
    </logic:iterate>
    </logic:notEmpty>
   
    
</table>
		<p class="botons">
			<input type="button" onclick="javascript:volver();" value="Volver"/>
			<input type="button" onclick="javascript:grabar()" value="Confirmar"/>
			
		</p>
		
	</fieldset>
	</html:form>
</div>	
</body>
</html>