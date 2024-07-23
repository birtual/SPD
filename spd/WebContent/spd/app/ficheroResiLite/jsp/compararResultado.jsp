<%@ page language="java" %>
<%@ page import="java.util.*" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
	<script language="javaScript" src="/spd/spd/app/ficheroResiLite/js/ficheroResiLite.js"></script>

<head>
<jsp:include page="/spd/jsp/global/head.jsp"/>
<title>Gestión carga ficheros Robot</title>
</head>

<bean:define id="formulari" name="FicheroResiForm" type="lopicost.spd.struts.form.FicheroResiForm" />

		

<body id="general">

<html:form action="/FicheroResiDetalleLite.do" method="post">	
<html:errors/>

    	<!-- mostramos mensajes y errores, si existen -->
 	 <ul>
        <logic:notEmpty name="FicheroResiForm" property="errors">
            <li class="error">
                <u>Mensaje:</u>
                <logic:iterate id="error" name="FicheroResiForm" property="errors" type="java.lang.String">
                    <span><bean:write name="error"/></span>
                </logic:iterate>
            </li>
        </logic:notEmpty>
    </ul>
    
<div id="contingut">
     <html:hidden property="parameter" value="list"/>
     <html:hidden property="ACTIONTODO" value="list"/>
     <html:hidden property="oidFicheroResiCabecera" />
     <html:hidden property="oidFicheroResiDetalle" />
     <html:hidden property="procesoValido" />
     <html:hidden property="idProceso" />
     <html:hidden property="filtroProceso" />
     <html:hidden property="idDivisionResidencia" />
     <html:hidden property="campoOrder" />
     <html:hidden property="excluirNoPintar" />
    <html:hidden property="idProcessIospd" />
    <html:hidden property="mode" />         
      
     <html:hidden property="oidDivisionResidencia" /> 

	
 	<fieldset>
		  <div>
			<p>Residencia: <%= formulari.getIdDivisionResidencia() %>	</p> 
		</div>

	   	<div>	
		    <p class="botons">
				<input type="button" class="azulCielo" onclick="javascript:goInicio();" value="Inicio" />
				<input type="button" class="azulCielo" onclick="javascript:listado();"  value="Volver"  />  

			</p> 
		</div>
 


 </fieldset>


<%
// Crear un HashMap para mapear los valores de tipoRegistro a clases CSS únicas
HashMap<String, String> tipoRegistroCssMap = new HashMap<String, String>();
// Contador para asignar valores únicos a cada tipoRegistro
int counter = 1;
// Iterar sobre la lista para asignar clases CSS únicas a cada tipoRegistro
for (lopicost.spd.struts.bean.FicheroResiBean data : formulari.getListaFicheroResiDetalleBean()) {
    String tipoRegistro = data.getTipoRegistro();
    // Verificar si el tipoRegistro ya está en el HashMap
    if (!tipoRegistroCssMap.containsKey(tipoRegistro)) {
        // Asignar una clase CSS única para este tipoRegistro
        tipoRegistroCssMap.put(tipoRegistro, "clase-" + counter);
        // Incrementar el contador para la siguiente clase CSS única
        counter++;
    }
}
//out.println("Contenido del tipoRegistroCssMap:");
//out.println("<ul>");
//for (Map.Entry<String, String> entry : tipoRegistroCssMap.entrySet()) {
	//   out.println("<li>" + entry.getKey() + " - " + entry.getValue() + "</li>");
    //}
//out.println("</ul>");
session.setAttribute("tipoRegistroCssMap", tipoRegistroCssMap);
%>

<table>
<c:forEach items="${FicheroResiForm.listaFicheroResiDetalleBean}" var="data" varStatus="status">

       <c:set var="tipoRegistroCss" value="${tipoRegistroCssMap[data.tipoRegistro]}" />              
     
     <tr class="${tipoRegistroCss}">

                   
       <!-- Tratar el primer registro -->
        <td >${data.tipoRegistro}</<td>
        <td >${data.resiCIP}</<td>
        <td>${data.resiApellidosNombre}</<td>
        <td>${data.resiCn}</<td>
        <td>${data.resiMedicamento}</<td>
        <td>${data.resiObservaciones}</<td>
        <td>${data.resiComentarios}</<td>
        <td>${data.resiPeriodo}</<td>
        <td>${data.resiVariante}</<td>
        <td>${data.resiSiPrecisa}</<td>
        <td>${data.spdCnFinal}</<td>
        <td>${data.spdNombreBolsa}</<td>
        <td>${data.spdAccionBolsa}</<td>
        <td>${data.resiD1}</<td>
        <td>${data.resiD2}</<td>
        <td>${data.resiD3}</<td>
        <td>${data.resiD4}</<td>
        <td>${data.resiD5}</<td>
        <td>${data.resiD6}</<td>
        <td>${data.resiD7}</<td>
        <td>${data.resiToma1}</<td>
        <td>${data.resiToma2}</<td>
        <td>${data.resiToma3}</<td>
        <td>${data.resiToma4}</<td>
        <td>${data.resiToma5}</<td>
        <td>${data.resiToma6}</<td>
        <c:if test="${not empty data.resiToma7}"><td>${data.resiToma7}</<td></c:if>
        <c:if test="${not empty data.resiToma8}"><td>${data.resiToma8}</<td></c:if>
        <c:if test="${not empty data.resiToma9}"><td>${data.resiToma9}</<td></c:if>
        <c:if test="${not empty data.resiToma10}"><td>${data.resiToma10}</<td></c:if>
        <c:if test="${not empty data.resiToma11}"><td>${data.resiToma11}</<td></c:if>
        <c:if test="${not empty data.resiToma12}"><td>${data.resiToma12}</<td></c:if>
        <c:if test="${not empty data.resiToma13}"><td>${data.resiToma13}</<td></c:if>
        <c:if test="${not empty data.resiToma14}"><td>${data.resiToma14}</<td></c:if>
        <c:if test="${not empty data.resiToma15}"><td>${data.resiToma15}</<td></c:if>
        <c:if test="${not empty data.resiToma16}"><td>${data.resiToma16}</<td></c:if>
        <c:if test="${not empty data.resiToma17}"><td>${data.resiToma17}</<td></c:if>
        <c:if test="${not empty data.resiToma18}"><td>${data.resiToma18}</<td></c:if>
        <c:if test="${not empty data.resiToma19}"><td>${data.resiToma19}</<td></c:if>
        <c:if test="${not empty data.resiToma20}"><td>${data.resiToma20}</<td></c:if>
        <c:if test="${not empty data.resiToma21}"><td>${data.resiToma21}</<td></c:if>
        <c:if test="${not empty data.resiToma22}"><td>${data.resiToma22}</<td></c:if>
        <c:if test="${not empty data.resiToma23}"><td>${data.resiToma23}</<td></c:if>
        <c:if test="${not empty data.resiToma24}"><td>${data.resiToma24}</<td></c:if>
</tr>
</c:forEach>
</table>
	

	

	</div>	
	</html:form>

</body>
</html>