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
<title>Gestión carga ficheros Robot</title>
</head>


<bean:define id="formulari" name="PacientesForm" type="lopicost.spd.struts.form.PacientesForm" />


<script type="text/javascript">	

		function goIndex()
		{
			var f = document.PacientesForm;
			document.location.href='http://'+window.location.host+'<%=request.getContextPath()%>/';
			return true;
		}	
		function getContextPath() {
		return "<c:out value="${pageContext.request.contextPath}" />";
			}
		
	

		function buscar()
		{
			var f = document.PacientesForm;
			f.parameter.value='listadoProceso';
			f.submit();	
		}
		

		
		function goDetalle(oidPaciente)
		{
			
			var f = document.PacientesForm;
			//f.parameter.action='FicheroResiDetalle.do';
			f.parameter.value='detalle';
			f.oidPaciente.value= oidPaciente;
			f.submit();
		}
		
		
		</script>
		
		

<body id="general">

<html:form action="/Pacientes.do" method="post">	
<html:errors/>


<div id="contingut">
    <html:hidden property="parameter" value="listadoProceso"/>
    <html:hidden property="ACTIONTODO" value="list"/>
    <html:hidden property="oidPaciente" />
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
<tr>
	<th>Residencia</br>
		
		     
			<div>

	<%--
			<html:select property="listaDivisionResidencias"  value="<%= formulari.getIdDivisionResidencia() %>" onchange="submitResi()"> 
				<html:option value="">Residencia</html:option>
	   			<html:optionsCollection name="formulari" property="listaDivisionResidencias" label="nombreDivisionResidencia" value="idDivisionResidencia" />
			</html:select>
		
			<html:select property="filtroDivisionResidenciasCargadas"  value="<%= formulari.getFiltroDivisionResidenciasCargadas() %>" onchange="submit()"> 
				<html:option value="">Id Residencia</html:option>
	   			<html:optionsCollection name="formulari" property="listaDivisionResidenciasCargadas" label="nombreDivisionResidencia" value="idDivisionResidencia" />
			</html:select>
	
			<html:select property="oidDivisionResidencia"  value="<%= formulari.getOidDivisionResidencia() %>" onchange="submit()"> 
				<html:option value="">Residencia</html:option>
	   			<html:optionsCollection name="formulari" property="listaDivisionResidenciasCargadas" label="nombreDivisionResidencia" value="oidDivisionResidencia" />
			</html:select>

--%>		
				<html:select property="idProceso"  value="<%= formulari.getIdProceso() %>" onchange="submit()"> 
		   	 	<html:option value="">Proceso</html:option>
   					<c:forEach items="${formulari.listaProcesosCargados}" var="bean"> 
			       		 <option value='${bean.idProceso}' ${formulari.idProceso == bean.idProceso ? 'selected' : ' '}><c:out value="${bean.idDivisionResidencia} - ${bean.idProceso}" ></c:out></option>   
    				</c:forEach> 
   				</html:select>
		
			<html:select property="filtroEstado"  value="<%= formulari.getFiltroEstadosResidente() %>" onchange="submit()"> 
		   	 	<html:option value="">Estado</html:option>
   				
				<c:forEach items="${formulari.listaEstadosCabecera}" var="bean"> 
			    	 <option value='${bean.idEstado}' ${formulari.filtroEstado == bean.idEstado ? 'selected' : ' '}><c:out value="${bean.idEstado}" ></c:out></option>   
    			</c:forEach> 
    		</html:select>	
	<%--
		   	 <html:select property="idProceso"  value="<%= formulari.getIdProceso() %>" onchange="submit()"> 
		   	 	<html:option value="">Proceso</html:option>
   					<c:forEach items="${formulari.listaProcesosCargados}" var="bean"> 
			       		 <option value='${bean.idProceso}' ${FicheroResiForm.idProceso == bean.idProceso ? 'selected' : ' '}><c:out value="${bean.idProceso}" ></c:out></option>   
    				</c:forEach> 
   				</html:select>
		
	 		<html:select property="filtroProceso"  value="<%= formulari.getIdProceso() %>" onchange="submitProceso()"> 
		   	 	<html:option value="">Proceso</html:option>
   					<c:forEach items="${formulari.listaProcesosCargados}" var="bean"> 
			       		 <option value='${bean.idProceso}' ${formulari.filtroProceso == bean.idProceso ? 'selected' : ' '}><c:out value="${bean.idDivisionResidencia} - ${bean.idProceso}" ></c:out></option>   
    				</c:forEach> 
   				</html:select>
   				

			
			 
			<html:select property="idEstadoProceso"  value="<%= formulari.getIdEstadoProceso() %>" onchange="submit()"> 
		   	 	<html:option value="">Estado</html:option>
   				
				<c:forEach items="${formulari.listaEstadosCabecera}" var="bean"> 
			    	 <option value='${bean.idEstado}' ${formulari.idEstadoProceso == bean.idEstado ? 'selected' : ' '}><c:out value="${bean.idEstado}" ></c:out></option>   
    			</c:forEach> 
    				
    		</html:select>	
	 --%>
		</div>	
		
		
    	
 		
 		
			     
	    <p class="botonBuscar">
			<input type="button" onclick="javascript:buscar()"  value="Buscar"  />  
			<!-- input type="button" onclick="javascript:nuevo()"  value="Nuevo"  /-->  
		</p> 
		
		
		
		
		     
		     
		     
     </th>
	</tr>
	</fieldset>

	<table border="1">
		<tr>
		<th>Residencia</th>
		 	<th>CIP</th>
			<th>Nombre</th>
			<th>Apellido1</th>
			<th>Apellido2</th>
			<th>Planta</th>
			<th>Habitación</th>
			<th>SPD</th>
			<th>Estado</th>
			<th>Mensaje</th>
	   </tr>


 	<logic:iterate id="data" name="formulari" property="listaPacientesBean" type="lopicost.spd.struts.bean.PacienteBean" indexId="position">
		
	<tr>
		<td><bean:write name="data" property="idDivisionResidencia" /></td>
		<td><bean:write name="data" property="CIP" /></td>
		<td><bean:write name="data" property="nombre" /></td>
		<td><bean:write name="data" property="apellido1" /></td>
		<td><bean:write name="data" property="apellido2" /></td>
		<td><bean:write name="data" property="planta" /></td>
		<td><bean:write name="data" property="habitacion" /></td>
		<td><bean:write name="data" property="spd" /></td>
		<td><bean:write name="data" property="estatus" /></td>
		<td></td>
		<td>
		<p class="botons">
				<input type="button" onclick="javascript:detalle('<bean:write name="data" property="oidPaciente" />');"  value="detalle"  />
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