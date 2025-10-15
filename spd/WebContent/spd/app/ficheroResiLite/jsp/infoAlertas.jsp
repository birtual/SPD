<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ include file="/spd/jsp/global/headFragmento.jspf" %>
<!DOCTYPE html>
<html:html>
<head>

<script language="javaScript" src="/spd/spd/app/ficheroResiLite/js/ficheroResiCabeceraLite.js"></script>

<script>
	function cerrar()
	{
		window.close();
	}
	</script>
<head>

<title>Info de control de alertas</title>
</head>
<script>
    // Este script manejará la respuesta del servidor y actualizará o redireccionará según sea necesario
    function handleServerResponse(responseText) {
        // Puedes mostrar una alerta, actualizar la página o redirigir según tus necesidades
        alert(responseText);
        // window.location.reload();  // Para recargar la página
        // window.location.href = '/tuApp/list';  // Para redirigir a un mapeo específico
    }
</script>
<bean:define id="formulari" name="FicheroResiForm" type="lopicost.spd.struts.form.FicheroResiForm" />

<body>

<html:form action="/FicheroResiCabeceraLite.do" method="post">	

<div id="container">
    <html:hidden property="idDivisionResidencia" />
    <html:hidden property="idUsuario" />
    <html:hidden property="parameter" value="list"/>
    <html:hidden property="ACTIONTODO" value="list"/>


 	
 	<fieldset>
	<!-- mostramos mensajes y errores, si existen -->
	<logic:notEmpty name="formulari" property="errors">
		<font color="red">
		<ul>
			<u>Mensaje:</u>
				<logic:iterate id="error" name="formulari" property="errors" type="java.lang.String">
					<li><bean:write name="error"/></li>
				</logic:iterate>
		</ul>
		</font>
		</logic:notEmpty>



	   	<div>	
		    <p class="botons">
				<input type="button" class="azulCielo" onclick="javascript:cerrar();" value="Cerrar" />
			</p> 
		</div>
		
		
	
	<table class="blueTable" border="1">
	<logic:iterate id="data" name="formulari" property="listaInfoAlertas" type="lopicost.spd.struts.bean.InfoAlertasBean" indexId="position">
			<tr><td class="<bean:write name="data" property="cssAlerta" />"><bean:write name="data" property="tituloAlerta" /></td><td class="">
			<c:out value="${data.textoAlerta}" escapeXml="false" />
			</td> </tr>
	</logic:iterate>

		</table>
	</fieldset>


</div>	
</html:form>
</body>
</html:html>