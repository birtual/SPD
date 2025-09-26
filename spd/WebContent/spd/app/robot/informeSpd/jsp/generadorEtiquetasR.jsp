<%@ page language="java" %>
<%@ page import="java.util.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <title>Informe global producción</title>
  <jsp:include page="/spd/jsp/global/head.jsp"/>
</head>
<body>
<bean:define id="formulari" name="InformeSpdForm" type="lopicost.spd.struts.form.InformeSpdForm" />
<bean:define id="cab" name="formulari"  property="cabecera" type="lopicost.spd.struts.bean.FicheroResiBean" />


<html:form action="/InformeSpd.do" method="post">

<fieldset>
	<!-- div>
		<label for="nombreDivisionResidencia" accesskey="e">Nombre de la residencia:</label><bean:write name="cab" property="nombreDivisionResidencia" />
	</div-->
	<div>
		<label for="fechaConsumo" accesskey="e">Fechas de consumo:</label><bean:write name="cab" property="fechaDesde" /> - <bean:write name="cab" property="fechaHasta" />
	</div>
	<div>
		<label for="idProceso" accesskey="e">Id carga fichero :</label><bean:write name="cab" property="idProceso" /> 
	</div>	
	<logic:notEmpty name="cab" property="usuarioDesemblistaSPD">
		<div>
			<label for="usuarioDesemblistaSPD" accesskey="e">Resp. desemblistado:</label><bean:write name="cab" property="fechaDesemblistaSPD" /> - <bean:write name="cab" property="usuarioDesemblistaSPD" />
		</div>
	</logic:notEmpty>	
	<logic:notEmpty name="cab" property="usuarioProduccionSPD">
		<div>
			<label for="usuarioProduccionSPD" accesskey="e">Resp. producción:</label><bean:write name="cab" property="fechaProduccionSPD" /> - <bean:write name="cab" property="usuarioProduccionSPD" />
		</div>	
	</logic:notEmpty>
	<logic:notEmpty name="cab" property="usuarioEntregaSPD">
		<div>
			<label for="fechaEntregaSPD" accesskey="e">Resp. entrega en residencia:</label><bean:write name="cab" property="fechaEntregaSPD" /> - <bean:write name="cab" property="usuarioEntregaSPD" />
		</div>	
	</logic:notEmpty>
	<logic:notEmpty name="cab" property="usuarioRecogidaSPD">
		<div>
			<label for="usuarioRecogidaSPD" accesskey="e">Resp. recogida en destino:</label><bean:write name="cab" property="fechaRecogidaSPD" /> - <bean:write name="cab" property="usuarioRecogidaSPD" />
		</div>	
	</logic:notEmpty>
	<!-- div>
		<label for="farmacia" accesskey="e">Farmacia responsable:</label>Farmacia Bertran
	</div>	
	<div>
		<label for="farmaceutico" accesskey="e">Farmacéutico responsable:</label>Marco A. González
	</div-->		

	<logic:notEmpty  name="formulari" property="producciones">
		<a href="ExportarInformeSpd.do?oidFicheroResiCabecera=<bean:write name="cab" property="oidFicheroResiCabecera" />&parameter=<bean:write name="formulari" property="parameter" />" >Exportar a PDF</a>
	</logic:notEmpty>	
</fieldset>			
		
	<logic:empty  name="formulari" property="producciones">
		Sin datos de la producción SPD
	</logic:empty>	
	<html:hidden property="parameter" />
		
	<input type="hidden" name="oidFicheroResiCabecera" value="<bean:write name='InformeSpdForm' property='oidFicheroResiCabecera' />" />
    <logic:iterate id="data" name="formulari" property="producciones" type="lopicost.spd.robot.bean.rd.ProduccionPaciente">

    <bean:define id="dias" 		name="data" property="diasProduccion" />
    <bean:define id="diasSPD" 	name="data" property="diasSPD" />
    <bean:define id="pac" 		name="data" property="paciente" />

	<fieldset style="width:90%">
		<fieldset style="width:95%">
			<h4>Residente: <bean:write name="pac" property="nombre" /> <bean:write name="pac" property="apellidos" /> - (<bean:write name="pac" property="CIP" />)</h4>
			<h4>Código numérico interno: <bean:write name="data" property="orderNumber" /></h4>
			<h4>Fechas SPD: <bean:write name="cab" property="fechaDesde" /> - <bean:write name="cab" property="fechaHasta" /></h4>
			
		<logic:notEmpty name="cab" property="medicoResponsable">
			<h4>Médico responsable: <bean:write name="cab" property="medicoResponsable" /></h4>
		</logic:notEmpty>	
			
			<h4><bean:write name="cab" property="nombreFarmacia" /> - <bean:write name="cab" property="responsableFarmacia" /></h4>
		</fieldset>	
		
	<h4>Medicamentos SPD</h4>
	<logic:notEmpty name="data" property="ttosEmblistados">
		<table border="1" style="width:100%">
	    	<thead>
	        	<tr class="rd_cabecera">
	    			<th>Medicamento</th>
	            	<!-- th>Lab</th> -->
	            	<!-- th>Nombre en bolsa</th> -->
	            	<th>Pauta residencia</th>
	            	<th>Lote</th>
	            	<th>Caducidad</th>
	            	<th>Número de serie</th>
	            	<th>Unidades utilizadas</th>
	            	<th>Descripción</th>
	            </tr>
			</thead>
<tbody>
  <logic:iterate id="trat" name="data" property="ttosEmblistados" type="lopicost.spd.robot.bean.rd.TratamientoPaciente">
    <bean:define id="medic" name="trat" property="medicamentoPaciente" />
    <bean:define id="identRobot" name="medic" property="identificacion" />

    <logic:notEmpty name="trat" property="medicamentoReceta">
      <bean:define id="receta" name="trat" property="medicamentoReceta" />
      <bean:define id="identReceta" name="receta" property="identificacion" />

      <tr class="rd_cabecera">
		<c:choose>
	    <c:when test="${not empty receta.cn}">
	    	<td style="color: blue;">
	    </c:when>
		<c:otherwise>
			<td>
		</c:otherwise>
		</c:choose>
        <bean:write name="receta" property="cn" /> - <bean:write name="receta" property="nombreMedicamentoBolsa" />
       		</td>

        <td style="text-align: center;"><bean:write name="medic" property="pautaResidencia" /></td>
        <td style="text-align: right;"><bean:write name="receta" property="lote" /></td>
        <td style="text-align: right;"><bean:write name="receta" property="caducidad" /></td>
        <td style="text-align: right;"><bean:write name="receta" property="numeroSerie" /></td>
        <td align="center">
          <c:choose>
            <c:when test="${trat.cantidadUtilizadaSPD % 1 == 0}">
              ${trat.cantidadUtilizadaSPD.intValue()}
            </c:when>
            <c:otherwise>
              ${trat.cantidadUtilizadaSPD}
            </c:otherwise>
          </c:choose>
        </td>
        <td><bean:write name="identReceta" property="resumen" /></td>
      </tr>
    </logic:notEmpty>

    <logic:empty name="trat" property="medicamentoReceta">
      <tr class="rd_cabecera">
        <td><bean:write name="medic" property="cn" /> - <bean:write name="medic" property="nombreMedicamentoConsejo" /></td>
        <td style="text-align: center;"><bean:write name="medic" property="pautaResidencia" /></td>
        <td style="text-align: right;"><bean:write name="medic" property="lote" /></td>
        <td style="text-align: right;"><bean:write name="medic" property="caducidad" /></td>
        <td style="text-align: right;"><bean:write name="medic" property="numeroSerie" /></td>
        <td align="center">
          <c:choose>
            <c:when test="${trat.cantidadUtilizadaSPD % 1 == 0}">
              ${trat.cantidadUtilizadaSPD.intValue()}
            </c:when>
            <c:otherwise>
              ${trat.cantidadUtilizadaSPD}
            </c:otherwise>
          </c:choose>
        </td>
        <td><bean:write name="identRobot" property="resumen" /></td>
      </tr>
    </logic:empty>
  </logic:iterate>
</tbody>

		</table>
	</logic:notEmpty>
	<logic:empty  name="data" property="ttosEmblistados">
		Sin tratamientos emblistados
	</logic:empty>	

	</fieldset>	
	
	</logic:iterate>
</html:form>
</body>
</html:html>
