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
			//alert('0 ' + oidResiToma + ' ' + accion);
			var f = document.FicheroResiForm;
			//alert('1 '  + f.oidResiToma.value);
			
			f.oidResiToma.value = oidResiToma;
			//alert('2 '  + f.oidResiToma.value);
			f.parameter.value = 'moverPosicion';
	        f.ACTIONTODO.value = accion;
		       
	        f.submit();
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
		        // Si el formato no es válido, muestra un mensaje de error o realiza alguna acci�n para informar al usuario.
		        alert("El formato de hora debe ser hh:mm");
		      }
		}	

		function borrar(resiToma)
		{
			var f = document.FicheroResiForm;
			f.parameter.value='borrar';
			f.ACTIONTODO.value='BORRAR_OK';
			f.oidResiToma.value=resiToma;

			f.submit();
			if (window.opener && !window.opener.closed) {
				window.opener.location.reload();
			}
		}	
</script>		

<body>
<html:form action="/CabecerasXLS.do" method="post">

     <html:hidden property="parameter" value="list"/>
     <html:hidden property="ACTIONTODO"/>
     <html:hidden property="mode"/>				
     <html:hidden property="oidResiToma"/>
     <html:hidden property="numeroResiTomas"/>		
     <html:hidden property="oidDivisionResidencia" />	
     <html:hidden property="oidFicheroResiCabecera" />	


     
   
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
	

 <fieldset><h2>Edición de las tomas </h2>
 <table>     	
 <logic:iterate id="toma" name="formulari" property="listaTomasCabecera" type="lopicost.spd.struts.bean.CabecerasXLSBean" indexId="position">

 
</logic:iterate>
</table> 

</fieldset>	

 <table class="detalle">
 <tr>
 <td class="primera"></td>
		<td class="primera">horaToma</td>
		<td class="primera">idToma</td>
		<td class="primera">nombreToma</td>
		<td class="segunda">posicionEnBBDD</td>
		<td class="segunda">posicionEnVistas</td>
		<td class="segunda">tipo</td>
		
   </tr>

	<%
		// Calcula el tamaño de la lista
		int totalIteraciones = (formulari.getListaTomasCabecera() != null) ? formulari.getListaTomasCabecera().size() : 0;
		int contador =0;

	%>
	
	
	   <c:forEach items="${formulari.listaTomasCabecera}" var="toma"> 
	  
		<tr>
			<td>
			</td>
			<td><c:out value="${toma.horaToma}" ></c:out></td>
			<td><c:out value="${toma.idToma}" ></c:out></td>
			<td>
			<c:if test="${formulari.mode != 'VIEW'}" >
	
		  	<% 
		  	contador++; 
			if(contador>1)
			{
				%>
				<i class="fa fa-arrow-up action-icon" onclick="javascript:moverPosicion('${toma.oidCabeceraXLS}', 'SUBIR')"  title="más pronto"></i> 
				<% 
			}else 
			{
				%>
				<i>&nbsp;&nbsp;&nbsp;</i> 
				<% 	
			}
			
			if(contador!=totalIteraciones)
			{
				%>
				<i class="fa fa-arrow-down action-icon" onclick="javascript:moverPosicion('${toma.oidCabeceraXLS}', 'BAJAR')"  title="más tarde"></i>
				<% 
			}
			%>
			</c:if>
			<c:out value="${toma.nombreToma}" ></c:out>
			</td>
			<td align="center"><c:out value="${toma.posicionEnBBDD}" ></c:out></td>
			<td align="center"><c:out value="${toma.posicionEnVistas}" ></c:out></td>
			<td><c:out value="${toma.tipo}" ></c:out>
			<c:if test="${formulari.mode != 'VIEW'}" >
			<c:if test="${toma.tipo == 'EXTRA'}" >
				<input type="button" onclick="javascript:borrar('${toma.oidCabeceraXLS}')" value="Borrar"/>
			</c:if>
			</c:if>
		</td>
	</tr>	
		</c:forEach> 

		<c:if test="${formulari.mode != 'VIEW'}" >
			<tr>
			<td></td>
			<td><input type="text" name="resiToma" placeholder="formato hh:mm"> <br></td>
			<td></td>
			<td><input type="text" name="resiTomaLiteral"  placeholder="añadir nombre"> </td>
			<td></td>
			<td></td>
			<td>
				<input type="button" onclick="javascript:nuevaToma()" value="Confirmar"/></td>
		</tr>	 
			</c:if>
	
 </table>
<div class="">	
	<p class="botons">
		<input type="button" class="btn primary" onclick="javascript:volver()" value="Volver"/>
	</p>	
</div>	
</html:form>
</html:html>
