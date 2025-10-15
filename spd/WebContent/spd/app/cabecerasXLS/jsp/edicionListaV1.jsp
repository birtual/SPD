<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<!DOCTYPE html>
<html:html>
<HEAD>
<title>${pageTitle != null ? pageTitle : 'Título por defecto'}</title>
<jsp:include page="/spd/jsp/global/head.jsp"/>
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
			//alert('1');
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
    <html:hidden property="oidResiToma"/>
    <html:hidden property="numeroResiTomas"/>		
    <html:hidden property="oidDivisionResidencia" />	
	<html:hidden property="oidFicheroResiCabecera" />	


	<p><h2>Edición de las tomas </h2></p>
   
	<!-- mostramos mensajes y errores, si existen -->
	<c:if test="${not empty formulari.errors}">
		<ul>
		<font color="red">
			<u>Mensaje:</u>
			<c:forEach items="${formulari.errors}" var="error"> 
				<li>"${error}"</li>
			</c:forEach> 
		</font>
		</ul>
	</c:if>
	
	<table class="detalle">
	<%
		// Calcula el tamaño de la lista
		int totalIteraciones = (formulari.getListaTomasCabecera() != null) ? formulari.getListaTomasCabecera().size() : 0;
		int contador =0;
	%>
	
		<tr>
			<c:forEach items="${formulari.listaTomasCabecera}" var="toma"> 
			<td class="${toma.tipo}">

				<% 
			  	contador++; 
				if(contador>1)
				{
					%>
					<i class="fa fa-arrow-left action-icon" onclick="javascript:moverPosicion('${toma.oidCabeceraXLS}', 'SUBIR')"  title="más pronto"></i> 
					<% 
				}
				%>
				<c:out value="${toma.nombreToma}" ></c:out>
				<% 
				if(contador!=totalIteraciones)
				{
					%>
					<i class="fa fa-arrow-right action-icon" onclick="javascript:moverPosicion('${toma.oidCabeceraXLS}', 'BAJAR')"  title="más tarde"></i>
					<% 
				}
				%>
			</td>
			</c:forEach> 
			<td>
			<input type="text" name="resiTomaLiteral"  placeholder="añadir nombre"> </td>
		</tr>	
		<tr>
			<c:forEach items="${formulari.listaTomasCabecera}" var="toma"> 
				<td><c:out value="${toma.horaToma}" ></c:out></td>
			</c:forEach> 
				<td><input type="text" name="resiToma" placeholder="formato hh:mm"> <br></td>
			
		</tr>	
		<c:if test="${idUsuario == 'admin'}">
		<tr>
			<c:forEach items="${formulari.listaTomasCabecera}" var="toma"> 
				<td><c:out value="${toma.idToma}" ></c:out></td>
			</c:forEach> 
				<td>idToma (solo visible para admins)<br></td>
		</tr>		
		<tr>
			<c:forEach items="${formulari.listaTomasCabecera}" var="toma"> 
				<td><c:out value="${toma.posicionEnVistas}" ></c:out></td>
			</c:forEach> 
				<td>Posición en vistas (solo visible para admins)<br></td>
		</tr>	
		<tr>
			<c:forEach items="${formulari.listaTomasCabecera}" var="toma"> 
				<td><c:out value="${toma.posicionEnBBDD}" ></c:out></td>
			</c:forEach> 
				<td>Posición en bbdd (solo visible para admins)<br></td>
		</tr>	
		</c:if>
		
		<tr>
			<c:forEach items="${formulari.listaTomasCabecera}" var="toma"> 
			<td>
			<c:if test="${formulari.mode != 'VIEW'}" >
			<c:if test="${toma.tipo == 'EXTRA'}" >
				<input type="button" onclick="javascript:borrar('${toma.oidCabeceraXLS}')" value="Borrar"/>
			</c:if>
			</c:if>
			</td>
			</c:forEach> 
			
		</tr>	
 </table>
 
 

 
<div class="">	
<fieldset style="width: 50%;">
 	<p><h5>Información importante</h5>

		Orden en los SPD:<br>
		- En los ficheros RX las tomas estarán ordenadas según aparece en la tabla <br>
		- En caso de utilizar Helium estarán ordenadas por la hora indicada (hh:mm)</td>
		<br><br>
		Borrado de tomas:<br>
		- Las <span style="background-color: #00abfd;">tomas base</span> no pueden borrarse<br>
		- Solo puede borrarse una <span style="background-color: #00ff00;">toma extra</span> en caso que no haya ningún residente con medicación en esa toma</td>
	</p>	
</fieldset>	
	<p class="botons">
		<input type="button" class="btn primary" onclick="javascript:volver()" value="Cerrar"/>
		<input type="button" onclick="javascript:nuevaToma()" value="Confirmar"/>
	</p>	
</div>	
</html:form>
</html:html>
