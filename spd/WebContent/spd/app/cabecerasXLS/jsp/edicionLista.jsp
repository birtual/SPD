<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/spd/jsp/global/headFragmento.jspf" %>

<!DOCTYPE html>
<html:html>
<head>

<title></title>
</HEAD>
<bean:define id="formulari" name="FicheroResiForm" type="lopicost.spd.struts.form.FicheroResiForm" />
   <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

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


	function moverPosicion(oidResiToma, accion)
	{
	    // Mostrar un cuadro de confirmación
	    var confirmacion = window.confirm("¿Estás seguro de que deseas desplazar la toma?");
	    
	    // Si el usuario hace clic en "Aceptar" (true)
	    if (confirmacion) {
			var f = document.FicheroResiForm;
			f.oidResiToma.value = oidResiToma;
			f.parameter.value = 'moverPosicion';
	        f.ACTIONTODO.value = accion;
	        f.submit();
	    } else {
	        // Si el usuario hace clic en "Cancelar", no hacer nada
	        return false;
	    }
	}	
		
	function nuevaToma()
	{
			 
		//alert('nuevaToma');
		var horaMinutos = document.getElementsByName("resiToma")[0].value;
		var resiTomaLiteral = document.getElementsByName('resiTomaLiteral')[0].value;
	    // Verificar si el formato es válido (hh:mm)
	    if (/^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$/.test(horaMinutos)) {
	     // La hora tiene el formato válido, puedes enviarla directamente al servidor
			
			var f = document.FicheroResiForm;
			f.parameter.value='nuevaToma';
			f.ACTIONTODO.value='NUEVA_OK';
				
			f.submit();
			if (window.opener && !window.opener.closed) {
				window.opener.location.reload();
			}

	    } else {
	        // Si el formato no es válido, muestra un mensaje de error o realiza alguna acción para informar al usuario.
	        alert("El formato de hora debe ser hh:mm");
	      }
	}	
		
	
	function editarHora(oidResiToma, idToma, horaToma, actiontodo) {
	    const regex = /^(?:[01][0-9]|2[0-3]):[0-5][0-9]$/;
	    if (!regex.test(horaToma)) {
	        alert("Formato inválido. Usa hh:mm (00:00 - 23:59)");
	        return;
	    }
	    return editarToma(oidResiToma, idToma, horaToma, actiontodo);
	}

    
	function editarNombre(oidResiToma, idToma, actiontodo)
	{
		 return editarToma(oidResiToma, idToma, '', actiontodo);
	}
	
		    
		function editarToma(oidResiToma, idToma, horaToma, actiontodo)
		{
			var f = document.FicheroResiForm;
				f.oidResiToma.value = oidResiToma;//alert(oidResiToma);
				//alert(horaToma);
				if(horaToma!='')
					f.horaToma.value = horaToma;
				f.idToma.value = idToma;//alert(idToma);
				f.parameter.value = 'edicionToma';
				f.ACTIONTODO.value=actiontodo;//alert(actiontodo);
		        f.submit();
		}	
		
		    

		
		function borrar(oidResiToma)
		{
		    // Mostrar un cuadro de confirmación
		    var confirmacion = window.confirm("¿Estás seguro de que deseas borrar la toma?");
		    // Si el usuario hace clic en "Aceptar" (true)
		    if (confirmacion) {
				var f = document.FicheroResiForm;
				f.oidResiToma.value = oidResiToma;
				f.parameter.value = 'borrar';
				f.ACTIONTODO.value='BORRAR_OK';
		        f.submit();
		    } else {
		        // Si el usuario hace clic en "Cancelar", no hacer nada
		        return false;
		    }
		}	
</script>		

<body>
<html:form action="/CabecerasXLS.do" method="post">

    <html:hidden property="parameter" value="edicionLista"/>
    <html:hidden property="ACTIONTODO"/>
    <html:hidden property="mode"/>				
    <html:hidden property="idProceso"/>
    <html:hidden property="idToma"/>				
    <html:hidden property="oidResiToma"/>
    <html:hidden property="numeroResiTomas"/>		
    <html:hidden property="oidDivisionResidencia" />	
	<html:hidden property="oidFicheroResiCabecera" />	
    <html:hidden property="oidFicheroResiDetalle"/>


	<p><h2>Edición de las tomas </h2></p>
   
	<!-- mostramos mensajes y errores, si existen -->
	<c:if test="${not empty formulari.errors}">
		<ul>
		<font color="red">
			<u>Mensaje:</u>
			<c:forEach items="${formulari.errors}" var="error"> 
				<li>${error}</li>
			</c:forEach> 
		</font>
		</ul>
	</c:if>
	
	<table >
	<%
		// Calcula el tamaño de la lista
		int totalIteraciones = (formulari.getListaTomasCabecera() != null) ? formulari.getListaTomasCabecera().size() : 0;
		int contador =0;
	%>
	
		<tr>
			<td>Nombre en bolsa:</td>
			<c:forEach items="${formulari.listaTomasCabecera}" var="toma"> 
			<td class="${toma.tipo}">
				<c:if test="${toma.edicionNombreToma}">
					<input type="button"  onclick="javascript:editarNombre('${toma.oidCabeceraXLS}', '${toma.idToma}', 'EDITAR_OK')" value="💾 Grabar"/>
					<input type="text" name="nombreToma" placeholder="${toma.nombreToma}" />
				</c:if>
				<c:if test="${not toma.edicionNombreToma}">
					<input type="button"   onclick="javascript:editarNombre('${toma.oidCabeceraXLS}', '${toma.idToma}', 'NOMBRETOMA')" value="Editar"/>
					<c:out value="${toma.nombreToma}" ></c:out>
				</c:if>
			</td>
			</c:forEach> 
			<td>
			<input type="text" name="resiTomaLiteral"  placeholder="nombre"> </td>
		</tr>	
		<tr>
			<td>Hora:</td>
			<c:forEach items="${formulari.listaTomasCabecera}" var="toma"> 
				<td>
				<c:if test="${toma.tipo == 'EXTRA'}" >
					<c:if test="${toma.edicionHoraToma}">
 					
       					<input type="button" name="horaToma"  onclick="editarHora('${toma.oidCabeceraXLS}', '${toma.idToma}', document.getElementById('horaToma').value, 'EDITAR_OK')" 
					       value="💾 Grabar" />
       					<input type="text" id="horaToma" name="horaToma" placeholder="${toma.horaToma}" maxlength="5" 
					       	pattern="([01][0-9]|2[0-3]):[0-5][0-9]" 
       						title="Formato válido: hh:mm (00:00 a 23:59)" />
       						

					</c:if>
					<c:if test="${not toma.edicionHoraToma}">
						<input type="button" name="horaToma"  onclick="javascript:editarHora('${toma.oidCabeceraXLS}', '${toma.idToma}', '${toma.horaToma}', 'HORATOMA')" value="Editar"/>
						
					</c:if>

				</c:if>
				<c:out value="${toma.horaToma}" ></c:out>
				</td>
			</c:forEach> 
				<td><input type="text" name="resiToma" placeholder="hh:mm" maxlength="5" pattern="([01][0-9]|2[0-3]):[0-5][0-9]"
       					title="Formato válido: hh:mm (00:00 a 23:59)" /> <br>
       					</td>
			
		</tr>	
		<!-- tr>	
			<td></td>
			<c:forEach items="${formulari.listaTomasCabecera}" var="toma"> 
				<td>
				<c:if test="${toma.desdeTomaPrimerDia}">
					El primer día comienza en esta toma
				</c:if>
				<c:if test="${toma.hastaTomaUltimoDia}">
					El último día acaba en esta toma
				</c:if>
				</td>
			</c:forEach> 
				<td></td>
		</tr-->		
		
		<c:if test="${idUsuario == 'admin'}">
		<tr>
			<td></td>
			<c:forEach items="${formulari.listaTomasCabecera}" var="toma"> 
				<td><c:out value="${toma.idToma}" ></c:out></td>
			</c:forEach> 
				<td>idToma (solo visible para admins)<br></td>
		</tr>		
		<tr>
			<td></td>
			<c:forEach items="${formulari.listaTomasCabecera}" var="toma"> 
				<td><c:out value="${toma.posicionEnVistas}" ></c:out></td>
			</c:forEach> 
				<td>Posición en vistas (solo visible para admins)<br></td>
		</tr>	
		<tr>
			<td></td>
			<c:forEach items="${formulari.listaTomasCabecera}" var="toma"> 
				<td><c:out value="${toma.posicionEnBBDD}" ></c:out></td>
			</c:forEach> 
				<td>Posición en bbdd (solo visible para admins)<br></td>
		</tr>	
		</c:if>
		
		<tr>
			<td></td>
			<c:forEach items="${formulari.listaTomasCabecera}" var="toma"> 
			<td>
			
			<c:if test="${toma.tipo == 'EXTRA'}" >
				<input type="button" onclick="javascript:borrar('${toma.oidCabeceraXLS}')" value="Borrar"/>
			</c:if>
			
			</td>
			
			</c:forEach> 
			<td><input type="button" onclick="javascript:nuevaToma()" value="Añadir nueva"/><br></td>
			
		</tr>	
 </table>
 
 

 
<div class="">	
<fieldset style="width: 50%;">
 	<p><h5>Información importante</h5>

		Orden en los SPD:<br>
		- Las tomas en robot estarán ordenadas por la hora indicada (hh:mm)</td>
		<br><br>
		Borrado de tomas:<br>
		- En las <span style="background-color: #00abfd;">tomas base</span> puede modificarse el nombre que figurará en la bolsa SPD<br>
		- Las <span style="background-color: #00ff00;">tomas extra</span> pueden borrarse en caso que no haya ningún residente con medicación en esa toma. También pueden desplazarse modificando la hora.</td>
	</p>	
</fieldset>	
	<p class="botons">
		<input type="button" class="btn primary" onclick="javascript:volver()" value="Cerrar"/>
	</p>	
</div>	
</html:form>
</html:html>
