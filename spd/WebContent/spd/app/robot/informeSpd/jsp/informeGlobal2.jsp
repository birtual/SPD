<%@ page language="java" %>
<%@ page import="java.util.*" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	
<html:html>
<head>
    <title>Informe global producción</title>
    <jsp:include page="/spd/jsp/global/head.jsp"/>
<head/> 

<body>

<html:form action="/InformeSpd.do" method="post">
<bean:define id="formulari" name="InformeSpdForm" type="lopicost.spd.struts.form.InformeSpdForm" />
 <html:hidden property="oidFicheroResiCabecera" /> 
 
<table style="width:80%">



<logic:iterate id="data" name="formulari" property="producciones" type="lopicost.spd.robot.bean.rd.ProduccionPaciente">
    <bean:define id="dias" name="data" property="diasProduccion" />
    <bean:define id="diasSPD" name="data" property="diasSPD" />
    <bean:define id="pac" name="data" property="paciente" />

    <div><bean:write name="pac" property="CIP" /></div>

    <table border="1">
        <thead>
            <tr>
                <th>cn</th>
                <th>medicamento</th>
                <th>pauta</th>
                <th>Lote</th>
                <th>Caducidad</th>

                <c:forEach var="dia" items="${data.diasSPD}">
                    <th><c:out value="${dia.fechaToma}" /></th>
                </c:forEach>
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
				                    <div><%= toma.getNombreToma() %></div>
				        <%
				                }
				            }
				        %>
				    </td>
				</c:forEach>
                </tr>
            </logic:iterate>
            <a href="ExportarInformeSpd.do?oidFicheroResiCabecera=14570" target="_blank">Exportar a PDF</a>
        </tbody>
    </table>
</logic:iterate>
 
</html:form>
</html:html>

