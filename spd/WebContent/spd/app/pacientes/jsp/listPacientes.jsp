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
<script language="javaScript" src="/spd/spd/app/pacientes/js/pacientes.js"></script>



<body id="general">

<html:form action="/Pacientes.do" method="post">	
<html:errors/>

<div>
    <html:hidden property="parameter" value="list"/>
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

		<% String var = formulari.getOidDivisionResidencia()+""; %>
		
		<div>
		<label for="campoGoogle" accesskey="e">Residencia</label>
		
			<html:select property="oidDivisionResidencia"  value="<%= var %>" onchange="submit()"> 
				<html:option value="">Residencia</html:option>
	   			<html:optionsCollection name="formulari" property="listaDivisionResidencias" label="nombreDivisionResidencia" value="oidDivisionResidencia" />
			</html:select>
		</div>	
		<div>
			<label for="campoGoogle" accesskey="e">Texto a buscar </label>
		   	<html:text name="formulari" property="campoGoogle" />
		</div>
		<div>		
			<label for="campoGoogle" accesskey="e">Activo/Inactivo</label>
			<html:select property="filtroEstadosResidente"  value="<%= formulari.getFiltroEstadosResidente() %>" onchange="submit()"> 
		   	<html:option value=""></html:option>
 				<c:forEach items="${formulari.listaEstadosResidente}" var="bean"> 
			    	 <option value='${bean}' ${formulari.filtroEstadosResidente == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    			</c:forEach> 
    		</html:select>	
		</div>     
		<div>		
		<label for="campoGoogle" accesskey="e">Estado</label>
			<html:select property="filtroEstatusResidente"  value="<%= formulari.getFiltroEstatusResidente() %>" onchange="submit()"> 
		   	<html:option value=""></html:option>
  				<c:forEach items="${formulari.listaEstatusResidente}" var="bean"> 
			    	 <option value='${bean}' ${formulari.filtroEstatusResidente == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
    			</c:forEach> 
    		</html:select>	
		</div> 
		<div>		
    		<label for="campoGoogle" accesskey="e">SPD</label>		
    		<html:select property="filtroEstadosSPD"  value="<%= formulari.getFiltroEstadosSPD() %>" onchange="submit()"> 
 			    	 <option value='%' ${formulari.filtroEstadosSPD == '%' ? 'selected' : ' '}>Todos</option>   
 			    	 <option value='S' ${formulari.filtroEstadosSPD == 'S' ? 'selected' : ' '}>S</option>   
 			    	 <option value='N' ${formulari.filtroEstadosSPD == 'N' ? 'selected' : ' '}>N</option>   
				</html:select>	

		</div> 
		<div>		
    		<label for="campoGoogle" accesskey="e">Pañales</label>		
    		<html:select property="filtroBolquers"  value="<%= formulari.getFiltroBolquers() %>" onchange="submit()"> 
 			    	 <option value='%' ${formulari.filtroBolquers== '%' ? 'selected' : ' '}>Todos</option>   
 			    	 <option value='S' ${formulari.filtroBolquers == 'S' ? 'selected' : ' '}>S</option>   
 			    	 <option value='N' ${formulari.filtroBolquers == 'N' ? 'selected' : ' '}>N</option>   
				</html:select>	

		</div> 
		<div>		
    		<label for="campoGoogle" accesskey="e">Mutua</label>		
    		<html:select property="filtroMutua"  value="<%= formulari.getFiltroMutua() %>" onchange="submit()"> 
 			    	 <option value='%' ${formulari.filtroMutua == '%' ? 'selected' : ' '}>Todos</option>   
 			    	 <option value='S' ${formulari.filtroMutua == 'S' ? 'selected' : ' '}>S</option>   
 			    	 <option value='N' ${formulari.filtroMutua == 'N' ? 'selected' : ' '}>N</option>   
				</html:select>	

		</div> 
		<div> 
		<p class="botonBuscar">
			<input type="button" onclick="javascript:buscar()"  value="Buscar"  />  
			<input type="button" onclick="javascript:goNuevoCIP();"  value="Nuevo"  />
			<!-- input type="button" onclick="javascript:nuevo()"  value="Nuevo"  /-->  
		</p> 
		</div> 

	

	<table  id="listaPacientesBean" align="center" border="1">
		<tr>
		 	<th>Residencia <a href="#" class="arrow" onclick="sortTable('listaPacientesBean', 0, true)"> &uarr;</a> <a href="#" class="arrow" onclick="sortTable('listaPacientesBean', 0, false)">&darr;</a></th>
		 	<th>CIP <a href="#" class="arrow" onclick="sortTable('listaPacientesBean', 1, true)"> &uarr;</a> <a href="#" class="arrow" onclick="sortTable('listaPacientesBean', 1, false)">&darr;</a></th>
			<th>Nombre <a href="#" class="arrow" onclick="sortTable('listaPacientesBean', 2, true)"> &uarr;</a> <a href="#" class="arrow" onclick="sortTable('listaPacientesBean', 2, false)">&darr;</a></th>
			<th>Apellido1 <a href="#" class="arrow" onclick="sortTable('listaPacientesBean', 3, true)"> &uarr;</a> <a href="#" class="arrow" onclick="sortTable('listaPacientesBean', 3, false)">&darr;</a></th>
			<th>Apellido2 <a href="#" class="arrow" onclick="sortTable('listaPacientesBean', 4, true)"> &uarr;</a> <a href="#" class="arrow" onclick="sortTable('listaPacientesBean', 4, false)">&darr;</a></th>
			<th>TSI<a href="#" class="arrow" onclick="sortTable('listaPacientesBean', 5, true)"> &uarr;</a> <a href="#" class="arrow" onclick="sortTable('listaPacientesBean', 5, false)">&darr;</a></th> 		
			<th>Planta <a href="#" class="arrow" onclick="sortTable('listaPacientesBean', 6, true)"> &uarr;</a> <a href="#" class="arrow" onclick="sortTable('listaPacientesBean', 6, false)">&darr;</a></th>
			<th>Habitación <a href="#" class="arrow" onclick="sortTable('listaPacientesBean', 7, true)"> &uarr;</a> <a href="#" class="arrow" onclick="sortTable('listaPacientesBean', 7, false)">&darr;</a></th>
			<th>SPD <a href="#" class="arrow" onclick="sortTable('listaPacientesBean', 8, true)"> &uarr;</a> <a href="#" class="arrow" onclick="sortTable('listaPacientesBean', 8, false)">&darr;</a></th> 
			<th>Pañales <a href="#" class="arrow" onclick="sortTable('listaPacientesBean', 9, true)"> &uarr;</a> <a href="#" class="arrow" onclick="sortTable('listaPacientesBean', 9, false)">&darr;</a></th> 
			<!-- th>Activo <a href="#" class="arrow" onclick="sortTable('listaPacientesBean', 10, true)"> &uarr;</a> <a href="#" class="arrow" onclick="sortTable('listaPacientesBean', 10, false)">&darr;</a></th> -->
			<th>Estado <a href="#" class="arrow" onclick="sortTable('listaPacientesBean', 10, true)"> &uarr;</a> <a href="#" class="arrow" onclick="sortTable('listaPacientesBean', 10, false)">&darr;</a></th>
			<th width="30%">Comentarios <a href="#" class="arrow" onclick="sortTable('listaPacientesBean', 11, true)"> &uarr;</a> <a href="#" class="arrow" onclick="sortTable('listaPacientesBean', 11, false)">&darr;</a></th>
			<th>Acciones</th>
	   </tr>


 	<logic:iterate id="data" name="formulari" property="listaPacientesBean" type="lopicost.spd.struts.bean.PacienteBean" indexId="position">
		
	<tr>
		<td><bean:write name="data" property="idDivisionResidencia" /></td>
		<td align="center"><a href="javascript:goDiscrepancias('<bean:write name="data" property="oidPaciente" />')"><bean:write name="data" property="CIP" /></a></td> 
		<td><bean:write name="data" property="nombre" /></td>
		<td><bean:write name="data" property="apellido1" /></td>
		<td><bean:write name="data" property="apellido2" /></td>
		<td align="left">
				<logic:notEqual name="data" property="segSocial" value="···">
				<a href="javascript:goTratamientoRct('<bean:write name="data" property="oidPaciente" />')"><bean:write name="data" property="segSocial" /></a>
				</logic:notEqual>
		
		
		</td> 
		
		<td><bean:write name="data" property="planta" /></td>
		<td><bean:write name="data" property="habitacion" /></td>
		<td align="center"><a href="javascript:goTratamientosSPD('<bean:write name="data" property="oidPaciente" />')"><bean:write name="data" property="spd" /></a></td> 
		<td align="center"><a href="javascript:goBolquers('<bean:write name="data" property="oidPaciente" />')"><bean:write name="data" property="bolquers" /></a></td> 
		<!--td><bean:write name="data" property="activo" /></td> -->
		<td><bean:write name="data" property="estatus" /></td>
		<td><bean:write name="data" property="comentarios" /></td>
		<td>
			<p class="botons">
				<input type="button" onclick="javascript:goDetalle('<bean:write name="data" property="oidPaciente" />');"  value="Detalle"  />
				<input type="button" onclick="javascript:goEditar('<bean:write name="data" property="oidPaciente" />');"  value="Editar"  />
			</p>
		</td>

	
	</tr>
	
	
    </logic:iterate>
</table>

    

    <p class="botonBuscar">
		<input type="button" class="azulCielo" value="Salir" onclick="javascript:salir();" />
	</p> 
		
		
			<!--  paginación  -->
								<logic:greaterThan name="formulari" property="numpages" value="1">
									<table border="0" align="center" width="100%">
										<tr>
											<td align="center">
												<logic:greaterThan name="formulari" property="currpage" value="0">
													<a href="javascript:pageDown();" ><<</a>
												</logic:greaterThan>
												&nbsp;<input type="text" name="newPage" value="<%= formulari.getCurrpage()+1 %>" size="1" maxlength="4" onkeypress="gotoPage(this.value,<%=formulari.getNumpages()%>);">/<%= formulari.getNumpages() %>&nbsp;
												<logic:lessThan name="formulari" property="currpage" value="<%= String.valueOf(formulari.getNumpages() -1)%>">
													<a href="javascript:pageUp();" >>></a>
												</logic:lessThan>
											</td>
										</tr>
									</table>
								</logic:greaterThan>
		
			<!--  paginación   -->
	</fieldset>
			
	</div>		
	
	</html:form>

</body>
</html>