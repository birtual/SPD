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
	<bean:define id="dias" name="data" property="diasProduccion" />
    <bean:define id="pac" name="data" property="paciente" />
	
		<table border="1" style="width:80%">
            <thead>
			<h4>Residente: <bean:write name="pac" property="nombre" /> <bean:write name="pac" property="apellidos" /> - (<bean:write name="pac" property="CIP" />)</h4>
			<h4>Código numérico interno: <bean:write name="data" property="orderNumber" /></h4>
			<h4>Fechas SPD: <bean:write name="cab" property="fechaDesde" /> - <bean:write name="cab" property="fechaHasta" /></h4>
			
		<logic:notEmpty name="cab" property="medicoResponsable">
			<h4>Médico responsable: <bean:write name="cab" property="medicoResponsable" /></h4>
		</logic:notEmpty>	
			
			<h4><bean:write name="cab" property="nombreFarmacia" /> - <bean:write name="cab" property="responsableFarmacia" /></h4>
			<br/><br/>
			<logic:iterate id="dia" name="data" property="diasSPD" type="lopicost.spd.robot.bean.rd.DiaSPD">
			<tr>
				<td><bean:write name="dia" property="fechaToma" /></td>
				<td>Hora</td>
				<td>ID Bolsa SPD</td>
				<td>Medicamento</td>
				<td>Pauta / posología </td>
				<td>FF / Descripción </td>
				<td>Lote</td>
				<td>Caducidad</td>
				<td>Número serie</td>
            </tr>
					<logic:iterate id="bolsa" name="dia" property="bolsaSPD" type="lopicost.spd.robot.bean.rd.BolsaSPD">
						<logic:iterate id="linea" name="bolsa" property="lineasBolsa" type="lopicost.spd.robot.bean.rd.LineaBolsaSPD">
						<tr>
							<td></td>
							<td><bean:write name="bolsa" property="tomaDelDia" /></td>
							<td><bean:write name="bolsa" property="idBolsa" /></td>
							<bean:define id="medic" name="linea" property="medicamentoPaciente" />
							<td><bean:write name="medic" property="cn" /> - <bean:write name="medic" property="nombreMedicamentoConsejo" /></td>
							<td><bean:write name="medic" property="pautaResidencia" /></td>
							<td><bean:write name="medic" property="formaFarmaceutica" /></td>
							<td><bean:write name="medic" property="lote" /></td>
							<td><bean:write name="medic" property="caducidad" /></td>
							<td><bean:write name="medic" property="numeroSerie" /></td>					
				    		</logic:iterate>
	 		           	</tr>
				    </logic:iterate>
  		    </logic:iterate>
            </thead>
	</table>

    </logic:iterate>
    
</html:form>
</body>
</html:html>
