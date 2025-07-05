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
<bean:define id="formulari" name="InformeProdSpdForm" type="lopicost.spd.struts.form.InformeProdSpdForm" />
<bean:define id="cab" name="formulari"  property="cabecera" type="lopicost.spd.struts.bean.FicheroResiBean" />


<html:form action="/InformeProdSpd.do" method="post">

<fieldset>

				 
	<div>
		<label for="nombreDivisionResidencia" accesskey="e">Nombre de la residencia:</label><bean:write name="cab" property="nombreDivisionResidencia" />
	</div>
	<div>
		<label for="fechaConsumo" accesskey="e">Fechas de consumo:</label><bean:write name="cab" property="fechaDesde" /> - <bean:write name="cab" property="fechaHasta" />
	</div>
	<div>
		<label for="fechaEntregaSPD" accesskey="e">Entrega en residencia:</label><bean:write name="cab" property="fechaEntregaSPD" /> - <bean:write name="cab" property="usuarioEntregaSPD" />
	</div>	
	<div>
		<label for="usuarioRecogidaSPD" accesskey="e">Recogida del SPD:</label><bean:write name="cab" property="fechaRecogidaSPD" /> - <bean:write name="cab" property="usuarioRecogidaSPD" />
	</div>	
	<div>
		<label for="farmacia" accesskey="e">Farmacia responsable:</label>Farmacia Bertran
	</div>	
	<div>
		<label for="farmaceutico" accesskey="e">Farmacéutico responsable:</label>Marco A. González
	</div>	
    <a href="ExportarInformeProdSpd.do?oidFicheroResiCabecera=<bean:write name="cab" property="oidFicheroResiCabecera" />" >Exportar a PDF</a>
</fieldset>			
			
	<input type="hidden" name="oidFicheroResiCabecera" value="<bean:write name='InformeProdSpdForm' property='oidFicheroResiCabecera' />" />
    <logic:iterate id="data" name="formulari" property="producciones" type="lopicost.spd.robot.bean.rd.ProduccionPaciente">

        <bean:define id="dias" name="data" property="diasProduccion" />
        <bean:define id="diasSPD" name="data" property="diasSPD" />
        <bean:define id="pac" name="data" property="paciente" />



        <table border="1" style="width:80%">
            <thead>
   			CIP:<bean:write name="pac" property="CIP" />
   			<br/>
   			Código numérico interno:<bean:write name="data" property="orderNumber" />
			<br/><br/>
                <tr>
                    <th>CN</th>
                    <th>Medicamento</th>
                    <th>Pauta</th>
                    <th>Lote</th>
                    <th>Caducidad</th>

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
			                java.util.List<lopicost.spd.robot.bean.rd.Toma> tomas = dia.getTomas();
			                if (tomas.isEmpty()) {
			        %>
			                    0
			        <%
			                } else {
			                    for (int j = 0; j < tomas.size(); j++) {
			                        lopicost.spd.robot.bean.rd.Toma toma = tomas.get(j);
			                        if (toma != null) {
			        %>
			                            <span><%= toma.getNombreToma() %> (<%= toma.getCantidadToma() %>)</span><br/>
			        <%
			                        } else {
			        %>
			                            <span>-</span><br/>
			        <%
			                        }
			                    }
			                }
			            } else {
			        %>
			                0
			        <%
			            }
			        %>
			    </td>
			</c:forEach>

                       	<td><bean:write name="trat" property="cantidadUtilizadaSPD" /></td>
                    </tr>
                </logic:iterate>
            </tbody>
        </table><br/><br/><br/>
    </logic:iterate>
    
</html:form>
</body>
</html:html>
