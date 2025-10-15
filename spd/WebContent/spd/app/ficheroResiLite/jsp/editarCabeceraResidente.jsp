<%@ page language="java" %>
<%@ page import="java.util.*" %>


<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>




<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
<script language="javaScript" src="/spd/spd/app/ficheroResiLite/js/ficheroResiCabeceraLite.js"></script>

<head>
<!-- Flatpickr CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">

<!-- Flatpickr JavaScript -->

<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
<script src="https://cdn.jsdelivr.net/npm/flatpickr/dist/l10n/es.js"></script>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        // Obtener los valores de los campos ocultos generados por Struts
        const fechaDesde = document.getElementById('fechaDesde') ? document.getElementById('fechaDesde').value : '';  // Valor de fechaDesde
        const fechaHasta = document.getElementById('fechaHasta') ? document.getElementById('fechaHasta').value : '';  // Valor de fechaHasta

        console.log('Fecha Desde:', fechaDesde);
        console.log('Fecha Hasta:', fechaHasta);

        // Obtener los campos nuevaFechaDesde y nuevaFechaHasta
        const nuevaFechaDesde = document.getElementById('nuevaFechaDesde') ? document.getElementById('nuevaFechaDesde').value || fechaDesde : fechaDesde;
        const nuevaFechaHasta = document.getElementById('nuevaFechaHasta') ? document.getElementById('nuevaFechaHasta').value || fechaHasta : fechaHasta;

        // Configurar Flatpickr para "Desde"
        flatpickr("#nuevaFechaDesde", {
            dateFormat: "d/m/Y", // Formato DD/MM/YYYY
            locale: "es",
            minDate: fechaDesde || "today", // El rango mínimo es fechaDesde
            maxDate: fechaHasta || fechaDesde, // El rango máximo es fechaHasta
            defaultDate: nuevaFechaDesde || undefined, // Fecha predeterminada en nuevaFechaDesde
        });

        // Configurar Flatpickr para "Hasta"
        flatpickr("#nuevaFechaHasta", {
            dateFormat: "d/m/Y", // Formato DD/MM/YYYY
            locale: "es",
            minDate: fechaDesde || "today", // El rango mínimo es fechaDesde
            maxDate: fechaHasta || fechaDesde, // El rango máximo es fechaHasta
            defaultDate: nuevaFechaHasta || undefined, // Fecha predeterminada en nuevaFechaHasta
        });

        // Función de confirmación de fechas
        function confirmarFechas(oidCab) {
            // Obtener las fechas seleccionadas
            const nuevaFechaDesde = document.getElementById('nuevaFechaDesde').value;
            const nuevaFechaHasta = document.getElementById('nuevaFechaHasta').value;

            // Convertir las fechas a formato Date
            const fechaDesdeObj = new Date(nuevaFechaDesde.split('/').reverse().join('-')); // Formato de fecha desde
            const fechaHastaObj = new Date(nuevaFechaHasta.split('/').reverse().join('-')); // Formato de fecha hasta

            // Verificar si la fecha hasta es anterior a la fecha desde
            if (fechaHastaObj < fechaDesdeObj) {
                alert("La fecha 'Hasta' no puede ser anterior a la fecha 'Desde'. Por favor, revisa las fechas.");
                return; // No ejecutar la acción si las fechas no son correctas
            } else {
                // Llamada a la función editarOk si las fechas son válidas
                editarOk(oidCab);
                // Si quisieras hacer algo más después de la verificación, puedes agregarlo aquí.
            }
        }
    });
</script>
<jsp:include page="/spd/jsp/global/head.jsp"/>
<title>Edición de cabecera de proceso</title>
</head>

<bean:define id="formulari" name="FicheroResiForm" type="lopicost.spd.struts.form.FicheroResiForm" />


<body id="general">
	<center>
		<h2>Edición de cabecera de proceso</h2>
		<html:form action="/FicheroResiCabeceraLite.do" method="post"  >	

<div >

     <html:hidden property="parameter" value="editar"/>
     <html:hidden property="ACTIONTODO" value="CONFIRMAR"/>
     <html:hidden property="oidPaciente" /> 
     <html:hidden property="filtroDivisionResidenciasCargadas" /> 
     <html:hidden property="oidFicheroResiCabecera" /> 
     <html:hidden property="oidDivisionResidencia" />
  	 <html:hidden property="fechaDesde" styleId="fechaDesde" />
	 <html:hidden property="fechaHasta" styleId="fechaHasta" />
<!-- Variables ocultas que contienen las fechas mínima y máxima del rango -->


	<h3>Confirmar edición</h3>
	<bean:define id="data" name="formulari" property="ficheroResiDetalleBean" />
	<fieldset align="left">
		<div>
			<label for="idDivisionResidencia">Residencia</label>		
			<bean:write name="data" property="idDivisionResidencia" />
		</div>
		<div>
			<label for="idProceso">idProceso</label>
			<bean:write name="data" property="idProceso" />
		</div>
		<div>
			<label for="fechaHoraProceso">Fecha carga fichero</label>
			<bean:write name="data" property="fechaHoraProceso" />
		</div>
	</fieldset>
	<fieldset align="left">
		<div style="color: blue;">
			(Afecta a "Ficheros DM y RX")
		</div>
		<div>
			Selección de otras fechas, en caso de querer que esta producción se envíe en otras fechas (siempre dentro del rango de días)
		</div>
	
		<!-- Campo de entrada para nuevaFechaDesde -->
		<div>
		    <label for="nuevaFechaDesde">Desde:</label>
		    <html:text name="formulari" property="nuevaFechaDesde" styleId="nuevaFechaDesde" value="${formulari.nuevaFechaDesde}" />
		</div>
		
		<!-- Campo para seleccionar la fecha de fin -->
		<div>
		    <label for="nuevaFechaHasta">Hasta:</label>
		    <html:text name="formulari" property="nuevaFechaHasta" styleId="nuevaFechaHasta" value="${formulari.nuevaFechaHasta}" />
		</div>

		<div>
			<p>Se puede configurar cuando comienzan las tomas del primer día y acaban las del último</p>
		</div>
		<div>    
		    <label for="nuevaTomaDesde">Primer día desde la toma:</label>    
		    <html:select property="nuevaTomaDesde"> 
		        <!-- Opciones generadas desde la lista -->
		        <logic:iterate id="toma" name="formulari" property="listaTomasCabecera" type="lopicost.spd.struts.bean.CabecerasXLSBean">
		            <option value="<%= toma.getIdToma() %>" 
		                <%= (toma.getIdToma().equals(formulari.getNuevaTomaDesde()) ? "selected=\"selected\"" : "") %>>
		                <%= toma.getNombreToma() %>
		            </option>
		        </logic:iterate>
		    </html:select>
		</div>
		<div>    
		    <label for="nuevaTomaHasta">último día hasta la toma:</label>    
		    <html:select property="nuevaTomaHasta"> 
		        <!-- Opciones generadas desde la lista -->
		        <logic:iterate id="toma" name="formulari" property="listaTomasCabecera" type="lopicost.spd.struts.bean.CabecerasXLSBean">
		            <% 
		                String selected = "";
		                if (formulari.getNuevaTomaHasta() != null && 
		                    formulari.getNuevaTomaHasta().equals(toma.getIdToma())) {
		                    selected = "selected=\"selected\"";
		                } else if (formulari.getNuevaTomaHasta() == null && 
		                           formulari.getListaTomasCabecera().indexOf(toma) == 
		                           formulari.getListaTomasCabecera().size() - 1) {
		                    selected = "selected=\"selected\"";
		                }
		            %>
		            <option value="<bean:write name='toma' property='idToma' />" <%= selected %>>
		                <bean:write name="toma" property="nombreToma" />
		            </option>
		        </logic:iterate>
		    </html:select>
		</div>
	</fieldset>

	<div>
		<p class="botons" align="left">
			<input type="button" onclick="javascript:cerrar()" value="Cerrar"/>
			<input type="button" onclick="javascript:editarOkResidente('<bean:write name='formulari' property='oidPaciente'/>', '<bean:write name='data' property='oidFicheroResiCabecera'/>')" value="Confirmar y generar Ficheros DM y RX"  />
		</p>	
	</div>			
</html:form>

</body>
</html>