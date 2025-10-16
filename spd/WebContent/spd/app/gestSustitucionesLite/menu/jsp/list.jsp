<%@ page language="java" %>
<%@ page import="java.util.*" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<%@ page session="true" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html:html>
<HEAD>
<title></title>
<jsp:include page="/spd/jsp/global/head.jsp"/>



</HEAD>
<bean:define id="formulari" name="MenuForm" type="lopicost.spd.struts.form.MenuForm" />

<script type="text/javascript">	

		function getMenuByProfile(idPerfil)
		{
			var f = document.MenuForm;
			f.idPerfil.value= idPerfil;
			f.submit();
		}	
		
		function editar(idPerfil, idEnlace, accion)
		{
			var f = document.MenuForm;
			f.idEnlace.value=idEnlace;
			f.parameter.value=accion;
			f.idPerfil.value= idPerfil;
			
			f.submit();
		}	

</script>		
		

<script type="text/javascript">	
function abre(loc)
{
	alert(loc);
	window.open(loc, 'new', 'dependent=yes,height=500,width=800,top=50,left=50,resizable=yes,scrollbars=yes' );

}
</script>
<body>
<html:form action="/Menu.do" method="post">

     <html:hidden property="parameter" value="list"/>	
     <html:hidden property="idPerfil"/>
		<html:hidden property="idEnlace"/>
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
	
<h2> </h2>
 <fieldset>
 <table>     	
 <logic:iterate id="perfil" name="formulari" property="listaPerfiles" type="lopicost.spd.struts.bean.PerfilesBean" indexId="position">
 <tr>		
 
 <td><a href="#" onclick="javascript:getMenuByProfile('<bean:write name="perfil" property="idPerfil" />');" ><bean:write name="perfil" property="nombrePerfil" /></a></td>

 </tr>
 
</logic:iterate>
</table> 
 
</fieldset>	
		
		
 <table class="CSS_Table_Example" >
 <tr>
		<td class="primera">Perfil</td>
		<td class="primera">Enlace</td>
		<td class="segunda">Descripción</td>
		<td class="segunda">Asignado</td>
   </tr>

	   <c:forEach items="${formulari.listaMenu}" var="menu"> 
	   
		<tr>
			<td><c:out value="${menu.idPerfil}" ></c:out></td>
			<td><c:out value="${menu.enlace.idEnlace}" ></c:out></td>
			<td><c:out value="${menu.enlace.nombreEnlace}" ></c:out></td>
			<td>
					<c:if test="${menu.enlace.activo}">
					<a href="#"  onclick="javascript:editar('${menu.idPerfil}', '${menu.enlace.idEnlace}', 'borrar');"> Desasignar</a> 
					
					</c:if>
					<c:if test="${!menu.enlace.activo}"><a href="#"  onclick="javascript:editar('${menu.idPerfil}', '${menu.enlace.idEnlace}', 'nuevo');"> Asignar</a> </c:if>
				
				</td>
				
			</tr>	

		</c:forEach> 




		<a href="index_farm1.html" target=_blank>.</a>
		<a href="index_frm_rct2.html" target=_blank>.</a>
 </table>
 
</html:form>
</html:html>
