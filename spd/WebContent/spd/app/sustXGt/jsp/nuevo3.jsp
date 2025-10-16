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
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<jsp:include page="/spd/jsp/global/head.jsp"/>
<script language="javaScript" src="/spd/spd/app/sustXGt/js/sustXGt.js"></script>
<title>Asignar Lab a una sustitución X GtVmpp (Conjunto Homogéneo)</title>


<style>
.ui-autocomplete {
    max-height: 200px; /* Limitar la altura del menº desplegable */
    overflow-y: auto;  /* Habilitar scroll vertical */
    overflow-x: hidden; /* Evitar scroll horizontal */
    z-index: 1000; /* Asegurarte de que sea visible */
}
    
        table {
            width: 60%; /* Ajusta el ancho */
            margin: 20px auto;
            border-collapse: collapse;
            font-family: Arial, sans-serif;
        }

        table th, table td {
            padding: 10px;
            border: 1px solid #ccc;
            text-align: left;
        }

        table th {
            background-color: #f5f5f5;
            font-weight: bold;
        }

        table tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        table tr:nth-child(odd) {
            background-color: #ffffff;
        }

        input[type="text"] {
            width: calc(100% - 20px);
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .botons {
            text-align: center;
            margin-top: 20px;
        }

        .botons input {
            margin: 0 5px;
            padding: 8px 16px;
            font-size: 14px;
            border: none;
            border-radius: 4px;
            background-color: #007bff;
            color: #ffffff;
            cursor: pointer;
        }

        .botons input:hover {
            background-color: #0056b3;
        }

        .select_corto {
            width: 100%;
        }
    </style>
    
    
    
</head>

<bean:define id="formulari" name="SustXGtForm" type="lopicost.spd.struts.form.SustXGtForm" />

<script type="text/javascript">	
		
		function volver()
		{
			var f = document.SustXGtForm;
			f.parameter.value='list';
			f.ACTIONTODO.value='LIST';
			f.submit();
		}	
		
		function grabar()
		{
			var f = document.SustXGtForm;

			if(f.filtroCodGtVmpp.value=='')
				alert('Falta indicar el GtVmpp (conjunto homogéneo)');
			if(f.filtroCodiLaboratorio.value=='')
				alert('Falta indicar el laboratorio');
		
			f.parameter.value='nuevo';
			f.ACTIONTODO.value='NUEVO_OK';
			f.submit();
		}	
</script>

<body id="general">
	<!-- mostramos mensajes y errores, si existen -->
	<center>
		<h2>Edición sustitución</h2>
		<html:form action="/SustXGt.do" method="post">	


<bean:define id="nivel3" name="formulari" property="nivel3"/>

<div id="contingut">

     <html:hidden property="parameter" value="nuevo"/>
     <html:hidden property="ACTIONTODO" value="NUEVO"/>
     <html:hidden property="oidSustXComposicion" />
     <html:hidden property="codGtVm" styleId="codGtVm" value="<%=formulari.getNivel3().getNivel2().getCodGtVm()%>"/>
 <table>
        <tr>
            <th>Descripción</th>
            <th>Valor</th>
        </tr>
        <tr>
            <td>GTVMP</td>
            <td><bean:write name="nivel3" property="nomGtVmp" /></td>
        </tr>
        <tr>
            <td>GTVMPP</td>
            <td><bean:write name="nivel3" property="nomGtVmpp" /></td>
        </tr>

        <tr>
            <td>Asignar el siguiente laboratorio:</td>
            <td>
                <div>
                    <input 
                        type="text" 
                        id="filtroNombreLaboratorio" 
                        name="filtroNombreLaboratorio" 
                        onkeypress="handleEnter(event)" 
                        value="<%= request.getParameter("filtroNombreLaboratorio") != null ? request.getParameter("filtroNombreLaboratorio") : "" %>" 
                        placeholder="Escribe 3 caracteres para buscar o asterisco para mostrar todos" 
                        class="select_corto"
                    />
                </div>
                <input 
                    type="hidden" 
                    id="filtroCodiLaboratorio" 
                    name="filtroCodiLaboratorio" 
                    value="<%= request.getParameter("filtroCodiLaboratorio") != null ? request.getParameter("filtroCodiLaboratorio") : "" %>"
                />
            </td>
        </tr>
        <tr>
            <td>Rentabilidad</td>
            <td><html:text name="formulari" property="rentabilidad" styleClass="rentabilidad" /></td>
        </tr>
        <tr>
            <td>Comentarios</td>
            <td><html:text name="formulari" property="comentarios" styleClass="comentarios" /></td>
        </tr>
    </table>

    <div class="botons">
        <input type="button" onclick="javascript:volver()" value="Volver"/>
        <input type="button" onclick="javascript:nuevoDesdeNivel3('<bean:write name="nivel3" property="codGtVmpp" />', 'NUEVO_OK')" value="Confirmar"/>
    </div>

	</html:form>

</body>
</html>