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
			alert(' 1 ' );
			var horaMinutos = document.getElementById(resiToma).value;
			alert(' horaMinutos ' + horaMinutos);
		    // Verificar si el formato es válido (hh:mm)
		    if (/^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$/.test(horaMinutos)) {
		     // La hora tiene el formato válido, puedes enviarla directamente al servidor
				
				var f = document.FicheroResiForm;
				alert('05 ' + f.resiTomaLiteral.value);	
			    
				//alert('1 ' + f.resiTomaLiteral.value);
				f.parameter.value='editar';
				f.ACTIONTODO.value='EDITA_OK';
				//f.hora.value = horaMinutos; // Enviar la hora con formato hh:mm
				//alert(f[resiToma].value) 
			alert(' grabar ' + f.ACTIONTODO.value);
				f.submit();
				if (window.opener && !window.opener.closed) {
					window.opener.location.reload();
				}

				//window.close();
		    } else {
		        // Si el formato no es válido, muestra un mensaje de error o realiza alguna acción para informar al usuario.
		        alert("El formato de hora debe ser hh:mm");
		      }
		}	

		function borrar(resiToma)
		{
			var f = document.FicheroResiForm;
			f.parameter.value='editar';
			f.ACTIONTODO.value='BORRAR_OK';
			f.resiToma.value=resiToma;

			f.submit();
			if (window.opener && !window.opener.closed) {
				window.opener.location.reload();
			}
		}	
		
		function moverPosicion(accion, posicion)
		{
			var f = document.FicheroResiForm;
			f.parameter.value='moverPosicion';
			f.ACTIONTODO.value=accion;
			f.parameter.value='moverPosicion';
			f.submit();
			if (window.opener && !window.opener.closed) 
			reload();
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
		<html:form action="/CabecerasXLS.do" >	


<div id="contingut">
     <html:hidden property="parameter" value="editar"/>
     <html:hidden property="ACTIONTODO" /> 
     <html:hidden property="oidDivisionResidencia" /> 
     <html:hidden property="filtroProceso" />
     <html:hidden property="oidFicheroResiCabecera" /> 
     <html:hidden property="oidFicheroResiDetalle" />
     <html:hidden property="idProceso" /> 
 	 <html:hidden property="resiToma" />
 
<bean:define id="data" name="formulari" property="ficheroResiDetalleBean" type="lopicost.spd.struts.bean.FicheroResiBean" />
	
<table class="tablaResi">	
	<bean:write name="data" property="idDivisionResidencia"/><br>
	<th></th><th></th><th>NombreToma </th>    
	<%
		int numeroDeTomasBase = data.getNumeroDeTomasBase(); // Obtener número de tomas BASE desde el formulario
		int numeroDeTomas = data.getNumeroDeTomas(); // Obtener número de tomas totales desde el formulario
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
		<td><a href="javascript:moverPosicion('SUBIR', '<%=i%>');">subir</a> - <a href="javascript:moverPosicion('BAJAR', '<%=i%>');">bajar</a></td>
		<td>
		<%
			out.print(i);
		%>
		
		
		<td>
			<bean:write name="data" property="<%=fieldName%>"  />
		</td>
		<td>
<% 
        if(i>numeroDeTomasBase)
        {
%> 
		<input type="button" onclick="javascript:borrar('<%= i %>')" value="Borrar"/>
		
<% 
        }
%> 		
		</td>
	</tr>
		<%
   		}
		%>  
    <tr>		
       <td></td> <td>
        <% 
        String nuevaToma = "resiToma" + i; // Crear el nombre del campo para la nueva toma
       
        out.print( i +  " Nueva toma (formato hh:mm)" ); 
        %>  
        </td> 
        <td>
            <input type="text" name="<%=nuevaToma%>" id="<%=nuevaToma%>" placeholder="hh:mm"> 
          </td>
        <td>
            <input type="text" name="resiTomaLiteral"  > 
         	 <input type="button" onclick="javascript:grabar('<%=nuevaToma %>')" value="Confirmar"/>
          </td>
    </tr>
</table>	 
	</html:form>

			</div>	
					

			</center>
<div class="">	
							<p class="botons">
							<input type="button" class="btn primary" onclick="javascript:volver()" value="Volver"/>
							
						</p>	
</div>	
</body>
</html>