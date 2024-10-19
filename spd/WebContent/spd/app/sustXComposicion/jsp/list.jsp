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
<title>Base para realizar sustituciones - Listado</title>
</head>


<bean:define id="formulari" name="SustXComposicionForm" type="lopicost.spd.struts.form.SustXComposicionForm" />





<script type="text/javascript">	


		function borrar(oidSustXComposicion)
		{
			var f = document.SustXComposicionForm;
			f.parameter.value='borrar';
			f.ACTIONTODO.value='CONFIRMAR';
			f.oidSustXComposicion.value= oidSustXComposicion;
			f.submit();
		}
		
		
		function editar(oidSustXComposicion)
		{
			var f = document.SustXComposicionForm;
			f.parameter.value='editar';
			f.ACTIONTODO.value='EDITA';
			f.oidSustXComposicion.value= oidSustXComposicion;
			f.submit();
		}
		
		function buscar()
		{
			var f = document.SustXComposicionForm;
			f.parameter.value='list';
			f.submit();	
		}
		
		function nuevo()
		{
			var f = document.SustXComposicionForm;
			f.parameter.value='nuevo';
			f.ACTIONTODO.value='NUEVO';
			f.submit();
		}	
		
		function asignarTodos()
		{
			var f = document.SustXComposicionForm;
			if(f.filtroCodiLaboratorio.value=='')
			{
				alert('Falta indicar el laboratorio a asignar');
				return;				
			}
				
			f.parameter.value='nuevoAsignacionMasiva';
			f.ACTIONTODO.value='NUEVO_MASIVA';
			f.submit();
		}	
		
		function desasignarTodos()
		{
			var f = document.SustXComposicionForm;
			if(f.filtroCodiLaboratorio.value=='')
			{
				alert('Falta indicar el laboratorio a desasignar');
				return;				
			}
			f.parameter.value='borradoMasivo';
			f.ACTIONTODO.value='BORRADO_MASIVO';
			f.submit();
		}		
		//función de carga del lookUp
		function doLookUpBdConsejo(){				
			var loc = '/spd/LookUpBdConsejo.do?parameter=init&'+ 						//url de llamanda				
				'CallBackID=cnOk&'+			  			//Nombre del campo para el valor Id
				'CallBackNAME=nombreConsejo';			   		//Nombre del campo para el valor descriptivo
		
			//Importante que se realice en ventana nueva!!!!
			window.open(loc, 'LookUpBdConsejo', 'dependent=yes,height=500,width=800,top=50,left=50,resizable=yes,scrollbars=yes' );
		}	
		
		//función de carga del lookUp
		function doLookUpLabsBdConsejo(){				
			var loc = '/spd/LookUpLabsBdConsejo.do?parameter=initLabs&'+ 						//url de llamanda				
				'CallBackID=filtroCodiLaboratorio&'+			  			//Nombre del campo para el valor Id
				'CallBackNAME=nombreLaboratorio';			   		//Nombre del campo para el valor descriptivo
				//'CallBackType=nombreLab';			   		//Nombre del campo para el valor descriptivo
		
			//Importante que se realice en ventana nueva!!!!
			window.open(loc, 'LookUpLabsBdConsejo', 'dependent=yes,height=500,width=800,top=50,left=50,resizable=yes,scrollbars=yes' );
		}	
		function reloadFilters(){				
			f.submit();
		}	
		
		function reloadLab()
		{
			var f = document.SustXComposicionForm;
			//			alert(f.filtroCodiLaboratorio.value);
//			alert(f.filtroCodiLaboratorio2.value);
			f.filtroCodiLaboratorio.value=f.filtroCodiLaboratorio2.value;
			//al seleccionar un lab ponemos la variable como true
			//	alert(f.filtroCodiLaboratorio.value);
			f.submit();
		}	
	
		function reloadCheckbox1()
		{
			var f = document.SustXComposicionForm;
			f.filtroCheckedLabsSoloAsignados.value=f.filtroLabsSoloAsignados.checked;
			f.filtroLabsSoloAsignados.value=f.filtroCheckedLabsSoloAsignados.value;
			f.submit();
		}	
		function reloadCheckbox2()
		{
			var f = document.SustXComposicionForm;
			f.filtroCheckedComposicionSinLabs.value=f.filtroComposicionSinLabs.checked;
			f.filtroComposicionSinLabs.value=f.filtroCheckedComposicionSinLabs.value;
			f.submit();
		}	
		function exportCSV()
		{

			var f = document.SustXComposicionForm;
			var loc = '/spd/Iospd/SustXConjExportData.do?parameter=specificPerform';			   	
	
		//Importante que se realice en ventana nueva!!!!
		window.open(loc, 'SustXComposicionExportData', 'dependent=yes,height=500,width=800,top=50,left=50,resizable=yes,scrollbars=yes' );
			
		}	
		
		function exportExcel()
		{
			var f = document.SustXComposicionForm;
			f.parameter.value='exportExcel';
			f.submit();   	
			f.parameter.value='list';  //volvemos a dejar list para que no se generen posteriormente al hacer submit
		}	
	</script>

<body id="general">

<html:form action="/SustXComposicion.do" method="post"  >	
<html:errors/>

<div id="contingut">
     <html:hidden property="parameter" value="list"/>
     <html:hidden property="ACTIONTODO" value="list"/>
     <html:hidden property="codGtVmpp" />
     <!-- html:hidden property="codiLab" / -->
     <!-- html:hidden property="nombreLab" / -->
     <html:hidden property="oidSustXComposicion" />
     <html:hidden property="filtroCheckedLabsSoloAsignados" />
     <html:hidden property="filtroCheckedComposicionSinLabs" />
     
     	<% String numPages = formulari.getNumpages()+""; %>
     	<% String currpage = formulari.getCurrpage()+""; %>
     <html:hidden property="numpages" value="<%= numPages %>"/>
	 <html:hidden property="currpage" value="<%= currpage %>"/>
	
   	<!-- mostramos mensajes y errores, si existen -->
	<logic:notEmpty name="SustXComposicionForm" property="errors">
		<font color="red"><ul>
			<u>Mensaje:</u>
				<logic:iterate id="error" name="SustXComposicionForm" property="errors" type="java.lang.String">
						<li><bean:write name="error"/></li>
				</logic:iterate>
			</ul></font>
	</logic:notEmpty>

	<fieldset >
		<table>
 		<th>
 		<div>
			Robot
			<logic:notEmpty name="SustXComposicionForm" property="listaRobots">	
		   	 <html:select property="filtroRobot"  value="<%= formulari.getFiltroRobot() %>" onchange="submit()"> 
		   	 	<html:option value="">Todos</html:option>
   					<html:optionsCollection name="SustXComposicionForm" property="listaRobots" label="nombreRobot" value="idRobot" />
				</html:select>
		     </logic:notEmpty>	
		</div>
		<div>&nbsp;</div>`
		<div>Texto a buscar 
		   	<html:text property="filtroTextoABuscar" alt="Texto a buscar" title="Texto a buscar" />
		</div>
		<div>Búsqueda por medicamento
			<html:text name="SustXComposicionForm" property="cnOk" styleClass="cnOk"/>-<html:text name="SustXComposicionForm" property="nombreConsejo" styleClass="nombreConsejo"/>
			<a href="#" onclick="javascript:doLookUpBdConsejo();">Buscar CN</a>
		 </div>	


		
		<div>
			Grupo VM  (Principio activo)
			<logic:notEmpty name="SustXComposicionForm" property="listaGtVm">	
		   	 <html:select property="filtroCodGtVm"  value="<%= formulari.getFiltroCodGtVm() %>" onchange="submit()"> 
		   	 	<html:option value="">Todos</html:option>
   					<html:optionsCollection name="SustXComposicionForm" property="listaGtVm" label="nomGtVm" value="codGtVm" />
				</html:select>
		     </logic:notEmpty>	
		</div>
		<div>
			Grupo VMP  (Principio activo, dosis y forma farmacéutica) 
			<logic:notEmpty name="SustXComposicionForm" property="listaGtVmp">	
		   	 <html:select property="filtroCodGtVmp"  value="<%= formulari.getFiltroCodGtVmp() %>" onchange="submit()" styleClass="select_corto"> 
		   	 	<html:option value="">Todos</html:option>
   					<html:optionsCollection name="SustXComposicionForm" property="listaGtVmp" label="nomGtVmp" value="codGtVmp" />
				</html:select>
		     </logic:notEmpty>	
		</div>	
		<div>
			Grupo VMPP  (Igual que Conjunto homogéneo - Principio activo, dosis, forma farmacéutica y número de unidades de dosificación)
			<logic:notEmpty name="SustXComposicionForm" property="listaGtVmpp">	
		   	 <html:select property="filtroCodGtVmpp"  value="<%= formulari.getFiltroCodGtVmpp() %>" onchange="submit()" styleClass="select_corto"> 
		   	 	<html:option value="">Todos</html:option>
   					<html:optionsCollection name="SustXComposicionForm" property="listaGtVmpp" label="nomGtVmpp" value="codGtVmpp" />	
				</html:select>
		     </logic:notEmpty>	
		</div>	
		<div>GtVmpp (Conjuntos homogéneos) disponibles del siguiente lab: 
			<html:text name="SustXComposicionForm" readonly="true"  property="filtroCodiLaboratorio" styleClass="filtroCodiLaboratorio"/> - 
			
			<logic:notEmpty name="SustXComposicionForm" property="listaLabs">	
		   	 <html:select property="filtroCodiLaboratorio2"  value="<%= formulari.getFiltroCodiLaboratorio() %>" onchange="reloadLab()" styleClass="select_corto"> 
		   	 	<html:option value="">Todos</html:option>
   					<html:optionsCollection name="SustXComposicionForm" property="listaLabs" label="nombreLaboratorio" value="codigoLaboratorio" />
				</html:select>
		     </logic:notEmpty>	
			<a href="#" onclick="javascript:doLookUpLabsBdConsejo();">... Buscar por nombre</a>
		</div>
		<div>&nbsp;</div>

		<div>GtVmpp (Conjuntos homogéneos) asignados al laboratorio
			<logic:equal name="SustXComposicionForm" property="filtroCheckedLabsSoloAsignados" value="false">
				<input type="checkbox" name="filtroLabsSoloAsignados"   onclick="reloadCheckbox1()" />
			</logic:equal>
			<logic:equal name="SustXComposicionForm" property="filtroCheckedLabsSoloAsignados" value="true">
				<input type="checkbox" name="filtroLabsSoloAsignados" checked onclick="reloadCheckbox1()" />
			</logic:equal>
		</div>
		<div>GtVmpp (Conjuntos homogéneos) SIN ASIGNAR a ningùn laboratorio
			<logic:equal name="SustXComposicionForm" property="filtroCheckedComposicionSinLabs" value="false">
				<input type="checkbox" name=filtroComposicionSinLabs   onclick="reloadCheckbox2()" />
			</logic:equal>
			<logic:equal name="SustXComposicionForm" property="filtroCheckedComposicionSinLabs" value="true">
				<input type="checkbox" name="filtroComposicionSinLabs" checked onclick="reloadCheckbox2()" />
			</logic:equal>
		</div>		
		<div><a href="javascript:asignarTodos();" >Asignar rentabilidad/lab a los conjuntos homogéneos listados</a></div>
<!--  		<div>Rentabilidad:<html:text name="SustXComposicionForm" property="rentabilidad" styleClass="rentabilidad"/></div>
		<div>Ponderación:<html:text name="SustXComposicionForm" property="ponderacion" styleClass="ponderacion"/></div>
-->					  
		<div><a href="javascript:desasignarTodos();">Dessignar la rentabilidad/lab de los conjuntos homogéneos listados</a></div>
		
  			</th>
		</tr>
	</table>
 </fieldset>
     <p >
     <input type="button" onclick="javascript:buscar();" value="Buscar"/> 
  	 <input type="button" onclick="javascript:nuevo();" value="Nuevo"/> 


</p>
    <!-- <p class="botonCerrar">
		 	<input type="button" onclick="javascript:window.close();" value="Cerrar"/> 
	</p>--> 
<table border="1">
    <tr>
    

    <!--<th>fecha</th> --> 
	<!--<th>Grupo VMP  (Principio activo, dosis y forma farmacéutica) </th> -->
		<th>Robot</th>
		<th>Conj Homog - Grupo VMPP (Princ.Activo, dosis, forma  y número de unidades de dosificación)</th>
        <th>Rentabilidad</th>
        <th>Ponderacion</th>
        <th>Nota</th>
      <!--    <th>Código Lab Asignado</th>-->
        <th>Nombre Lab Asignado</th>
        <!--  <th>Fecha creación</th>-->
        <!--  <th>última modificacion</th>-->
        <th>Comentario</th>
		<th></th>
		<th></th>
 		<th>CN</th>
		<th>Medicamento</th>
		<th>¿Sustituible?</th>
		    </tr>
     
 
 	<logic:notEmpty name="SustXComposicionForm" property="listaSustXComposicion">	

		<logic:iterate id="data" name="SustXComposicionForm" property="listaSustXComposicion" type="lopicost.spd.model.SustXComposicion" indexId="position">
		
        <tr>
          <!--  td %=data.getFecha()%></td-->
           <!--  <td><bean:write name="data" property="nomGtVmp" /><html:hidden name="data" property="codGtVmp"/></td> -->
            <td><bean:write name="data" property="idRobot" /></td>
            <td><bean:write name="data" property="nomGtVmpp" /><html:hidden name="data" property="codGtVmpp"/></td>
             <td><bean:write name="data" property="rentabilidad" /></td>
            <td><bean:write name="data" property="ponderacion" /></td>
            <td><bean:write name="data" property="nota" /></td>
          <!--         <td><bean:write name="data" property="codiLab" /></td>-->
            <td><bean:write name="data" property="nombreLab" /></td>
    <!--          <td><bean:write name="data" property="fechaCreacion" /></td>  -->
     <!--         <td><bean:write name="data" property="ultimaModificacion" /></td> -->
            <td><bean:write name="data" property="comentarios" /></td>
			<td>
				<p class="botons">
				<logic:notEqual name="data" property="oidSustXComposicion" value="0">	
				<input type="button" onclick="javascript:borrar('<bean:write name="data" property="oidSustXComposicion" />');"  value="Borrar"  />
				</a>
				</logic:notEqual>
				</p>
			</td>
			<td>
				<p class="botons">

				<logic:notEqual name="data" property="oidSustXComposicion" value="0">	
					<input type="button" onclick="javascript:editar('<bean:write name="data" property="oidSustXComposicion" />');"  value="Editar"  />
				</logic:notEqual>
				</p>
			</td>
			<td><bean:write name="data" property="cn7" /></td>
			<td><bean:write name="data" property="nombreMedicamento" /></td>
			<td><bean:write name="data" property="sustituible" /></td>        
			</tr>
    </logic:iterate>
    </logic:notEmpty>
   	
    
</table>

		  	<input type="button" value="<bean:message key="new window"/>"  onclick="javascript:exportExcel();"/>
		  	<input type="button" value="<bean:message key="Export.SustXConj.submit"/>" 	="javascript:document.forms[0].action='/spd/Iospd/ExportData.do?parameter=specificPerform'; document.forms[0].submit();" />

	<!--  paginación  -->
	<div>
		<logic:greaterThan name="formulari" property="numpages" value="1">
			<p align="center">
				<logic:greaterThan name="formulari" property="currpage" value="0">
					<a href="javascript:pageDown();" ><<</a>
				</logic:greaterThan>
				&nbsp;<input type="text" name="newPage" value="<%= formulari.getCurrpage()+1 %>" size="1" maxlength="4" onkeypress="gotoPage(this.value,<%=formulari.getNumpages()%>);">/<%= formulari.getNumpages() %>&nbsp;
				<logic:lessThan name="formulari" property="currpage" value="<%= String.valueOf(formulari.getNumpages() -1)%>">
					<a href="javascript:pageUp();" >>></a>
				</logic:lessThan>
			</p>
		</logic:greaterThan>
	</div>
	<!--  paginación   -->

		
		
	</fieldset>
	</html:form>
</div>	
</body>
</html>