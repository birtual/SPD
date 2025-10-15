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
<bean:define id="formulari" name="InformeRDForm" type="lopicost.spd.struts.form.InformeRDForm" />
<bean:define id="cab" name="formulari"  property="cabecera" type="lopicost.spd.struts.bean.FicheroResiBean" />


<html:form action="/InformeRD.do" method="post">

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
		<a href="ExportarInformeRD.do?oidFicheroResiCabecera=<bean:write name="cab" property="oidFicheroResiCabecera" />&parameter=<bean:write name="formulari" property="parameter" />" >Exportar a PDF</a>
	</logic:notEmpty>	
</fieldset>			
		
	<logic:empty  name="formulari" property="producciones">
		Sin datos de la producción SPD
	</logic:empty>	
	<html:hidden property="parameter" />
		
	<input type="hidden" name="oidFicheroResiCabecera" value="<bean:write name='InformeRDForm' property='oidFicheroResiCabecera' />" />
    <logic:iterate id="data" name="formulari" property="producciones" type="lopicost.spd.model.spd.ProduccionPaciente">

    <bean:define id="dias" name="data" property="diasProduccion" />
    <bean:define id="diasSPD" name="data" property="diasSPD" />
    <bean:define id="pac" name="data" property="paciente" />

	<fieldset style="width:90%">
		<fieldset style="width:95%">
			<h4>Residente:<bean:write name="pac" property="nombre" /> <bean:write name="pac" property="apellidos" /> - (<bean:write name="pac" property="CIP" />)</h4>
			<h4>Código numérico interno: <bean:write name="data" property="orderNumber" /></h4>
			<h4>Fechas SPD: <bean:write name="cab" property="fechaDesde" /> - <bean:write name="cab" property="fechaHasta" /></h4>
			
			<logic:notEmpty name="cab" property="medicoResponsable">
				<h4>Médico responsable:<bean:write name="cab" property="medicoResponsable" /></h4>
			</logic:notEmpty>	
			<h4><bean:write name="cab" property="nombreFarmacia" /> - <bean:write name="cab" property="responsableFarmacia" />
		</fieldset>	
		
	<h4>Medicamentos SPD</h4>
	<logic:notEmpty  name="data" property="ttosEmblistados">
		<table border="1" style="width:100%">
	    	<thead>
	        	<tr class="rd_cabecera">
	        	<th>BIRTUAL</th>
				<th>pautaResidencia</th>
				<th>cantidadPrevistaSPD</th>
				<th>cn</th>
				<th>nombreConsejoCn</th>
				<th>nombreMedicamentoBolsa</th>
				<th>nomGtVmp</th>
				<th>idProceso</th>
				<th>idDivisionResidencia</th>
				<th>CIP</th>
				<th>ROBOT</th>
				<th>cantidadEmblistadaSPD</th>
				<th>cn</th>
				<th>nombreConsejoCn</th>
				<th>nombreMedicamentoBolsa</th>
				<th>nomGtVmp</th>
				<th>lab</th>
				<th>lote</th>
				<th>caducidad</th>
				<th>numeroSerie</th>
				<th>aspectoMedicamento</th>
				<th>RECETA</th>
				<th>codigoDispensado</th>
				<th>nombreConsejoCn</th>
				<th>nomGtVmp</th>
				<th>lab</th>
				<th>lote</th>
				<th>caducidad</th>
				<th>numeroSerie</th>
		       </tr>
			</thead>
	        <tbody>
	       	
	       	<c:forEach var="trat" items="${data.ttosEmblistados}">


	       	<c:set var="birtual" value="${trat.medicamentoBirtual}" />
	       	<c:set var="robot" value="${trat.medicamentoRobot}" />
			<c:if test="${not empty robot}">
			  	<c:set var="identRobot" value="${robot.aspectoMedicamento}" />
			</c:if>
			
			<c:set var="receta" value="${trat.medicamentoReceta}" />
			<c:if test="${not empty receta}">
			  	<c:set var="identReceta" value="${receta.aspectoMedicamento}" />
			</c:if>

			
						
	       <tr class="rd_cabecera">
				<th>BIRTUAL</th>
				<td><bean:write name="birtual" property="pautaResidencia" /></td>
				<td><bean:write name="birtual" property="cantidadPrevistaSPD" /></td>
				<td><bean:write name="birtual" property="cn" /></td>
				<td><bean:write name="birtual" property="nombreConsejoCnFinal" /></td>
				<td><bean:write name="birtual" property="nombreMedicamentoBolsa" /></td>
				<td><bean:write name="birtual" property="nomGtVmp" /></td>
				<td><bean:write name="birtual" property="idProceso" /></td>
				<td><bean:write name="birtual" property="idDivisionResidencia" /></td>
				<td><bean:write name="birtual" property="CIP" /></td>
				<th>ROBOT</th>
				<td><bean:write name="robot" property="cantidadEmblistadaSPD" /></td>
				<td><bean:write name="robot" property="cn" /></td>
				<td><bean:write name="robot" property="nombreConsejoCn" /></td>
				<td><bean:write name="robot" property="nombreMedicamentoBolsa" /></td>
				<td><bean:write name="robot" property="nomGtVmp" /></td>
				<td><bean:write name="robot" property="lab" /></td>
				<td><bean:write name="robot" property="lote" /></td>
				<td><bean:write name="robot" property="caducidad" /></td>
				<td><bean:write name="robot" property="numeroSerie" /></td>
				<td><bean:write name="identRobot" property="resumen" /></td>
				<td>RECETA</td>
				<c:if test="${not empty receta}">
				<td><bean:write name="receta" property="codigoDispensado" /></td>
				<td><bean:write name="receta" property="nombreConsejoCn" /></td>
				<td><bean:write name="receta" property="nomGtVmp" /></td>
				<td><bean:write name="receta" property="lab" /></td>
				<td><bean:write name="receta" property="lote" /></td>
				<td><bean:write name="receta" property="caducidad" /></td>
				<td><bean:write name="receta" property="numeroSerie" /></td>
				<td><bean:write name="identReceta" property="resumen" /></td>
				</c:if>
				
					
	            </tr>
			</c:forEach>
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
