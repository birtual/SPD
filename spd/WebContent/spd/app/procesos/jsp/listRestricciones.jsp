<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	

<html>

<head>
	<jsp:include page="/spd/jsp/global/head.jsp"/>
	<title>Listado de Restricciones</title>
</head>

<body>
<script>

	function nueva()
	{
		var f = document.ProcesosRestriccionesForm;
		f.parameter.value='nueva';
		//f.ACTIONTODO.value='CONFIRMADO_OK';
		f.submit();
	}
	function editar(oidRestriccion)
	{
		var f = document.ProcesosRestriccionesForm;
		f.parameter.value='editar';
		f.oidRestriccion.value=oidRestriccion;
		//f.ACTIONTODO.value='CONFIRMADO_OK';
		f.submit();
	}
	
	function borrar(oidRestriccion) {
	    var f = document.ProcesosRestriccionesForm;
	    if (confirm('¿Seguro que quieres borrar esta restricción?')) {
	        f.parameter.value = 'borrar';
	        f.oidRestriccion.value = oidRestriccion;
	        f.submit();
	    }
	}
	
</script>


<h2>Listado de Restricciones</h2>

<html:form action="/ProcesosRestricciones" method="post">
    <html:hidden property="parameter"/>
    <html:hidden property="oidRestriccion"/>
 	<input type="button" class="azulCielo" value="Nueva Restricción" onclick="javascript:nueva();"  />
    <input type="button" class="azulCielo" value="Salir" onclick="javascript:salir();"  />
    


<br/>

<table style="width:70%">
    <thead>
        <tr>
            <th>oid</th>
            <th>lanzadera</th>
            <th>Tipo</th>
            <th>Valor restricción</th>
            <th>Descripción</th>
            <th>¿Usar restricción?</th>
            <th>¿Está bloqueando ahora?</th>
            <th>Acciones</th>
            
        </tr>
    </thead>
    <tbody>
    		
    
        <c:forEach var="r" items="${listaRestricciones}">
            <tr>
                <td><bean:write name="r" property="oidRestriccion"/></td>
                <td><bean:write name="r" property="lanzadera"/></td>
                <td><bean:write name="r" property="tipoRestriccion"/></td>
                <td>
	                <logic:equal property="tipoRestriccion" name="r" value="HORA">
		                <bean:write name="r" property="horasDesde"/> - <bean:write name="r" property="horasHasta"/> 
	                </logic:equal>
	                <logic:equal property="tipoRestriccion" name="r" value="DIA">
		                <bean:write name="r" property="valorDia"/> 
	                </logic:equal>
	                <logic:equal property="tipoRestriccion" name="r" value="FECHA">
		                <bean:write name="r" property="valorFecha"/> 
	                </logic:equal>
	                
                </td>
                <td><bean:write name="r" property="descripcion"/></td>
                
                <c:choose>
                	<c:when test="${r.usarRestriccion}">
                		<td align="center">SI</td>
                	    <c:choose>
						  <c:when test="${r.bloqueaAhora == 'SI'}">
						    <td align="center" style="background-color: red; color: white;">
						      <bean:write name="r" property="bloqueaAhora"/>
						    </td>
						  </c:when>
						  <c:otherwise>
						    <td align="center" style="background-color: green; color: white;">
						      <bean:write name="r" property="bloqueaAhora"/>
						    </td>
						  </c:otherwise>
						</c:choose>
                	</c:when>
                	
					<c:otherwise>
						<td align="center">NO</td>
						<td align="center"></td>
					</c:otherwise>
				</c:choose>
                
                <td>
                 	<input type="button" class="azulCielo" value="Editar" onclick="javascript:editar('${r.oidRestriccion}');"  />
                	<input type="button" class="azulCielo" value="Borrar" onclick="javascript:borrar('${r.oidRestriccion}');"  />
               </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</html:form>
</body>
</html>
