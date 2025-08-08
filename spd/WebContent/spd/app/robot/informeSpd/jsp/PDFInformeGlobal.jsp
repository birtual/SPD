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

<style>
	/* A4 en modo paisaje y márgenes mínimos */
	@page {
	  size: A4 landscape;
	  margin: 5mm;      /* márgenes muy reducidos */
	}
	
	body {
	  margin: 0;
	  padding: 0;
	  font: 0.7em Verdana, Geneva, Arial, Helvetica, sans-serif;
	  color: #000;
	  background-color: #FFF;
	}
	
	/* Tablas en auto layout: columnas crecen según contenido */
	table {
	  width: 100%;    /* Usar todo el ancho disponible */
	  border-collapse: collapse; 
	  /* table-layout auto por defecto */
		table-layout: auto;
	}
	
	/* Celdas y encabezados */
	th, td {
	  border: 1px solid #000;
	  padding: 3px 5px;   /* padding reducido */
	  font-size: 0.6em;   /* tamaño de fuente pequeño */
	  word-wrap: break-word;
	  text-align: left;
	}
	
	/* Estilo de los encabezados */
	th {
	  background: #007BFF;
	  color: #FFF;
	  font-weight: bold;
	  text-align: center;
	}
	
	/* Zebra striping opcional */
	tr:nth-child(even) td { background: #f2f2f2;}
	tr:nth-child(odd)  td { background: #fff; 	}
	
	/* Fieldsets que no se corten en mitad y salten página */
	fieldset {
	  width: 100%;
	  margin: 0.5em 0;
	  padding: 0.5em;
	  border: 1px solid #1bbcb8;
	  page-break-inside: avoid;
	  page-break-after: always;
	}
	
	/* Leyendas destacadas */
	legend {
	  font-size: 1em;
	  font-weight: bold;
	  color: #1bbcb8;
	}
</style>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <title>Informe global producción - PDFInformeGlobalLiteAll</title>
  <jsp:include page="/spd/jsp/global/headPDF.jsp"/>
</head>
<body>

<html:form action="/InformeSpd.do" method="post">
<bean:define id="formulari" name="InformeSpdForm" type="lopicost.spd.struts.form.InformeSpdForm" />
<bean:define id="cab" name="formulari"  property="cabecera" type="lopicost.spd.struts.bean.FicheroResiBean" />

<fieldset style="width: 50%;">     
<div>PDFInformeGlobal</div>
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
</fieldset>

   	<logic:empty  name="formulari" property="producciones">
		Sin datos de la producción SPD
	</logic:empty>	
   
     <logic:iterate id="data" name="formulari" property="producciones" type="lopicost.spd.robot.bean.rd.ProduccionPaciente">
        <bean:define id="dias" name="data" property="diasProduccion" />
        <bean:define id="diasSPD" name="data" property="diasSPD" />
        <bean:define id="pac" name="data" property="paciente" />
	<fieldset style="width:90%">
      
			<h4>Residente:<bean:write name="pac" property="nombre" /> <bean:write name="pac" property="apellidos" /> - (<bean:write name="pac" property="CIP" />)</h4>
			<h4>Código numérico interno:<bean:write name="data" property="orderNumber" /></h4>
			<h4>Farmacia Bertran39 - Barcelona. Ldo Marco A. González</h4>

			<br/><br/>
	<h4>Producción SPD</h4>
	<logic:notEmpty  name="data" property="ttosEmblistados">
		<table border="1" >
	    	<thead>
                <tr>
                   <th>Medicamento</th>
	            	<th>Lab</th>
	            	<th>Nombre en bolsa</th>
	            	<th>Pauta residencia</th>
	            	<th>Lote</th>
	            	<th>Caducidad</th>
	            	<th>Número de serie</th>
	            	<th>FF/Descripción</th>
                  <c:forEach var="dia" items="${data.diasSPD}">
                        <th><c:out value="${dia.fechaToma}" /></th>
                    </c:forEach>
	            	<th>Unidades utilizadas</th>
                </tr>
            </thead>
  	        <tbody>
	       	<logic:iterate id="trat" name="data" property="ttosEmblistados" type="lopicost.spd.robot.bean.rd.TratamientoPaciente">
			<bean:define id="medic" name="trat" property="medicamentoPaciente" />
	        	<tr class="rd_cabecera">
					<td><bean:write name="medic" property="cn" /> - <bean:write name="medic" property="nombreMedicamentoConsejo" /></td>
					<td><bean:write name="medic" property="labMedicamento" /></td>
					<td><bean:write name="medic" property="pautaResidencia" /></td>
					<td><bean:write name="medic" property="nombreMedicamentoBolsa" /></td>
					<td><bean:write name="medic" property="lote" /></td>
					<td><bean:write name="medic" property="caducidad" /></td>
					<td><bean:write name="medic" property="numeroSerie" /></td>
					<td><bean:write name="medic" property="formaFarmaceutica" /></td>
                       <c:forEach var="i" begin="0" end="${dias}">
                            <td>
                                <%
                                    int index = ((Integer) pageContext.getAttribute("i")).intValue();
                                    lopicost.spd.robot.bean.rd.MedicamentoPaciente med =
                                        (lopicost.spd.robot.bean.rd.MedicamentoPaciente) pageContext.findAttribute("medic");

                                    lopicost.spd.robot.bean.rd.DiaTomas dia = null;
                                    if (index >= 0 && index < med.getDiaTomas().size()) {
                                        dia = med.getDiaTomas().get(index);
                                    }

                                    if (dia != null && dia.getTomas() != null) {
                                        for (lopicost.spd.robot.bean.rd.Toma toma : dia.getTomas()) {
                                %>
                                             (<%= toma.getCantidadToma() %>)<%= toma.getNombreToma() %>
                                <%
                                        }
                                    }
                                %>
                            </td>
                        </c:forEach>

					<td>
						<c:choose>
						  <c:when test="${trat.cantidadUtilizadaSPD % 1 == 0}">
						    ${trat.cantidadUtilizadaSPD.intValue()}
						  </c:when>
						  <c:otherwise>
						    ${trat.cantidadUtilizadaSPD}
						  </c:otherwise>
						</c:choose>
					</td>
	            </tr>
			</logic:iterate>
	        </tbody>
		</table>
	</logic:notEmpty>
	<logic:empty  name="data" property="ttosEmblistados">
		Sin tratamientos emblistados
	</logic:empty>	
	<br/>
	<br/>
	<h4>Medicación fuera de blister</h4>
	<logic:notEmpty  name="data" property="ttosFueraBlister">
	    <table border="1" style="width:50%">
	    	<thead>  
				<tr class="rd_cabecera">
	            	<th>Nombre en bolsa</th>
	            	<th>Pauta residencia</th>
				</tr>
			</thead>
			
			<logic:iterate id="trat" name="data" property="ttosFueraBlister" type="lopicost.spd.robot.bean.rd.TratamientoPaciente">
			<bean:define id="medic" name="trat" property="medicamentoPaciente" />
				<tr class="rd_cabecera">
					<td><bean:write name="medic" property="cn" /> - <bean:write name="medic" property="nombreMedicamentoBolsa" /></td>
					<td><bean:write name="medic" property="pautaResidencia" /></td>
				</tr>
			</logic:iterate>
		</table>
	</logic:notEmpty>
	<logic:empty  name="data" property="ttosFueraBlister">
		Sin fuera de blister
	</logic:empty>
	<br/>
	<br/>
	<h4>Dispensaciones receta</h4>
	<logic:notEmpty  name="pac" property="dispensacionesReceta">
		<table border="1" style="width:80%">
			<thead>  
				<tr class="rd_cabecera">
					<th>Medicamento</th>
					<th>Lab</th>
					<th>Lote</th>
					<th>Caducidad</th>
					<th>Núm. Serie</th>
				</tr>
			</thead>
			<logic:iterate id="disp" name="pac" property="dispensacionesReceta" type="lopicost.spd.robot.bean.rd.MedicamentoReceta">
			<tr>
				<td><bean:write name="disp" property="cn" /> - <bean:write name="disp" property="nombreMedicamentoConsejo" /></td>
				<td><bean:write name="disp" property="labMedicamento" /></td>
				<td><bean:write name="disp" property="lote" /></td>
				<td><bean:write name="disp" property="caducidad" /></td>
				<td><bean:write name="disp" property="numSerie" /></td>
			</tr>
			</logic:iterate>
		</table>
	</logic:notEmpty>
	<logic:empty  name="pac" property="dispensacionesReceta">
		No existen registros a mostrar de dispensaciones
	</logic:empty>
	




	</fieldset>	
	
	</logic:iterate>
</html:form>
</body>
</html:html>