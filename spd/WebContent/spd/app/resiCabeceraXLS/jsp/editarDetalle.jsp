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
<title>Edición Cabecera Plantilla Residencia</title>
</head>
<bean:define id="formulari" name="FicheroResiForm" type="lopicost.spd.struts.form.FicheroResiForm" />

<script type="text/javascript">	
	
		function volver()
		{
			var f = document.FicheroResiForm;
			if (window.opener) 
			{
				window.opener.location.reload();
			}
			
			window.close();
			f.submit();
		}	
		
		function grabar(resiToma)
		{
			var horaMinutos = document.getElementById(resiToma).value;

		    // Verificar si el formato es válido (hh:mm)
		    if (/^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$/.test(horaMinutos)) {
		     // La hora tiene el formato válido, puedes enviarla directamente al servidor
				
				var f = document.FicheroResiForm;
				f.parameter.value='editar';
				f.ACTIONTODO.value='EDITA_OK';
				//f.hora.value = horaMinutos; // Enviar la hora con formato hh:mm
				//alert(f[resiToma].value) 
				f.submit();
				if (window.opener && !window.opener.closed) {
					window.opener.location.reload();
				}
				window.close();
		    } else {
		        // Si el formato no es válido, muestra un mensaje de error o realiza alguna acción para informar al usuario.
		        alert("El formato de hora debe ser hh:mm");
		      }
		}	

				
</script>

<body id="general">
    	<!-- mostramos mensajes y errores, si existen -->
 	 <ul>
        <logic:notEmpty name="FicheroResiForm" property="errors">
            <li class="error">
                <u>Mensaje:</u>
                <logic:iterate id="error" name="FicheroResiForm" property="errors" type="java.lang.String">
                    <span><bean:write name="error"/></span>
                </logic:iterate>
            </li>
        </logic:notEmpty>
    </ul>

	<center>
		<h2>Edición Cabecera Plantilla Residencia</h2>
		<html:form action="/ResiCabecerasXLS.do" >	


<div id="contingut">
     <html:hidden property="parameter" value="editar"/>
     <html:hidden property="ACTIONTODO" value="EDITAR"/>
     <html:hidden property="oidDivisionResidencia" /> 
     <html:hidden property="filtroProceso" />
     <html:hidden property="oidFicheroResiCabecera" /> 
     <html:hidden property="oidFicheroResiDetalle" />
     <html:hidden property="idProceso" /> 
 
 
<bean:define id="data" name="formulari" property="ficheroResiDetalleBean" type="lopicost.spd.struts.bean.FicheroResiBean" />
	
<table>	
	<bean:write name="data" property="idDivisionResidencia"/><br>
	<th></th><th>NombreToma </th>    
	<%
		int numeroDeTomas = data.getNumeroDeTomas(); // Obtener número de tomas desde el formulario
		int totalCampos = 24;
	
		String fieldName = "";
		int i=1;
		for (i = 1; i <= numeroDeTomas; i++) 
		{
	    	boolean soloLectura = i <= numeroDeTomas; // Determinar si el campo es de solo lectura
			fieldName = "resiToma" + i;
			String fieldValue = request.getParameter(fieldName); // Obtener el valor del campo del formulario
			%>
	<tr>		
		<td>
		<%
			out.print(i);
		%>
		</td>
		<td>
			<bean:write name="data" property="<%=fieldName%>"  />
		</td>
	</tr>
		<%
   		}
		%>  
    <tr>		
        <td>
        <% 
        String nuevaToma = "resiToma" + i; // Crear el nombre del campo para la nueva toma
        out.print( i +  " Nueva toma (formato hh:mm)" ); 
        %>  
        </td>
        <td>
            <input type="text" name="<%=nuevaToma%>" id="<%=nuevaToma%>" placeholder="hh:mm">
         	 <input type="button" onclick="javascript:grabar('<%=nuevaToma %>')" value="Confirmar"/>
          </td>
    </tr>
</table>	 
	</html:form>

			</div>	
					
				<table>	 
				 	<tr>
						<td>	
							<p class="botons">
							<input type="button" onclick="javascript:volver()" value="Volver"/>
							
						</p>	
						</td>	
					</tr>
				</table>
			</center>
</body>
</html>