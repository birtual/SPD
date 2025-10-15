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
	<logic:notEmpty name="cab" property="usuarioEntregaSPD">
		<div>
			<label for="fechaEntregaSPD" accesskey="e">Entrega en residencia:</label><bean:write name="cab" property="fechaEntregaSPD" /> - <bean:write name="cab" property="usuarioEntregaSPD" />
		</div>	
	</logic:notEmpty>
	<logic:notEmpty name="cab" property="usuarioRecogidaSPD">
		<div>
			<label for="usuarioRecogidaSPD" accesskey="e">Recogida del SPD:</label><bean:write name="cab" property="fechaRecogidaSPD" /> - <bean:write name="cab" property="usuarioRecogidaSPD" />
		</div>	
	</logic:notEmpty>
	<!-- div>
		<label for="farmacia" accesskey="e">Farmacia responsable:</label>Farmacia Bertran
	</div>	
	<div>
		<label for="farmaceutico" accesskey="e">Farmacéutico responsable:</label>Marco A. González
	</div-->	
    <a href="ExportarInformeSpd.do?oidFicheroResiCabecera=<bean:write name="cab" property="oidFicheroResiCabecera" />" >Exportar a PDF</a>
</fieldset>			
			
	<input type="hidden" name="oidFicheroResiCabecera" value="<bean:write name='InformeSpdForm' property='oidFicheroResiCabecera' />" />
    <logic:iterate id="data" name="formulari" property="producciones" type="lopicost.spd.robot.bean.rd.ProduccionPaciente">

    <bean:define id="dias" name="data" property="diasProduccion" />
    <bean:define id="diasSPD" name="data" property="diasSPD" />
    <bean:define id="pac" name="data" property="paciente" />

	<fieldset style="width:90%">

		<fieldset style="width:70%">
			<h4>CIP:<bean:write name="pac" property="CIP" /></h4>
			<h4>Código numérico interno:<bean:write name="data" property="orderNumber" /></h4>
		</fieldset>	
		<table border="1" style="width:80%">
	    	<thead>
				<tr class="rd_cabecera">
			    	<th colspan="3"  style="text-align:center; font-weight:bold;">Producción SPD</th>
			    </tr>
	        	<tr class="rd_cabecera">
	            	<!-- th>Medicamento</th -->
	            	<!-- th>Lab</th -->
	            	<th>Nombre en bolsa</th>
	            	<th>Pauta residencia</th>
	            	<!-- th>Lote</th -->
	            	<!-- th>Caducidad</th -->
	            	<th>Unidades utilizadas</th>
	            	<th>FF/Descripción</th>
	            </tr>
			</thead>
	        <tbody>
	       	<logic:iterate id="trat" name="data" property="tratamientosPaciente" type="lopicost.spd.robot.bean.rd.TratamientoPaciente">
			<bean:define id="medic" name="trat" property="medicamentoPaciente" />
	        	<tr class="rd_cabecera">
	            <logic:equal property="emblistar" name="trat" value="true">
					<!--td><bean:write name="medic" property="cn" /> - <bean:write name="medic" property="nombreMedicamentoConsejo" /></td-->
					<td><bean:write name="medic" property="nombreMedicamentoBolsa" /></td>
					<!-- td><bean:write name="medic" property="labMedicamento" /></td -->
					<td><bean:write name="medic" property="pautaResidencia" /></td>
					<!-- td><bean:write name="medic" property="nombreMedicamentoBolsa" /></td> -->
					<!-- td><bean:write name="medic" property="lote" /></td -->
					<!-- td><bean:write name="medic" property="caducidad" /></td -->
					<td>
						<!-- bean:write name="trat" property="cantidadUtilizadaSPD" / --eliminamos decimales 0-->
						<c:choose>
						  <c:when test="${trat.cantidadUtilizadaSPD % 1 == 0}">
						    ${trat.cantidadUtilizadaSPD.intValue()}
						  </c:when>
						  <c:otherwise>
						    ${trat.cantidadUtilizadaSPD}
						  </c:otherwise>
						</c:choose>

					</td>
						
					<td><bean:write name="medic" property="formaFarmaceutica" /></td>
				</logic:equal>
	            </tr>
			</logic:iterate>
	        </tbody>
		</table>
	    <table border="1" style="width:50%">
	    	<thead> 
	         	<tr class="rd_cabecera">
			      <th colspan="3" style="text-align:center; font-weight:bold;">Medicación fuera de blister</th>
			    </tr>

			</thead>
	        <br/>
			<tbody>       
			<logic:iterate id="trat" name="data" property="tratamientosPaciente" type="lopicost.spd.robot.bean.rd.TratamientoPaciente">
			<bean:define id="medic" name="trat" property="medicamentoPaciente" />
				<tr class="rd_cabecera">
				<logic:notEqual property="emblistar" name="trat" value="true">
					<td><bean:write name="medic" property="cn" /> - <bean:write name="medic" property="nombreMedicamentoBolsa" /></td>
					<td><bean:write name="medic" property="pautaResidencia" /></td>
				</logic:notEqual>
				</tr>
			</logic:iterate>
			</tbody>
		</table>
	<br/>
		<table border="1" style="width:80%">
			<thead>  
				<tr class="rd_cabecera">
			    	<th colspan="2" style="text-align:center; font-weight:bold;">Dispensaciones receta</th>
			    </tr>
				<!--  tr class="rd_cabecera">
					
					<th>Medicamento</th>
					<th>Lab</th>
					<th>Lote</th>
					<th>Caducidad</th>
					<th>Núm. Serie</th>
				</tr-->
			</thead>
		<br/>
			<tbody>  
			<logic:iterate id="disp" name="pac" property="dispensacionesReceta" type="lopicost.spd.robot.bean.rd.MedicamentoReceta">
				<tr>
					<td><bean:write name="disp" property="cn" /> - <bean:write name="disp" property="nombreMedicamentoConsejo" /></td>
					<td><bean:write name="disp" property="labMedicamento" /></td>
					<td><bean:write name="disp" property="lote" /></td>
					<td><bean:write name="disp" property="caducidad" /></td>
					<td><bean:write name="disp" property="numSerie" /></td>
				</tr>
			</logic:iterate>
			</tbody>  
		</table>
	    <br/><br/>
	</fieldset>	
	    </logic:iterate>
</html:form>
</body>
</html:html>
