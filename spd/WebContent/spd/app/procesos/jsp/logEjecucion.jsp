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
<bean:define id="ejec" name="data" property="ejecucion" />


<body>


<h2>Listado de Ejecuciones</h2>

<html:form action="/Procesos" method="post">



<table style="width:70%">
    <thead>
        <p>oidProcesoEjecucion: <B><bean:write name="ejec" property="oidProcesoEjecucion"/></B></p>
        <p>lanzadera: <B><bean:write name="ejec" property="lanzadera"/></B></p>
        
           


        <tr>
            <th>oidLogProcesoEjecucion</th>
            <th>paso</th>
            <th>fecha</th>
            <th>mensaje</th>
            

        </tr>
    </thead>
    
                <tr>

   	<logic:iterate id="log" name="ejec" property="detalleEjecucion" type="lopicost.spd.model.ProcesoEjecucionLog" indexId="position">
                <td><bean:write name="log" property="oidLogProcesoEjecucion"/></td>
				<td><bean:write name="log" property="paso"/></td>
				<td><bean:write name="log" property="fecha"/></td>
				<td><bean:write name="log" property="mensaje"/></td>

                
   
                
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
