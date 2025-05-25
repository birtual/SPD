<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	

<html>

<head>
	<jsp:include page="/spd/jsp/global/head.jsp"/>
	<title>Listado de Ejecuciones</title>
</head>
<bean:define id="formulari" name="ProcesosForm" type="lopicost.spd.struts.form.ProcesosForm" />
<bean:define id="data" name="formulari" property="proceso" />
<body>
<script>

	

	function detalleLog(oidProcesoEjecucion)
	{
		 var url = '/spd/Procesos.do?parameter=logEjecucion&oidProcesoEjecucion=' + oidProcesoEjecucion;
		 window.open(url, 'logEjecucion', 'dependent=yes,height=500,width=800,top=50,left=0,resizable=yes,scrollbars=yes' );
	}
	
</script>


<h2>Listado de Ejecuciones</h2>

<html:form action="/Procesos" method="post">
    <html:hidden property="parameter"/>
    <html:hidden property="oidProceso"/>

<table style="width:70%">
    <thead>
        <tr>
            <th>oidProcesoEjecucion</th>
            <th>lanzadera</th>
            <th>version</th>
            <th>usuarioEjecucion</th>
            <th>estado</th>
            <th>fechaInicioEjecucion</th>
            <th>fechaFinEjecucion</th>
            <th>duracionSegundos</th>
            <th>codigoResultado</th>
            <th>resultado</th>
            <th>mensaje</th>
            <th>tipoError</th>
            <th>error</th>
            <th>numIntentos</th>
        </tr>
    </thead>
    	<logic:iterate id="r" name="data" property="listadoEjecuciones" type="lopicost.spd.model.ProcesoEjecucion" indexId="position">

                <tr>
                <td><a href="javascript:detalleLog('<bean:write name="r" property="oidProcesoEjecucion" />');"><bean:write name="r" property="oidProcesoEjecucion"/></a></td>
                <td><bean:write name="r" property="lanzadera"/></td>
                <td><bean:write name="r" property="version"/></td>
				<td><bean:write name="r" property="usuarioEjecucion"/></td>
				<td><bean:write name="r" property="estado"/></td>
				<td><bean:write name="r" property="fechaInicioEjecucion"/></td>
				<td><bean:write name="r" property="fechaFinEjecucion"/></td>
				<td><bean:write name="r" property="duracionSegundos"/></td>
				<td><bean:write name="r" property="codigoResultado"/></td>
				<td><bean:write name="r" property="resultado"/></td>
				<td><bean:write name="r" property="mensaje"/></td>
				<td><bean:write name="r" property="tipoError"/></td>
				<td><bean:write name="r" property="error"/></td>
				<td><bean:write name="r" property="numIntentos"/></td>
                
   
                
            </tr>
       </logic:iterate>
                <p>
                	<input type="button" class="azulCielo" value="Cerrar" onclick="javascript:salir();"  />
               </p>
    </tbody>
</table>
</html:form>
</body>
</html>
