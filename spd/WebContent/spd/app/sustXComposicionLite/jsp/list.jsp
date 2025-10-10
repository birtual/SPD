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
<title>Base para realizar sustituciones - Listado</title>
</head>


<bean:define id="formulari" name="SustXGtForm" type="lopicost.spd.struts.form.SustXGtForm" />


<script type="text/javascript">	

		function retorno(valor) {
	 		 alert('El valor de retorno es ' + valor);
		}
	
	
		function borrar(oidSustXComposicion)
		{
			var f = document.SustXGtForm;
			f.parameter.value='borrar';
			f.ACTIONTODO.value='CONFIRMAR';
			f.oidSustXComposicion.value= oidSustXComposicion;
			f.submit();
		}
		
		
		function editar(oidSustXComposicion)
		{
			var f = document.SustXGtForm;
			f.parameter.value='editar';
			f.ACTIONTODO.value='EDITA';
			f.oidSustXComposicion.value= oidSustXComposicion;
			f.submit();
		}

		
		
		function buscar()
		{
			var f = document.SustXGtForm;
			f.parameter.value='list';
			f.submit();	
		}
		

	</script>

<body id="general">

<html:form action="/SustXGt.do" method="post"  >	
<html:errors/>

<div id="contingut">
     <html:hidden property="parameter" value="list"/>
     <html:hidden property="ACTIONTODO" value="list"/>
     <html:hidden property="oidSustXComposicion" />

     
     	<% String numPages = formulari.getNumpages()+""; %>
     	<% String currpage = formulari.getCurrpage()+""; %>
     <html:hidden property="numpages" value="<%= numPages %>"/>
	 <html:hidden property="currpage" value="<%= currpage %>"/>
	
   	<!-- mostramos mensajes y errores, si existen -->
	<logic:notEmpty name="SustXGtForm" property="errors">
		<font color="red"><ul>
			<u>Mensaje:</u>
				<logic:iterate id="error" name="SustXGtVmpForm" property="errors" type="java.lang.String">
						<li><bean:write name="error"/></li>
				</logic:iterate>
			</ul></font>
	</logic:notEmpty>

	<fieldset >
		<table>
 		<th>
 		<div>
			Robot
		</div>
		<div>&nbsp;</div>`
		<div>Texto a buscar 
		</div>
		<div>Búsqueda por medicamento
		 </div>	


		<div>
x			Grupo VMP

		</div>	
		<div>
			Grupo VMPP 
		</div>	



		
  			</th>
		</tr>
	</table>
 </fieldset>

<table border="1">
    <tr>
    

 			<th>GTVMP</th>
			<th>GTVMPP</th>
        	<th>Lab</th>
        	<th>Ponderacion</th>
            <th>Farmacia</th>
            <th>dto farmacia</th>
			<th>cn6</th>
			<th>cn7</th>
			<th>sustituible</th>        
			<th>tolva</th>        
		    </tr>
     
 
 	<logic:notEmpty name="SustXGtVmpForm" property="listaGtVmp">	
		<logic:iterate id="padre" name="SustXGtVmpForm" property="listaGtVmp" type="lopicost.spd.model.SustXGtvmp" indexId="position">
		
        <tr>
            <td><bean:write name="padre" property="nomGtVmp" /><html:hidden name="padre" property="codGtVmp"/></td>
            <td></td>
            <td><bean:write name="padre" property="nombreLab" /></td>
            <td><bean:write name="padre" property="ponderacion" /></td>
            <td>Farmacia</td>
            <td>dto farmacia</td>
			<td>cn6</td>
			<td>cn7</td>
			<td>sustituible</td>        
			<td>tolva</td>        
			<td>
				<p class="botons">
				<logic:notEqual name="padre" property="oidSustXComposicion" value="0">	
					<input type="button" onclick="javascript:editar('<bean:write name="padre" property="oidSustXComposicion" />');"  value="Editar"  />
				</logic:notEqual>
				</p>
			</td>
        </tr>
    </logic:iterate>
    </logic:notEmpty>


			<td><bean:write name="padre" property="cn6" /></td>
			<td><bean:write name="padre" property="cn7" /></td>
			<td><bean:write name="padre" property="sustituible" /></td>        
			<td><bean:write name="padre" property="tolva" /></td>        

   	
    
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
		
		
		
	</fieldset>
	</html:form>
</div>	
</body>
</html>