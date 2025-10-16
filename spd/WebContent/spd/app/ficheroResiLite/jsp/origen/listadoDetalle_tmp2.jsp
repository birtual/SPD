<%@ page language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="lopicost.spd.utils.SPDConstants" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 	
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">

<head>
    <bean:define id="formulari" name="FicheroResiForm" type="lopicost.spd.struts.form.FicheroResiForm" />
<script type="text/javascript">
        // Función para hacer AJAX y cambiar la visibilidad de los datos
        function toggleVerDatos() {
            var mostrarDatosCompletos = document.getElementById("verDatos").value === "true";
            
            // Llamada AJAX para obtener los datos completos o enmascarados
            var xhr = new XMLHttpRequest();
            xhr.open("GET", "obtenerDatos.jsp?mostrarCompletos=" + !mostrarDatosCompletos, true);
            xhr.onreadystatechange = function() {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    var response = JSON.parse(xhr.responseText);
                    var rows = document.querySelectorAll(".dataRow");
                    
                    rows.forEach(function(row, index) {
                        var resiCIP = row.querySelector(".resiCIP");
                        var resiCIPMask = row.querySelector(".resiCIPMask");
                        
                        // Cambiar entre mostrar datos completos o enmascarados
                        if (response[index].mostrarCompletos) {
                            resiCIP.textContent = response[index].resiCIP;
                            resiCIPMask.textContent = "";
                        } else {
                            resiCIP.textContent = "";
                            resiCIPMask.textContent = response[index].resiCIPMask;
                        }
                    });

                    // Cambiar el estado para la siguiente vez
                    document.getElementById("verDatos").value = mostrarDatosCompletos ? "false" : "true";
                }
            };
            xhr.send();
        }
    </script>
</head>

<bean:define id="formulari" name="FicheroResiForm" type="lopicost.spd.struts.form.FicheroResiForm" />
    <!-- botón para alternar entre mostrar/ocultar datos completos o enmascarados -->
     <button type="button" onclick="toggleVerDatos()">Ver Datos Completos/Enmascarados</button>

<table class="blueTable" border="1">
<thead>
	<tr>
		<!-- spdCnFinal -->
		<th>CnFinal 
			<html:select property="seleccionSpdCnFinal"  value="${formulari.seleccionSpdCnFinal}" onchange="javascript:goSubmit();" styleClass="ancho_5" >    
	    		<html:option value="">Todos</html:option>
	           		<c:forEach items="${FicheroResiForm.listaSpdCnFinal}" var="bean"> 
	   	       		<c:set var="selected" value="${FicheroResiForm.seleccionSpdCnFinal == bean ? 'selected' : ''}" />
	   	       		<option value="${bean}" ${selected}><c:out value="${bean}" /></option>   
	 			</c:forEach>                  
			</html:select>
		<a href="javascript:void(0);" onclick="ordenarPor('spdCnFinal asc')" class="arrow">&uarr;</a><a href="javascript:void(0);" onclick="ordenarPor('spdCnFinal desc')" class="arrow">&darr;</a>			
		</th>

		<!-- Nombre en bolsa -->
		<th nowrap>Nombre en bolsa<br>
			<html:select property="seleccionSpdNombreBolsa"  value="${formulari.seleccionSpdNombreBolsa}" onchange="javascript:goSubmit();"  styleClass="ancho_17">  
				<html:option value="">Todos</html:option>
	          		<c:forEach items="${FicheroResiForm.listaSpdNombreBolsa}" var="bean"> 
	   	       		<c:set var="selected" value="${FicheroResiForm.seleccionSpdNombreBolsa == bean ? 'selected' : ''}" />
	   	     		<option value="${bean}" ${selected}><c:out value="${bean}" /></option>   
	 			</c:forEach>                  
			</html:select><br>
		<a href="javascript:void(0);" onclick="ordenarPor('spdNombreBolsa asc')" class="arrow">&uarr;</a><a href="javascript:void(0);" onclick="ordenarPor('spdNombreBolsa desc')" class="arrow">&darr;</a>			
		</th>

		<!-- Nombre paciente -->
		<th> Nombre paciente
		    <html:select property="seleccionResiApellidosNombre" onchange="goSubmit();" styleClass="ancho_23">
		        <html:option value="">Todos</html:option>
		        <html:options name="FicheroResiForm" property="listaResiApellidosNombre"/>
		    </html:select>
		    
		    <a href="javascript:void(0);" onclick="ordenarPor('resiApellidosNombre asc')" class="arrow">&uarr;</a>
		    <a href="javascript:void(0);" onclick="ordenarPor('resiApellidosNombre desc')" class="arrow">&darr;</a>
		</th>	    
	
		<!-- CIP -->
		<th >CIP
			<html:select property="seleccionResiCIP"  value="${formulari.seleccionResiCIP}" onchange="javascript:goSubmit();" styleClass="ancho_10" >   
	   			<html:option value="">Todos</html:option>
	 				<c:forEach items="${FicheroResiForm.listaResiCIP}" var="bean"> 
					 <option value='${bean}' ${FicheroResiForm.seleccionResiCIP == bean ? 'selected' : ' '}><c:out value="${bean}" ></c:out></option>   
	  				</c:forEach>                     
			</html:select>
			<a href="javascript:void(0);" onclick="ordenarPor('resiCIP asc')" class="arrow">&uarr;</a><a href="javascript:void(0);" onclick="ordenarPor('resiCIP desc')" class="arrow">&darr;</a>
	    </th>	
     </tr>
</thead>
<tbody>   

<!--  Inicio del bucle para mostrar las filas -->

<table class="blueTable" border="1" id="tablaDatos">
        <thead>
            <tr>
                <th>CnFinal</th>
                <th>Nombre en Bolsa</th>
                <th>Nombre Paciente</th>
                <th>CIP</th>
            </tr>
        </thead>
        <tbody>
           <!-- Si verDatos es true, el botón será 'Ver Datos Completos', si es false será 'Ver Datos Enmascarados' -->
    <input type="hidden" name="verDatos" value="${sessionScope.verDatos ? 'false' : 'true'}" />
    <input type="submit" value="${sessionScope.verDatos ? 'Ver Datos Enmascarados' : 'Ver Datos Completos'}" />
        
<logic:iterate id="data" name="FicheroResiForm" property="listaFicheroResiDetalleBean" type="lopicost.spd.struts.bean.FicheroResiBean" indexId="position">
        <div class="dataRow">
            <!-- Muestra el CIP completo (por defecto) o enmascarado -->
            <span class="resiCIP" style="display: inline;"><bean:write name="data" property="resiCIP" /></span>
            <span class="resiCIPMask" style="display: none;"><bean:write name="data" property="resiCIPMask" /></span>
            
            <!-- Mostrar otros campos (por ejemplo, nombre) -->
            <span><bean:write name="data" property="resiApellidosNombre" /></span>
        </div>
    </logic:iterate>

    <!-- Valor oculto que guarda el estado del botón (si se están mostrando los datos completos o no) -->
    <input type="hidden" id="verDatos" value="false" />
        </tbody>
    </table>
 </tbody>   