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
<title>Creación de usuario SPD</title>
</head>

<bean:define id="formulari" name="PacientesForm" type="lopicost.spd.struts.form.PacientesForm" />
<script language="javaScript" src="/spd/spd/app/pacientes/js/pacientes.js"></script>


<body id="general">

<html:form action="/Pacientes.do" method="post">	
<html:errors/>

<div id="contingut">

    <html:hidden property="parameter" value="nuevo"/>
    <html:hidden property="ACTIONTODO"/>

  		<table class="detallePaciente">
		<tr >

		<td>Residencia</td><td>
		
			<html:select property="oidDivisionResidencia"  > 
				<html:option value="">Residencia</html:option>
	   			<html:optionsCollection name="formulari" property="listaDivisionResidencias" label="nombreDivisionResidencia" value="oidDivisionResidencia" />
			</html:select></td>
		
		</tr>
		<tr>
			<td>CIP</td><td><html:text name="formulari" property="CIP"/></td>
		</tr>
		<tr>
			<td>Residente</td><td>
                    <html:text property="nombre" styleId="nombreField" />
                    <html:errors property="nombre" />
                    
			 <html:text name="formulari" property="apellido1"  styleId="apellido1Field" />  <html:text name="formulari" property="apellido2"  styleId="apellido2Field"/></td>
			  <html:errors property="nombre" />
		</tr>
		<tr>
			<td>Mutua</td>
			<td><select name="mutua" >
			   	 	<option value="S"  >SI</option>
			   	 	<option value="N"  selected>NO</option>				</select>
			</td>
		</tr>	
		<tr>
			<td>Nº identidad</br>
			Nº Seg Social</br>
			id en la residencia
			</td>
			<td><html:text name="formulari" property="nIdentidad"/></br>
			<html:text name="formulari" property="segSocial"/></br>
			<html:text name="formulari" property="idPacienteResidencia"/></td>
		</tr>
		<tr>
			<td>Planta</br>Habitación</td>
			<td><html:text name="formulari" property="planta" /></br><html:text name="formulari" property="habitacion" /></td>
		</tr>
		<tr>
			<td>Hacer spd</td>
			<td><select name=spd" >
			   	 	<option value="S"  selected>SI</option>
			   	 	<option value="N"  >NO</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>Servir pañales</td>
			<td>
				<select name="bolquers" >
			   	 	<option value="S"  selected>SI</option>
			   	 	<option value="N"  >NO</option>
				</select>
			</td>
		</tr>		
		<tr>
			<td>Fecha alta</td>
			<td><html:text name="formulari" property="fechaAltaPaciente" />(DD/YY/MMMM)</td>
			
		</tr>
		<tr>
			<td>Comentarios</td>
			<td><html:textarea name="formulari" property="comentarios" /></td>
		</tr>
		<tr>
			<td>Estado</td>
			<td>
			<select name="estatus">
			    <option value=""></option>
			    <c:forEach items="${formulari.listaEstatusResidente}" var="bean">
			        <option value="${bean}" 
			            <c:if test="${bean == 'Alta'}">selected</c:if>
			        >
			            <c:out value="${bean}" />
			        </option>
			    </c:forEach>
			</select>
			</td>
		</tr>
		<tr>
			<td>	
				<p class="botons">
					<input type="button" onclick="javascript:volver()" value="Volver"/>
					<input type="button" onclick="javascript:goNuevoOK()" value="Grabar"/>
				</p>	
			</td>	
		</tr>	
	</table>
	

		         <script type="text/javascript">
        // Agregar placeholders a los campos de texto
        document.getElementById('nombreField').setAttribute('placeholder', 'Añadir el nombre');
        document.getElementById('apellido1Field').setAttribute('placeholder', 'Añadir primer apellido');
        document.getElementById('apellido2Field').setAttribute('placeholder', 'Añadir segundo apellido');
    </script>
	
</div>	
</html:form>

</body>
</html>