
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <jsp:include page="/spd/jsp/global/head.jsp"/>
</head>
<body>
    <h3>Estado de la generación de Archivos</h3>
	<fieldset>
		<span style="color: red; font-size: 18px;"> 
		Otro usuario está generando el XML en este momento 
		<c:choose>
		  <c:when test="${not empty fechaInicioBloqueo}">
    		<p>se está ejecutando desde: <strong>${fechaInicioBloqueo}</strong></p>
  		</c:when>
		</c:choose>
		Por favor, vuelva a intentarlo en unos minutos.
		</span>
	</fieldset>
		<p class="botons">
			<input type="button" onclick="javascript:cerrar()" value="Cerrar"/>
		</p>	
				
</body>
</html>
