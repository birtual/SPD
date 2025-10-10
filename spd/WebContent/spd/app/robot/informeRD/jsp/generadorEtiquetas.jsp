<%@ page language="java" %>
<%@ page import="java.util.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:formatNumber value="${trat.dosis}" minFractionDigits="0" maxFractionDigits="2" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <title>Informe global producción</title>
  <jsp:include page="/spd/jsp/global/head.jsp"/>
</head>
<body>

<c:set var="formulari" value="${InformeRDForm}" />
<c:set var="cab" value="${formulari.cabecera}" />

<html:form action="/InformeRD.do" method="post">
	<fieldset>
	<div>
		<label for="fechaConsumo" accesskey="e">Fechas de consumo:</label>${cab.fechaDesde} - ${cab.fechaHasta}/>
	</div>
	<div>
		<label for="idProceso" accesskey="e">Id carga fichero :</label>${cab.idProceso}
	</div>	
	<c:if test="${not empty cab.usuarioDesemblistaSPD}">
		<div>
			<label>Resp. desemblistado:</label>${cab.fechaDesemblistaSPD} - ${cab.usuarioDesemblistaSPD}
		</div>	
	</c:if>	
	<c:if test="${not empty cab.usuarioProduccionSPD}">
		<div>
			<label>Resp. producción:</label>${cab.fechaProduccionSPD} - ${cab.usuarioProduccionSPD}
		</div>	
	</c:if>	
	<c:if test="${not empty cab.usuarioEntregaSPD}">
		<div>
			<label>Resp. entrega en residencia:</label>${cab.fechaEntregaSPD} - ${cab.usuarioEntregaSPD}
		</div>	
	</c:if>	
	<c:if test="${not empty cab.usuarioRecogidaSPD}">
		<div>
			<label>Resp. recogida en destino:</label>${cab.fechaRecogidaSPD} -  ${cab.usuarioRecogidaSPD}
		</div>	
	</c:if>	
	
	<c:if test="${not empty formulari.producciones}">
		<a href="ExportarInformeRD.do?oidFicheroResiCabecera=${cab.oidFicheroResiCabecera}&parameter=${formulari.parameter}" 
		    onclick="alert('en pruebas'); return false;">
		   Exportar a PDF
		</a>
	</c:if>	
</fieldset>			
		
	<c:if test="${empty formulari.producciones}">
		Sin datos de la producción SPD
	</c:if>	
	<html:hidden property="parameter" />
	<html:hidden property="oidFicheroResiCabecera" />
    <c:forEach var="data" items="${formulari.producciones}">

		<c:set var="dias" value="${data.diasProduccion}" />
		<c:set var="diasSPD" value="${data.diasSPD}" />
		<c:set var="pac" value="${data.paciente}" />

	<fieldset style="width:90%">
		<fieldset style="width:95%">
			<h4>Residente: ${pac.nombre} ${pac.apellidos} - (${pac.CIP}>)</h4>
			<h4>Código numérico interno: ${data.orderNumber}</h4>
			<h4>Fechas SPD: ${cab.fechaDesde} - ${cab.fechaHasta} </h4>
			
			<c:if test="${not empty cab.medicoResponsable}">
				<h4>Médico responsable:${cab.medicoResponsable}</h4>
			</c:if>
			
			<h4>${cab.nombreFarmacia} - ${cab.responsableFarmacia}</h4>
	</fieldset>	
		
	<h4>Medicamentos SPD</h4>
	<c:if test="${not empty data.ttosEmblistados}">
	<table border="1" style="width:100%">
	  	<thead>
		<tr class="rd_cabecera">
			<th>pauta Residencia</th>
			<th>nombre en Bolsa</th>
			<th>cantidad</th>
			<th>Medicamento ROBOT</th> 
			<th>lote / caducidad</th>
			<th>numeroSerie</th>
			<th>aspecto</th>
			<th>Medicamento RECETA</th> 
			<th>lote / caducidad</th>
			<th>numeroSerie</th>
			<th>aspecto</th>
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
			<td>${birtual.pautaResidencia}</td>
			<td>${birtual.nombreMedicamentoBolsa}</td>
			<td>
			<c:choose>
			  <c:when test="${trat.cantidadTotalEmblistadaSPD % 1 == 0}">
			    ${trat.cantidadTotalEmblistadaSPD.intValue()}
			  </c:when>
			  <c:otherwise>
			    ${trat.cantidadTotalEmblistadaSPD}
			  </c:otherwise>
			</c:choose>
			</td>
			<td>${robot.cn} / ${robot.nombreConsejoCn} (${robot.lab})</td>
			<td>${robot.lote} - ${robot.caducidad}</td>
			<td>${robot.numeroSerie}</td>
			<td>${identRobot.resumen}</td>
	<c:choose>
    	<c:when test="${not empty receta}">
    		<td>${receta.codigoDispensado} / ${receta.nombreConsejoCn} (${receta.lab})</td>
			<td>${receta.lote} /${receta.caducidad}</td>
			<td>${receta.numeroSerie}</td>
			<td>${identReceta.resumen}</td>
		 </c:when>
   		<c:otherwise>
   			<td colspan="4">No hay receta disponible.</td>
   		</c:otherwise>
	</c:choose>
		</tr>
	</c:forEach>
	</tbody>
	</table>
	</c:if>
	<c:if test="${empty data.ttosEmblistados}">
	    Sin tratamientos emblistados
	</c:if>	
	</fieldset>	
	</c:forEach>
</html:form>
</body>
</html:html>
