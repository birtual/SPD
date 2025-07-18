<%@ page language="java" %>
<%@ page import="java.util.*" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<%@ page session="true" %>
<%@page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html:html>
<HEAD>
<title></title>
<jsp:include page="/spd/jsp/global/head.jsp"/>

<script>
	function buscar()
	{
		var f = document.SpdLogForm;
		f.submit();
	}
</script>	
</HEAD>
<bean:define id="formulari" name="SpdLogForm" type="lopicost.spd.struts.form.SpdLogForm" />
	
		

<body>
<html:form action="/SpdLog.do" method="post">

    <html:hidden property="parameter" value="list"/>	
 	<html:hidden property="idDivisionResidencia"/>
	<html:hidden property="idApartado"/>
	<html:hidden property="idAccion"/>
	<html:hidden property="idSubAccion"/>
	<html:hidden property="numpages"/>
	<html:hidden property="currpage"/>
	<!-- mostramos mensajes y errores, si existen -->
	<logic:notEmpty name="formulari" property="errors">
		<ul>
		<font color="red">
			<u>Mensaje:</u>
				<logic:iterate id="error" name="formulari" property="errors" type="java.lang.String">
						<li><bean:write name="error"/></li>
				</logic:iterate>
		</font>
		</ul>
	</logic:notEmpty>
	
<h2></h2>
		<div>
			<label for="campoGoogle" accesskey="e">Texto a buscar </label>
		   	<html:text name="formulari" property="campoGoogle" />
		</div>
		<div class="clsMenuRow">
		<label for="fechaInicioFiltro" accesskey="e">Desde </label>
		<input type="text" name="fechaInicioFiltro" value="<%= formulari.getFechaInicioFiltro()!=null?formulari.getFechaInicioFiltro():"" %>" class="clsColorMenuRow" readonly>
			<A HREF="#"  onclick="calDateFormat='DD/MM/yyyy';setDateField(forms[0].fechaInicioFiltro); 
			top.newWin = window.open('<%=getServletContext().getContextPath()%>/spd/common/dbformslib/jscal/calendar.html','cal','WIDTH=270, HEIGHT=280,location=NO,scrolling=NO, toolbar=NO, menubar=NO')">
			<IMG SRC="<%=getServletContext().getContextPath()%>/spd/common/dbformslib/jscal/calendar.gif"  BORDER=0  alt='Seleccionar fecha inicio'></a>
			&nbsp;<A HREF="#"  onclick="JavaScript:document.forms[0].fechaInicioFiltro.value='';">
			<IMG SRC="<%=getServletContext().getContextPath()%>/spd/common/dbformslib/jscal/nocalendar.gif"  BORDER=0  alt='borrar fecha'></a>
		</div>
		</div>
		<div class="clsMenuRow">
		<label for="fechaFinFiltro" accesskey="e">Hasta</label>
		<input type="text" name="fechaFinFiltro" value="<%= formulari.getFechaFinFiltro()!=null?formulari.getFechaFinFiltro():"" %>" class="clsColorMenuRow" readonly>
			<A HREF="#"  onclick="calDateFormat='DD/MM/yyyy';setDateField(forms[0].fechaFinFiltro); 
			top.newWin = window.open('<%=getServletContext().getContextPath()%>/spd/common/dbformslib/jscal/calendar.html','cal','WIDTH=270, HEIGHT=280,location=NO,scrolling=NO, toolbar=NO, menubar=NO')">
			<IMG SRC="<%=getServletContext().getContextPath()%>/spd/common/dbformslib/jscal/calendar.gif"  BORDER=0  alt='Seleccionar fecha fin'></a>
			&nbsp;<A HREF="#"  onclick="JavaScript:document.forms[0].fechaFinFiltro.value='';">
			<IMG SRC="<%=getServletContext().getContextPath()%>/spd/common/dbformslib/jscal/nocalendar.gif"  BORDER=0  alt='borrar fecha'></a>
		</div>
		<div>
			<label for="usuario" accesskey="e">Usuario</label>
			<html:text name="formulari" property="idUsuarioFiltro"/>
		</div>

		<div>
			   <label for="listaDivisionResidencia" accesskey="e">Residencia</label>
			<logic:notEmpty name="formulari" property="listaDivisionResidencia">	
			  
		   	 <html:select property="oidDivisionResidenciaFiltro"   onchange="submit()"> 
		   	 	<html:option value="">Todas</html:option>
   					<html:optionsCollection name="formulari" property="listaDivisionResidencia" label="nombreDivisionResidencia" value="oidDivisionResidencia" />
				</html:select>
		     </logic:notEmpty>	
		</div>	
		<logic:notEmpty name="formulari" property="listaApartados">	
		<div>
		     <label for="listaApartados" accesskey="e">Apartado</label>
					<html:select property="idApartadoFiltro"  value="<%= formulari.getIdApartadoFiltro() %>" onchange="submit()"> 
			   	 		<html:option value="">Todos</html:option>
	  					<html:optionsCollection name="formulari" property="listaApartados" label="idApartado" value="idApartado" />
				</html:select>
		</div>	
	    </logic:notEmpty>	
		<logic:notEmpty name="formulari" property="listaAcciones">	
		<div>
		     <label for="listaAcciones" accesskey="e">Acción</label>
					<html:select property="idAccionFiltro"  value="<%= formulari.getIdAccionFiltro() %>" onchange="submit()"> 
			   	 		<html:option value="">Todos</html:option>
	  					<html:optionsCollection name="formulari" property="listaAcciones" label="idAccion" value="idAccion" />
				</html:select>
		</div>	
	    </logic:notEmpty>	
		<logic:notEmpty name="formulari" property="listaSubAcciones">	
		<div>
		     <label for="listaSubAcciones" accesskey="e">SubAcción</label>
					<html:select property="idSubAccionFiltro"  value="<%= formulari.getIdSubAccionFiltro() %>" onchange="submit()"> 
			   	 		<html:option value="">Todos</html:option>
	  					<html:optionsCollection name="formulari" property="listaSubAcciones" label="idSubAccion" value="idSubAccion" />
				</html:select>
		</div>	
	    </logic:notEmpty>		   
    <p class="botonBuscar">
		<input type="button" onclick="javascript:buscar()"	 	 value="Buscar" />  
		<input type="button" onclick="javascript:salir()" 		 value="Salir"  />  
	</p>
	
	
	
		
 <table class="CSS_Table_Example" >
 <tr>
		<td class="segunda">Fecha</td>
		<td class="primera">Usuario</td>
		<td class="primera">CIP</td>
		<td class="primera">Residencia</td>
		<td class="segunda">Apartado</td>
		<td class="segunda">Accion</td>
		<td class="segunda">SubAccion</td>
		<td class="segunda">Descripcion</td>
   </tr>

        
	   <c:forEach items="${formulari.lista}" var="spdLog"> 
	   
		<tr>

    
    
			<td><c:out value="${spdLog.fecha}" ></c:out></td>
			<td><c:out value="${spdLog.idUsuario}" ></c:out></td>
			<td><c:out value="${spdLog.CIP}" ></c:out></td>
			<td><c:out value="${spdLog.idDivisionResidencia}" ></c:out></td>
			<td><c:out value="${spdLog.idApartado}" ></c:out></td>
			<td><c:out value="${spdLog.idAccion}" ></c:out></td>
			<td><c:out value="${spdLog.idSubAccion}" ></c:out></td>
			<td><c:out value="${spdLog.descripcion}" escapeXml="false"></c:out></td>
			</tr>	

		</c:forEach> 




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
				
</html:form>
</html:html>
