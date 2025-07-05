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
  <title>Informe global producción</title>
  <jsp:include page="/spd/jsp/global/headPDF.jsp"/>
</head>
<body>

<html:form action="/InformeProdSpd.do" method="post">
<bean:define id="formulari" name="InformeProdSpdForm" type="lopicost.spd.struts.form.InformeProdSpdForm" />
<bean:define id="cab" name="formulari"  property="cabecera" type="lopicost.spd.struts.bean.FicheroResiBean" />

<fieldset style="width: 50%;">     
	<div>
		<label for="fechaRecogidaSPD" accesskey="e">Nombre de la residencia:</label><bean:write name="cab" property="nombreDivisionResidencia" />
	</div>
	<div>
		<label for="fechaRecogidaSPD" accesskey="e">Fechas de consumo:</label><bean:write name="cab" property="fechaDesde" /> - <bean:write name="cab" property="fechaHasta" />
	</div>
	<div>
		<label for="fechaRecogidaSPD" accesskey="e">Fecha de entrega en residencia:</label><bean:write name="cab" property="fechaEntregaSPD" />
	</div>	
	<div>
		<label for="fechaRecogidaSPD" accesskey="e">Persona que recoge el SPD:</label><bean:write name="cab" property="fechaRecogidaSPD" />
	</div>	
	<div>
		<label for="farmacia" accesskey="e">Farmacia responsable:</label>Farmacia Bertran
	</div>	
	<div>
		<label for="farmaceutico" accesskey="e">Farmacéutico responsable:</label>Marco A. González
	</div>	
</fieldset>

     <logic:iterate id="data" name="formulari" property="producciones" type="lopicost.spd.robot.bean.rd.ProduccionPaciente">

        <bean:define id="dias" name="data" property="diasProduccion" />
        <bean:define id="diasSPD" name="data" property="diasSPD" />
        <bean:define id="pac" name="data" property="paciente" />
     		CIP:<bean:write name="pac" property="CIP" />
   			<br/>
   			Código numérico interno:<bean:write name="data" property="orderNumber" />
			<br/><br/>

        <table border="1" >
        
          <!-- colgroup>
		    <col style="width: 5%;" />
		    <col style="width: 30%;" />  
		    <col style="width: 11%;" />
		    <col style="width: 10%;" />
		    <col style="width: 10%;" />
            <c:forEach var="dia" items="${data.diasSPD}">
                      <col style="width: 10%;" />
            </c:forEach>

		    <col style="width: 10%;" />
		  </colgroup -->
  
  
  
            <thead>
                <tr>
                    <th style="width: 4em">cn</th>
                    <th>medicamento</th>
                    <th style="width: 4em">pauta</th>
                    <th style="width: 4em">Lote</th>
                    <th style="width: 4em">Caducidad</th>

                    <c:forEach var="dia" items="${data.diasSPD}">
                        <th><c:out value="${dia.fechaToma}" /></th>
                    </c:forEach>
                    <th>Unidades utilizadas</th>
                </tr>
            </thead>
            <tbody>
                <logic:iterate id="trat" name="data" property="tratamientosPaciente" type="lopicost.spd.robot.bean.rd.TratamientoPaciente">
                    <bean:define id="medic" name="trat" property="medicamentoPaciente" />
                    <tr>		
                        <td><bean:write name="medic" property="cn" /></td>
                        <td><bean:write name="medic" property="nombreMedicamentoBolsa" /></td>
                        <td><bean:write name="medic" property="pautaResidencia" /></td>
                        <td><bean:write name="medic" property="lote" /></td>
                        <td><bean:write name="medic" property="caducidad" /></td>

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
						<td><bean:write name="trat" property="cantidadUtilizadaSPD" /></td>
                    </tr>
                </logic:iterate>
            </tbody>
        </table>
    </logic:iterate>
</html:form>
</body>
</html:html>
