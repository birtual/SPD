<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	

<html>

<head>
	<jsp:include page="/spd/jsp/global/head.jsp"/>
	<title>Listado de Bloqueos Horarios</title>
</head>

<body>
<script>
	function salir()
	{
		var f = document.ProcesosBloqueosHorariosForm;
		f.parameter.value='guardar';
		f.ACTIONTODO.value='CONFIRMADO_OK';
		f.submit();
	}
	function nueva()
	{
		var f = document.ProcesosBloqueosHorariosForm;
		f.parameter.value='nueva';
		//f.ACTIONTODO.value='CONFIRMADO_OK';
		f.submit();
	}
	function editar(oidBloqueoHorario)
	{
		var f = document.ProcesosBloqueosHorariosForm;
		f.parameter.value='editar';
		f.oidBloqueoHorario.value=oidBloqueoHorario;
		//f.ACTIONTODO.value='CONFIRMADO_OK';
		f.submit();
	}
	function borrar(oidBloqueoHorario)
	{
		var f = document.ProcesosBloqueosHorariosForm;
		f.parameter.value='borrar';
		f.oidBloqueoHorario.value=oidBloqueoHorario;
		//f.ACTIONTODO.value='CONFIRMADO_OK';
		f.submit();
	}	
</script>


<h2>Listado de Bloqueos Horarios</h2>

<html:form action="/ProcesosBloqueosHorarios" method="get">
    <html:hidden property="parameter"/>
 	<input type="button" class="azulCielo" value="Nuevo bloqueo" onclick="javascript:nueva();"  />
    <input type="button" class="azulCielo" value="Salir" onclick="javascript:salir();"  />
    


<br/>

<table style="width:70%">
    <thead>
        <tr>
            <th>oid</th>
            <th>lanzadera</th>
            <th>Tipo</th>
            <th>Valor bloqueo</th>
            <th>Descripción</th>
            <th>Activo</th>
            <th>Acciones</th>
        </tr>
    </thead>
    <tbody>
    		
    
        <c:forEach var="r" items="${listaBloqueosHorarios}">
            <tr>
                <td><bean:write name="r" property="oidBloqueoHorario"/></td>
                <td><bean:write name="r" property="lanzadera"/></td>
                <td><bean:write name="r" property="tipoBloqueoHorario"/></td>
                <td>
	                <logic:equal property="tipoBloqueoHorario" name="r" value="HORA">
		                <bean:write name="r" property="horasDesde"/> - <bean:write name="r" property="horasHasta"/> 
	                </logic:equal>
	                <logic:equal property="tipoBloqueoHorario" name="r" value="DIA">
		                <bean:write name="r" property="valorDia"/> 
	                </logic:equal>
	                <logic:equal property="tipoBloqueoHorario" name="r" value="FECHA">
		                <bean:write name="r" property="valorFecha"/> 
	                </logic:equal>
	                
                </td>
                <td><bean:write name="r" property="descripcion"/></td>
                <td><bean:write name="r" property="activo"/></td>
                <td>
                 	<input type="button" class="azulCielo" value="Editar" onclick="javascript:editar('${r.oidBloqueoHorario}');"  />
                
                
                    <html:form action="/ProcesosBloqueosHorarios" method="get" styleId="formEditar${r.oidBloqueoHorario}">

                        <html:submit value="" />
                    </html:form>
                    <html:form action="/ProcesosBloqueosHorarios" method="post" styleId="formBorrar${r.oidBloqueoHorario}" onsubmit="return confirm('¿Seguro que quieres borrar este bloqueo?');">
                        <html:hidden property="parameter" value="borrar" />
                        <html:hidden property="oidBloqueoHorario" value="${r.oidBloqueoHorario}"  />
                        <html:submit value="Borrar" />
                   
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</html:form>
</body>
</html>
