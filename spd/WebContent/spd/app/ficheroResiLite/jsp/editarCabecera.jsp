<%@ page language="java" %>
<%@ page import="java.util.*" %>



<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	


<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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

        const opcionesComunes = {
                dateFormat: "d/m/Y",
                locale: "es"
            };
        
	        // Configurar Flatpickr para "Desde"
	        flatpickr("#nuevaFechaDesde", {
	            dateFormat: "d/m/Y", // Formato DD/MM/YYYY
	            locale: "es",
	            minDate: fechaDesde || "today", // El rango m�nimo es fechaDesde
	            maxDate: fechaHasta || fechaDesde, // El rango m�ximo es fechaHasta
	            defaultDate: nuevaFechaDesde || undefined, // Fecha predeterminada en nuevaFechaDesde
	        });

        // Aplicar Flatpickr a otros campos de fecha
        ["fechaEntregaSPD", "fechaRecogidaSPD", "fechaDesemblistaSPD", "fechaProduccionSPD"].forEach(id => {
            const input = document.getElementById(id);
            if (input) {
                flatpickr(input, {
                    dateFormat: "d/m/Y",
                    locale: "es"
                });
            }
        });        
        
        // Configurar Flatpickr para "Hasta"
        flatpickr("#nuevaFechaHasta", {
            dateFormat: "d/m/Y", // Formato DD/MM/YYYY
            locale: "es",
            minDate: fechaDesde || "today", // El rango m�nimo es fechaDesde
            maxDate: fechaHasta || fechaDesde, // El rango m�ximo es fechaHasta
            defaultDate: nuevaFechaHasta || undefined, // Fecha predeterminada en nuevaFechaHasta
        });

        // Funci�n de confirmaci�n de fechas
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
                return; // No ejecutar la acci�n si las fechas no son correctas
            } else {
                // Llamada a la funci�n editarOk si las fechas son v�lidas
                editarOk(oidCab);
                // Si quisieras hacer algo m�s despu�s de la verificaci�n, puedes agregarlo aqu�.
            }
        }
    });
    

    
</script>

<script type="text/javascript">
    function lanzarProceso(oidProceso, boton) {
        if (window.confirm("�Est�s seguro de que deseas lanzar este proceso?")) {
            // Desactivar el bot�n
            boton.disabled = true;
            boton.value = "Lanzando...";

            fetch('<%= request.getContextPath() %>/Procesos.do', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: new URLSearchParams({
                    parameter: 'lanzar',
                    ACTIONTODO: 'LANZAR',
                    oidProceso: oidProceso
                })
            })
            .then(response => {
                if (response.ok) {
                    alert('Proceso lanzado correctamente, en unos minutos estar�n recogidos');
                } else {
                    alert('Error al lanzar el proceso');
                    boton.disabled = false;
                    boton.value = "Lanzar proceso";
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Error en la petici�n');
                boton.disabled = false;
                boton.value = "Lanzar proceso";
            });
        }
    }
    
</script>

<jsp:include page="/spd/jsp/global/head.jsp"/>
<title>Borrado de carga de fichero</title>
</head>

<bean:define id="formulari" name="FicheroResiForm" type="lopicost.spd.struts.form.FicheroResiForm" />


<body id="general">
	<center>
		<h2>Edici�n de cabecera de proceso</h2>
		<html:form action="/FicheroResiCabeceraLite.do" method="post"  >	

<div>
     <html:hidden property="parameter" value="editar"/>
     <html:hidden property="ACTIONTODO" value="CONFIRMAR"/>
     <html:hidden property="filtroDivisionResidenciasCargadas" /> 
     <html:hidden property="oidFicheroResiCabecera" /> 
     <html:hidden property="oidProceso" /> 
     <html:hidden property="oidDivisionResidencia" />
  	 <html:hidden property="fechaDesde" styleId="fechaDesde" />
	 <html:hidden property="fechaHasta" styleId="fechaHasta" />
<!-- Variables ocultas que contienen las fechas m�nima y m�xima del rango -->

	<h3>Confirmar edici�n</h3>
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
			<label for="fechaHoraProceso">Fecha carga fichero resi</label>
			<bean:write name="data" property="fechaHoraProceso" />
		</div>
		<div>
		<label for="nombreProduccionRobot">Nombre en robot<br> (residencia + inicio + n�creaciones de fichero)</label>
		<c:choose>
		    <c:when test="${empty data.nombreProduccionRobot}">
		        Pendiente de creaci�n 
		    </c:when>
		    <c:otherwise>
		        <bean:write name="data" property="nombreProduccionRobot" />  
		    </c:otherwise>
		</c:choose>
		</div>
	</fieldset>
	<fieldset align="left">
		<div style="color: blue;">
			(Afecta a "Ficheros DM y RX")
		</div>
		<div>
			Selecci�n de otras fechas, en caso de querer que esta producci�n se env�e en otras fechas (siempre dentro del rango de d�as)
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
			<p>Se puede configurar cuando comienzan las tomas del primer d�a y acaban las del �ltimo</p>
		</div>
		<div>    
		    <label for="nuevaTomaDesde">Primer d�a desde la toma:</label>    
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
		    <label for="nuevaTomaHasta">�ltimo d�a hasta la toma:</label>    
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
	
	<fieldset align="left">
		<div>
		    <label for="free1">Nota 1</label>
		    <html:text name="data" property="free1"  />
		</div>
		<div>
		    <label for="free2">Nota 2</label>
		    <html:text name="data" property="free2"  />
		</div>
		<div>
		    <label for="free3">Nota 3</label>
		    <html:text name="data" property="free3"  />
		</div>
	</fieldset>	
	<fieldset style="text-align: left;">
		
		<div style="color: blue;" align="left">
			Producci�n.  Forzar recogida de datos --> <input type="button" value="Lanzar proceso 1" onclick="lanzarProceso('24', this);" />
			<input type="button" value="Lanzar proceso 2" onclick="lanzarProceso('20', this);" />
			</br></br>
		<a href="#" onclick="informeRobotSpd('<bean:write name="data" property="oidFicheroResiCabecera" />', 'etiquetasR'); return false;">
			Generador etiquetas agosto
		</a></br></br>
		<a href="#" onclick="informeRobotSpd('<bean:write name="data" property="oidFicheroResiCabecera" />', 'etiquetas'); return false;">
			Generador etiquetas
		</a></br></br>		
		<a href="#" onclick="informeRobotSpd('<bean:write name="data" property="oidFicheroResiCabecera" />', 'globalLiteAll'); return false;">
			Generador etiquetas (con receta)
		</a></br></br>
		<logic:equal property="idUsuario" name="formulari" value="admin">
		<a href="#" onclick="informeRobotSpd('<bean:write name="data" property="oidFicheroResiCabecera" />', 'global'); return false;">
			Generador etiquetas (con receta y d�as en columnas)
		</a></br></br>
			<a href="#" onclick="informeRobotSpd('<bean:write name="data" property="oidFicheroResiCabecera" />', 'detalle'); return false;">
				Detalle
			</a></br>
		</logic:equal>
		</div>
		<!-- a href="#" onclick="informeRobotSpd('<bean:write name="data" property="oidFicheroResiCabecera" />', 'globalLite'); return false;">
			Global - Modelo 3 (globalLite)
		</a> -->
		<div style="color: blue;">
			Campos opcionales, en caso de rellenarse y grabar, aparecer�n en el informe
		</div>

		<div>
		    <label for="medicoResponsable">M�dico responsable</label>
		    <html:text name="data" property="medicoResponsable"  />
		</div>		
		<div>
		    <label for="usuarioDesemblistaSPD">Resp. desemblistado / fecha</label>
		    <html:text name="data" property="usuarioDesemblistaSPD"  />
		    / <html:text name="formulari" property="fechaDesemblistaSPD" styleId="fechaDesemblistaSPD" value="${data.fechaDesemblistaSPD}" />
		</div>
		<div>
		    <label for="usuarioProduccionSPD">Resp. producci�n  / fecha</label>
		    <html:text name="data" property="usuarioProduccionSPD"  />
		    / <html:text name="formulari" property="fechaProduccionSPD" styleId="fechaProduccionSPD" value="${data.fechaProduccionSPD}" />
		</div>
		<div>
		    <label for="usuarioEntregaSPD">Resp. entrega  / fecha</label>
		    <html:text name="data" property="usuarioEntregaSPD"  />
		    / <html:text name="formulari" property="fechaEntregaSPD" styleId="fechaEntregaSPD" value="${data.fechaEntregaSPD}" />
		</div>
		<div>
		    <label for="usuarioRecogidaSPD">Resp. recogida en destino / fecha </label>
		    <html:text name="data" property="usuarioRecogidaSPD"  />
		    / <html:text name="formulari" property="fechaRecogidaSPD" styleId="fechaRecogidaSPD" value="${data.fechaRecogidaSPD}" />
		</div>

		</br>
	</fieldset>	


		


	<div>
		<p class="botons" align="left">
			<input type="button" onclick="javascript:volver()" value="Volver"/>
			<input type="button" onclick="javascript:editarOk('<bean:write name='data' property='oidFicheroResiCabecera'/>')" value="Grabar"  />
		</p>	
	</div>			
</html:form>

<html:form action="/Procesos.do" method="post" style="display:none;" focus="false" styleId="LanzadorForm">
    <html:hidden property="parameter" />
    <html:hidden property="ACTIONTODO" />
    <html:hidden property="oidProceso" />
</html:form>

</body>
</html>