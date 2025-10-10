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
<title>Gestión carga ficheros Robot</title>
</head>

<bean:define id="formulari" name="FicheroResiForm" type="lopicost.spd.struts.form.FicheroResiForm" />

<script type="text/javascript">	
		
		function goIndex()
		{
			var f = document.FicheroResiForm;
			document.location.href='http://'+window.location.host+'<%=request.getContextPath()%>/';
			return true;
		}	
		
		function getContextPath() 
		{
			return "<c:out value="${pageContext.request.contextPath}" />";
		}
		

		//inicializamos procesos y estado al cambiar de resi
		function submitResi()
		{
			var f = document.FicheroResiForm;
			f.filtroProceso.value='';
			f.filtroEstado.value='';
			f.submit();				
		}
		
		function submitProceso()
		{
			var f = document.FicheroResiForm;
			f.filtroEstado.value='';
			f.submit();	
		}	
		
		function borrar(oidFicheroResiCabecera)
		{
			var f = document.FicheroResiForm;
			f.parameter.value='borrar';
			f.ACTIONTODO.value='CONFIRMAR';
			f.oidFicheroResiCabecera.value= oidFicheroResiCabecera;
			f.submit();
		}
		
		function refrescar(oidFicheroResiCabecera)
		{
			var f = document.FicheroResiForm;
			f.parameter.value='refrescar';
			f.ACTIONTODO.value='REFRESCAR';
			f.oidFicheroResiCabecera.value= oidFicheroResiCabecera;
			f.submit();
		}		
	
		function goPacientes(oidFicheroResiCabecera)
		{
			
			var f = document.FicheroResiForm;
			f.action='Pacientes.do';
			f.parameter.value='listadoProceso';
			f.oidFicheroResiCabecera.value= oidFicheroResiCabecera;
			f.submit();
		}

		function goDetalle(oidDivisionResidencia, idProceso, oidFicheroResiCabecera)
		{
			
			var f = document.FicheroResiForm;
			f.action='FicheroResiDetalle.do';
			f.parameter.value='list';
			f.oidDivisionResidencia.value= oidDivisionResidencia;
			f.oidFicheroResiCabecera.value= oidFicheroResiCabecera;
			f.filtroProceso.value= idProceso;
			f.submit();
		}

		function go(oidFicheroResiCabecera, metodo)
		{
			
			var f = document.FicheroResiForm;
			f.parameter.value=metodo;
			f.oidFicheroResiCabecera.value= oidFicheroResiCabecera;
			f.submit();
		}
</script>
		
		

<body id="general">

<html:form action="/FicheroResiCabecera.do" method="post">	
<html:errors/>


<div id="contingut">
    <html:hidden property="parameter" value="list"/>
    <html:hidden property="ACTIONTODO" value="list"/>
    <html:hidden property="oidFicheroResiCabecera" />
	<% String numPages = formulari.getNumpages()+""; %>
    <% String currpage = formulari.getCurrpage()+""; %>
    <html:hidden property="numpages" value="<%= numPages %>"/>
	<html:hidden property="currpage" value="<%= currpage %>"/>
	
 	<fieldset>
	   	<!-- mostramos mensajes y errores, si existen -->
		<logic:notEmpty name="formulari" property="errors">
			<font color="red"><ul>
				<u>Mensaje:</u>
					<logic:iterate id="error" name="formulari" property="errors" type="java.lang.String">
							<li><bean:write name="error"/></li>
					</logic:iterate>
				</ul></font>
		</logic:notEmpty>
		
		<p><html:link page="/Iospd/Iospd.do?parameter=list&operation=FILTER">Gestión de carga de ficheros resi</html:link></p> 
				<!-- <p><a href="javascript:generarFicherosRobot();">Generar ficheros robot</a></p>  -->
				<!-- <p><a href="javascript:limpiarFiltrosResi();">Limpiar filtros listado</a></p>  -->
	
	
			<div>	
				<label for="Name">Residencia</label>	
				<html:select property="oidDivisionResidencia"  value="<%= formulari.getOidDivisionResidencia() %>" onchange="submitResi()"> 
				<html:option value="">Residencia</html:option>
	   			<html:optionsCollection name="formulari" property="listaDivisionResidenciasCargadas" label="nombreDivisionResidencia" value="oidDivisionResidencia" />
			</html:select>
			</div>

		   	<div>	
				<label for="apellido" accesskey="e">Proceso</label>	
		   	<html:select property="filtroProceso"  value="<%= formulari.getFiltroProceso() %>" onchange="submitProceso()"> 
   			<html:option value="">Todos</html:option>
   					<c:forEach items="${formulari.listaProcesosCargados}" var="bean"> 
			       		 <option value='${bean.idProceso}' ${formulari.filtroProceso == bean.idProceso ? 'selected' : ' '}><c:out value="${bean.idDivisionResidencia} - ${bean.idProceso}" ></c:out></option>   
    				</c:forEach> 
   				</html:select>
			</div>
	   	 	<div>	
				<label for="Estado">Estado</label>	
			<html:select property="filtroEstado"  value="<%= formulari.getFiltroEstado() %>" onchange="submit()"> 
	   			<html:option value="">Todos</html:option>	
				<c:forEach items="${formulari.listaEstadosCabecera}" var="bean"> 
			    	 <option value='${bean.idEstado}' ${formulari.filtroEstado == bean.idEstado ? 'selected' : ' '}><c:out value="${bean.idEstado}" ></c:out></option>   
    			</c:forEach> 
    		</html:select>		
			</div>

	    <p class="botonBuscar">
			<input type="button" onclick="javascript:submit()"  value="Buscar"  />  
			<!-- input type="button" onclick="javascript:nuevo()"  value="Nuevo"  /-->  
		</p> 

	
	</fieldset>

	<table border="1">
		<tr>
		 	<th>Residencia</th>
			<th>Id Proceso</th>
			<th>Estado</th>
			<th>Fecha Creación</th>
			<th>Filas totales</th>
		<%--	<th>Nombre Fichero Resi</th>  --%>
			<th>nº Cips Fichero Resi</th>
			<th>nº Cips no existentes <br>en Gestión pacientes</th>
			<th>nº Cips no existentes <br>en fichero resi</th>
			<th>nº mensajes Info</th>
			<th>nº mensajes Alertas</th>
		<%--		<th>Fecha Validacion Datos</th>--%>
			<th>fecha Creación Fichero Robot</th>
			<th>Usuario validación</th>
			<%--	<th>Nombre Fichero Robot</th>--%>
			<th>Cips Fichero Robot</th>
	   </tr>


 	<logic:iterate id="data" name="formulari" property="listaFicheroResiCabeceraBean" type="lopicost.spd.struts.bean.FicheroResiBean" indexId="position">
		
	<tr>
		<td><bean:write name="data" property="idDivisionResidencia" /></td>
		<td><a href="javascript:goDetalle('<bean:write name="data" property="oidDivisionResidencia" />', '<bean:write name="data" property="idProceso" />', '<bean:write name="data" property="oidFicheroResiCabecera" />');"><bean:write name="data" property="idProceso" /></a> <br>
		<!--  
		<a href="javascript:go('<bean:write name="data" property="oidFicheroResiCabecera" />', 'detalle');"><bean:write name="data" property="idProceso" /></a>
		 -->
		</td>
		<td><bean:write name="data" property="idEstado" /></td>
		<td><bean:write name="data" property="fechaHoraProceso" /></td>
		<td><bean:write name="data" property="filasTotales" /></td>
		<td><a href="javascript:goPacientes('<bean:write name="data" property="oidFicheroResiCabecera" />');"><bean:write name="data" property="cipsTotalesProceso" /></a></td>
		<td><a href="javascript:go('<bean:write name="data" property="oidFicheroResiCabecera" />', 'detalleNoExistentesBbdd');"><bean:write name="data" property="cipsNoExistentesBbdd" /></a></td>
		<td><a href="javascript:go('<bean:write name="data" property="oidFicheroResiCabecera" />', 'detalleNoExistentesFichero');"><bean:write name="data" property="cipsSpdResiNoExistentesEnProceso" /></a></td>
		<td><a href="javascript:go('<bean:write name="data" property="oidFicheroResiCabecera" />', 'detalleMensajesInfo');"><bean:write name="data" property="numeroMensajesInfo" /></a></td>
		<td><a href="javascript:go('<bean:write name="data" property="oidFicheroResiCabecera" />', 'detalleMensajesAlerta');"><bean:write name="data" property="numeroMensajesAlerta" /></a></td>
		<td><bean:write name="data" property="fechaCreacionFicheroXML" /></td>
		<td></td>
		<td><bean:write name="data" property="cipsFicheroXML" /></td>
 		<td>
			<p class="botons">
				<input type="button" onclick="javascript:borrar('<bean:write name="data" property="oidFicheroResiCabecera" />');"  value="Borrar"  />
			</p>
		</td>
		<td>
			<p class="botons">
				<input type="button" onclick="javascript:refrescar('<bean:write name="data" property="oidFicheroResiCabecera" />');"  value="Refrescar"  />
			</p>
		</td>
	</tr>
    </logic:iterate>

    
</table>
    <p class="botonBuscar">
			<input type="button" onclick="javascript:goIndex()" value="Volver"/>
	</p> 
		
		
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