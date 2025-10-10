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
<title>Listado sustituciones Robot</title>
</head>


<bean:define id="formulari" name="GestSustitucionesForm" type="lopicost.spd.struts.form.GestSustitucionesForm" />

<script type="text/javascript">	
		

		function borrarSustXResi(oidGestSustituciones, oidGestSustitucionesXResi)
		{
			var f = document.GestSustitucionesForm;
			f.parameter.value='borrarSustXResi';
			f.ACTIONTODO.value='CONFIRMAR';
			f.oidGestSustituciones.value= oidGestSustituciones;
			f.oidGestSustitucionesXResi.value= oidGestSustitucionesXResi;
			f.submit();
		}
		
		
		function editarSustXResi(oidGestSustituciones, oidGestSustitucionesXResi)
		{
			var f = document.GestSustitucionesForm;
			f.parameter.value='editarSustXResi';
			f.ACTIONTODO.value='EDITA';
			f.oidGestSustituciones.value= oidGestSustituciones;
			f.oidGestSustitucionesXResi.value= oidGestSustitucionesXResi;
			f.submit();
		}
		
		
		function borrar(oidGestSustituciones)
		{
			var f = document.GestSustitucionesForm;
			f.parameter.value='borrar';
			f.ACTIONTODO.value='CONFIRMAR';
			f.oidGestSustituciones.value= oidGestSustituciones;
			f.submit();
		}
		
		
		function editar(oidGestSustituciones)
		{
			var f = document.GestSustitucionesForm;
			f.parameter.value='editar';
			f.ACTIONTODO.value='EDITA';
			f.oidGestSustituciones.value= oidGestSustituciones;
			f.submit();
		}
		
		function buscar()
		{
			var f = document.GestSustitucionesForm;
			f.parameter.value='list';
			f.submit();	
		}
		
		function nuevo()
		{
			var f = document.GestSustitucionesForm;
	//		alert(f.filtroMedicamentoResi.value);
	//		alert(f.medicamentoResi.value);
	//		alert(f.filtroNombreCortoOK.value);
	//		f.cnResi.value=this.filtroNombreCortoOK.value;
	//		f.medicamentoResi.value=f.filtroMedicamentoResi.value;
			f.parameter.value='nuevo';
			f.ACTIONTODO.value='NUEVO';
			f.submit();
		}	
		
		function goIndex()
		{
			var f = document.GestSustitucionesForm;
			document.location.href='http://'+window.location.host+'<%=request.getContextPath()%>/';
			return true;
		}	
		function getContextPath() {
		return "<c:out value="${pageContext.request.contextPath}" />";
			}
		
		
		function actualizaBiblia(cnOk)
		{
			//var loc = '/spd//GestSustituciones.do?parameter=list&filtroNombreCorto='+ cnResi + '&filtroMedicamentoResi='+ resiMedicamento +'&oidDivisionResidenciaFiltro='+ oidDivisionResidencia	;				//url de llamanda				
			var loc = '/spd/SustXComposicion.do?parameter=list&cnOk='+ cnOk	;				//url de llamanda				
		//Importante que se realice en ventana nueva!!!!
		window.open(loc, 'actualizaBiblia', 'dependent=yes,height=500,width=800,top=50,left=50,resizable=yes,scrollbars=yes' );

		}	
		
</script>

<body id="general">

<html:form action="/GestSustituciones.do" method="post">	
<html:errors/>

<div id="contingut">
     <html:hidden property="parameter" value="list"/>
     <html:hidden property="ACTIONTODO" value="list"/>
     <html:hidden property="accion" />
     <html:hidden property="idDivisionResidencia" />
     <html:hidden property="oidGestSustituciones" />
     <html:hidden property="oidGestSustitucionesXResi" />
     <html:hidden property="medicamentoResi" />
     
     
      
     <html:hidden property="filtroCheckeadoExisteBdConsejo" />
     	<% String numPages = formulari.getNumpages()+""; %>
     	<% String currpage = formulari.getCurrpage()+""; %>
     <html:hidden property="numpages" value="<%= numPages %>"/>
	 <html:hidden property="currpage" value="<%= currpage %>"/>

	
   	<!-- mostramos mensajes y errores, si existen -->
	<logic:notEmpty name="GestSustitucionesForm" property="errors">
		<ul>
		<font color="red">
			<u>Mensaje:</u>
				<logic:iterate id="error" name="GestSustitucionesForm" property="errors" type="java.lang.String">
						<li><bean:write name="error"/></li>
				</logic:iterate>
		</font>
		</ul>
	</logic:notEmpty>

	<fieldset>
			<div>Texto a buscar 
		   	<html:text property="filtroNombreCorto" alt="Texto a buscar" title="Texto a buscar" />
		</div>
		<div>
			Residencia
			<logic:notEmpty name="GestSustitucionesForm" property="listaDivisionResidencia">	
			   	<% String div = formulari.getOidDivisionResidenciaFiltro()+""; %>
		   	 <html:select property="oidDivisionResidenciaFiltro"  value="<%= div %>"  onchange="submit()"> 
		   	 	<html:option value="">Todas</html:option>
   					<html:optionsCollection name="GestSustitucionesForm" property="listaDivisionResidencia" label="nombreDivisionResidencia" value="oidDivisionResidencia" />
				</html:select>
		     </logic:notEmpty>	
		</div>	
		<div>Medicamentos residencia
			<logic:notEmpty name="GestSustitucionesForm" property="listaMedicamentoResi">	
		   	 <html:select property="filtroMedicamentoResi"  value="<%= formulari.getFiltroMedicamentoResi() %>" onchange="submit()"> 
		   	 	<html:option value="">Todos</html:option>
   					<html:optionsCollection name="GestSustitucionesForm" property="listaMedicamentoResi" label="medicamentoResi" value="medicamentoResi" />
				</html:select>
		     </logic:notEmpty>
		     </div>	

		<div>Medicamentos sustitución
			<logic:notEmpty name="GestSustitucionesForm" property="listaNombreCortoOK">	
		   	 <html:select property="filtroNombreCortoOK"  value="<%= formulari.getFiltroNombreCortoOK() %>" onchange="submit()"> 
		   	 	<html:option value="">Todos</html:option>
   					<html:optionsCollection name="GestSustitucionesForm" property="listaNombreCortoOK" label="nombreCorto" value="nombreCorto" />
				</html:select>
		     </logic:notEmpty>	
		     </div>

		<div>Forma farmacéutica
			<logic:notEmpty name="GestSustitucionesForm" property="listaFormasFarmaceuticas">	
		   	 <html:select property="filtroFormaFarmaceutica"  value="<%= formulari.getFiltroFormaFarmaceutica() %>" onchange="submit()"> 
		   	 	<html:option value="">Todos</html:option>
   					<html:optionsCollection name="GestSustitucionesForm" property="listaFormasFarmaceuticas" label="nombreFormaFarmaceutica" value="codigoFormaFarmaceutica" />
				</html:select>
		     </logic:notEmpty>	
		</div>
		<div>Tipo acción SPD
			<logic:notEmpty name="GestSustitucionesForm" property="listaTiposAccion">	
		   	 <html:select property="filtroAccion"  value="<%= formulari.getFiltroAccion() %>" onchange="submit()"> 
		   	 	<html:option value="">Todos</html:option>
   					<html:optionsCollection name="GestSustitucionesForm" property="listaTiposAccion" label="idTipoAccion" value="idTipoAccion" />
				</html:select>
		     </logic:notEmpty>	
		</div>				

				
			<div>	
				<select name="filtroExisteBdConsejo" >
		   	 	<option value="0"  <%if (formulari.getFiltroExisteBdConsejo()!=null&&formulari.getFiltroExisteBdConsejo().equals("0")){%>selected<%}%>	>Muestra todos</option>
		   	 	<option value="1"  <% if(formulari.getFiltroExisteBdConsejo()!=null&&formulari.getFiltroExisteBdConsejo().equals("1")){%>selected<%}%>	>NO EXISTEN en BdConsejo</option>
   		   	 	<option value="2"  <% if(formulari.getFiltroExisteBdConsejo()!=null&&formulari.getFiltroExisteBdConsejo().equals("2")){%>selected<%}%>	>EXISTEN en BdConsejo</option>

				</select>



			</div>
			
			
		<div>&nbsp;</div>

 </fieldset>
     <p class="botonBuscar">
		<input type="button" onclick="javascript:buscar()"  value="Buscar"  />  
		<input type="button" onclick="javascript:nuevo()"  value="Nuevo"  />  
		<input type="button" onclick="javascript:goIndex()" value="Volver"/>
		<input type="button" onclick="javascript:window.close()" value="Cerrar"/>
	</p>

<table border="1">


    <tr>
        <th>CN (Resi)</th>
        <th>Medicamento (Resi)</th>
        <th>Residencia</th>
        <th>CN (1ª sust)</th>
        <th>Medicacmento (1ª sust) </th>
        <th>Nombre bolsa (SPD)</th>
        <th>Conj Homog (Vmpp - 1ª sust)</th>
        <th>Forma  (1ª sust) </th>
        <th>Acción  (SPD)</th>
        <th>Comentario</th>
	<!--	<th>ExisteBibliaRelacionada</th> -->    
		<th>Sustituible</th>
		<th>CN (Vademecum)</th>
		<th>Medicamento (Vademecum)</th>
		<th>Conj Homog (Vmpp Vademecum)</th>
		<th>Laboratorio (Vademecum)</th>
		<th>Ponderación total (Vademecum)</th>
		<th></th>
		<th></th>
     <th WIDTH="50%">Info</th>
     <th WIDTH="50%">Alertas</th>
     </tr>
     


 
 	<logic:notEmpty name="GestSustitucionesForm" property="listaSustituciones">	

		<logic:iterate id="data" name="formulari" property="listaSustituciones"  type="lopicost.spd.model.GestSustituciones"  indexId="position">


		
		<bean:define id="bdConsejo" name="data" property="bdConsejo"/>
		<bean:define id="bdConsejoBiblia" name="data" property="bdConsejoBiblia"/>


<%	
String mensajesInfoBase =(data.getMensajesInfo()!=null&&!data.getMensajesInfo().equals("")?"info":"");
String mensajesAlertaBase =(data.getMensajesAlerta()!=null&&!data.getMensajesAlerta().equals("")?"alerta":"");
	%>
       <tr class="">
            <td><bean:write name="data" property="cnResi" /></td>
            <td><bean:write name="data" property="medicamentoResi" /></td>
            <td>Por defecto</td>
			<td><a href="javascript:actualizaBiblia('<bean:write name="data" property="cnOk" />')"><bean:write name="data" property="cnOk" /></a></td>
           <!-- td><bean:write name="data" property="cnOk" /></td  -->
            <td><bean:write name="bdConsejo" property="nombreMedicamento" /></td>
            <td><bean:write name="data" property="nombreCorto" /></td>
            <td><bean:write name="data" property="nomGtVmppOk" /></td>
            <td><bean:write name="bdConsejo" property="nombreFormaFarmaceutica" /></td>
            <td><bean:write name="data" property="accion" /></td>
            <td><bean:write name="data" property="comentario" /></td>
         <logic:equal name="data" property="sustituible" value="1">
         <td>SI</td>
		</logic:equal>
         <logic:notEqual name="data" property="sustituible" value="1">
         <td>NO</td>
		</logic:notEqual>			
			
        <!-- <td><bean:write name="data" property="existeBdConsejoBiblia" /></td> -->    
      	<logic:notEmpty name="bdConsejoBiblia" property="cnConsejo">
          	<td><bean:write name="bdConsejoBiblia" property="cnConsejo" /></td>
            <td><bean:write name="bdConsejoBiblia" property="nombreMedicamento" /></td>
            <td><bean:write name="bdConsejoBiblia" property="nomGtVmpp" /></td>
            <td><bean:write name="bdConsejoBiblia" property="nombreLaboratorio" /></td>
            <td><bean:write name="bdConsejoBiblia" property="nota" /></td>
 		</logic:notEmpty>
   		<logic:empty name="bdConsejoBiblia" property="cnConsejo">
 			<td></td>
 			<td></td>
 			<td></td>
 			<td></td>
 			<td></td>
        </logic:empty>  
 
 			<td>
       		 <logic:lessThan name="data" property="sustXResiAsociados" value="1" >
				<p class="botons">
					<input type="button" onclick="javascript:borrar('<bean:write name="data" property="oidGestSustituciones" />');"  value="Borrar"  />
				</p>
			</logic:lessThan>
			</td>
			<td>
				<p class="botons">
						<input type="button" onclick="javascript:editar('<bean:write name="data" property="oidGestSustituciones" />');"  value="Editar"  />
				</p>
			</td>
            <td NOWRAP class="<%=mensajesInfoBase%>"><%= data.getMensajesInfo() %></td>
            <td NOWRAP class="<%=mensajesAlertaBase%>"><%= data.getMensajesAlerta() %></td>
        </tr>
        
         <logic:iterate id="dataXResi" name="data" property="listaSustitucionesXResi"  type="lopicost.spd.model.GestSustitucionesXResi" indexId="positionXResi">
        <logic:greaterEqual name="data" property="sustXResiAsociados" value="1" >
        <bean:define id="bdConsejoSustXResi" name="dataXResi" property="bdConsejoSustXResi"/>
        <bean:define id="bdConsejoSustXResiBiblia" name="dataXResi" property="bdConsejoSustXResiBiblia"/>
        
<%		String mensajesInfoSustXResi =(dataXResi.getMensajesInfo()!=null&&!dataXResi.getMensajesInfo().equals("")?"infoSustXResi":"");
		String mensajesAlertaSustXResi =(dataXResi.getMensajesAlerta()!=null&&!dataXResi.getMensajesAlerta().equals("")?"alertaSustXResi":"");
	%>

      <tr class="">
     
		<td> 
 				&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp|---
			</td>
			<td> 
				------------------->

		</td>            
            
		<td> 
				<bean:write name="dataXResi" property="idDivisionResidencia" />
		</td>            

		<td> 
				<bean:write name="dataXResi" property="cnOkSustXResi" />
		</td>            
		<td> 
				<bean:write name="bdConsejoSustXResi" property="nombreMedicamento" />
		</td>            
		<td> 
				<bean:write name="dataXResi" property="nombreCortoSustXResi" />
		</td>            
		<td> 
				<bean:write name="bdConsejoSustXResi" property="nomGtVmpp" />
		</td>            
		<td> 
				<bean:write name="bdConsejoSustXResi" property="nombreFormaFarmaceutica" />
		</td>            
		<td> 
				<bean:write name="dataXResi" property="accionSustXResi" />
		</td>            
		<td> 
				<bean:write name="dataXResi" property="comentarioSustXResi" />
		</td>            
		<!--  
		<td> 
			<logic:equal property="gtVmppDiferente" name="dataXResi" value="true"></logic:equal>
				<bean:write name="dataXResi" property="existeBdConsejoSustXResiBiblia" />
			<logic:equal property="gtVmppDiferente" name="dataXResi" value="true"></logic:equal>
		</td>             
		-->    

		  <logic:equal name="dataXResi" property="sustituibleXResi" value="1">
         <td>SI</td>
		</logic:equal>
         <logic:notEqual name="dataXResi" property="sustituibleXResi" value="1">
         <td>NO</td>
		</logic:notEqual>	

   		<logic:notEmpty name="bdConsejoSustXResiBiblia" property="cnConsejo">
            <td><bean:write name="bdConsejoSustXResiBiblia" property="cnConsejo" /></td>
            <td><bean:write name="bdConsejoSustXResiBiblia" property="nombreMedicamento" /></td>
            <td><bean:write name="bdConsejoSustXResiBiblia" property="nomGtVmpp" /></td>
            <td><bean:write name="bdConsejoSustXResiBiblia" property="nombreLaboratorio" /></td>
            <td><bean:write name="bdConsejoSustXResiBiblia" property="nota" /></td>
		</logic:notEmpty>
   		<logic:empty name="bdConsejoSustXResiBiblia" property="cnConsejo">
 			<td>-</td>
 			<td>-</td>
 			<td>-</td>
 			<td>-</td>
 			<td>-</td>
        </logic:empty>    
 			<td>
				<p class="botons">
				<input type="button" onclick="javascript:borrarSustXResi('<bean:write name="data" property="oidGestSustituciones" />', '<bean:write name="dataXResi" property="oidGestSustitucionesXResi" />');"  value="Borrar"  />
				</p>
			</td>
			<td>
				<p class="botons">
						<input type="button" onclick="javascript:editarSustXResi('<bean:write name="data" property="oidGestSustituciones" />', '<bean:write name="dataXResi" property="oidGestSustitucionesXResi" />');"  value="Editar"  />
				</p>
			</td>
            
		<td class="<%=mensajesInfoSustXResi%>" NOWRAP>
           <%= dataXResi.getMensajesInfo() %>

		</td>  
				<td class="<%=mensajesAlertaSustXResi%>" NOWRAP>
           <%= dataXResi.getMensajesAlerta() %>

		</td>       
        </tr>
        
             </logic:greaterEqual>
        </logic:iterate>

      </logic:iterate>
   </logic:notEmpty>
    
</table>

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
		
		
</div>	

	</html:form>
</body>
</html>