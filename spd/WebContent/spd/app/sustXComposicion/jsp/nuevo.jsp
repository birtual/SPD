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
<title>Asignar Lab a una sustitución X GtVmpp (Conjunto Homogéneo)</title>

</head>

<bean:define id="formulari" name="SustXComposicionForm" type="lopicost.spd.struts.form.SustXComposicionForm" />

<script type="text/javascript">	
		
		function volver()
		{
			var f = document.SustXComposicionForm;
			f.parameter.value='list';
			f.ACTIONTODO.value='LIST';
			f.submit();
		}	
		
		function grabar()
		{
			var f = document.SustXComposicionForm;

			if(f.filtroCodGtVmpp.value=='')
				alert('Falta indicar el GtVmpp (conjunto homogéneo)');
			if(f.filtroCodiLaboratorio.value=='')
				alert('Falta indicar el laboratorio');
		
			f.parameter.value='nuevo';
			f.ACTIONTODO.value='NUEVO_OK';
			f.submit();
		}	
		//función de carga del lookUp
		function doLookUpLabsBdConsejo(){				
			var loc = '/spd/LookUpLabsBdConsejo.do?parameter=initLabs&'+ 						//url de llamanda				
				'CallBackID=filtroCodiLaboratorio&'+			  			//Nombre del campo para el valor Id
				'CallBackNAME=nombreLab';			   		//Nombre del campo para el valor descriptivo
				//'CallBackType=nombreLab';			   		//Nombre del campo para el valor descriptivo
		
			//Importante que se realice en ventana nueva!!!!
			window.open(loc, 'LookUpLabsBdConsejo', 'dependent=yes,height=500,width=800,top=50,left=50,resizable=yes,scrollbars=yes' );
		}	
		
		//función de carga del lookUp
			
		
		
		function reloadFilters(){				
			f.submit();
		}	
		
				
		function reloadLab()
		{
			var f = document.SustXComposicionForm;
			//			alert(f.filtroCodiLaboratorio.value);
//			alert(f.filtroCodiLaboratorio2.value);
			f.filtroCodiLaboratorio.value=f.filtroCodiLaboratorio2.value;
			//	alert(f.filtroCodiLaboratorio.value);
			f.submit();
		}			

		
		function reload()
		{
			var f = document.SustXComposicionForm;
			//f.parameter.value="list";
			//f.ACTIONTODO.value="list";
			f.submit();
		}			
		
</script>

<body id="general">
	<!-- mostramos mensajes y errores, si existen -->
	<center>
		<h2>Edición sustitución</h2>
		<html:form action="/SustXComposicion.do" method="post">	


<div id="contingut">
<bean:define id="data" name="SustXComposicionForm" property="sustXComposicion"/>

     <html:hidden property="parameter" value="nuevo"/>
     <html:hidden property="ACTIONTODO" value="NUEVO"/>
     <html:hidden property="oidSustXComposicion" />
     
  
<table>
	<tr>
		<td>>Grupo VM (Principio activo)</td>
		<td>			
		<logic:notEmpty name="SustXComposicionForm" property="listaGtVm">	
		   	 <html:select property="listaGtVm"  value="<%= formulari.getFiltroCodGtVm() %>" onchange="submit()"   > 
		   	 	<html:option value="">Todos</html:option>
   					<html:optionsCollection name="SustXComposicionForm" property="listaGtVm" label="nomGtVm" value="codGtVm" />
				</html:select>
		     </logic:notEmpty>
		</td>	
	</tr>
		<tr>
			<td>Grupo VMPP  (Princ.Activo, dosis, forma y número de unidades) equivale al conjunto homogéneo</td>
			<td>		
			<logic:notEmpty name="SustXComposicionForm" property="listaGtVmpp">	
		   	 <html:select property="listaGtVmpp"  value="<%= formulari.getFiltroCodGtVmpp() %>" onchange="submit()"> 
		   	 	<html:option value="">Todos</html:option>
   					<html:optionsCollection name="SustXComposicionForm" property="listaGtVmpp" label="nomGtVmpp" value="codGtVmpp" />
				</html:select>
		     </logic:notEmpty>	
		</td>	
	</tr>	
	<tr>
		<td>Asignar el siguiente laboratorio:</td>
		<td>			
			<html:text name="SustXComposicionForm" readonly="true"  property="filtroCodiLaboratorio" styleClass="filtroCodiLaboratorio"/> - 
			
			<logic:notEmpty name="SustXComposicionForm" property="listaLabs">	
		   	 <html:select property="filtroCodiLaboratorio2"  value="<%= formulari.getFiltroCodiLaboratorio() %>" onchange="reloadLab()"> 
		   	 	<html:option value="">Todos</html:option>
   					<html:optionsCollection name="SustXComposicionForm" property="listaLabs" label="nombreLaboratorio" value="codigoLaboratorio" />
				</html:select>
		     </logic:notEmpty>	
			<a href="#" onclick="javascript:doLookUpLabsBdConsejo();">... Buscar por nombre</a>
		</div>
		<div>&nbsp;</div>
			
			
			
		</td>	
	</tr>	

	<tr>
		<td>Rentabilidad</td>
		<td><html:text name="SustXComposicionForm" property="rentabilidad" styleClass="rentabilidad" /></td>	
	</tr>	
	<tr>
		<td>Ponderacion</td>
		<td><html:text name="SustXComposicionForm" property="ponderacion" styleClass="ponderacion" /></td>	
	</tr>	
	<tr>
		<td>Comentarios</td>
		<td><html:text name="SustXComposicionForm" property="comentarios" styleClass="comentarios" /></td>	
	</tr>	


			
		
		
</table>

	<tr>
		<td>	
		<p class="botons">
			<input type="button" onclick="javascript:volver()" value="Volver"/>
			<input type="button" onclick="javascript:grabar()" value="Confirmar"/>
		</p>	
		</td>	
	</tr>
		
	
	</center>

</div>	

	</html:form>

</body>
</html>